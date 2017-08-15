package com.mohitajwani.zolostays;

import android.app.Application;

import com.mohitajwani.zolostays.data.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohit Ajwani.
 * Application class
 */

public class App extends Application {

	//Array List to handle copy of users w/o querying db multiple times
    private List<User> userList;

    @Override
    public void onCreate() {
        super.onCreate();
        userList = new ArrayList<>();
    }
}
