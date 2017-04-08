package ixigo.example.apple.ixigohack.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.activity.BaseActivity;
import ixigo.example.apple.ixigohack.extras.AppConstants;

/**
 * Created by apple on 08/04/17.
 */

public class HomeSearchFragment extends BaseFragment {

    public static HomeSearchFragment newInstance() {
        Bundle args = new Bundle();

        HomeSearchFragment fragment = new HomeSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_search, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.home_search_destination)
    void searchDestination() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).openLocationSelectionActivity(
                    AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_DESTINATION);
        }
    }

    @OnClick(R.id.home_search_origin)
    void searchOrigin() {
        ((BaseActivity) getActivity()).openLocationSelectionActivity(
                AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_ORIGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_ORIGIN) {

        } else if (requestCode == AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_DESTINATION) {

        }
    }
}
