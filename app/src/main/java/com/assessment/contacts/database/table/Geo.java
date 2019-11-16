
package com.assessment.contacts.database.table;

import androidx.annotation.NonNull;

import com.assessment.contacts.BuildConfig;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 * One of the embedded field of {@link Address}.
 */
public class Geo {
	@SerializedName("lat")
	private String mLatitude;

	@SerializedName("lng")
	private String mLongitude;

	@NonNull
	@Override
	public String toString() {
		if (BuildConfig.DEBUG) {
			return String.format(Locale.getDefault(), "Lat %s\tLng %s", mLatitude, mLongitude);
		}
		return super.toString();
	}

	public String getLatitude() {
		return mLatitude;
	}

	public void setLatitude(String mLatitude) {
		this.mLatitude = mLatitude;
	}

	public String getLongitude() {
		return mLongitude;
	}

	public void setLongitude(String mLongitude) {
		this.mLongitude = mLongitude;
	}
}