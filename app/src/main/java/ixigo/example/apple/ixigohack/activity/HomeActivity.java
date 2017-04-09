package ixigo.example.apple.ixigohack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.branch.referral.Branch;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.eventBus.EventBusHelper;
import ixigo.example.apple.ixigohack.eventBus.HomeEventBus;
import ixigo.example.apple.ixigohack.eventBus.PlacePickerEventBus;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.fragment.HomeSearchFragment;
import ixigo.example.apple.ixigohack.fragment.HomeTrendingFragment;
import ixigo.example.apple.ixigohack.fragment.TimePickerFragment;

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
    protected void onDestroy() {
        EventBusHelper.unRegister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(HomeEventBus.TrendingItemClick obj) {
        viewPager.setCurrentItem(1, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        EventBusHelper.register(this);

        setToolbar(getResources().getString(R.string.app_name), false);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        checkForDeepLink();
    }

    private void checkForDeepLink() {
        if (isActivityResumed) {
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
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return HomeTrendingFragment.newInstance();
            } else {
                return HomeSearchFragment.newInstance();
            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
