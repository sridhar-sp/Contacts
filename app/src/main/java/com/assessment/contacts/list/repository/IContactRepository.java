package com.assessment.contacts.list.repository;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.assessment.contacts.database.table.model.ContactMinimal;

import java.util.List;

public interface IContactRepository {

	void loadAllContacts();

	@MainThread
	LiveData<List<ContactMinimal>> getAllContactsWithMinimalDetailsDesc();

	@MainThread
	LiveData<List<ContactMinimal>> getAllContactsWithMinimalDetailsAsc();

	void setCallback(@Nullable Callback callback);

	@Nullable
	Callback getCallback();

	
	interface Callback {
		void onContactListFetched(boolean isSuccess);
	}
}
