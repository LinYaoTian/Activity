package rdc.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;

public class UserCenterActivity extends BaseActivity{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rl_change_account_act_account_manage)
    RelativeLayout mRlAccountManage;
    @Override
    public BasePresenter getInstance() {
        return null;
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_user_center;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initToolBar();
    }

    @Override
    protected void initListener() {
        mRlAccountManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserCenterActivity.this,AccountManageActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    private void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }
    public static  void actionStart(Context context){
        Intent intent = new Intent(context,UserCenterActivity.class);
        context.startActivity(intent);
    }
}
