package rdc.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rdc.adapter.OrganizationActivityListAdapter;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.bean.Organization;
import rdc.bean.OrganizationActivity;
import rdc.contract.IOrganizationDetailsContract;
import rdc.presenter.OrganizationDetailsPresenter;
import rdc.util.ACacheUtil;
import rdc.util.LoadingDialogUtil;
import rdc.util.ObjectCastUtil;

import static rdc.configs.OrganizationItemType.sORGANIZATION;

public class OrganizationDetailsActivity extends BaseActivity<OrganizationDetailsPresenter> implements IOrganizationDetailsContract.View {

    @BindView(R.id.rv_organization)
    RecyclerView mActivityRecyclerView;
    private List<OrganizationActivity> mActivities;
    private ACacheUtil mACacheUtil;
    private OrganizationActivityListAdapter mAdapter;
    private String mId;
    private  Organization organization;
    private Dialog mLoadingDialog;



    @Override
    protected void initData() {
        organization = (Organization) getIntent().getBundleExtra("bundle").get("organization");
        mId = organization.getId();
        mACacheUtil = ACacheUtil.get(getApplicationContext());
        mActivities = new ArrayList<>();
        mActivities.add(initHeaderOrganization(organization));
        mAdapter = new OrganizationActivityListAdapter(mActivities,this);
//        if (ObjectCastUtil.cast(mACacheUtil.getAsObject(mId))!=null){
//            mActivities.addAll((ArrayList)ObjectCastUtil.cast(mACacheUtil.getAsObject("id")));
//            mAdapter.notifyDataSetChanged();
//        }else {
            presenter.getManagedActivity(organization);
        mLoadingDialog = LoadingDialogUtil.createLoadingDialog(OrganizationDetailsActivity.this, "正在加载数据...");

//        }
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mActivityRecyclerView.setLayoutManager(manager);
        mActivityRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }


    @Override
    public void setManagedActivity(List<OrganizationActivity> list) {
        mACacheUtil.put(mId,(ArrayList)list);

        mActivities.addAll(list);
        mAdapter.notifyDataSetChanged();
        LoadingDialogUtil.closeDialog(mLoadingDialog);
    }

    @Override
    public OrganizationDetailsPresenter getInstance() {
        return new OrganizationDetailsPresenter();
    }



    private OrganizationActivity initHeaderOrganization(Organization organization){
        OrganizationActivity activity = new OrganizationActivity();
        activity.setPhoto(organization.getPhoto().getUrl());
        activity.setImage(organization.getImage().getUrl());
        activity.setName(organization.getName());
        activity.setType(sORGANIZATION);
        activity.setIntroduction(organization.getIntroduction());
        return activity;
    }

    @Override
    protected int setLayoutResID() {
        requestWindowFeature(1);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        return R.layout.activity_organization_details;
    }

    public static void actionStart(Context context, Organization organization) {
        Intent intent = new Intent(context, OrganizationDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("organization",organization);
        intent.putExtra("bundle",bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivities.clear();
    }
}
