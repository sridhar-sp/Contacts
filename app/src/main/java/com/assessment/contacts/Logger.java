package com.assessment.contacts;

import android.util.Log;

public class Logger {

	private Logger() {
		//This is an utility class, object creation is forbidden.
	}

	public static void log(String tag, String log) {
		if (BuildConfig.DEBUG)
			Log.d(tag, log);
	}

	public static void log(String tag, Exception e) {
		if (BuildConfig.DEBUG) {
			Log.e(tag, e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
