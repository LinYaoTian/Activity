package rdc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rdc.avtivity.R;
import rdc.bean.OrganizationActivity;

import static rdc.configs.OrganizationItemType.sACTIVITY;
import static rdc.configs.OrganizationItemType.sORGANIZATION;

/**
 * Created by asus on 18-4-18.
 */

public class OrganizationActivityListAdapter extends RecyclerView.Adapter {

    private List<OrganizationActivity> mActivities;
    private Context mContext;
    private OnClickListener mClickListener;
    public interface OnClickListener{
        void click(OrganizationActivity activity);
    }
    public OrganizationActivityListAdapter(List<OrganizationActivity> activities, Context context) {
        mActivities = activities;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == sACTIVITY) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_organization_activity, parent, false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            final OrganizationActivity activity = mActivities.get(position);
            if (holder instanceof ItemViewHolder) {
                Glide.with(mContext).load(activity.getCoverImageUrl()).into(((ItemViewHolder) holder).mImageView);
                ((ItemViewHolder)holder).mTitle.setText(activity.getTitle());
                ((ItemViewHolder)holder).mPlace.setText(activity.getLocation());
                ((ItemViewHolder)holder).mTime.setText(activity.getTime());
                ((ItemViewHolder)holder).mSawNum.setText(activity.getSawNum()+"");
                ((ItemViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mClickListener!=null){
                            mClickListener.click(activity);
                        }
                    }
                });

            }

    }

    @Override
    public int getItemCount() {
        return mActivities.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mActivities.get(position).getType();
    }


    public void setClickListener(OnClickListener clickListener) {
        mClickListener = clickListener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_image)
        ImageView mImageView;
        @BindView(R.id.tv_title)
        TextView mTitle;
        @BindView(R.id.tv_place)
        TextView mPlace;
        @BindView(R.id.tv_time)
        TextView mTime;
        @BindView(R.id.tv_saw_num)
        TextView mSawNum;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
