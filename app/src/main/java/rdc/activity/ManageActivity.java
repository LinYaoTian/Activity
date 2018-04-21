package rdc.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import rdc.util.ACacheUtil;
import rdc.util.LoadingDialogUtil;
import rdc.util.ObjectCastUtil;

public class ManageActivity extends BaseActivity<ManagedPresenter> implements IManagedContract.View {
    @BindView(R.id.rv_managed_activity)
    RecyclerView mActivityRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    private List<ManagedActivity> mManagedActivityList;
    private ManagedAdapter mAdapter;
    private Dialog mLoadingDialog;
    private ACacheUtil mACacheUtil;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_manage;
    }

    @Override
    protected void initData() {
        mManagedActivityList = new ArrayList<>();
        mAdapter = new ManagedAdapter(mManagedActivityList, this);
        mACacheUtil = ACacheUtil.get(getApplicationContext());
        //先从缓存中读取，缓存没有联网获取数据

        if (ObjectCastUtil.cast(mACacheUtil.getAsObject("manage")) != null) {
            mManagedActivityList.addAll((ArrayList) ObjectCastUtil.cast(mACacheUtil.getAsObject("manage")));
        } else {
            presenter.getManagedActivity();
            mLoadingDialog = LoadingDialogUtil.createLoadingDialog(ManageActivity.this, "正在加载数据...");

        }
    }

    @Override
    protected void initView() {
        initToolBar();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mActivityRecyclerView.setLayoutManager(manager);
        mActivityRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {
          mAdapter.setOnClickListener(new ManagedAdapter.OnClickListener() {
              @Override
              public void click(ManagedActivity activity) {
                  //跳转到管理活动的详情界面
                  CheckManagedActivity.actionStart(ManageActivity.this,activity.getId());
              }
          });
    }

    /**
     * 启动方法
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ManageActivity.class);
        context.startActivity(intent);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * 设置ToolBar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /**
     * 显示我管理的活动的列表
     * @param list
     */
    @Override
    public void setManagedActivity(List<ManagedActivity> list) {
        mACacheUtil.put("manage",(ArrayList)list,5*60);
        mManagedActivityList.clear();
        mManagedActivityList.addAll(list);
        mAdapter.notifyDataSetChanged();
        LoadingDialogUtil.closeDialog(mLoadingDialog);
    }

    @Override
    public ManagedPresenter getInstance() {
        return new ManagedPresenter();
    }
}
