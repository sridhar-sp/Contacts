package com.assessment.contacts.list.model;

/**
 * Implementation for {@link IContactItem}, It's a miniature version of
 * {@link com.assessment.contacts.database.table.Contact}.
 * Miniature version used, because the Contact List Screen has very small amount of data to be shown, and using the
 * {@link com.assessment.contacts.database.table.Contact} for that purpose would consume too much of memory.
 */
public class ContactMinimal implements IContactItem {
}
