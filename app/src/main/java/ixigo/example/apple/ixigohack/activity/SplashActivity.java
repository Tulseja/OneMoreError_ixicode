package ixigo.example.apple.ixigohack.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

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
        setContentView(R.layout.splash_activity_layout);
        ButterKnife.bind(this);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                switchToHomeOrLoginActivity();
            }
        }, splashDuration);
    }

    private void switchToHomeOrLoginActivity() {
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
