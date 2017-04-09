package ixigo.example.apple.ixigohack.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import ixigo.example.apple.ixigohack.BR;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.objects.FirebaseDataObject;
import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;
import ixigo.example.apple.ixigohack.utils.AndroidUtils;
import ixigo.example.apple.ixigohack.utils.UIUtils;

/**
 * Created by apple on 08/04/17.
 */

public class PlannerFragmentListAdapter extends RecyclerView.Adapter<PlannerFragmentListAdapter.PlannerHolder> {

    Context context;
    List<FirebaseDataObject> mData;

    LayoutInflater layoutInflater;
    PlannerFragmentInterface plannerFragmentInterface;

    AlertDialog alertDialog;

    public void addData(FirebaseDataObject data) {
        mData.add(0, data);
        Collections.sort(mData);
        notifyDataSetChanged();
    }

    public void changeData(FirebaseDataObject data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        for (int i = 0; i < mData.size(); i++) {
            if (AndroidUtils.compareString(mData.get(i).getFirebaseKey(), data.getFirebaseKey())) {
                mData.set(i, data);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void removeData(FirebaseDataObject data) {
        if (mData == null) {
            return;
        }
        for (int i = 0; i < mData.size(); i++) {
            if (AndroidUtils.compareString(mData.get(i).getFirebaseKey(), data.getFirebaseKey())) {
                mData.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public PlannerFragmentListAdapter(Context context, PlannerFragmentInterface plannerFragmentInterface) {
        this.context = context;
        this.plannerFragmentInterface = plannerFragmentInterface;
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

    public void updateData(FirebaseDataObject data) {
        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                if (AndroidUtils.compareString(mData.get(i).getFirebaseKey(), data.getFirebaseKey())) {
                    mData.set(i, data);
                    notifyItemChanged(i);
                    break;
                }
            }
        }
    }

    class PlannerHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;
        FirebaseDataObject data;

        @OnClick(R.id.card_view)
        void onItemClick() {
            if (context != null && data != null) {
                plannerFragmentInterface.onUpdateClick(data);
            }
        }

        @OnLongClick(R.id.card_view)
        boolean deleteItem() {
            if (context != null && data != null) {
                UIUtils.makeSimpleAlertDialogWithNegativeButton("Delete visit",
                        "Are you sure you want to delete this item?",
                        "YES",
                        context,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (plannerFragmentInterface != null && data != null) {
                                    plannerFragmentInterface.onDeleteClick(data);
                                }
                            }
                        }, "NO");
            }
            return true;
        }

        public PlannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            binding = DataBindingUtil.bind(itemView);
        }

        public void setData(FirebaseDataObject obj) {
            this.data = obj;
            binding.setVariable(BR.data, obj);
            binding.executePendingBindings();
        }
    }

    public interface PlannerFragmentInterface {
        void onUpdateClick(FirebaseDataObject obj);

        void onDeleteClick(FirebaseDataObject obj);
    }
}
