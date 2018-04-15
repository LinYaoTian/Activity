package rdc.activity;

import android.content.Intent;
import android.os.Bundle;

import cn.bmob.v3.BmobUser;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.User;
import rdc.contract.SimpleContract;
import rdc.presenter.SimplePresenter;

public class LaunchActivity extends BaseActivity<SimplePresenter> implements SimpleContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public SimplePresenter getInstance() {
        return new SimplePresenter();
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
                            startActivity(new Intent(LaunchActivity.this,MainActivity.class));
                        }else {
                            startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
                        }
                        finish();
                    }
                });
            }
        }).start();
    }

    @Override
    protected void initListener() {

    }
}
