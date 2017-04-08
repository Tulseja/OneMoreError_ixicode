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
}
