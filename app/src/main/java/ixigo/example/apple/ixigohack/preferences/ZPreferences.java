/**
 *
 */
package ixigo.example.apple.ixigohack.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ZPreferences {

    private static final String KEY = "ixigo.prefs";

    private static final String IS_USER_LOGIN = "IS_USER_LOGIN";

    // IS_USER_LOGIN
    public static void setIsUserLogin(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.putBoolean(IS_USER_LOGIN, loggedIn);
        editor.apply();
    }

    // IS_USER_LOGIN
    public static boolean getIsUserLogin(Context context) {
        SharedPreferences savedSession = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return savedSession.getBoolean(IS_USER_LOGIN, false);
    }
}
