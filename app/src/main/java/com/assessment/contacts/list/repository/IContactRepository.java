package com.assessment.contacts.list.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.assessment.contacts.database.table.model.ContactMinimal;

import java.util.List;

public interface IContactRepository {

	void loadAllContacts();

	@MainThread
	LiveData<List<ContactMinimal>> getAllContactsWithMinimalDetailsDesc();

	@MainThread
	LiveData<List<ContactMinimal>> getAllContactsWithMinimalDetailsAsc();

	@WorkerThread
	List<ContactMinimal> filterContactsBasedOnUserName(@NonNull String nameQuery);

	void setCallback(@Nullable Callback callback);

	@Nullable
	Callback getCallback();

	
	interface Callback {
		void onContactListFetched(boolean isSuccess);
	}
}
