package ixigo.example.apple.ixigohack.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.eventBus.EventBusHelper;
import ixigo.example.apple.ixigohack.eventBus.PlacePickerEventBus;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    PlacePickerResponse.PlacesToVisit data;
    int fragmentPosition;
    String title;

    public static TimePickerFragment newInstance(PlacePickerResponse.PlacesToVisit data, int fragmentPosition, String title) {
        Bundle args = new Bundle();
        args.putParcelable(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_PLACE_PICKER_RESPONSE, data);
        args.putInt(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_POSITION, fragmentPosition);
        args.putString(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_DIALOG_TITLE, title);

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

        title = getArguments().getString(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_DIALOG_TITLE);
        data = getArguments().getParcelable(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_PLACE_PICKER_RESPONSE);
        fragmentPosition = getArguments().getInt(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_POSITION);

        if (data.getActivityStartHour() != null) {
            hour = data.getActivityStartHour() + 1;
        }

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, hour, minute, false);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_custom_title, null, false);
        TextView text = (TextView) view.findViewById(R.id.text_custom_title);
        text.setText(title);
        dialog.setCustomTitle(view);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

        dismiss();
    }
}