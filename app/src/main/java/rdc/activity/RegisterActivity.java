package rdc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.User;
import rdc.constant.Constant;
import rdc.contract.RegisterContract;
import rdc.presenter.RegisterPresenter;
import rdc.util.RegisterUtils;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.toolbar_act_Register)
    Toolbar mToolbar;
    @BindView(R.id.et_username_act_register)
    EditText mEtUsername;
    @BindView(R.id.et_nickname_act_register)
    EditText mEtNickname;
    @BindView(R.id.et_password_act_register)
    EditText mEtPassword;
    @BindView(R.id.btn_register_act_register)
    Button mBtnRegister;
    @BindView(R.id.et_university_act_register)
    EditText mEtUniversity;
    @BindView(R.id.iv_see_password_act_register)
    ImageView mIvSeePassword;

    private boolean isSeePassword;//密码是否显示明文

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public RegisterPresenter getInstance() {
        return new RegisterPresenter();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
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

    }


    @Override
    protected void initListener() {
        mEtUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] schoolArray = {
                        "广东工业大学","华南理工大学",
                        "中山大学","广州大学",
                        "广东外语外贸大学","广州中医药大学",
                        "广东药科大学","华南师范大学",
                        "广州美术学院","星海音乐学院"};
                final AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).create();
                List<String> schoolList = Arrays.asList(schoolArray);
                final ArrayAdapter<String> listAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1, schoolList);
                View view1 = LayoutInflater.from(RegisterActivity.this)
                        .inflate(R.layout.dialog_universities_act_register,null);
                TextView tvTitle = view1.findViewById(R.id.tv_title_dialog_act_register);
                ListView lvList = view1.findViewById(R.id.lv_university_dialog_act_register);
                lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mEtUniversity.setText(listAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
                tvTitle.setText("请选择所在的学校");
                lvList.setAdapter(listAdapter);
                dialog.show();
                dialog.getWindow().setContentView(view1);
            }
        });
        mEtNickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    if (TextUtils.isEmpty(getString(mEtNickname))){
                        showToast("昵称不能为空！");
                    }
                }
            }
        });
        mEtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    if (!RegisterUtils.checkUsername(getString(mEtUsername))){
                        showToast("请输入正确的手机/邮箱！");
                    }
                }
            }
        });
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
                if (!RegisterUtils.checkUsername(getString(mEtUsername))){
                    showToast("请输入正确的手机/邮箱！");
                }else if (!RegisterUtils.checkPassword(getString(mEtPassword))){
                    showToast("密码位数必须不小于"+Constant.PASSWORD_NUM+"位");
                }else if (TextUtils.isEmpty(getString(mEtNickname))){
                    showToast("昵称不能为空！");
                } else{
                    User user = new User();
                    user.setUsername(getString(mEtUsername));
                    user.setPassword(getString(mEtPassword));
                    user.setNickname(getString(mEtNickname));
                    user.setUniversity(getString(mEtUniversity));
                    presenter.register(user);
                }
            }
        });
    }

    @Override
    public void registerSuccess() {
        showToast("注册成功！");
        MainActivity.actionStart(RegisterActivity.this);
        finish();
    }

    @Override
    public void registerError(String message) {
        showToast(message);
    }
}
