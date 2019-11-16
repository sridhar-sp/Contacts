package com.assessment.contacts.database.table;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.assessment.contacts.BuildConfig;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 * The class which holds the contact information of the individual.
 * This class is constructed in such a way that it can be used for serialisation , and de-serialisation from Retrofit,
 * and considered as a table from RoomDatabase perspective respectively.
 */
@Entity(tableName = "contact", indices = @Index("name"))
public class Contact {

	@SerializedName("id")
	@ColumnInfo(name = "id")
	@PrimaryKey
	private int mId;

	@SerializedName("name")
	@ColumnInfo(name = "name")
	private String mName;

	@SerializedName("username")
	@ColumnInfo(name = "user_name")
	private String mUserName;

	@SerializedName("email")
	@ColumnInfo(name = "email")
	private String mEmail;

	@SerializedName("address")
	@Embedded
	private Address mAddress;

	@SerializedName("phone")
	@ColumnInfo(name = "phone")
	private String mPhone;

	@SerializedName("website")
	@ColumnInfo(name = "website")
	private String mWebsite;

	@SerializedName("company")
	@Embedded
	private Company mCompany;


	@NonNull
	@Override
	public String toString() {
		if (BuildConfig.DEBUG) {
			return String.format(Locale.getDefault(),
					"Id %d\tName %s\tUserName %s\tEmail %s\tAddress %s\tPhone %s\tWebsite %s\tCompnay %s",
					mId, mName, mUserName, mEmail, mAddress, mPhone, mWebsite, mCompany);
		}
		return super.toString();
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public void setUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public void setAddress(Address mAddress) {
		this.mAddress = mAddress;
	}

	public void setPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public void setWebsite(String mWebsite) {
		this.mWebsite = mWebsite;
	}

	public void setCompany(Company mCompany) {
		this.mCompany = mCompany;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public String getUserName() {
		return mUserName;
	}

	public String getEmail() {
		return mEmail;
	}

	public Address getAddress() {
		return mAddress;
	}

	public String getPhone() {
		return mPhone;
	}

	public String getWebsite() {
		return mWebsite;
	}

	public Company getCompany() {
		return mCompany;
	}
}
