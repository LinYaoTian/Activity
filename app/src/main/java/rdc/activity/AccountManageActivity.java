package rdc.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.constant.Constant;
import rdc.contract.AccountManageContract;
import rdc.presenter.AccountManagePresenter;
import rdc.util.ACacheUtil;
import rdc.util.ActivityCollectorUtil;
import rdc.util.RegisterUtils;
import rdc.util.UserUtil;

public class AccountManageActivity extends BaseActivity<AccountManagePresenter> implements AccountManageContract.View {

    @BindView(R.id.toolbar_act_account_manage)
    Toolbar mToolbar;
    @BindView(R.id.ll_change_account_act_account_manage)
    LinearLayout mLlChangeAccount;
    @BindView(R.id.ll_change_password_act_account_manage)
    LinearLayout mLlChangePassword;

    private View mDialogView;
    private EditText mEtOldPassword;
    private EditText mEtNewPassword;
    private ImageView mIvSeePassword;
    private AlertDialog mDialogChangePassword;

    private boolean isSeePassword;//新密码是否显示明文
    private ACacheUtil mACacheUtil;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AccountManageActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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

    @Override
    public AccountManagePresenter getInstance() {
        return new AccountManagePresenter();
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_account_manage;
    }

    @Override
    protected void initData() {
        isSeePassword = false;
        mACacheUtil = ACacheUtil.get(getApplicationContext());
    }

    @Override
    protected void initView() {
        initToolBar();
        mLlChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountManageActivity.this);
                mDialogView = LayoutInflater.from(AccountManageActivity.this).inflate(R.layout.dialog_change_password,null);
                mEtOldPassword = mDialogView.findViewById(R.id.et_old_password_dialog);
                mEtNewPassword = mDialogView.findViewById(R.id.et_new_password_dialog);
                mIvSeePassword = mDialogView.findViewById(R.id.iv_see_password_dialog);
                mIvSeePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isSeePassword = !isSeePassword;
                        if (isSeePassword){
                            //让密码明文
                            mIvSeePassword.setImageResource(R.drawable.iv_eye_blue);
                            mEtNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }else {
                            //让密码密文
                            mIvSeePassword.setImageResource(R.drawable.iv_eye_grey);
                            mEtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确认", null);
                builder.setCancelable(false);
                builder.setView(mDialogView);
                mDialogChangePassword = builder.create();
                mDialogChangePassword.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button btnPositive = mDialogChangePassword.getButton(AlertDialog.BUTTON_POSITIVE);
                        btnPositive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!RegisterUtils.checkPassword(getString(mEtOldPassword))){
                                    showToast("旧密码位数必须不小于"+ Constant.PASSWORD_NUM+"位");
                                }else if (!RegisterUtils.checkPassword(getString(mEtNewPassword))){
                                    showToast("新密码位数必须不小于"+ Constant.PASSWORD_NUM+"位");
                                }else {
                                    presenter.changePassword(getString(mEtOldPassword),getString(mEtNewPassword));
                                }
                            }
                        });
                        Button btnCancel = mDialogChangePassword.getButton(AlertDialog.BUTTON_NEGATIVE);
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDialogChangePassword.dismiss();
                            }
                        });
                    }
                });
                mDialogChangePassword.show();
            }
        });
    }


    @Override
    protected void initListener() {

        mLlChangeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mACacheUtil.clear();

                ActivityCollectorUtil.finishAll();
                UserUtil.clearUser();
                startActivity(new Intent(AccountManageActivity.this,LoginActivity.class));
            }
        });
    }

    @Override
    public void changePasswordSuccess() {
        showToast("修改密码成功！");
        mDialogChangePassword.dismiss();
        mACacheUtil.clear();
    }

    @Override
    public void changePasswordError(String error) {
        showToast(error);
    }
}
