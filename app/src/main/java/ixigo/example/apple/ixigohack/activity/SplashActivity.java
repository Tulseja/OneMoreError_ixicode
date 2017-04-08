package ixigo.example.apple.ixigohack.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.preferences.ZPreferences;

/**
 * Created by apple on 08/04/17.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.progressbar_splash)
    ProgressBar progressBar;

    int splashDuration = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                switchToHomeOrLoginActivity();
            }
        }, splashDuration);
        // ** hello firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("copyright");
        myRef.setValue("©2017 OneMoreError. All rights Reserved");



        myRef.setValue("Hello, World!");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private void switchToHomeOrLoginActivity() {
        ZPreferences.setIsUserLogin(this, true);
        if (ZPreferences.getIsUserLogin(this)) {
            openHomeActivity();
        } else {
            openLoginActivity();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        super.onDestroy();
    }
}
