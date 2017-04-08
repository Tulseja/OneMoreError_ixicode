package ixigo.example.apple.ixigohack.activity;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.extras.AppConstants;

/**
 * Created by apple on 08/04/17.
 */

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.home_select_location_origin)
    void onOriginClick() {
        openLocationSelectionActivity(AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_ORIGIN);
    }

    @OnClick(R.id.home_select_location_destination)
    void onDestinationClick() {
        openLocationSelectionActivity(AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_DESTINATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_ORIGIN) {

        } else if (requestCode == AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_DESTINATION) {

        }
    }
}
