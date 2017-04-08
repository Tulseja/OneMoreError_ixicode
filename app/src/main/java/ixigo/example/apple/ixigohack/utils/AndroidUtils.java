package ixigo.example.apple.ixigohack.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Ashish on 09/07/16.
 */
public class AndroidUtils {

    public static Context getContextFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public static void openApplicationNotificationSettings(Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openPlayStore(Context context) {
        try {
            final String appPackageName = context.getPackageName();
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {
            try {
                final String appPackageName = context.getPackageName();
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openMailToSupport(String emailAddress, Context context) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Uri.encode(emailAddress)));
            context.startActivity(Intent.createChooser(emailIntent, "Send email via..."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean compareString(String str1, String str2) {
        return compareString(str1, str2, true);
    }

    public static boolean compareString(String str1, String str2, boolean logError) {
        try {
            if (str1.equalsIgnoreCase(str2)) {
                return true;
            }
        } catch (Exception e) {
            if (logError) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isEmpty(String text) {
        try {
            if (text != null && text.length() > 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isEmpty(EditText editText) {
        try {
            if (editText != null && editText.getText().toString().trim().length() > 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean checkEmailFormat(String email) {
        try {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getDeviceId(Context context) {
        try {
            String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            return deviceId;
        } catch (Exception e) {
            e.printStackTrace();
            DebugUtils.logException(e, true);
        }
        return "";
    }

    public static String getConnectivityStatus(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    boolean isWiFi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
                    if (isWiFi) {
                        return "WIFI";
                    }
                }
            }
        } catch (Exception e) {
            DebugUtils.logException(e, true);
        }
        return getConnectionTypeSim(context);
    }

    private static String getConnectionTypeSim(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "Unknown";
        }
    }

    @SuppressWarnings("deprecation")
    public static Spanned getHtmlText(String source) {
        try {
            try {
                source = source.replaceAll("\\<u>", "");
                source = source.replaceAll("\\</u>", "");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Spanned result;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                result = Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
            } else {
                result = Html.fromHtml(source);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public static String convertFromSpannedToHtml(Spanned source) {
        try {
            String result;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                result = Html.toHtml(source, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE);
            } else {
                result = Html.toHtml(source);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
