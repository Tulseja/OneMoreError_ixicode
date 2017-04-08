package ixigo.example.apple.ixigohack.serverApi;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ixigo.example.apple.ixigohack.application.AppApplication;
import ixigo.example.apple.ixigohack.extras.RequestTags;
import ixigo.example.apple.ixigohack.objects.autoComplete.AutoCompleteResponse;
import ixigo.example.apple.ixigohack.utils.AndroidUtils;
import ixigo.example.apple.ixigohack.utils.DebugUtils;
import ixigo.example.apple.ixigohack.utils.VolleyUtils;

/**
 * Created by apple on 08/04/17.
 */

public class CustomRequest extends Request<Object> {

    AppRequestListener appRequestListener;
    HashMap<String, String> headers;
    String tag;
    String url;

    String body;

    long requestStartTime;

    Context context;

    public CustomRequest(Context context, int method, String url, String tag, AppRequestListener appRequestListener, HashMap<String, String> headers) {
        this(context, method, url, tag, appRequestListener, headers, null);
    }

    public CustomRequest(Context context, int method, String url, String tag,
                         AppRequestListener appRequestListener, HashMap<String, String> headers, String body) {
        super(method, url, null);
        this.appRequestListener = appRequestListener;
        this.tag = tag;
        this.headers = headers;
        this.url = url;
        this.body = body;
        this.context = context;

        DebugUtils.logRequests("Request Started. URL = " + url);

        if (DebugUtils.noteAppRequestTimes) {
            requestStartTime = System.currentTimeMillis();
        }

        if (appRequestListener != null && context != null) {
            appRequestListener.onRequestStarted(tag);
        }

        AppApplication.getInstance().addToRequestQueue(this, tag);
    }

    void destroyContext() {
        context = null;
        appRequestListener = null;
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
    public void deliverError(VolleyError error) {
        String responseCode = "Network error";
        if (error != null && error.networkResponse != null) {
            responseCode = Integer.toString(error.networkResponse.statusCode);
        }

        if (DebugUtils.noteAppRequestTimes) {
            long requestEndTime = System.currentTimeMillis();
            long difference = (requestEndTime - requestStartTime);
            DebugUtils.logRequests("Request Failed in " + difference + "ms. URL = " + url + " Code : " + responseCode);
        } else {
            DebugUtils.logRequests("Request Failed. URL = " + url + " Code : " + responseCode);
        }

        DebugUtils.printToSystem(error);

        if (appRequestListener != null && context != null) {
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
        destroyContext();
    }

    @Override
    protected Response<Object> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Object responseObject = null;

            if (AndroidUtils.compareString(tag, RequestTags.AUTOCOMPLETE_LOCATION)) {
                Type listType = new TypeToken<ArrayList<AutoCompleteResponse>>() {
                }.getType();
                responseObject = new Gson().fromJson(json, listType);
            }
            return Response.success(responseObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        if (DebugUtils.noteAppRequestTimes) {
            long requestEndTime = System.currentTimeMillis();
            long difference = (requestEndTime - requestStartTime);
            DebugUtils.logRequests("Request Complete in " + difference + "ms. URL = " + url);
        } else {
            DebugUtils.logRequests("Request Complete. URL = " + url);
        }

        if (appRequestListener != null && context != null) {
            appRequestListener.onRequestCompleted(tag, response);
        }
        destroyContext();
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
