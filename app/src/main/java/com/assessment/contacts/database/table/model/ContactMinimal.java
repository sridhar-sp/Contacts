package com.assessment.contacts.database.table.model;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

/**
 * Implementation for {@link IContactItem}, It's a miniature version of
 * {@link com.assessment.contacts.database.table.Contact}.
 * Miniature version used, because the Contact List Screen has very small amount of data to be shown, and using the
 * {@link com.assessment.contacts.database.table.Contact} for that purpose would consume too much of memory.
 */
public class ContactMinimal implements IContactItem {

	private static final String EMPTY_STRING = "";

	@ColumnInfo(name = "id")
	private int mId;

	@ColumnInfo(name = "name")
	private String mName;

	@ColorInt
	@Ignore
	private int mInitialBackgroundColor = -1;

	@NonNull
	@Override
	public String getText() {
		String name = getName();
		return null == name ? EMPTY_STRING : name;
	}

	@NonNull
	@Override
	public String getInitial() {
		String text = getText();
		return text.isEmpty() ? EMPTY_STRING : String.valueOf(text.charAt(0));
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public String getName() {
		return mName;
	}

	public int getId() {
		return mId;
	}

	@Override
	public void setInitialBackgroundColor(int color) {
		this.mInitialBackgroundColor = color;
	}

	@Override
	public int getInitialBackgroundColor() {
		return mInitialBackgroundColor;
	}
}
