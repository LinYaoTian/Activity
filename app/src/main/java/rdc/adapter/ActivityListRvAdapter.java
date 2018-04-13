package rdc.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.BinderThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.stream.UrlLoader;

import butterknife.BindView;
import rdc.avtivity.R;
import rdc.base.BaseRecyclerViewAdapter;
import rdc.bean.ItemActivity;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public class ActivityListRvAdapter extends BaseRecyclerViewAdapter<ItemActivity> {

    private Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_main,parent,false);
        return new ActivityHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ActivityHolder)holder).bindView(mDataList.get(position));
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
        protected void bindView(ItemActivity itemActivity) {
            if (itemActivity.getSawNum() == 120){
                mIvCover.setImageResource(R.drawable.iv_test_cover);
            }else {
                mIvCover.setImageResource(R.drawable.iv_test_folwer);
            }

//            Glide.with(mContext).load(mContext.getResources().getDrawable(Integer.valueOf(itemActivity.getCoverImageUrl()))).into(mIvCover);
            mTvTitle.setText(itemActivity.getTitle());
            mTvLocation.setText(itemActivity.getLocation());
            mTvTime.setText(itemActivity.getTime());
            mTvSawNum.setText(String.valueOf(itemActivity.getSawNum()));
        }
    }
}
