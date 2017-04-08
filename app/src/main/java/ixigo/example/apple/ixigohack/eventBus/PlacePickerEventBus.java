package ixigo.example.apple.ixigohack.eventBus;

import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;

/**
 * Created by apple on 27/03/17.
 */

public class PlacePickerEventBus {

    public static class OnPlannerStartDateSelected {
        PlacePickerResponse.PlacesToVisit data;

        public OnPlannerStartDateSelected(PlacePickerResponse.PlacesToVisit data) {
            this.data = data;
        }

        public PlacePickerResponse.PlacesToVisit getData() {
            return data;
        }

        public void setData(PlacePickerResponse.PlacesToVisit data) {
            this.data = data;
        }
    }

    public static class OnPlacePicked {
        PlacePickerResponse.PlacesToVisit data;
        int fragmentPosition;

        public OnPlacePicked(PlacePickerResponse.PlacesToVisit data, int fragmentPosition) {
            this.data = data;
            this.fragmentPosition = fragmentPosition;
        }

        public int getFragmentPosition() {
            return fragmentPosition;
        }

        public void setFragmentPosition(int fragmentPosition) {
            this.fragmentPosition = fragmentPosition;
        }

        public PlacePickerResponse.PlacesToVisit getData() {
            return data;
        }

        public void setData(PlacePickerResponse.PlacesToVisit data) {
            this.data = data;
        }
    }
}
