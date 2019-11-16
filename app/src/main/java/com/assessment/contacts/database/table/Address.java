package com.assessment.contacts.database.table;

import androidx.annotation.NonNull;
import androidx.room.Embedded;

import com.assessment.contacts.BuildConfig;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 * One of the embedded field of {@link Contact}.
 */
public class Address {

	@SerializedName("street")
	private String mStreet;

	@SerializedName("suite")
	private String mSuite;

	@SerializedName("city")
	private String mCity;

	@SerializedName("zipcode")
	private String mZipcode;

	@SerializedName("geo")
	@Embedded
	private Geo mGeo;

	@NonNull
	@Override
	public String toString() {
		if (BuildConfig.DEBUG) {
			return String.format(Locale.getDefault(),
					"Street %s\tSuite %s\tCity %s\tZipcode %s\tGeo %s",
					mStreet, mSuite, mCity, mZipcode, mGeo);
		}
		return super.toString();
	}

	public String getStreet() {
		return mStreet;
	}

	public void setStreet(String mStreet) {
		this.mStreet = mStreet;
	}

	public String getSuite() {
		return mSuite;
	}

	public void setSuite(String mSuite) {
		this.mSuite = mSuite;
	}

	public String getCity() {
		return mCity;
	}

	public void setCity(String mCity) {
		this.mCity = mCity;
	}

	public String getZipcode() {
		return mZipcode;
	}

	public void setZipcode(String mZipcode) {
		this.mZipcode = mZipcode;
	}

	public Geo getGeo() {
		return mGeo;
	}

	public void setGeo(Geo mGeo) {
		this.mGeo = mGeo;
	}
}
