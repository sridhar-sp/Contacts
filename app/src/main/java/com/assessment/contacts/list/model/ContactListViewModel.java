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
import com.assessment.contacts.Logger;
import com.assessment.contacts.database.table.model.ContactMinimal;
import com.assessment.contacts.list.repository.ContactRepository;
import com.assessment.contacts.list.repository.IContactRepository;

import java.util.List;

public class ContactListViewModel extends ViewModel {

	private static final String TAG = ContactListViewModel.class.getSimpleName();

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
		if (isDataNetworkAvailable()) {
			Logger.log(TAG, "Data network is connected");
			mContactRepository.loadAllContacts();
		} else {
			Logger.log(TAG, "Data network is not connected");
			fetchDataFromDatabase();
		}
	}

	private void onContactListFetchedFromNetwork(boolean isSuccess) {
		fetchDataFromDatabase();
	}

	private void fetchDataFromDatabase() {
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
		if (isSortAsc)
			attachSortAscDataSource();
		else
			attachSortDescDataSource();
	}

	private void attachSortAscDataSource() {
		removeLastAttachedDataSource();

		mLastQueriedContactMinimalListLiveData = mContactRepository.getAllContactsWithMinimalDetailsAsc();
		mMediatorLiveData.addSource(mLastQueriedContactMinimalListLiveData,
				contactMinimals -> mMediatorLiveData.setValue(contactMinimals));
	}

	private void attachSortDescDataSource() {
		removeLastAttachedDataSource();

		mLastQueriedContactMinimalListLiveData = mContactRepository.getAllContactsWithMinimalDetailsDesc();
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
