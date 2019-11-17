package com.assessment.contacts.list.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.assessment.contacts.database.table.Contact;
import com.assessment.contacts.database.table.model.ContactMinimal;

import java.util.List;

public interface IContactRepository {

	/**
	 * Fetch all the contacts.
	 * <p>
	 * Fetch contacts from network and populate the local database, if network available, otherwise load data from
	 * local database.
	 */
	void loadAllContacts();

	/**
	 * Query and return the Minimal contact details based on sort preference given by {@code isAsc}.
	 *
	 * @param isAsc True if results are to be sorted in ascending order, false in descending order.
	 * @return The sorted list based on {@code isAsc}.
	 */
	@MainThread
	LiveData<List<ContactMinimal>> getAllContactsWithMinimalDetails(boolean isAsc);

	/**
	 * Query and return the search result.
	 *
	 * @param nameQuery The query to search against {@link Contact#getName()}.
	 * @return The search result for the {@code nameQuery} against {@link Contact#getName()}.
	 */
	@WorkerThread
	List<ContactMinimal> filterContactsBasedOnUserName(@NonNull String nameQuery);

	/**
	 * @param callback The callback instance to receive updates.
	 */
	void setCallback(@Nullable Callback callback);

	/**
	 * @return The registered callback instance.
	 */
	@Nullable
	Callback getCallback();

	/**
	 * Callback interface used to notify the view-model after some async operation.
	 */
	interface Callback {

		/**
		 * Callback method to receive as a result of network call to fetch contact list.
		 *
		 * @param isSuccess True if the data is fetched successfully from the network, false otherwise.
		 */
		void onContactListFetched(boolean isSuccess);
	}
}
