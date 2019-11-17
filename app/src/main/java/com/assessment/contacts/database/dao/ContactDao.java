package com.assessment.contacts.database.dao;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.assessment.contacts.database.table.Contact;
import com.assessment.contacts.database.table.model.ContactMinimal;

import java.util.List;

@Dao
public interface ContactDao {

	/**
	 * Insert the list of contacts, and replace when same primary id detected.
	 *
	 * @param contactList The list of contact to be inserted.
	 */
	@WorkerThread
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(List<Contact> contactList);

	/**
	 * Query and return the Minimal contact details based on sort preference given by {@code isAsc}.
	 *
	 * @param isAsc True if results are to be sorted in ascending order, false if descending order.
	 * @return The sorted list based on {@code isAsc}.
	 */
	@MainThread
	@Query("SELECT id,name FROM contact " +
			"ORDER BY CASE WHEN :isAsc = 1 THEN name END  ASC, CASE WHEN :isAsc = 0 THEN name END DESC")
	LiveData<List<ContactMinimal>> getAllContactsWithMinimalDetails(boolean isAsc);

	/**
	 * Query and return the search result.
	 *
	 * @param nameQuery The query to search against {@link Contact#getName()}.
	 * @return The search result for the {@code nameQuery} against {@link Contact#getName()}.
	 */
	@WorkerThread
	@Query("SELECT id,name FROM contact WHERE name LIKE :nameQuery")
	List<ContactMinimal> filterContactsBasedOnUserName(String nameQuery);
}
