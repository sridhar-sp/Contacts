package com.assessment.contacts.list.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.assessment.contacts.AppExecutorService;
import com.assessment.contacts.Application;
import com.assessment.contacts.database.table.model.ContactMinimal;
import com.assessment.contacts.list.repository.ContactRepository;
import com.assessment.contacts.list.repository.IContactRepository;

import java.util.List;

public class ContactListViewModel extends ViewModel {

	private LiveData<List<ContactMinimal>> mLastQueriedContactMinimalListLiveData;

	private MediatorLiveData<List<ContactMinimal>> mMediatorLiveData;

	private IContactRepository mContactRepository;

	private boolean isSortAsc = true;

	public ContactListViewModel() {
		mContactRepository = ContactRepository.newInstance(this::onContactListFetchedFromNetwork);

		mMediatorLiveData = new MediatorLiveData<>();
	}

	public LiveData<List<ContactMinimal>> getContactMinimalList() {
		return mMediatorLiveData;
	}

	public void loadAllContacts() {
		if (isDataNetworkAvailable())
			mContactRepository.loadAllContacts();
		else
			fetchContactBasedOnSortPreference();
	}

	private void onContactListFetchedFromNetwork(boolean isSuccess) {
		fetchContactBasedOnSortPreference();
	}

	public void enterSearchMode() {
		removeLastAttachedDataSource();
	}

	public void searchContactAsync(@NonNull String query) {
		AppExecutorService.getInstance().getBackgroundThreadExecutor().execute(() ->
				mMediatorLiveData.postValue(mContactRepository.filterContactsBasedOnUserName(query)));
	}

	public void exitSearchMode() {
		fetchContactBasedOnSortPreference();
	}

	public void toggleSort() {
		isSortAsc = !isSortAsc;
		fetchContactBasedOnSortPreference();
	}

	private void fetchContactBasedOnSortPreference() {
		removeLastAttachedDataSource();

		mLastQueriedContactMinimalListLiveData = mContactRepository.getAllContactsWithMinimalDetails(isSortAsc);
		mMediatorLiveData.addSource(mLastQueriedContactMinimalListLiveData,
				contactMinimals -> mMediatorLiveData.setValue(contactMinimals));
	}

	private void removeLastAttachedDataSource() {
		if (null != mLastQueriedContactMinimalListLiveData)
			mMediatorLiveData.removeSource(mLastQueriedContactMinimalListLiveData);
	}

	private boolean isDataNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager)
				Application.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return null != activeNetworkInfo && activeNetworkInfo.isConnected();
	}
}
