package com.mohitajwani.zolostays.login;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;

import com.mohitajwani.zolostays.data.User;
import com.mohitajwani.zolostays.data.source.UsersDataSource;
import com.mohitajwani.zolostays.data.source.UsersRepository;
import com.mohitajwani.zolostays.util.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohit Ajwani.
 * Exposes the data to be used in the login screen.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */

public class LoginViewModel extends BaseObservable {

    // These observable fields will update Views automatically
    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    public final ObservableField<String> currentFilteringLabel = new ObservableField<>();

    public final ObservableField<String> noTasksLabel = new ObservableField<>();

    public final ObservableField<Drawable> noTaskIconRes = new ObservableField<>();

    public final ObservableBoolean tasksAddViewVisible = new ObservableBoolean();

    final ObservableField<String> snackbarText = new ObservableField<>();

    private final UsersRepository mUsersRepository;

    private final ObservableBoolean mIsDataLoadingError = new ObservableBoolean(false);

    private Context mContext; // To avoid leaks, this must be an Application Context.

    private LoginNavigator mNavigator;

    public LoginViewModel(
            UsersRepository repository,
            Context context) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mUsersRepository = repository;
    }

    void setNavigator(LoginNavigator navigator) {
        mNavigator = navigator;
    }

    void onActivityDestroyed() {
        // Clear references to avoid potential memory leaks.
        mNavigator = null;
    }

    public void start() {
        loadUsers(false);
    }

    public String getSnackbarText() {
        return snackbarText.get();
    }

    /**
     * Called by the Data Binding library and the FAB's click listener.
     */
    public void createUser() {
        if (mNavigator != null) {
            mNavigator.onLoginClicked();
        }
    }

    /**
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadUsers(final boolean showLoadingUI) {
        if (showLoadingUI) {
            dataLoading.set(true);
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        mUsersRepository.loadAllUsers(new UsersDataSource.LoadUsersCallback() {
            @Override
            public void allUsersLoaded(List<User> userList) {
                List<User> users = new ArrayList<>();

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }
                if (showLoadingUI) {
                    dataLoading.set(false);
                }
                mIsDataLoadingError.set(false);
            }

            @Override
            public void onDataNotAvailable() {
                mIsDataLoadingError.set(true);
            }
        });
    }

}
