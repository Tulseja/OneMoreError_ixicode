package ixigo.example.apple.ixigohack.eventBus;

import ixigo.example.apple.ixigohack.objects.FirebaseDataObject;
import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;

/**
 * Created by apple on 27/03/17.
 */

public class PlacePickerEventBus {

    public static class OnUdpateEventStartDateEvent {
        FirebaseDataObject data;
        int position;

        public OnUdpateEventStartDateEvent(FirebaseDataObject data, int position) {
            this.data = data;
            this.position = position;
        }

        public FirebaseDataObject getData() {
            return data;
        }

        public void setData(FirebaseDataObject data) {
            this.data = data;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public static class OnUpdateEventCompleteEvent {
        FirebaseDataObject data;
        int position;

        public OnUpdateEventCompleteEvent(FirebaseDataObject data, int position) {
            this.data = data;
            this.position = position;
        }

        public FirebaseDataObject getData() {
            return data;
        }

        public void setData(FirebaseDataObject data) {
            this.data = data;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

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
