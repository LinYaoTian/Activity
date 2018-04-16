package rdc.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rdc.adapter.ManagedAdapter;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;
import rdc.bean.ManagedActivity;
import rdc.contract.IManagedContract;
import rdc.presenter.ManagedPresenter;

public class ManageActivity extends BaseActivity<ManagedPresenter> implements IManagedContract.View {
    @BindView(R.id.rv_managed_activity)
    RecyclerView mActivityRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private List<ManagedActivity> mManagedActivityList;
    private ManagedAdapter mAdapter;


    @Override
    protected int setLayoutResID() {
        return R.layout.activity_manage;
    }

    @Override
    protected void initData() {
         mManagedActivityList = new ArrayList<>();
         mAdapter = new ManagedAdapter(mManagedActivityList,this);
         presenter.getManagedActivity();
    }

    @Override
    protected void initView() {
        initToolBar();
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
    private void  initToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    @Override
    public void setManagedActivity(List<ManagedActivity> list) {
        mManagedActivityList.clear();
        mManagedActivityList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public ManagedPresenter getInstance() {
        return new ManagedPresenter();
    }
}
