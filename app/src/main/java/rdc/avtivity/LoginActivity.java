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
import rdc.contract.LoginContract;
import rdc.presenter.LoginPresenter;

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
    }

    @Override
    protected void initListener() {

    }
}
