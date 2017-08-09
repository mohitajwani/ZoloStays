package com.mohitajwani.zolostays.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mohit Ajwani.
 */

public class UsersDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Users.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UsersPersistenceContract.UserEntry.TABLE_NAME + " (" +
                    UsersPersistenceContract.UserEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_EMAIL_ID + TEXT_TYPE + COMMA_SEP +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_USER_NAME + TEXT_TYPE +
                    " )";

    public UsersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
