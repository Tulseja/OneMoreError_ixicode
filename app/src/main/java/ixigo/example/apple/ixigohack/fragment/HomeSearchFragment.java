package ixigo.example.apple.ixigohack.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.activity.BaseActivity;
import ixigo.example.apple.ixigohack.activity.LocationSelectionActivity;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.objects.autoComplete.AutoCompleteResponse;

/**
 * Created by apple on 08/04/17.
 */

public class HomeSearchFragment extends BaseFragment {

    @BindView(R.id.home_search_origin)
    TextView origin;
    @BindView(R.id.home_search_destination)
    TextView destination;

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
            startActivityForResult(LocationSelectionActivity.getLaunchIntent(getActivity()),
                    AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_DESTINATION);
        }
    }

    @OnClick(R.id.home_search_origin)
    void searchOrigin() {
        if (getActivity() != null) {
            startActivityForResult(LocationSelectionActivity.getLaunchIntent(getActivity()),
                    AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_ORIGIN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_ORIGIN) {
            if (resultCode == Activity.RESULT_OK) {
                AutoCompleteResponse obj = data.getParcelableExtra(AppConstants.INTENT_EXTRAS.EXTRA_LOCATION_AUTOCOMPLETE);

                origin.setText(obj.getText());
            }
        } else if (requestCode == AppConstants.ACTIVITY_OPEN_REQUEST_CODES.ACTIVITY_LOCATION_SELECTION_DESTINATION) {
            if (resultCode == Activity.RESULT_OK) {
                AutoCompleteResponse obj = data.getParcelableExtra(AppConstants.INTENT_EXTRAS.EXTRA_LOCATION_AUTOCOMPLETE);

                destination.setText(obj.getText());
            }
        }
    }
}
