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
import rdc.bean.Activity;
import rdc.bean.Organization;
import rdc.bean.OrganizationActivity;

import static rdc.configs.OrganizationItemType.sACTIVITY;
import static rdc.configs.OrganizationItemType.sORGANIZATION;

/**
 * Created by asus on 18-4-18.
 */

public class OrganizationActivityListAdapter extends RecyclerView.Adapter {

    private List<OrganizationActivity> mActivities;
    private Context mContext;

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
        } else if (viewType == sORGANIZATION) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_organization_header, parent, false);
            return new HeaderViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrganizationActivity activity = mActivities.get(position);
        if (holder instanceof ItemViewHolder) {
            Glide.with(mContext).load(activity.getCoverImageUrl()).into(((ItemViewHolder) holder).mImageView);
            ((ItemViewHolder)holder).mTitle.setText(activity.getTitle());
            ((ItemViewHolder)holder).mPlace.setText(activity.getLocation());
            ((ItemViewHolder)holder).mTime.setText(activity.getType());
            ((ItemViewHolder)holder).mSawNum.setText(activity.getSawNum());

        }else if (holder instanceof  HeaderViewHolder){

            Glide.with(mContext).load(activity.getPhoto()).into(((HeaderViewHolder)holder).mPhoto);
            Glide.with(mContext).load(activity.getImage()).into(((HeaderViewHolder)holder).mImage);
            ((HeaderViewHolder)holder).mName.setText(activity.getName());
            ((HeaderViewHolder)holder).mIntroduction.setText(activity.getIntroduction());


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

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_photo)
        ImageView mPhoto;
        @BindView(R.id.imv_image)
        ImageView mImage;
        @BindView(R.id.tv_name)
        TextView mName;
        @BindView(R.id.tv_introduction)
        TextView mIntroduction;


        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
