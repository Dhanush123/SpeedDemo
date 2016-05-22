package com.varma.samples.speeddemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by chapatel on 5/22/16.
 */
public class LeaderboardActivity extends Activity {
    ArrayList<String> leaderboard = new ArrayList<>();
    private ListView lv;
    String total;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
        lv = (ListView) findViewById(R.id.listView);
        Firebase fbDB = new Firebase("https://quick-throw.firebaseIO.com");

        fbDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                    leaderboard = null;
                    // System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);
                        total = user.getName() + " - " + user.score;
                        leaderboard.add(total);

                        Log.i("New user to leaderboard ", total);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            leaderboard);

                    lv.setAdapter(arrayAdapter);
                }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }
}
