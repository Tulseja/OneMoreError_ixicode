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
import ixigo.example.apple.ixigohack.objects.FirebaseDataObject;

public class TimePickerPlannerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    FirebaseDataObject data;
    int fragmentPosition;
    String title;

    public static TimePickerPlannerFragment newInstance(FirebaseDataObject data, int fragmentPosition, String title) {
        Bundle args = new Bundle();
        args.putParcelable(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_PLACE_PICKER_RESPONSE, data);
        args.putInt(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_POSITION, fragmentPosition);
        args.putString(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_DIALOG_TITLE, title);

        TimePickerPlannerFragment fragment = new TimePickerPlannerFragment();
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

        if (data.getStartHour() != null) {
            hour = data.getStartHour() + 1;
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
        if (data.getStartHour() == null) {
            data.setStartHour(hourOfDay);
            data.setStarMin(minute);

            EventBusHelper.sendEvent(new PlacePickerEventBus.OnUdpateEventStartDateEvent(data, fragmentPosition));
        } else {
            data.setEndHour(hourOfDay);
            data.setEndMin(minute);

            EventBusHelper.sendEventSticky(new PlacePickerEventBus.OnUpdateEventCompleteEvent(data, fragmentPosition));
        }
        dismiss();
    }
}