package ixigo.example.apple.ixigohack.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
import ixigo.example.apple.ixigohack.objects.FirebaseDataObject;
import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;
import ixigo.example.apple.ixigohack.utils.AndroidUtils;

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

    String deviceId;

    @Override
    protected void onDestroy() {
        EventBusHelper.unRegister(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        ButterKnife.bind(this);
        EventBusHelper.register(this);

        placeId = getIntent().getStringExtra(AppConstants.INTENT_EXTRAS.EXTRA_PLACE_ID_PLANNER);
        days = getIntent().getIntExtra(AppConstants.INTENT_EXTRAS.EXTRA_DAYS_PLANNER, 0);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(days);

        deviceId = AndroidUtils.getDeviceId(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlacePickerEventBus.OnPlacePicked obj) {
        addToFirebase(obj.getData());
    }

    public void addToFirebase(PlacePickerResponse.PlacesToVisit data) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(AppConstants.FIREBASE_CONSTANTS.FIREBASE_ROOT_NODE);
        DatabaseReference childNode = myRef.child(deviceId);

        FirebaseDataObject obj = new FirebaseDataObject(data.getKeyImageUrl(),
                data.getName(),
                data.getActivityStartHour(),
                data.getActivityStartMinute(),
                data.getActivityEndHour(),
                data.getActivityEndMinute());

        DatabaseReference pushedChild = childNode.push();
        pushedChild.setValue(obj);
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
