package ixigo.example.apple.ixigohack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ixigo.example.apple.ixigohack.activity.BaseActivity;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.objects.autoComplete.AutoCompleteResponse;

/**
 * Created by apple on 08/04/17.
 */

public class LocationSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<AutoCompleteResponse> mData;

    LayoutInflater layoutInflater;

    public LocationSearchAdapter(Context context, List<AutoCompleteResponse> mData) {
        this.context = context;
        this.mData = mData;

        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<AutoCompleteResponse> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(layoutInflater.inflate(R.layout.location_list_item_layout, parent, false));
    }

    class Holder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;
        AutoCompleteResponse obj;

        @OnClick(R.id.location_list_item_container)
        void onLocationClick() {
            if (context != null && obj != null) {
                Intent intent = new Intent();
                intent.putExtra(AppConstants.INTENT_EXTRAS.EXTRA_LOCATION_AUTOCOMPLETE, obj);
                ((BaseActivity) context).setResult(Activity.RESULT_OK, intent);
                ((BaseActivity) context).finish();
            }
        }

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            binding = DataBindingUtil.bind(itemView);
        }

        public void setData(AutoCompleteResponse data) {
            this.obj = data;
            binding.setVariable(BR.search, data);
            binding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderCom, int position) {
        Holder holder = (Holder) holderCom;
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
