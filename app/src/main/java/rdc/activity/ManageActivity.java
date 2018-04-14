package rdc.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rdc.adapter.ManagedAdapter;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;
import rdc.bean.ManagedActivity;

public class ManageActivity extends BaseActivity {
    @BindView(R.id.rv_managed_activity)
    RecyclerView mActivityRecyclerView;
    private List<ManagedActivity> mManagedActivityList;
    private ManagedAdapter mAdapter;

    @Override
    public BasePresenter getInstance() {
        return null;
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_manage;
    }

    @Override
    protected void initData() {
         mManagedActivityList = new ArrayList<>();
         for (int i=0;i<10;i++){
             ManagedActivity managedActivity = new ManagedActivity();
             managedActivity.setTitle("计算机研发中心");
             managedActivity.setSendTime("今天下午");
             mManagedActivityList.add(managedActivity);
         }
         mAdapter = new ManagedAdapter(mManagedActivityList);
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

    public static void actionStart(Context context){
        Intent intent = new Intent(context,ManageActivity.class);
        context.startActivity(intent);
    }
}
