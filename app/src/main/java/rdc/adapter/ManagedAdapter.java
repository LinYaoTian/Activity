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
import rdc.activity.MainActivity;
import rdc.avtivity.R;
import rdc.bean.ManagedActivity;

/**
 * Created by asus on 18-4-14.
 */

public class ManagedAdapter extends RecyclerView.Adapter<ManagedAdapter.ViewHolder> {
    private List<ManagedActivity> mManagedActivityList;
    private OnClickListener mOnClickListener;

    public ManagedAdapter(List<ManagedActivity> managedActivityList) {
        mManagedActivityList = managedActivityList;
    }

    public interface OnClickListener {
        void click();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_managed, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ManagedActivity activity = mManagedActivityList.get(position);

        holder.mSendTime.setText(activity.getSendTime());
//        holder.mImage.setImageURI(activity.getImageUrl());
        holder.mTitle.setText(activity.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.click();
                }
            }
        });
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

}
