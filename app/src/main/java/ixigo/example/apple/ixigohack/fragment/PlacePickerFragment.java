package ixigo.example.apple.ixigohack.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.activity.PlacePickerActivity;
import ixigo.example.apple.ixigohack.adapters.HomeTrendingFragmentAdapter;
import ixigo.example.apple.ixigohack.adapters.PlacePickerListingAdapter;
import ixigo.example.apple.ixigohack.eventBus.EventBusHelper;
import ixigo.example.apple.ixigohack.eventBus.PlacePickerEventBus;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;
import ixigo.example.apple.ixigohack.utils.DebugUtils;

/**
 * Created by apple on 08/04/17.
 */

public class PlacePickerFragment extends BaseFragment implements PlacePickerListingAdapter.OnPlaceClickInterface {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    PlacePickerListingAdapter adapter;

    List<PlacePickerResponse.PlacesToVisit> mData;

    public static PlacePickerFragment newInstance(ArrayList<PlacePickerResponse.PlacesToVisit> data) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_PLACE_PICKER_RESPONSE, data);

        PlacePickerFragment fragment = new PlacePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_trending, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mData = getArguments().getParcelableArrayList(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_PLACE_PICKER_RESPONSE);

        adapter = new PlacePickerListingAdapter(getActivity(), mData, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPlaceClicked(PlacePickerResponse.PlacesToVisit data) {
        data.setActivityStartHour(null);

        DialogFragment newFragment = PlacePickerActivity.TimePickerFragment.newInstance(data,
                0, getResources().getString(R.string.time_picker_title_select_start_time));
        newFragment.show(getChildFragmentManager(), "timePicker");
    }
}
