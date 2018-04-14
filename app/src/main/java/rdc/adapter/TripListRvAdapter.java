package rdc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rdc.avtivity.R;
import rdc.bean.Trip;

import static rdc.configs.TripItemType.sACTIVITY;
import static rdc.configs.TripItemType.sDIVIDER;
import static rdc.configs.TripItemType.sEMPTY;

/**
 * Created by asus on 18-4-14.
 */

public class TripListRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Trip> mTripList;
    private OnClickListener mOnClickListener;



    public interface OnClickListener{
        void click();
    }
    public TripListRvAdapter(List<Trip> tripList) {
        mTripList = tripList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType==sACTIVITY){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_trip,parent,false);
            return new ActivityViewHolder(view);
        }else if (viewType==sEMPTY){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_trip,parent,false);
            return new TipViewHolder(view);
        }else if (viewType==sDIVIDER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_trip,parent,false);
            return new TipViewHolder(view);
        }else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ActivityViewHolder){
            Trip trip = mTripList.get(position);
            ((ActivityViewHolder)holder).mTvPlace.setText(trip.getLocation());
            ((ActivityViewHolder)holder).mTvTime.setText(trip.getTime());
            ((ActivityViewHolder)holder).mTvTitle.setText(trip.getTitle());
            ((ActivityViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnClickListener!=null){
                        mOnClickListener.click();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTripList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return mTripList.get(position).getType();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imv_trip_image)
        ImageView mImageView;
        @BindView(R.id.tv_trip_title)
        TextView mTvTitle;
        @BindView(R.id.tv_trip_time)
        TextView mTvTime;
        @BindView(R.id.tv_trip_place)
        TextView mTvPlace;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class TipViewHolder extends RecyclerView.ViewHolder{
        public TipViewHolder(View itemView) {
            super(itemView);
        }
    }
}
