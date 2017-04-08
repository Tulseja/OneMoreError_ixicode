package ixigo.example.apple.ixigohack.requests;

import android.content.Context;

import java.util.HashMap;

import ixigo.example.apple.ixigohack.extras.RequestTags;
import ixigo.example.apple.ixigohack.utils.AndroidUtils;

/**
 * Created by Ashish on 04/06/16.
 */
public class AppRequests implements RequestTags {

    public static HashMap<String, String> getHeaders(Context context) {
        HashMap<String, String> headers = new HashMap<>();
        return headers;
    }
}
