package ixigo.example.apple.ixigohack.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.eventBus.EventBusHelper;
import ixigo.example.apple.ixigohack.eventBus.PlacePickerEventBus;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.extras.RequestTags;
import ixigo.example.apple.ixigohack.fragment.PlacePickerFragment;
import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;
import ixigo.example.apple.ixigohack.serverApi.AppRequestListener;
import ixigo.example.apple.ixigohack.serverApi.CustomRequest;
import ixigo.example.apple.ixigohack.urls.PlannerAppUrls;
import ixigo.example.apple.ixigohack.utils.DebugUtils;

/**
 * Created by apple on 08/04/17.
 */

public class PlacePickerActivity extends BaseActivity implements AppRequestListener {

    PlacePickerResponse placePickerResponse;
    String placeId;
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

        EventBusHelper.register(this);

        placeId = getIntent().getStringExtra(AppConstants.INTENT_EXTRAS.EXTRA_PLACE_ID_PLANNER);
        fragmentPosition = getIntent().getIntExtra(AppConstants.INTENT_EXTRAS.EXTRA_FRAGMENT_POSITION, 0);

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
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.PLACE_PICKER_PLACES_TO_VISIT)) {

        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error, boolean networkError) {
        if (requestTag.equalsIgnoreCase(RequestTags.PLACE_PICKER_PLACES_TO_VISIT)) {

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

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        PlacePickerResponse.PlacesToVisit data;
        int fragmentPosition;

        public static TimePickerFragment newInstance(PlacePickerResponse.PlacesToVisit data, int fragmentPosition) {
            Bundle args = new Bundle();
            args.putParcelable(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_PLACE_PICKER_RESPONSE, data);
            args.putInt(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_POSITION, fragmentPosition);

            TimePickerFragment fragment = new TimePickerFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = 0;

            return new TimePickerDialog(getActivity(), this, hour, minute, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            data = getArguments().getParcelable(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_PLACE_PICKER_RESPONSE);
            fragmentPosition = getArguments().getInt(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_POSITION);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (data.getActivityStartHour() == null) {
                data.setActivityStartHour(hourOfDay);
                data.setActivityStartMinute(minute);

                EventBusHelper.sendEvent(new PlacePickerEventBus.OnPlannerStartDateSelected(data));
            } else {
                data.setActivityEndHour(hourOfDay);
                data.setActivityEndMinute(minute);

                EventBusHelper.sendEventSticky(new PlacePickerEventBus.OnPlacePicked(data, fragmentPosition));
                getActivity().finish();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlacePickerEventBus.OnPlannerStartDateSelected obj) {
        DialogFragment newFragment = TimePickerFragment.newInstance(obj.getData(), fragmentPosition);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
