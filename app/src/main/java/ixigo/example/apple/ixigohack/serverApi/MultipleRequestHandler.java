package ixigo.example.apple.ixigohack.serverApi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import ixigo.example.apple.ixigohack.application.AppApplication;
import ixigo.example.apple.ixigohack.requests.AppRequests;

/**
 * Created by apple on 24/03/17.
 */

public class MultipleRequestHandler implements AppRequestListener {

    int count;
    MultipleRequestInterface mInterface;

    public MultipleRequestHandler(MultipleRequestInterface mInterface) {
        this.count = 0;
        this.mInterface = mInterface;
    }

    public void addRequest(Context context, MultipleRequestObj... data) {
        if (data != null) {
            for (MultipleRequestObj obj : data) {
                CustomStringRequest request = new CustomStringRequest(Request.Method.GET, obj.url, obj.tag,
                        this, null, AppRequests.getHeaders(context));
                AppApplication.getInstance().addToRequestQueue(request, obj.tag);
                count++;
            }
        }
    }

    public void addRequestExtended(Context context, MultipleRequestObj... data) {
        if (data != null) {
            for (MultipleRequestObj obj : data) {
                CustomStringRequest request = new CustomStringRequest(obj.requestMethod, obj.url, obj.tag,
                        this, null, obj.headers);
                AppApplication.getInstance().addToRequestQueue(request, obj.tag);
                count++;
            }
        }
    }

    public void reset() {
        count = 0;
        mInterface = null;
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (mInterface != null) {
            mInterface.startedMultipleRequest(requestTag);
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error, boolean networkError) {
        if (mInterface != null) {
            mInterface.failedMultipleRequest(requestTag, error, networkError);
        }
        checkIfComplete();
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (mInterface != null) {
            mInterface.successMultipleRequest(requestTag, response);
        }
        checkIfComplete();
    }

    private void checkIfComplete() {
        count--;
        if (count <= 0 && mInterface != null) {
            mInterface.completeMultipleRequest();
        }
    }

    @Override
    public void onRequestHeaders(Map<String, String> headers) {

    }

    public interface MultipleRequestInterface {
        void startedMultipleRequest(String requestTag);

        void failedMultipleRequest(String requestTag, VolleyError error, boolean networkError);

        void completeMultipleRequest();

        void successMultipleRequest(String requestTag, String response);
    }

    public class MultipleRequestObj {
        public String url;
        public String tag;
        public int requestMethod;
        HashMap<String, String> headers;

        public MultipleRequestObj(String url, String tag) {
            this.url = url;
            this.tag = tag;
        }

        public MultipleRequestObj(String url, String tag, int requestMethod, HashMap<String, String> headers) {
            this.url = url;
            this.tag = tag;
            this.requestMethod = requestMethod;
            this.headers = headers;
        }
    }
}
