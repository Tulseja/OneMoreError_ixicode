package ixigo.example.apple.ixigohack.eventBus;

import android.app.Activity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Ashish on 18/02/17.
 */

public class EventBusHelper {

    public static void sendEvent(Object object) {
        EventBus.getDefault().post(object);
    }

    public static void sendEventSticky(Object object) {
        EventBus.getDefault().postSticky(object);
    }

    public static void register(Activity context) {
        EventBus.getDefault().register(context);
    }

    public static void register(Object context) {
        EventBus.getDefault().register(context);
    }

    public static void unRegister(Activity context) {
        EventBus.getDefault().unregister(context);
    }

    public static void unRegister(Object context) {
        EventBus.getDefault().unregister(context);
    }
}
