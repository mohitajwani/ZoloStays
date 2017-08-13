package com.mohitajwani.zolostays.data.source;

import android.support.annotation.NonNull;

import com.mohitajwani.zolostays.data.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Mohit Ajwani.
 * Main entry point for accessing users data.
 */

public class UsersRepository implements UsersDataSource {

    private static UsersRepository INSTANCE = null;

    private final UsersDataSource mUsersDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    private Map<String, User> mCachedUsers;

    // Prevent direct instantiation.
    private UsersRepository(@NonNull UsersDataSource usersDataSource, @NonNull LoadUsersCallback callback) {
        mUsersDataSource = checkNotNull(usersDataSource);
        mUsersDataSource.loadAllUsers(callback);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param usersDataSource  the device storage data source
     * @return the {@link UsersRepository} instance
     */
    public static UsersRepository getInstance(UsersDataSource usersDataSource, LoadUsersCallback callback) {
        if (INSTANCE == null) {
            INSTANCE = new UsersRepository(usersDataSource, callback);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(UsersDataSource,LoadUsersCallback)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    public void getUser(@NonNull User user, @NonNull GetUserCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedUsers != null) {
            if (mCachedUsers.containsKey(user.getEmail())) {
                User storedUser = mCachedUsers.get(user.getEmail());
                callback.userFound(storedUser);
            } else {
                callback.userNotFound();
            }
        } else {
            callback.userNotFound();
        }
    }

    @Override
    public void loadAllUsers(@NonNull LoadUsersCallback callback) {
        checkNotNull(callback);
        mUsersDataSource.loadAllUsers(callback);
    }

    @Override
    public void createUser(@NonNull User user, @NonNull GetUserCallback callback) {
        if (mCachedUsers == null) {
            mCachedUsers = new LinkedHashMap<>();
        } else {
            mCachedUsers.put(user.getEmail(), user);
        }
    }

    private void refreshCache(List<User> users) {
        if (mCachedUsers == null) {
            mCachedUsers = new LinkedHashMap<>();
        }
        mCachedUsers.clear();
        for (User user : users) {
            mCachedUsers.put(user.getEmail(), user);
        }
    }
}
