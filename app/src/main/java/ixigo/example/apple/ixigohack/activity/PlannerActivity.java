package ixigo.example.apple.ixigohack.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.application.AppApplication;
import ixigo.example.apple.ixigohack.eventBus.EventBusHelper;
import ixigo.example.apple.ixigohack.eventBus.FirebaseEventBus;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.fragment.PlannerFragment;
import ixigo.example.apple.ixigohack.objects.FirebaseDataObject;
import ixigo.example.apple.ixigohack.utils.AndroidUtils;
import ixigo.example.apple.ixigohack.utils.AppUtils;
import ixigo.example.apple.ixigohack.utils.DebugUtils;

/**
 * Created by apple on 08/04/17.
 */

public class PlannerActivity extends BaseActivity implements ChildEventListener {

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
        removeFirebaseCallbacks();

        super.onDestroy();
    }

    private void removeFirebaseCallbacks() {
        try {
            FirebaseDatabase database = AppApplication.getFirebaseInstance();
            DatabaseReference myRef = database.getReference(AppConstants.FIREBASE_CONSTANTS.FIREBASE_ROOT_NODE);
            DatabaseReference childNode = myRef.child(deviceId);


            childNode.orderByKey().removeEventListener(this);
            childNode.removeEventListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        ButterKnife.bind(this);

        placeId = getIntent().getStringExtra(AppConstants.INTENT_EXTRAS.EXTRA_PLACE_ID_PLANNER);
        placeName = getIntent().getStringExtra(AppConstants.INTENT_EXTRAS.EXTRA_PLACE_NAME_PLANNER);
        days = getIntent().getIntExtra(AppConstants.INTENT_EXTRAS.EXTRA_DAYS_PLANNER, 0);
        deviceId = getIntent().getStringExtra(AppConstants.INTENT_EXTRAS.EXTRA_ANDROID_DEVICE_ID);

        setToolbar("Plan " + placeName + " trip");

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(days);

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                AppUtils.getInstance().doBranchShare(this, deviceId, placeName, days, placeId);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        FirebaseDatabase database = AppApplication.getFirebaseInstance();
        DatabaseReference myRef = database.getReference(AppConstants.FIREBASE_CONSTANTS.FIREBASE_ROOT_NODE);
        DatabaseReference childNode = myRef.child(deviceId);


        childNode.orderByKey().addChildEventListener(this);
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
            return PlannerFragment.newInstance(position, deviceId, placeId);
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

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        DebugUtils.log("added");
        try {
            HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
            FirebaseDataObject object = new FirebaseDataObject();
            object.setFirebaseKey(hashMap.get("firebaseKey").toString());
            object.setEndMin(((Long) hashMap.get("endMin")).intValue());
            object.setEndHour(((Long) hashMap.get("endHour")).intValue());
            object.setStarMin(((Long) hashMap.get("starMin")).intValue());
            object.setDayPos(((Long) hashMap.get("dayPos")).intValue());
            try {
                object.setPlaceId(hashMap.get("placeId").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                object.setImage(hashMap.get("image").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            object.setName(hashMap.get("name").toString());
            object.setStartHour(((Long) hashMap.get("startHour")).intValue());

            EventBusHelper.sendEventSticky(new FirebaseEventBus.OnFirebaseEventAdded(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        DebugUtils.log("changed");
        try {
            HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
            FirebaseDataObject object = new FirebaseDataObject();
            object.setFirebaseKey(hashMap.get("firebaseKey").toString());
            object.setEndMin(((Long) hashMap.get("endMin")).intValue());
            object.setEndHour(((Long) hashMap.get("endHour")).intValue());
            object.setStarMin(((Long) hashMap.get("starMin")).intValue());
            object.setDayPos(((Long) hashMap.get("dayPos")).intValue());
            try {
                object.setPlaceId(hashMap.get("placeId").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                object.setImage(hashMap.get("image").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            object.setName(hashMap.get("name").toString());
            object.setStartHour(((Long) hashMap.get("startHour")).intValue());

            EventBusHelper.sendEventSticky(new FirebaseEventBus.OnFirebaseEventChanged(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        DebugUtils.log("removed");
        try {
            HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
            FirebaseDataObject object = new FirebaseDataObject();
            object.setFirebaseKey(hashMap.get("firebaseKey").toString());
            object.setEndMin(((Long) hashMap.get("endMin")).intValue());
            object.setEndHour(((Long) hashMap.get("endHour")).intValue());
            object.setStarMin(((Long) hashMap.get("starMin")).intValue());
            object.setDayPos(((Long) hashMap.get("dayPos")).intValue());
            try {
                object.setPlaceId(hashMap.get("placeId").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                object.setImage(hashMap.get("image").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            object.setName(hashMap.get("name").toString());
            object.setStartHour(((Long) hashMap.get("startHour")).intValue());

            EventBusHelper.sendEventSticky(new FirebaseEventBus.OnFirebaseEventRemoved(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        DebugUtils.log("moved");
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        DebugUtils.log("cancelled");
    }
}
