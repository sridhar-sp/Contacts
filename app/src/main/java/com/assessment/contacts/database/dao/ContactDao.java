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
	 * @return The {@link LiveData} holder for the {@link List<Contact>}.
	 */
	@MainThread
	@Query("SELECT * FROM contact")
	LiveData<List<Contact>> getAll();

	@MainThread
	@Query("SELECT id,name FROM contact ORDER BY name DESC")
	LiveData<List<ContactMinimal>> getAllContactsWithMinimalDetailsDesc();

	@MainThread
	@Query("SELECT id,name FROM contact ORDER BY name ASC")
	LiveData<List<ContactMinimal>> getAllContactsWithMinimalDetailsAsc();
}
