package com.assessment.contacts.list.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactListViewModel extends ViewModel {

	private MutableLiveData<List<ContactMinimal>> mContactList = new MutableLiveData<>();

	public LiveData<List<ContactMinimal>> getContactList() {
		return mContactList;
	}

	public void mockData() {
		List<ContactMinimal> contactList = new ArrayList<>();
		for (int i = 0; i < 100; i++)
			contactList.add(new ContactMinimal());

		mContactList.setValue(contactList);
	}
}
