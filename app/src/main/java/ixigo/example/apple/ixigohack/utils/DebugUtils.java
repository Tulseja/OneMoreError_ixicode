package ixigo.example.apple.ixigohack.utils;

import android.util.Log;

import com.android.volley.VolleyError;

import ixigo.example.apple.ixigohack.BuildConfig;

/**
 * Created by Ashish on 04/06/16.
 */
public class DebugUtils {

    public static boolean showTags = BuildConfig.DEBUG;
    public static boolean noteAppRequestTimes = BuildConfig.DEBUG;

    public static void log(String message) {
        if (showTags && message != null) {
            Log.w("HOG", message);
        }
    }

    public static void logRequests(String message) {
        if (showTags && message != null) {
            Log.w("CustomStringRequest", message);
        }
    }

    public static void printToSystem(String response) {
        if (showTags && response != null) {
            System.out.println(response);
        }
    }

    public static void printToSystem(VolleyError error) {
        if (showTags && error != null && error.networkResponse != null && error.networkResponse.data != null) {
            String string = new String(error.networkResponse.data);
            System.out.println(string);
        }
    }

    public static void log(String tag, String message) {
        if (showTags && message != null && tag != null) {
            Log.w(tag, message);
        }
    }

    public static void logException(Exception exception, boolean isFatal) {

    }
}
