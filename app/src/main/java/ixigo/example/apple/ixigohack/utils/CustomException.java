package ixigo.example.apple.ixigohack.utils;

/**
 * Created by Ashish on 02/05/16.
 */

import android.support.annotation.NonNull;
import android.util.Log;

public class CustomException extends Exception {

    public static void logException(Exception e) {
        try {
            // TODO: 21/02/17 uncomment this line
//            Crashlytics.logException(e);
        } catch (Exception e1) {
            Log.w("Crashlytics", "Crashlytics not enabled");
        }
    }

    public CustomException(@NonNull String mesage) {
        super("Custom Exception : " + mesage);
    }
}
