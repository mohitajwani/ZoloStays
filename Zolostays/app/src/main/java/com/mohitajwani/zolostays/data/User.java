package com.mohitajwani.zolostays.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;

/**
 * Created by Mohit Ajwani.
 */

public final class User {

    @NonNull
    private final String mEmail;

    @NonNull
    private final String mPassword;

    @Nullable
    private final String mUsername;

    @Nullable
    private final String mPhone;

    /**
     * Use this constructor to create a new completed user.
     *
     * @param email       email of the user
     * @param password    password of the user
     */
    public User(@NonNull String email, @NonNull String password) {
        this(email, password, null, null);
    }

    /**
     * Use this constructor to specify a completed user if the user already has an userEmail (copy of
     * another user).
     *
     * @param email       email of the user
     * @param password    password of the user
     * @param userName    name of the user
     * @param phone       phone of the user
     */
    public User(@NonNull String email, @NonNull String password,
                @Nullable String userName, @Nullable String phone) {
        mEmail = email;
        mPassword = password;
        mUsername = userName;
        mPhone = phone;
    }

    @NonNull
    public String getEmail() {
        return mEmail;
    }

    @NonNull
    public String getPassword() {
        return mPassword;
    }

    @Nullable
    public String getUsername() {
        return mUsername;
    }

    @Nullable
    public String getPhone() {
        return mPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(mEmail, user.mEmail) &&
                Objects.equal(mPassword, user.mPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mEmail, mPassword);
    }

    @Override
    public String toString() {
        return "User with email " + mEmail;
    }
}
