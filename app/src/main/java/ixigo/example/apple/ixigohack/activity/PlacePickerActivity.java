package ixigo.example.apple.ixigohack.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;

/**
 * Created by apple on 08/04/17.
 */

public class PlacePickerActivity extends BaseActivity {

    PlacePickerResponse placePickerResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);
        ButterKnife.bind(this);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Places to Visit";
            } else if (position == 1) {
                return "Things to do";
            } else {
                return "NA";
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
