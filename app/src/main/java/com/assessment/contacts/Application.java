package com.assessment.contacts;

import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Application class.
 */
public class Application extends android.app.Application {

	private static Context sContext;

	@Override
	public void onCreate() {
		super.onCreate();
		sContext = this;

		if (BuildConfig.DEBUG)
			Stetho.initializeWithDefaults(this);
	}

	public static Context getInstance() {
		return sContext;
	}
}
