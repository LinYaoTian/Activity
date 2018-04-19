package rdc.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;

import butterknife.BindView;
import rdc.avtivity.R;
import rdc.base.BaseRecyclerViewAdapter;
import rdc.bean.ItemTag;
import rdc.listener.IItemTouchHelperAdapter;
import rdc.listener.IItemTouchHelperViewHolder;
import rdc.listener.OnStartDragListener;

/**
 * Created by Lin Yaotian on 2018/4/19.
 */

public class TagsRvAdapter extends BaseRecyclerViewAdapter<ItemTag> implements IItemTouchHelperAdapter {

    private OnClickSwitchListener mOnClickSwitchListener;
    private final OnStartDragListener mDragListener;

    public TagsRvAdapter(OnStartDragListener onStartDragListener){
        mDragListener = onStartDragListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag_list,parent,false);
        return new ItemTagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemTagViewHolder)holder).bindView(mDataList.get(position));
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDataList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    class ItemTagViewHolder extends BaseRvHolder implements IItemTouchHelperViewHolder {

        @BindView(R.id.tv_tag_item_act_tags)
        TextView text;
        @BindView(R.id.iv_menu_item_act_tags)
        ImageView menu;
        @BindView(R.id.sc_subscribe_item_act_tags)
        SwitchCompat switchCompat;

        public ItemTagViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(final ItemTag itemTag) {
            text.setText(itemTag.getTag());
            switchCompat.setChecked(itemTag.isChecked());
            switchCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean b = itemTag.isChecked();
                    mDataList.get(getLayoutPosition()).setChecked(!b);
                    mOnClickSwitchListener.onClick(getLayoutPosition(), !b);
                }
            });
            menu.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction()
                            == MotionEvent.ACTION_DOWN) {
                        mDragListener.onStartDrag(ItemTagViewHolder.this);
                    }
                    return false;
                }
            });
        }

        @Override
        public void onItemSelected() {
            itemView.setTranslationZ(10);
        }

        @Override
        public void onItemClear() {
            itemView.setTranslationZ(0);
        }
    }

    public void setOnClickSwitchListener(OnClickSwitchListener onClickSwitchListener) {
        mOnClickSwitchListener = onClickSwitchListener;
    }

    public interface OnClickSwitchListener {
        void onClick(int position, boolean isChecked);
    }
}
