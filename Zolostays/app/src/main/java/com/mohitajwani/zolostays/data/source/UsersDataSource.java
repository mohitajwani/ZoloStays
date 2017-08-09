package com.mohitajwani.zolostays.data.source;

import android.support.annotation.NonNull;

import com.mohitajwani.zolostays.data.User;

import java.util.List;

/**
 * Created by Mohit Ajwani.
 * Main entry point for accessing users data.
 */

public interface UsersDataSource {

    interface LoadUsersCallback {

        void allUsersLoaded(List<User> userList);

        void onDataNotAvailable();
    }

    interface GetUserCallback {

        void userSaved();

        void userFound(User user);

        void userNotFound();
    }

    void loadAllUsers(@NonNull LoadUsersCallback callback);

    void saveUser(@NonNull User user, @NonNull GetUserCallback callback);
}
