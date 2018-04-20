package rdc.activity;

import android.content.Intent;
import android.os.Bundle;

import cn.bmob.v3.BmobUser;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.User;
import rdc.contract.LaunchContract;
import rdc.presenter.LaunchPresenter;

public class LaunchActivity extends BaseActivity<LaunchPresenter> implements LaunchContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public LaunchPresenter getInstance() {
        return new LaunchPresenter();
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (BmobUser.getCurrentUser(User.class) != null){
                            presenter.updateUser();
                        }else {
                            finish();
                            startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void updateUserSuccess() {
        Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateUserError() {
        finish();
        showToast("自动登录失败，请重新登录！");
        startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
    }
}
