package com.mohitajwani.zolostays.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by Mohit Ajwani.
 * The contract used for the db to save the users locally.
 */

public final class UsersPersistenceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private UsersPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_EMAIL_ID = "emailId";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_USER_NAME = "userName";
        public static final String COLUMN_NAME_PHONE = "phone";
    }
}
