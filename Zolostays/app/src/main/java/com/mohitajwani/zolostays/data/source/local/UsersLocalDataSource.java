package com.mohitajwani.zolostays.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.mohitajwani.zolostays.data.User;
import com.mohitajwani.zolostays.data.source.UsersDataSource;
import com.mohitajwani.zolostays.data.source.local.UsersPersistenceContract.UserEntry;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Mohit Ajwani.
 */

public class UsersLocalDataSource implements UsersDataSource {

    private static UsersLocalDataSource INSTANCE;

    private UsersDbHelper mDbHelper;

    // Prevent direct instantiation.
    private UsersLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new UsersDbHelper(context);
    }

    public static UsersLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UsersLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void loadAllUsers(@NonNull LoadUsersCallback callback) {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                UserEntry.COLUMN_NAME_EMAIL_ID,
                UserEntry.COLUMN_NAME_PASSWORD,
                UserEntry.COLUMN_NAME_USER_NAME,
                UserEntry.COLUMN_NAME_PHONE
        };

        Cursor c = db.query(
                UserEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String email = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_EMAIL_ID));
                String password = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_PASSWORD));
                String name = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_USER_NAME));
                String phone = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_PHONE));
                User user = new User(email, password, name, phone);
                users.add(user);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (!users.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.allUsersLoaded(users);
        }
    }

    @Override
    public void createUser(@NonNull User user, @NonNull GetUserCallback callback) {
        checkNotNull(user);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_EMAIL_ID, user.getEmail());
        values.put(UserEntry.COLUMN_NAME_PASSWORD, user.getPassword());
        values.put(UserEntry.COLUMN_NAME_USER_NAME, user.getUsername());
        values.put(UserEntry.COLUMN_NAME_PHONE, user.getPhone());

        db.insert(UserEntry.TABLE_NAME, null, values);

        db.close();
        callback.userSaved();
    }
}
