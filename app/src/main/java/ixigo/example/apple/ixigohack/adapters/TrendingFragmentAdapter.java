package ixigo.example.apple.ixigohack.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.model.TrendingFragmentModel;

/**
 * Created by hp on 8/4/17.
 */

public class TrendingFragmentAdapter extends RecyclerView.Adapter<TrendingFragmentAdapter.MyViewHolder> {

    private Context mContext;
    private List<TrendingFragmentModel> trendingList ;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nameOfPlace)
        TextView placeName;

        @BindView(R.id.imageOfPlace)
        ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
        public TrendingFragmentAdapter(Context mContext, List<TrendingFragmentModel> list) {
            this.mContext = mContext;
            this.trendingList = list;
        }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_trending, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TrendingFragmentModel trendingFragmentModel = trendingList.get(position);
        holder.placeName.setText(trendingFragmentModel.getPlaceName());
        Glide.with(mContext).load(trendingFragmentModel.getImageUrl()).into(holder.thumbnail);
    }
    @Override
    public int getItemCount() {
        return trendingList.size();
    }
}
