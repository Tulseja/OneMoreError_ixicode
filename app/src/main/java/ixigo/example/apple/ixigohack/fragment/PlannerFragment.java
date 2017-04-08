package ixigo.example.apple.ixigohack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.adapters.PlannerFragmentListAdapter;
import ixigo.example.apple.ixigohack.eventBus.EventBusHelper;
import ixigo.example.apple.ixigohack.eventBus.PlacePickerEventBus;
import ixigo.example.apple.ixigohack.extras.AppConstants;

/**
 * Created by apple on 08/04/17.
 */

public class PlannerFragment extends BaseFragment {

    int position;

    @BindView(R.id.planner_fragment_recycler)
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    PlannerFragmentListAdapter adapter;

    @Override
    public void onDestroy() {
        EventBusHelper.unRegister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlacePickerEventBus.OnPlacePicked obj) {
        if (obj != null && position == obj.getFragmentPosition()) {
            if(adapter!=null){
                adapter.addData(obj.getData());
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusHelper.register(this);
    }

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

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlannerFragmentListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }
}
