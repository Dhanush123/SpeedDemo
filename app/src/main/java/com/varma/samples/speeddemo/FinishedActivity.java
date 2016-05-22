package com.varma.samples.speeddemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chapatel on 5/22/16.
 */
public class FinishedActivity extends Activity {
    double maxSpeed =0;
    Firebase fbDB;
    Firebase leaders;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finished);
        maxSpeed = getIntent().getDoubleExtra("maxspeed",0.0);
        setRandName();

        savePrefs(maxSpeed);
        ((TextView)findViewById(R.id.currentResult)).append(" "+maxSpeed+" mph");

        fbDB = new Firebase("https://quick-throw.firebaseIO.com");
    }

    public void retryGame(View v){
        startActivity(new Intent(this,MainActivity.class));
    }

    public void showLeaderboard(View v){
        startActivity(new Intent(this,LeaderboardActivity.class));
    }

    private void savePrefs(double s){
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        float bestGet = mSharedPreference.getFloat("personalbestspeed",0);
        if( bestGet==0 && bestGet<s) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat("personalbestspeed", (float) s);
            editor.commit();

            User thisUser = new User(s);
            leaders= fbDB.child("users").child(thisUser.getName());
            leaders.setValue(thisUser);

        }
        else if(mSharedPreference.getFloat("personalbestspeed",0)<s){
            leaders = fbDB.child("users");
            Map<String, Object> user = new HashMap<String, Object>();
            user.put(mSharedPreference.getString("username","Anonymous"),s);
            leaders.updateChildren(user);
        }

    }

    private void setRandName(){
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String nameGet = mSharedPreference.getString("username","Anonymous");
        if(nameGet.equals("Anonymous")) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            String username = "User" + ((Math.random() * 1000000) + 1);
            Toast.makeText(this, "Your username is: " + username, Toast.LENGTH_LONG).show();
            editor.putString("username", username);
            editor.commit();
        }
    }




}
