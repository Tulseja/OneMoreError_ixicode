package ixigo.example.apple.ixigohack.fragment;

import android.os.Bundle;

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
}
