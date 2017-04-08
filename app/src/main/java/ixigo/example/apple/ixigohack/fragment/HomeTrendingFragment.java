package ixigo.example.apple.ixigohack.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.adapters.HomeTrendingFragmentAdapter;
import ixigo.example.apple.ixigohack.extras.RequestTags;
import ixigo.example.apple.ixigohack.objects.trending.TrendingFragmentResponse;
import ixigo.example.apple.ixigohack.serverApi.AppRequestListener;
import ixigo.example.apple.ixigohack.serverApi.CustomRequest;
import ixigo.example.apple.ixigohack.urls.TrendingAppUrls;

/**
 * Created by apple on 08/04/17.
 */

public class HomeTrendingFragment extends BaseFragment implements AppRequestListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    private HomeTrendingFragmentAdapter adapter;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        loadData();
    }

    private void loadData() {
        String url = TrendingAppUrls.getTrendingPlacesUrl();
        new CustomRequest(getActivity(), Request.Method.GET, url, RequestTags.TRENDING_PLACES, this, null);
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.TRENDING_PLACES)) {

        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error, boolean networkError) {
        if (requestTag.equalsIgnoreCase(RequestTags.TRENDING_PLACES)) {

        }
    }

    @Override
    public void onRequestCompleted(String requestTag, Object response) {
        if (requestTag.equalsIgnoreCase(RequestTags.TRENDING_PLACES)) {
            TrendingFragmentResponse mData = (TrendingFragmentResponse) response;

            adapter = new HomeTrendingFragmentAdapter(getActivity(), mData);
            recyclerView.setAdapter(adapter);
        }
    }
}
