package ixigo.example.apple.ixigohack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.preferences.ZPreferences;
import ixigo.example.apple.ixigohack.utils.DebugUtils;


public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initialiseGoogleLogin();
    }

    private void initialiseGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @OnClick(R.id.login_google)
    void callGoogleLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_GOOGLE_LOGIN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        DebugUtils.log("onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_GOOGLE_LOGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            ZPreferences.setIsUserLogin(this, true);
            openHomeActivity();
            finish();
        } else {
            // Signed out, show unauthenticated UI.
        }
    }
}

