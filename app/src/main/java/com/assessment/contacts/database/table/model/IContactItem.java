package com.assessment.contacts.database.table.model;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/**
 * Class represents the item displayed by the Contact List screen.
 */
public interface IContactItem {

	@NonNull
	String getText();

	@NonNull
	String getInitial();

	@ColorInt
	int getInitialBackgroundColor();

	void setInitialBackgroundColor(@ColorInt int color);
}
