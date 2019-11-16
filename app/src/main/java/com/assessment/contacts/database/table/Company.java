package com.assessment.contacts.database.table;

import androidx.annotation.NonNull;

import com.assessment.contacts.BuildConfig;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 * One of the embedded field of {@link Contact}.
 */
public class Company {

	@SerializedName("name")
	private String mName;

	@SerializedName("catchPhrase")
	private String mCatchPhrase;

	@SerializedName("bs")
	private String mBs;

	@NonNull
	@Override
	public String toString() {
		if (BuildConfig.DEBUG) {
			return String.format(Locale.getDefault(), "Name %s\tCatchPhrase %s\tBs %s", mName, mCatchPhrase, mBs);
		}
		return super.toString();
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getCatchPhrase() {
		return mCatchPhrase;
	}

	public void setCatchPhrase(String mCatchPhrase) {
		this.mCatchPhrase = mCatchPhrase;
	}

	public String getBs() {
		return mBs;
	}

	public void setBs(String mBs) {
		this.mBs = mBs;
	}
}
