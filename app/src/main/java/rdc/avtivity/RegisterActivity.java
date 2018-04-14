package rdc.avtivity;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import rdc.base.BaseActivity;
import rdc.bean.User;
import rdc.contract.RegisterContract;
import rdc.presenter.RegisterPresenter;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.toolbar_act_Register)
    Toolbar mToolbar;
    @BindView(R.id.et_account_number_act_register)
    EditText mEtAccountNumber;
    @BindView(R.id.et_nickname_act_register)
    EditText mEtNickname;
    @BindView(R.id.et_password_act_register)
    EditText mEtPassword;
    @BindView(R.id.btn_register_act_register)
    Button mBtnRegister;
    @BindView(R.id.iv_see_password_act_register)
    ImageView mIvSeePassword;

    private boolean isSeePassword;//密码是否显示明文

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    @Override
    public RegisterPresenter getInstance() {
        return new RegisterPresenter();
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

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setTitle("");
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        isSeePassword = false;
    }

    @Override
    protected void initView() {
        mIvSeePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSeePassword = !isSeePassword;
                if (isSeePassword){
                    //让密码明文
                    mIvSeePassword.setImageResource(R.drawable.iv_eye_blue);
                    mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    //让密码密文
                    mIvSeePassword.setImageResource(R.drawable.iv_eye_grey);
                    mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setUsername(getString(mEtAccountNumber));
                user.setPassword(getString(mEtPassword));
                user.setNickname(getString(mEtNickname));
                presenter.register(user);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void registerSuccess() {
        showToast("注册成功！");
    }

    @Override
    public void registerError(String message) {
        showToast("组册失败!");
    }
}
