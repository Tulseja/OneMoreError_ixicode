package ixigo.example.apple.ixigohack.adapters;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;
import ixigo.example.apple.ixigohack.objects.trending.TrendingFragmentResponse;
import ixigo.example.apple.ixigohack.serverApi.ImageRequestManager;

/**
 * Created by hp on 8/4/17.
 */

public class PlacePickerListingAdapter extends RecyclerView.Adapter<PlacePickerListingAdapter.MyViewHolder> {

    private Context mContext;
    private List<PlacePickerResponse.PlacesToVisit> mData;

    LayoutInflater layoutInflater;
    OnPlaceClickInterface onPlaceClickInterface;

    public PlacePickerListingAdapter(Context mContext, List<PlacePickerResponse.PlacesToVisit> list, OnPlaceClickInterface onPlaceClickInterface) {
        this.mContext = mContext;
        this.mData = list;
        this.onPlaceClickInterface = onPlaceClickInterface;

        layoutInflater = LayoutInflater.from(mContext);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nameOfPlace)
        TextView placeName;

        @BindView(R.id.imageOfPlace)
        ImageView thumbnail;

        PlacePickerResponse.PlacesToVisit obj;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.card_view)
        void onItemClick() {
            if (obj != null && onPlaceClickInterface != null) {
                onPlaceClickInterface.onPlaceClicked(obj);
            }
        }

        public void setData(PlacePickerResponse.PlacesToVisit data) {
            this.obj = data;
            placeName.setText(data.getName());
            ImageRequestManager.requestImage(mContext, thumbnail, data.getKeyImageUrl());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(layoutInflater.inflate(R.layout.trending_places_list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PlacePickerResponse.PlacesToVisit data = mData.get(position);

        holder.setData(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnPlaceClickInterface {
        void onPlaceClicked(PlacePickerResponse.PlacesToVisit data);
    }
}
