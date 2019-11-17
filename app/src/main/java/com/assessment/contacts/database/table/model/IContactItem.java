package com.assessment.contacts.database.table.model;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.assessment.contacts.sticky.header.IIndexItem;

/**
 * Class represents the item displayed by the Contact List screen.
 */
public interface IContactItem extends IIndexItem {

	@NonNull
	String getText();

	@ColorInt
	int getInitialBackgroundColor();

	void setInitialBackgroundColor(@ColorInt int color);

}
