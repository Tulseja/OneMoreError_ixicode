package ixigo.example.apple.ixigohack.serverApi;

import com.android.volley.VolleyError;

import java.util.Map;

public interface AppRequestListener {

    void onRequestStarted(String requestTag);

    void onRequestFailed(String requestTag, VolleyError error, boolean networkError);

    void onRequestCompleted(String requestTag, Object response);
}
