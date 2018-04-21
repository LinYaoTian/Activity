package rdc.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rdc.adapter.ConcernedListAdapter;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.Organization;
import rdc.contract.IConcernedContract;
import rdc.presenter.ConcernedPresenter;
import rdc.util.ACacheUtil;
import rdc.util.LoadingDialogUtil;
import rdc.util.ObjectCastUtil;

public class ConcernedActivity extends BaseActivity<ConcernedPresenter> implements IConcernedContract.View {

    @BindView(R.id.rv_concerned_organization)
    RecyclerView mConcernedRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private List<Organization> mOrganizationList;
    private ConcernedListAdapter mAdapter;
    private Dialog mDialog;
    private ACacheUtil mACacheUtil;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_concerned;
    }

    @Override
    protected void initData() {
        mOrganizationList = new ArrayList<>();
        mAdapter = new ConcernedListAdapter(mOrganizationList,this);
        mConcernedRecyclerView.setAdapter(mAdapter);
        mACacheUtil = ACacheUtil.get(getApplicationContext());
        //先从缓存中读取，缓存没有联网获取数据
        if (ObjectCastUtil.cast(mACacheUtil.getAsObject("concerned"))!=null){
            mOrganizationList.addAll((ArrayList)ObjectCastUtil.cast(mACacheUtil.getAsObject("concerned")));
            mAdapter.notifyDataSetChanged();
        }else {
            presenter.getConcernedOrganization();
            mDialog = LoadingDialogUtil.createLoadingDialog(ConcernedActivity.this,"正在加载数据...");

        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (ObjectCastUtil.cast(mACacheUtil.getAsObject("concerned"))!=null){
            mOrganizationList.clear();
            mOrganizationList.addAll((ArrayList)ObjectCastUtil.cast(mACacheUtil.getAsObject("concerned")));
            mAdapter.notifyDataSetChanged();
        }else {
            presenter.getConcernedOrganization();
            mDialog = LoadingDialogUtil.createLoadingDialog(ConcernedActivity.this,"正在加载数据...");

        }
    }

    @Override
    protected void initView() {
        initToolBar();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mConcernedRecyclerView.setLayoutManager(manager);

    }

    @Override
    protected void initListener() {
        mAdapter.setClickListener(new ConcernedListAdapter.OnClickListener() {
            @Override
            public void click(Organization organization) {
                //跳转到组织者个人主页
                OrganizationDetailsActivity.actionStart(ConcernedActivity.this,organization.getImage()
                        !=null?organization.getImage().getUrl():"",organization.getPhoto()!=null?organization.getPhoto().getUrl():"",
                        organization.getName(),organization.getIntroduction(),organization.getId());
            }
        });
    }

    /**
     * 启动方法
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ConcernedActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示我关注的组织
     * @param list
     */
    @Override
    public void setOrganization(List<Organization> list) {
        mACacheUtil.put("concerned",(ArrayList)list,5*60);
        mOrganizationList.clear();
        mOrganizationList.addAll(list);
        mAdapter.notifyDataSetChanged();
        LoadingDialogUtil.closeDialog(mDialog);
    }

    /**
     * 设置ToolBar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /**
     * 初始化ToolBar
     */
    private void  initToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void setOnError() {
        Toast.makeText(this,"获取数据失败!",Toast.LENGTH_SHORT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrganizationList.clear();
    }

    @Override
    public ConcernedPresenter getInstance() {
        return new ConcernedPresenter();

    }
}
