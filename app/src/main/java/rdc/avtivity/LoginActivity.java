package rdc.avtivity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;
import rdc.bean.User;
import rdc.constant.Constant;
import rdc.contract.LoginContract;
import rdc.presenter.LoginPresenter;
import rdc.util.RegisterUtils;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.toolbar_act_login)
    Toolbar mToolbar;
    @BindView(R.id.et_account_number_act_login)
    EditText mEtAccountNumber;
    @BindView(R.id.et_password_act_login)
    EditText mEtPassword;
    @BindView(R.id.btn_login_act_login)
    Button mBtnLogin;
    @BindView(R.id.tv_register_act_login)
    TextView mTvRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public LoginPresenter getInstance() {
        return new LoginPresenter();
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RegisterUtils.checkAccountNumber(getString(mEtAccountNumber))){
                    showToast("请输入正确的手机/邮箱！");
                }else if (!RegisterUtils.checkPassword(getString(mEtPassword))){
                    showToast("密码位数必须不小于"+ Constant.PASSWORD_NUM+"位");
                }else {
                    User user = new User();
                    user.setUsername(getString(mEtAccountNumber));
                    user.setPassword(getString(mEtPassword));
                    presenter.login(user);
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void loginSuccess() {
        showToast("登录成功！");
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void loginError(String message) {
        showToast(message);
    }
}
