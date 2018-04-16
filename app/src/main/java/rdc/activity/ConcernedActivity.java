package rdc.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rdc.adapter.OrganizationListAdapter;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;
import rdc.bean.Organization;
import rdc.bean.User;
import rdc.contract.IOrganizationContract;
import rdc.presenter.OrganizationPresenter;
import rdc.util.LoadingDialogUtil;

public class ConcernedActivity extends BaseActivity<OrganizationPresenter> implements IOrganizationContract.View {

    @BindView(R.id.rv_concerned_organization)
    RecyclerView mConcernedRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private List<Organization> mOrganizationList;
    private OrganizationListAdapter mAdapter;
    private Dialog mDialog;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_concerned;
    }

    @Override
    protected void initData() {
        mOrganizationList = new ArrayList<>();
        mAdapter = new OrganizationListAdapter(mOrganizationList,this);
        mConcernedRecyclerView.setAdapter(mAdapter);
        presenter.getConcernedOrganization();

    }

    @Override
    protected void initView() {
        initToolBar();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mConcernedRecyclerView.setLayoutManager(manager);
        mDialog = LoadingDialogUtil.createLoadingDialog(ConcernedActivity.this,"正在加载数据...");

    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ConcernedActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setOrganization(List<Organization> list) {
        mOrganizationList.clear();
        mOrganizationList.addAll(list);
        mAdapter.notifyDataSetChanged();
        LoadingDialogUtil.closeDialog(mDialog);
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
    private void  initToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public OrganizationPresenter getInstance() {
        return new OrganizationPresenter();

    }
}
