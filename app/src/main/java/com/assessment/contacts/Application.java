package com.assessment.contacts;

import android.content.Context;

/**
 * Application class.
 */
public class Application extends android.app.Application {

	private static Context sContext;

	@Override
	public void onCreate() {
		super.onCreate();
		sContext = this;
	}

	public static Context getInstance() {
		return sContext;
	}
}
