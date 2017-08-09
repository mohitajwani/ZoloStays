package com.mohitajwani.zolostays;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.mohitajwani.zolostays.data.User;
import com.mohitajwani.zolostays.data.source.UsersDataSource;
import com.mohitajwani.zolostays.data.source.UsersRepository;

import java.util.List;

/**
 * Created by Mohit Ajwani.
 * Abstract class for View Models that expose a single {@link User}.
 */

public class UserViewModel extends BaseObservable implements UsersDataSource.LoadUsersCallback, UsersDataSource.GetUserCallback{

    public final ObservableField<String> snackbarText = new ObservableField<>();

    public final ObservableField<String> email = new ObservableField<>();

    public final ObservableField<String> password = new ObservableField<>();

    private final ObservableField<User> mTaskObservable = new ObservableField<>();

    private final UsersRepository mUsersRepository;

    private final Context mContext;

    private boolean mIsDataLoading;

    public UserViewModel(Context context, UsersRepository usersRepository) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mUsersRepository = usersRepository;

        // Exposed observables depend on the mTaskObservable observable:
        mTaskObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                User user = mTaskObservable.get();
                if (user != null) {
                    email.set(user.getEmail());
                    password.set(user.getPassword());
                } else {
                    email.set("");
                    password.set("");
                }
            }
        });
    }


    @Override
    public void userSaved() {
        snackbarText.set(mContext.getString(R.string.welcome));

    }

    @Override
    public void userFound(User user) {
        snackbarText.set(mContext.getString(R.string.welcome) + user.getUsername());
    }

    @Override
    public void userNotFound() {
        snackbarText.set(mContext.getString(R.string.user_not_found));
    }

    @Override
    public void allUsersLoaded(List<User> userList) {

    }

    @Override
    public void onDataNotAvailable() {

    }
}
