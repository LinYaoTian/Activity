package rdc.activity;

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

public class ConcernedActivity extends BaseActivity<OrganizationPresenter> implements IOrganizationContract.View {

    @BindView(R.id.rv_concerned_organization)
    RecyclerView mConcernedRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private List<Organization> mOrganizationList;
    private OrganizationListAdapter mAdapter;

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
//        for (int i = 0; i < 10; i++) {
//            Organization organization = new Organization();
//            organization.setName("计算机研发中心");
//            organization.setTime("今天下午");
//            organization.setMessage("颜色的飞机啊电视机分厘卡是快递放假");
//            Log.e("TAG", "initData: " );
//            mOrganizationList.add(organization);
//        }
    }

    @Override
    protected void initView() {
        initToolBar();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mConcernedRecyclerView.setLayoutManager(manager);


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
