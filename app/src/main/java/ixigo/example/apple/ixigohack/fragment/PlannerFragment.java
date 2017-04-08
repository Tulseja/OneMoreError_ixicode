package ixigo.example.apple.ixigohack.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.extras.AppConstants;

/**
 * Created by apple on 08/04/17.
 */

public class PlannerFragment extends BaseFragment {

    int position;

    public static PlannerFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_POSITION, position);

        PlannerFragment fragment = new PlannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_planner, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        position = getArguments().getInt(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_POSITION);
    }
}
