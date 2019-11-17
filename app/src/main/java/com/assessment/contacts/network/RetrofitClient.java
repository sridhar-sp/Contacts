package com.assessment.contacts.network;

import com.assessment.contacts.Application;
import com.assessment.contacts.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

	private static final String BASE_URL = Application.getInstance().getString(R.string.base_url);

	private static RetrofitClient sInstance;

	private Retrofit retrofit;

	public static RetrofitClient getInstance() {
		if (null == sInstance) {
			sInstance = new RetrofitClient();
		}
		return sInstance;
	}

	private RetrofitClient() {
		//Singleton class, object creation from outside the class using the constructor is forbidden.
		// use {@link #getInstance}.

		retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

	}

	public Retrofit getRetrofit() {
		return retrofit;
	}
}
