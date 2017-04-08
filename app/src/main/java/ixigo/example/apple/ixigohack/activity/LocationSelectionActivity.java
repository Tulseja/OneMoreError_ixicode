package ixigo.example.apple.ixigohack.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.LocationSearchAdapter;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.application.AppApplication;
import ixigo.example.apple.ixigohack.extras.RequestTags;
import ixigo.example.apple.ixigohack.objects.autoComplete.AutoCompleteResponse;
import ixigo.example.apple.ixigohack.serverApi.AppRequestListener;
import ixigo.example.apple.ixigohack.serverApi.CustomRequest;
import ixigo.example.apple.ixigohack.urls.AutocompleteUrls;
import ixigo.example.apple.ixigohack.utils.AndroidUtils;
import ixigo.example.apple.ixigohack.utils.DebugUtils;

/**
 * Created by apple on 08/04/17.
 */

public class LocationSelectionActivity extends BaseActivity implements TextWatcher, AppRequestListener {

    @BindView(R.id.edittext_location)
    EditText editText;
    @BindView(R.id.clear_search_text)
    FrameLayout clearEditTextButton;
    @BindView(R.id.location_recyclerview)
    RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    LocationSearchAdapter adapter;

    String currentQuery;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, LocationSelectionActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setToolbar("");

        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (getCurrentText().length() == 0) {
            clearEditTextButton.setVisibility(View.GONE);
        } else {
            clearEditTextButton.setVisibility(View.VISIBLE);
            if (!AndroidUtils.compareString(currentQuery, getCurrentText())) {
                currentQuery = getCurrentText();
                sendRequest();
            }
        }
    }

    private void sendRequest() {
        AppApplication.getInstance().getRequestQueue().cancelAll(RequestTags.AUTOCOMPLETE_LOCATION);

        String url = AutocompleteUrls.getAutoCompleteUrl(currentQuery);
        new CustomRequest(this, Request.Method.GET, url, RequestTags.AUTOCOMPLETE_LOCATION, this, null);
    }

    String getCurrentText() {
        return editText.getText().toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.AUTOCOMPLETE_LOCATION)) {
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error, boolean networkError) {
        if (requestTag.equalsIgnoreCase(RequestTags.AUTOCOMPLETE_LOCATION)) {

        }
    }

    @Override
    public void onRequestCompleted(String requestTag, Object response) {
        if (requestTag.equalsIgnoreCase(RequestTags.AUTOCOMPLETE_LOCATION)) {
            List<AutoCompleteResponse> mData = (List<AutoCompleteResponse>) response;
            if (mData != null && mData.size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);

                if (adapter == null) {
                    adapter = new LocationSearchAdapter(this, mData);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.setData(mData);
                }
            }
        }
    }
}
