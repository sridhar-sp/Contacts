package com.assessment.contacts.list.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.assessment.contacts.AppExecutorService;
import com.assessment.contacts.database.ContactDatabase;
import com.assessment.contacts.database.table.Contact;
import com.assessment.contacts.database.table.model.ContactMinimal;
import com.assessment.contacts.network.RetrofitClient;
import com.assessment.contacts.network.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ContactRepository implements IContactRepository {

	private Callback mRepositoryCallback;

	private UserService mUserService;

	private ContactListCallback mContactListCallback = new ContactListCallback();

	public static IContactRepository newInstance(@NonNull Callback callback) {
		ContactRepository contactRepository = new ContactRepository();
		contactRepository.setCallback(callback);
		return contactRepository;
	}

	private ContactRepository() {
		//Use static factory {@link newInstance} method to create objects.
	}

	@Override
	public void setCallback(@Nullable Callback callback) {
		mRepositoryCallback = callback;
	}

	@Nullable
	@Override
	public Callback getCallback() {
		return mRepositoryCallback;
	}

	@Override
	public void loadAllContacts() {
		if (null == mUserService)
			mUserService = RetrofitClient.getInstance().getRetrofit().create(UserService.class);

		mUserService.getUsers().enqueue(mContactListCallback);
	}

	@Override
	public LiveData<List<ContactMinimal>> getAllContactsWithMinimalDetailsDesc() {
		return ContactDatabase.getDatabase().getContactDao().getAllContactsWithMinimalDetailsDesc();
	}

	@Override
	public LiveData<List<ContactMinimal>> getAllContactsWithMinimalDetailsAsc() {
		return ContactDatabase.getDatabase().getContactDao().getAllContactsWithMinimalDetailsAsc();
	}

	private class ContactListCallback implements retrofit2.Callback<List<Contact>> {

		@Override
		public void onResponse(@NonNull Call<List<Contact>> call, Response<List<Contact>> response) {

			List<Contact> contactList = response.body();

			if (response.isSuccessful()) {
				if (null == contactList)
					contactList = new ArrayList<>(0);

				insertContactListAsync(contactList);
			}

			notifyContactListFetched(response.isSuccessful());
		}

		@Override
		public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
			notifyContactListFetched(false);
		}

		private void notifyContactListFetched(boolean isSuccessful) {
			Callback callback = getCallback();
			if (null != callback)
				callback.onContactListFetched(isSuccessful);
		}

		private void insertContactListAsync(@NonNull List<Contact> contacts) {
			AppExecutorService.getInstance().getBackgroundThreadExecutor()
					.execute(() -> ContactDatabase.getDatabase().getContactDao().insert(contacts));
		}
	}

}
