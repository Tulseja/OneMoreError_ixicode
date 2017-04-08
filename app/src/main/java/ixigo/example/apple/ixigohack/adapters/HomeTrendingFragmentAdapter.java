package ixigo.example.apple.ixigohack.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.objects.trending.TrendingFragmentResponse;
import ixigo.example.apple.ixigohack.serverApi.ImageRequestManager;

/**
 * Created by hp on 8/4/17.
 */

public class HomeTrendingFragmentAdapter extends RecyclerView.Adapter<HomeTrendingFragmentAdapter.MyViewHolder> {

    private Context mContext;
    private List<TrendingFragmentResponse.Flight> mData;

    LayoutInflater layoutInflater;

    public HomeTrendingFragmentAdapter(Context mContext, TrendingFragmentResponse list) {
        this.mContext = mContext;
        this.mData = list.getData().getFlight();

        layoutInflater = LayoutInflater.from(mContext);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nameOfPlace)
        TextView placeName;

        @BindView(R.id.imageOfPlace)
        ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(TrendingFragmentResponse.Flight data) {
            placeName.setText(data.getCityName());
            ImageRequestManager.requestImage(mContext, thumbnail, data.getImage());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(layoutInflater.inflate(R.layout.trending_places_list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TrendingFragmentResponse.Flight data = mData.get(position);

        holder.setData(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
