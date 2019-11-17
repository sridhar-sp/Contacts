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
import com.assessment.contacts.database.table.Contact;
import com.assessment.contacts.database.table.model.ContactMinimal;
import com.assessment.contacts.list.repository.ContactRepository;
import com.assessment.contacts.list.repository.IContactRepository;

import java.util.List;

/**
 * The class is constructed in such a way that it expose a single {@link MediatorLiveData} to the view, and takes care
 * of delegating the data from various data source, based on the application state (i.e sort, filter and etc).
 * <p>
 *
 * @see IContactRepository
 */
public class ContactListViewModel extends ViewModel {

	private LiveData<List<ContactMinimal>> mLastQueriedContactMinimalListLiveData;

	/**
	 * This is a single end-point to the view. where various data sources will get attached with this at various
	 * lifecycle based on various input such as sort, filter(search) etc.
	 * <p>
	 * Even though various data sources will get attached and detached with LiveData, View will only aware of this
	 * component and nothing else, which give us a nice abstraction to delegate data from various source to the view.
	 */
	private MediatorLiveData<List<ContactMinimal>> mMediatorLiveData;

	private IContactRepository mContactRepository;

	/**
	 * Last selected sort preference.
	 */
	private boolean isSortAsc = true;

	/**
	 * Search-mode status.
	 */
	private boolean isSearchModeActive = false;

	public ContactListViewModel() {
		mContactRepository = ContactRepository.newInstance(this::onContactListFetchedFromNetwork);

		mMediatorLiveData = new MediatorLiveData<>();
	}

	/**
	 * @return {@link #mMediatorLiveData} which is attached to some source based on the application state (i.e sort,
	 * filter).
	 */
	public LiveData<List<ContactMinimal>> getContactMinimalList() {
		return mMediatorLiveData;
	}

	public void loadAllContacts() {
		if (isDataNetworkAvailable())
			mContactRepository.loadAllContacts();
		else
			fetchContactBasedOnSortPreference();
	}

	/**
	 * Callback for the network operation to fetch the contact list.
	 *
	 * @param isSuccess True if the contact list is successfully fetched from the network., false otherwise.
	 */
	private void onContactListFetchedFromNetwork(boolean isSuccess) {
		if (isSearchModeActive)
			return;//Search in progress, hence should not update the values.
		fetchContactBasedOnSortPreference();
	}

	/**
	 * Perform necessary operation upon entering search mode.
	 */
	public void enterSearchMode() {
		isSearchModeActive = true;
		removeLastAttachedDataSource();
	}

	/**
	 * Search the database with the {@code query} against {@link Contact#getName()}. the results will be delivered to
	 * the view via {@link #mMediatorLiveData} asynchronously.
	 *
	 * @param query The query to search against {@link Contact#getName()}.
	 */
	public void searchContactAsync(@NonNull String query) {
		AppExecutorService.getInstance().getBackgroundThreadExecutor().execute(() ->
				mMediatorLiveData.postValue(mContactRepository.filterContactsBasedOnUserName(query)));
	}

	/**
	 * Perform necessary operation upon exiting search mode.
	 */
	public void exitSearchMode() {
		isSearchModeActive = false;
		fetchContactBasedOnSortPreference();
	}

	/**
	 * Toggle the current sort order.
	 */
	public void toggleSort() {
		isSortAsc = !isSortAsc;
		fetchContactBasedOnSortPreference();
	}

	/**
	 * Attach and fetch the data based on sort preference {@code #isSortAsc}.
	 */
	private void fetchContactBasedOnSortPreference() {
		removeLastAttachedDataSource();

		mLastQueriedContactMinimalListLiveData = mContactRepository.getAllContactsWithMinimalDetails(isSortAsc);
		mMediatorLiveData.addSource(mLastQueriedContactMinimalListLiveData,
				contactMinimals -> mMediatorLiveData.setValue(contactMinimals));
	}

	/**
	 * {@link #mMediatorLiveData} should always connect to single data source, hence remove any attached data-source
	 * before adding any new data source to it.
	 */
	private void removeLastAttachedDataSource() {
		if (null != mLastQueriedContactMinimalListLiveData)
			mMediatorLiveData.removeSource(mLastQueriedContactMinimalListLiveData);
	}

	/**
	 * @return True if Data network is connected, false otherwise.
	 */
	private boolean isDataNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager)
				Application.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return null != activeNetworkInfo && activeNetworkInfo.isConnected();
	}
}
