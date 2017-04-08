package ixigo.example.apple.ixigohack.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;

/**
 * Created by apple on 08/04/17.
 */

public class HomeTrendingFragment extends BaseFragment {

    public static HomeTrendingFragment newInstance() {
        Bundle args = new Bundle();

        HomeTrendingFragment fragment = new HomeTrendingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_trending, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }
}
