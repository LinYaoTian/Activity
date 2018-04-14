package rdc.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rdc.avtivity.R;
import rdc.bean.Organization;

/**
 * Created by asus on 18-4-14.
 */

public class OrganizationListAdapter extends RecyclerView.Adapter<OrganizationListAdapter.ViewHolder> {
    private List<Organization> mOrganizationList;
    private OnClickListener mClickListener;
    public interface OnClickListener {
        void click();
    }

    public OrganizationListAdapter(List<Organization> organizationList) {
        mOrganizationList = organizationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_organization,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Organization organization = mOrganizationList.get(position);
        holder.mImage.setImageResource(R.drawable.photo);
        holder.mName.setText(organization.getName());
        holder.mMessage.setText(organization.getMessage());
        holder.mTime.setText(organization.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener!=null){
                    mClickListener.click();
                }
            }
        });
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
}
