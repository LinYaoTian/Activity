package rdc.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rdc.avtivity.R;
import rdc.bean.Organization;

import static rdc.configs.OrganizationItemType.sEMPTY;
import static rdc.configs.OrganizationItemType.sORGANIZATION;

/**
 * Created by asus on 18-4-14.
 */

public class OrganizationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Organization> mOrganizationList;
    private OnClickListener mClickListener;
    private Context mContext;
    public interface OnClickListener {
        void click();
    }

    public OrganizationListAdapter(List<Organization> organizationList, Context context) {
        mOrganizationList = organizationList;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType==sORGANIZATION){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_organization,parent,false);
            return new ViewHolder(view);
        }else if (viewType==sEMPTY){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_organization,parent,false);
            return new EmptyViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if (holder instanceof ViewHolder){
           Organization organization = mOrganizationList.get(position);
           Glide.with(mContext).load(organization.getImage().getUrl()).into( ((ViewHolder)holder).mImage);
           ((ViewHolder)holder).mName.setText(organization.getName());
           ((ViewHolder)holder).mMessage.setText(organization.getMessage());
           ((ViewHolder)holder).mTime.setText(organization.getTime());
           ((ViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (mClickListener!=null){
                       mClickListener.click();
                   }
               }
           });
       }
    }


    @Override
    public int getItemViewType(int position) {
        return mOrganizationList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mOrganizationList.size();
    }

    public void setClickListener(OnClickListener clickListener) {
        mClickListener = clickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imv_image)
        ImageView mImage;
        @BindView(R.id.tv_name)
        TextView mName;
        @BindView(R.id.tv_message)
        TextView mMessage;
        @BindView(R.id.tv_time)
        TextView mTime;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
