package com.varma.samples.speeddemo;

import android.app.Application;

import com.firebase.client.Firebase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(getApplicationContext());
    }
}