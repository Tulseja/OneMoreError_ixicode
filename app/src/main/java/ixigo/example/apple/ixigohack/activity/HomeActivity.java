package ixigo.example.apple.ixigohack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.fragment.HomeTrendingFragment;

/**
 * Created by apple on 08/04/17.
 */

public class HomeActivity extends BaseActivity {

    @BindView(R.id.home_viewpager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setToolbar(getResources().getString(R.string.app_name), false);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return HomeTrendingFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return getResources().getString(R.string.home_page_trending);
            } else {
                return getResources().getString(R.string.home_page_search);
            }
        }
    }
}
