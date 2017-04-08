package ixigo.example.apple.ixigohack.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.BR;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;

/**
 * Created by apple on 08/04/17.
 */

public class PlannerFragmentListAdapter extends RecyclerView.Adapter<PlannerFragmentListAdapter.PlannerHolder> {

    Context context;
    List<PlacePickerResponse.PlacesToVisit> mData;

    LayoutInflater layoutInflater;

    public void addData(PlacePickerResponse.PlacesToVisit data) {
        mData.add(0, data);
        Collections.sort(mData);
        notifyDataSetChanged();
    }

    public PlannerFragmentListAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public PlannerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlannerHolder(layoutInflater.inflate(R.layout.planner_fragment_list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(PlannerHolder holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class PlannerHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public PlannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            binding = DataBindingUtil.bind(itemView);
        }

        public void setData(PlacePickerResponse.PlacesToVisit placesToVisit) {
            binding.setVariable(BR.data, placesToVisit);
            binding.executePendingBindings();
        }
    }
}
