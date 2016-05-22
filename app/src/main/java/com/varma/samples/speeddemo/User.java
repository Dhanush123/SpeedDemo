package com.varma.samples.speeddemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class User extends Activity{
    String name;
    double score;
    public User(double s){
        name=getName();
        score=s;
    }
    public String getName(){
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return mSharedPreference.getString("username","Anonymous");
    }
}