package ixigo.example.apple.ixigohack.serverApi;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ixigo.example.apple.ixigohack.utils.DebugUtils;


public class CustomStringRequest extends StringRequest {

    AppRequestListener appRequestListener;
    HashMap<String, String> params;
    HashMap<String, String> headers;
    String tag;
    String url;

    String body;

    long requestStartTime;

    public CustomStringRequest(int method, String url, String tag,
                               AppRequestListener appRequestListener,
                               HashMap<String, String> params, HashMap<String, String> headers) {
        this(method, url, tag, appRequestListener, params, headers, null);
    }

    public CustomStringRequest(int method, String url, String tag,
                               AppRequestListener appRequestListener,
                               HashMap<String, String> params, HashMap<String, String> headers, String body) {
        super(method, url, null, null);
        this.appRequestListener = appRequestListener;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.url = url;
        this.body = body;

        DebugUtils.logRequests("Request Started. URL = " + url);

        if (DebugUtils.noteAppRequestTimes) {
            requestStartTime = System.currentTimeMillis();
        }

        if (appRequestListener != null) {
            appRequestListener.onRequestStarted(tag);
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (body == null) {
            return super.getBody();
        } else {
            try {
                DebugUtils.log(body);
                return body.getBytes("UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                return super.getBody();
            }
        }
    }

    @Override
    protected void deliverResponse(String response) {
        if (DebugUtils.noteAppRequestTimes) {
            long requestEndTime = System.currentTimeMillis();
            long difference = (requestEndTime - requestStartTime);
            DebugUtils.logRequests("Request Complete in " + difference + "ms. URL = " + url);
        } else {
            DebugUtils.logRequests("Request Complete. URL = " + url);
        }
        DebugUtils.printToSystem(response);

        sendResponseToListener(response);
    }

    private void sendResponseToListener(String response) {
        if (appRequestListener != null) {
            appRequestListener.onRequestCompleted(tag, response);
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        String responseCode = "Network error";
        if (error != null && error.networkResponse != null) {
            responseCode = "" + error.networkResponse.statusCode;
        }

        if (DebugUtils.noteAppRequestTimes) {
            long requestEndTime = System.currentTimeMillis();
            long difference = (requestEndTime - requestStartTime);
            DebugUtils.logRequests("Request Failed in " + difference + "ms. URL = " + url + " Code : " + responseCode);
        } else {
            DebugUtils.logRequests("Request Failed. URL = " + url + " Code : " + responseCode);
        }

        DebugUtils.printToSystem(error);

        if (appRequestListener != null) {
            boolean networkError = false;
            if (error == null || error.networkResponse == null) {
                networkError = true;
            } else if (error instanceof NoConnectionError) {
                networkError = true;
            } else if (error instanceof NetworkError) {
                networkError = true;
            } else if (error instanceof TimeoutError) {
                networkError = true;
            }

            appRequestListener.onRequestFailed(tag, error, networkError);
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return super.parseNetworkResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : Collections.<String, String>emptyMap();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headers != null) {
            DebugUtils.log(headers.toString());
        }
        return headers != null ? headers : Collections.<String, String>emptyMap();
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }
}
