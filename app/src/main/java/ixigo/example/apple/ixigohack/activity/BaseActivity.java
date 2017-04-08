package ixigo.example.apple.ixigohack.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.ArrayList;

import butterknife.BindView;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.extras.AppConstants;


/**
 * Created by Ashish on 04/06/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Toast toast;
    Snackbar snackbar;

    LinearLayout progressLayout, errorLayout;
    ProgressBar progressBarLoading;
    Button retryButton;

    boolean isActivityResumed;

    public boolean isActivityResumed() {
        return isActivityResumed;
    }

    @Override
    protected void onPause() {
        isActivityResumed = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityResumed = true;
    }

    public void makeToast(String message) {
        if (!isFinishing()) {
            if (toast != null) {
                toast.cancel();
            }

            if (message != null && message.length() > 0) {
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void makeToastLong(String message) {
        if (!isFinishing()) {
            if (toast != null) {
                toast.cancel();
            }

            if (message != null && message.length() > 0) {
                toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public void showSnackbar(String message) {
        try {
            hideSnackbar();

//            if (message != null && findViewById(R.id.coordinator_layout) != null) {
//                snackbar = Snackbar.make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_SHORT);
//                snackbar.show();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideSnackbar() {
        try {
            if (snackbar != null && snackbar.isShown()) {
                snackbar.dismiss();
                snackbar = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Context getContext() {
        return BaseActivity.this;
    }

    public AppCompatActivity getActivity() {
        return BaseActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setProgressAndErrorLayoutVariables() {
        progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        retryButton = (Button) findViewById(R.id.button_error);
        progressBarLoading = (ProgressBar) findViewById(R.id.progress_bar_loading);
    }

    public void showProgressLayout() {
        try {
            progressLayout.setVisibility(View.VISIBLE);
            progressBarLoading.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void showErrorLayout() {
        try {
            errorLayout.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void hideProgressLayout() {
        try {
            progressLayout.setVisibility(View.GONE);
            progressBarLoading.setVisibility(View.GONE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void hideErrorLayout() {
        try {
            errorLayout.setVisibility(View.GONE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    void setToolbar(String title) {
        setToolbar(title, true);
    }

    void setToolbar(String title, boolean showBackButton) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
                getSupportActionBar().setDisplayShowHomeEnabled(showBackButton);
                getSupportActionBar().setTitle(title);
            }
        }
    }

    void setToolbarText(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    /*
    *
    * activity opening code
    *
    * */

    void openLoginActivity() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openPlannerActivity(int days, String placeId) {
        Intent intent = new Intent(this, PlannerActivity.class);
        intent.putExtra(AppConstants.INTENT_EXTRAS.EXTRA_PLACE_ID_PLANNER, placeId);
        intent.putExtra(AppConstants.INTENT_EXTRAS.EXTRA_DAYS_PLANNER, days);
        startActivity(intent);
    }
}