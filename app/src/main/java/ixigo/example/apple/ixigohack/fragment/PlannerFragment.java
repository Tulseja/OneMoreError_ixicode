package ixigo.example.apple.ixigohack.fragment;

import android.os.Bundle;

import ixigo.example.apple.ixigohack.extras.AppConstants;

/**
 * Created by apple on 08/04/17.
 */

public class PlannerFragment extends BaseFragment {

    public static PlannerFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_POSITION, position);

        PlannerFragment fragment = new PlannerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
