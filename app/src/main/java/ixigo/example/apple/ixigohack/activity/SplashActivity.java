package ixigo.example.apple.ixigohack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.preferences.ZPreferences;
import ixigo.example.apple.ixigohack.utils.DebugUtils;

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
    }

    @Override
    public void onStart() {
        super.onStart();
        Branch branch = Branch.getInstance();

        branch.initSession(new Branch.BranchUniversalReferralInitListener() {
            @Override
            public void onInitFinished(BranchUniversalObject branchUniversalObject, LinkProperties linkProperties, BranchError error) {
                if (error == null) {
                    try {
                        JSONObject sessionParams = Branch.getInstance().getLatestReferringParams();
                        if (sessionParams != null && sessionParams.has(AppConstants.BRANCH.BRANCH_ANDROID_ID)) {
                            int days = Integer.parseInt(sessionParams.getString(AppConstants.BRANCH.BRANCH_DAYS));
                            String placeId = sessionParams.getString(AppConstants.BRANCH.BRANCH_PLACE_ID);
                            String placeName = sessionParams.getString(AppConstants.BRANCH.BRANCH_PLACE_NAME);
                            String androidId = sessionParams.getString(AppConstants.BRANCH.BRANCH_ANDROID_ID);
                            openPlannerActivity(days, placeId, placeName, androidId);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    DebugUtils.log(error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
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
