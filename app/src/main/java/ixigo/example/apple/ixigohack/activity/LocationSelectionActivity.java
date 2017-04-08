package ixigo.example.apple.ixigohack.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.extras.RequestTags;
import ixigo.example.apple.ixigohack.serverApi.AppRequestListener;
import ixigo.example.apple.ixigohack.serverApi.CustomRequest;
import ixigo.example.apple.ixigohack.urls.AutocompleteUrls;
import ixigo.example.apple.ixigohack.utils.AndroidUtils;

/**
 * Created by apple on 08/04/17.
 */

public class LocationSelectionActivity extends BaseActivity implements TextWatcher, AppRequestListener {

    @BindView(R.id.edittext_location)
    EditText editText;
    @BindView(R.id.clear_search_text)
    FrameLayout clearEditTextButton;

    String currentQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);
        ButterKnife.bind(this);

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

        }
    }
}
