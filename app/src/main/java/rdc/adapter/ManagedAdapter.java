package rdc.adapter;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rdc.activity.MainActivity;
import rdc.avtivity.R;
import rdc.bean.ManagedActivity;

import static rdc.configs.ManagedItemType.sEMPTY;
import static rdc.configs.ManagedItemType.sMANAGED;

/**
 * Created by asus on 18-4-14.
 */

public class ManagedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ManagedActivity> mManagedActivityList;
    private OnClickListener mOnClickListener;
    private Context mContext;
    public ManagedAdapter(List<ManagedActivity> managedActivityList,Context context) {
        mManagedActivityList = managedActivityList;
        mContext = context;
    }

    public interface OnClickListener {
        void click();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType==sMANAGED){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_managed, parent, false);
            return new ViewHolder(view);
        }else if (viewType==sEMPTY){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_managed,parent,false);
            return new EmptyViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if (holder instanceof ViewHolder){
           ManagedActivity activity = mManagedActivityList.get(position);
           Glide.with(mContext).load(activity.getImageUrl()).into(((ViewHolder)holder).mImage);
           ((ViewHolder)holder).mSendTime.setText(activity.getSendTime());
           ((ViewHolder)holder).mTitle.setText(activity.getTitle());
           ((ViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (mOnClickListener != null) {
                       mOnClickListener.click();
                   }
               }
           });
       }
    }


    @Override
    public int getItemViewType(int position) {
        return mManagedActivityList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mManagedActivityList.size();
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_image)
        ImageView mImage;
        @BindView(R.id.tv_title)
        TextView mTitle;
        @BindView(R.id.tv_send_time)
        TextView mSendTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class EmptyViewHolder  extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
