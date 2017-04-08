package ixigo.example.apple.ixigohack.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ixigo.example.apple.ixigohack.R;


/**
 * Created by Ashish on 04/06/16.
 */
public abstract class BaseFragment extends Fragment {

    View rootView;

    LinearLayout progressLayout, errorLayout;
    ProgressBar progressBarLoading;
    Button retryButton;

    LinearLayout emptyLayoutContainer;
    TextView emptyLayoutText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setProgressAndErrorLayoutVariables() {
        if (rootView != null) {
            progressLayout = (LinearLayout) rootView.findViewById(R.id.progress_layout);
            errorLayout = (LinearLayout) rootView.findViewById(R.id.error_layout);
            retryButton = (Button) rootView.findViewById(R.id.button_error);
            progressBarLoading = (ProgressBar) rootView.findViewById(R.id.progress_bar_loading);
        }
    }

    void setEmptyScreenVariables() {
        if (rootView != null) {
            emptyLayoutContainer = (LinearLayout) rootView.findViewById(R.id.empty_screen_container);
            emptyLayoutText = (TextView) rootView.findViewById(R.id.empty_screen_text);
        }
    }

    void showEmptyScreenLayout(String text) {
        emptyLayoutContainer.setVisibility(View.VISIBLE);
        emptyLayoutText.setText(text);
    }

    void hideEmptyScreenLayout() {
        emptyLayoutContainer.setVisibility(View.GONE);
    }

    public void showProgressLayout() {
        try {
            progressLayout.setVisibility(View.VISIBLE);
            progressBarLoading.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void showErrorLayout() {
        try {
            errorLayout.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void hideProgressLayout() {
        try {
            progressLayout.setVisibility(View.GONE);
            progressBarLoading.setVisibility(View.GONE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void hideErrorLayout() {
        try {
            errorLayout.setVisibility(View.GONE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
