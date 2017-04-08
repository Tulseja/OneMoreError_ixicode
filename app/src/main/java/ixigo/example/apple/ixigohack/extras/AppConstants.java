package ixigo.example.apple.ixigohack.extras;

public interface AppConstants {

    int TYPE_RECYCLER_VIEW_NORMAL = 0;
    int TYPE_RECYCLER_VIEW_LOADING = 1;
    int TYPE_RECYCLER_VIEW_HEADER = 2;
    int TYPE_RECYCLER_VIEW_FOOTER = 3;

    public interface ACTIVITY_OPEN_REQUEST_CODES {
        int ACTIVITY_LOCATION_SELECTION_ORIGIN = 1;
        int ACTIVITY_LOCATION_SELECTION_DESTINATION = 2;
        int ACTIVITY_GOOGLE_LOGIN = 3;
    }

    public interface INTENT_EXTRAS {
        String EXTRA_LOCATION_AUTOCOMPLETE = "EXTRA_LOCATION_AUTOCOMPLETE";
        String EXTRA_PLACE_ID_PLANNER = "EXTRA_PLACE_ID_PLANNER";
        String EXTRA_DAYS_PLANNER = "EXTRA_DAYS_PLANNER";
    }

    public interface FRAGMENT_EXTRAS {
        String EXTRA_FRAGMENT_POSITION = "EXTRA_FRAGMENT_POSITION";
    }
}
