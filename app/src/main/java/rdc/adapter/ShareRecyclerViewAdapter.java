package rdc.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rdc.avtivity.R;
import rdc.bean.ShareItem;


public class ShareRecyclerViewAdapter extends RecyclerView.Adapter<ShareRecyclerViewAdapter.ItemViewHolder> {

    private List<ShareItem> mList;
    private Context mContext;
    private OnClickShareItemListener mOnClickShareItemListener = null;

    public ShareRecyclerViewAdapter(List<ShareItem> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_share, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.icon.setImageDrawable(mList.get(position).getIcon());
        holder.label.setText(mList.get(position).getLabel());
        if (mOnClickShareItemListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickShareItemListener.OnClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView label;
        private ImageView icon;

        ItemViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.item_share_label_textView);
            icon = itemView.findViewById(R.id.item_share_icon_imageView);
        }
    }

    public void setOnClickShareItemListener(OnClickShareItemListener onClickShareItemListener) {
        mOnClickShareItemListener = onClickShareItemListener;
    }

    public interface OnClickShareItemListener {
        void OnClick(int position);
    }
}
