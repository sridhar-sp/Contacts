package com.assessment.contacts.sticky.header;

import androidx.annotation.NonNull;

/**
 * Model class used for showing the group sticky index.
 *
 * @see IndexAdapter
 */
public interface IIndexItem {

	/**
	 * @return The single char string with the index character representing a group.
	 */
	@NonNull
	String getInitial();
}
