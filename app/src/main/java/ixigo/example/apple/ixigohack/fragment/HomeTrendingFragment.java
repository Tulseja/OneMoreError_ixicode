package ixigo.example.apple.ixigohack.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.adapters.TrendingFragmentAdapter;
import ixigo.example.apple.ixigohack.model.TrendingFragmentModel;

/**
 * Created by apple on 08/04/17.
 */

public class HomeTrendingFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private TrendingFragmentAdapter adapter;

    private List<TrendingFragmentModel> trendingFragmentModelList ;
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

        trendingFragmentModelList = new ArrayList<>();
        adapter = new TrendingFragmentAdapter(getContext(),trendingFragmentModelList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
