package ixigo.example.apple.ixigohack.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.eventBus.EventBusHelper;
import ixigo.example.apple.ixigohack.eventBus.PlacePickerEventBus;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.fragment.PlannerFragment;

/**
 * Created by apple on 08/04/17.
 */

public class PlannerActivity extends BaseActivity {

    @BindView(R.id.planner_viewpager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.fab_planner)
    FloatingActionButton floatingActionButton;

    String placeId;
    int days;
    MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        ButterKnife.bind(this);

        placeId = getIntent().getStringExtra(AppConstants.INTENT_EXTRAS.EXTRA_PLACE_ID_PLANNER);
        days = getIntent().getIntExtra(AppConstants.INTENT_EXTRAS.EXTRA_DAYS_PLANNER, 0);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(days);
    }

    @OnClick(R.id.fab_planner)
    void onFabClick() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).openPlacePickerActivity(placeId, viewPager.getCurrentItem());
        }
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlannerFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return days;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Day " + (position + 1);
        }
    }
}
