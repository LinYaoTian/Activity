package rdc.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.User;
import rdc.contract.LaunchContract;
import rdc.presenter.LaunchPresenter;

public class LaunchActivity extends BaseActivity<LaunchPresenter> implements LaunchContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPermission();
        super.onCreate(savedInstanceState);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LaunchActivity.class);
        context.startActivity(intent);
    }

    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);

        }else {
            checkLoginState();
        }
    }

    private void checkLoginState(){
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
                            LoginActivity.actionStart(LaunchActivity.this);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 0:
                if (grantResults.length > 0){
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                        showToast("请开通写入文件权限！");
                        finish();
                    }
                    if (grantResults[1] != PackageManager.PERMISSION_GRANTED){
                        showToast("请开通读取文件权限！");
                        finish();
                    }
                    checkLoginState();
                }
        }
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

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void updateUserSuccess() {
        MainActivity.actionStart(this);
        finish();
    }

    @Override
    public void updateUserError() {
        showToast("自动登录失败，请重新登录！");
        LoginActivity.actionStart(this);
        finish();
    }
}
