package com.assessment.contacts.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.assessment.contacts.Application;
import com.assessment.contacts.database.dao.ContactDao;
import com.assessment.contacts.database.table.Contact;

@Database(entities = Contact.class, version = 1, exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {

	//Database name.
	private static final String DATABASE_NAME = "contact_database";

	//Singleton instance.
	private static ContactDatabase sInstance;

	public static ContactDatabase getDatabase() {
		if (null == sInstance) {
			synchronized (ContactDatabase.class) {
				sInstance = Room.databaseBuilder(Application.getInstance(),
						ContactDatabase.class, DATABASE_NAME)
						.fallbackToDestructiveMigration()
						.build();
			}
		}
		return sInstance;
	}

	public abstract ContactDao getContactDao();

}
