package rdc.adapter;

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
import rdc.avtivity.R;
import rdc.base.BaseRecyclerViewAdapter;
import rdc.bean.Activity;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public class ActivitiesRvAdapter extends BaseRecyclerViewAdapter<Activity> {

    private Context mContext;

    public String getActivityObjectId(int position){
        return mDataList.get(position).getObjectId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new ActivityHolder(mFooterView);
        }else if (mNoneView != null && viewType == TYPE_NONE){
            return new ActivityHolder(mNoneView);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_main,parent,false);
        return new ActivityHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TYPE_NORMAL:
                ((ActivityHolder)holder).bindView(mDataList.get(position));
                break;
            case TYPE_NONE:
                break;
            case TYPE_FOOTER:
                break;
        }
    }

    class ActivityHolder extends BaseRvHolder{

        @BindView(R.id.iv_cover_item_act_main)
        ImageView mIvCover;
        @BindView(R.id.tv_activity_title_item_act_main)
        TextView mTvTitle;
        @BindView(R.id.tv_location_item_act_main)
        TextView mTvLocation;
        @BindView(R.id.tv_time_item_act_main)
        TextView mTvTime;
        @BindView(R.id.tv_saw_num_item_act_main)
        TextView mTvSawNum;


        public ActivityHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Activity activity) {
            if (activity.getImage()!= null){
                Glide.with(mContext).load(activity.getImage().getUrl()).into(mIvCover);
            }
            mTvTitle.setText(activity.getTitle());
            mTvLocation.setText(activity.getPlace());
            mTvTime.setText(activity.getTime());
            mTvSawNum.setText(String.valueOf(activity.getSawnum()));
        }
    }
}
