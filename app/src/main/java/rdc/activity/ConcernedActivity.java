package rdc.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rdc.adapter.OrganizationListAdapter;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;
import rdc.bean.Organization;

public class ConcernedActivity extends BaseActivity {

    @BindView(R.id.rv_concerned_organization)
    RecyclerView mConcernedRecyclerView;
    private List<Organization> mOrganizationList;
    private OrganizationListAdapter mAdapter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_concerned;
    }

    @Override
    protected void initData() {
        mOrganizationList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Organization organization = new Organization();
            organization.setName("计算机研发中心");
            organization.setTime("今天下午");
            organization.setMessage("颜色的飞机啊电视机分厘卡是快递放假");
            Log.e("TAG", "initData: " );
            mOrganizationList.add(organization);
        }
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mConcernedRecyclerView.setLayoutManager(manager);
        mAdapter = new OrganizationListAdapter(mOrganizationList);
        mConcernedRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,ConcernedActivity.class);
        context.startActivity(intent);
    }

    @Override
    public BasePresenter getInstance() {
        return null;
    }
}
