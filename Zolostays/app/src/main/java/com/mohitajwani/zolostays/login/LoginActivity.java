package com.mohitajwani.zolostays.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mohitajwani.zolostays.R;
import com.mohitajwani.zolostays.UserViewModel;
import com.mohitajwani.zolostays.ViewModelHolder;
import com.mohitajwani.zolostays.util.ActivityUtils;

public class LoginActivity extends AppCompatActivity {

    public static final String USER_VIEWMODEL_TAG = "USER_VIEWMODEL_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    private UserViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<UserViewModel> retainedViewModel =
                (ViewModelHolder<UserViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(USER_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            UserViewModel viewModel = new UserViewModel(
                    getApplicationContext(),
                    Injection.provideTasksRepository(getApplicationContext()));
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    USER_VIEWMODEL_TAG);
            return viewModel;
        }
    }
}
