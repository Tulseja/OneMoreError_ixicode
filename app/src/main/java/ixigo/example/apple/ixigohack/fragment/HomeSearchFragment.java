package ixigo.example.apple.ixigohack.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.activity.BaseActivity;
import ixigo.example.apple.ixigohack.activity.LocationSelectionActivity;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.objects.autoComplete.AutoCompleteResponse;
import ixigo.example.apple.ixigohack.utils.DebugUtils;
import ixigo.example.apple.ixigohack.utils.UIUtils;

/**
 * Created by apple on 08/04/17.
 */

public class HomeSearchFragment extends BaseFragment {

    @BindView(R.id.home_search_origin)
    TextView origin;
    @BindView(R.id.home_search_destination)
    TextView destination;
    @BindView(R.id.home_search_days)
    TextView numberOfDays;

    Integer days;
    String destinationPlaceId, destinationPlaceName;

    AlertDialog alertDialog;
    MaterialNumberPicker numberPicker;

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

    @OnClick(R.id.home_search_days)
    void selectDays() {
        numberPicker = new MaterialNumberPicker.Builder(getActivity())
                .minValue(1)
                .maxValue(20)
                .defaultValue(1)
                .backgroundColor(Color.WHITE)
                .separatorColor(Color.TRANSPARENT)
                .textColor(Color.BLACK)
                .textSize(20)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Select number of days")
                .setView(numberPicker)
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (numberPicker != null) {
                            DebugUtils.log("Number : " + numberPicker.getValue());
                            days = numberPicker.getValue();

                            numberOfDays.setText("Days : " + days);
                            numberOfDays.setTextColor(getActivity().getResources().getColor(R.color.z_text_color_medium_dark));
                        }
                    }
                });
        alertDialog = builder.create();
        UIUtils.showDialog(getActivity(), alertDialog);
    }

    @OnClick(R.id.home_search_done)
    void onSearchClicked() {
        if (getActivity() != null) {
            if (days == null) {
                ((BaseActivity) getActivity()).makeToast("Please enter number of days to plan");
            } else if (destinationPlaceId == null) {
                ((BaseActivity) getActivity()).makeToast("Please select destination place");
            } else {
                ((BaseActivity) getActivity()).openPlannerActivity(days, destinationPlaceId, destinationPlaceName, null);
            }
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
                destination.setTextColor(getActivity().getResources().getColor(R.color.z_text_color_medium_dark));
                destinationPlaceId = obj.get_id();
                destinationPlaceName = obj.getText();
            }
        }
    }
}
