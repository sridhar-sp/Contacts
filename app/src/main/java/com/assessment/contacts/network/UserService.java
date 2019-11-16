package com.assessment.contacts.network;


import com.assessment.contacts.database.table.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

	@GET("users")
	Call<List<Contact>> getUsers();
}
