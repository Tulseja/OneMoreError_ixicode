/**
 *
 */
package ixigo.example.apple.ixigohack.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ZPreferences {

    private static final String KEY = "ixigo.prefs";

    private static final String ACCESS_TOKEN = "HOGTOKENLOGINAPI_NEW";

    // ACCESS_TOKEN
    public static void setAccessToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.putString(ACCESS_TOKEN, token);
        editor.apply();
    }

    // ACCESS_TOKEN
    public static String getAccessToken(Context context) {
        SharedPreferences savedSession = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return savedSession.getString(ACCESS_TOKEN, null);
    }
}
