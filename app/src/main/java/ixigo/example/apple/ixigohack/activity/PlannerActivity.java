package ixigo.example.apple.ixigohack.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.application.AppApplication;
import ixigo.example.apple.ixigohack.eventBus.EventBusHelper;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.fragment.PlannerFragment;
import ixigo.example.apple.ixigohack.utils.AndroidUtils;
import ixigo.example.apple.ixigohack.utils.DebugUtils;

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
    String placeName;
    int days;
    MyPagerAdapter adapter;

    String deviceId;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        ButterKnife.bind(this);

        placeId = getIntent().getStringExtra(AppConstants.INTENT_EXTRAS.EXTRA_PLACE_ID_PLANNER);
        placeName = getIntent().getStringExtra(AppConstants.INTENT_EXTRAS.EXTRA_PLACE_NAME_PLANNER);
        days = getIntent().getIntExtra(AppConstants.INTENT_EXTRAS.EXTRA_DAYS_PLANNER, 0);

        setToolbar("Plan " + placeName + " trip");

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(days);

        deviceId = AndroidUtils.getDeviceId(this);

        loadData();
    }

    private void loadData() {
        FirebaseDatabase database = AppApplication.getFirebaseInstance();
        database.setPersistenceEnabled(true);
        DatabaseReference myRef = database.getReference(AppConstants.FIREBASE_CONSTANTS.FIREBASE_ROOT_NODE);
        DatabaseReference childNode = myRef.child(deviceId);

        childNode.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DebugUtils.log("added");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DebugUtils.log("changed");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DebugUtils.log("removed");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                DebugUtils.log("moved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DebugUtils.log("cancelled");
            }
        });
    }

    @OnClick(R.id.fab_planner)
    void onFabClick() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).openPlacePickerActivity(placeId, placeName, viewPager.getCurrentItem());
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
