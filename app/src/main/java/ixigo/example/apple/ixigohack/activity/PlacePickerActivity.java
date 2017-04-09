package ixigo.example.apple.ixigohack.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.transition.Visibility;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.eventBus.EventBusHelper;
import ixigo.example.apple.ixigohack.eventBus.PlacePickerEventBus;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.extras.RequestTags;
import ixigo.example.apple.ixigohack.fragment.PlacePickerFragment;
import ixigo.example.apple.ixigohack.fragment.TimePickerFragment;
import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;
import ixigo.example.apple.ixigohack.serverApi.AppRequestListener;
import ixigo.example.apple.ixigohack.serverApi.CustomRequest;
import ixigo.example.apple.ixigohack.urls.PlannerAppUrls;

/**
 * Created by apple on 08/04/17.
 */

public class PlacePickerActivity extends BaseActivity implements AppRequestListener {

    PlacePickerResponse placePickerResponse;
    String placeId, placeName;
    int fragmentPosition;

    @BindView(R.id.place_picker_viewpager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    MyPagerAdapter adapter;

    @Override
    public void onDestroy() {
        EventBusHelper.unRegister(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);
        ButterKnife.bind(this);
        setProgressAndErrorLayoutVariables();

        EventBusHelper.register(this);
        tabLayout.setVisibility(View.GONE);

        placeId = getIntent().getStringExtra(AppConstants.INTENT_EXTRAS.EXTRA_PLACE_ID_PLANNER);
        placeName = getIntent().getStringExtra(AppConstants.INTENT_EXTRAS.EXTRA_PLACE_NAME_PLANNER);
        fragmentPosition = getIntent().getIntExtra(AppConstants.INTENT_EXTRAS.EXTRA_FRAGMENT_POSITION, 0);

        setToolbar("Interests in " + placeName);

        loadData();
    }

    private void loadData() {
        String url = PlannerAppUrls.getInterestsUrl(placeId, 0, 100);
        new CustomRequest(this, Request.Method.GET, url, RequestTags.PLACE_PICKER_PLACES_TO_VISIT, this, null);
    }

    private void setAdapter() {
        hideErrorLayout();
        hideProgressLayout();

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.PLACE_PICKER_PLACES_TO_VISIT)) {
            showProgressLayout() ;
            hideErrorLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error, boolean networkError) {
        if (requestTag.equalsIgnoreCase(RequestTags.PLACE_PICKER_PLACES_TO_VISIT)) {
            hideProgressLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, Object response) {
        if (requestTag.equalsIgnoreCase(RequestTags.PLACE_PICKER_PLACES_TO_VISIT)) {
            placePickerResponse = (PlacePickerResponse) response;

            setAdapter();
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return PlacePickerFragment.newInstance(placePickerResponse.getData().getPlacesToVisit());
            } else {
                return PlacePickerFragment.newInstance(placePickerResponse.getData().getThingsToDo());
            }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlacePickerEventBus.OnPlannerStartDateSelected obj) {
        DialogFragment newFragment = TimePickerFragment.newInstance(obj.getData(),
                fragmentPosition, getResources().getString(R.string.time_picker_title_select_end_time));
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
