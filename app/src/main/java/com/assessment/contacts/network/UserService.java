package com.assessment.contacts.network;


import com.assessment.contacts.database.table.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * User related end points declaration are placed here.
 */
public interface UserService {

	/**
	 * @return The Call instance to fetch Contact details from {@code "users"} end point.
	 */
	@GET("users")
	Call<List<Contact>> getUsers();
}
