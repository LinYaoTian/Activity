package rdc.avtivity;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
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

import java.util.List;

import butterknife.BindView;
import rdc.base.BaseActivity;
import rdc.bean.User;
import rdc.constant.Constant;
import rdc.contract.RegisterContract;
import rdc.presenter.RegisterPresenter;
import rdc.util.RegisterUtils;
import rdc.util.UniversityUtils;

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
        mEtUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).create();
                final List<String> provinces = UniversityUtils.getProvinces();
                final ArrayAdapter<String> listAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1, provinces);
                View view1 = LayoutInflater.from(RegisterActivity.this)
                        .inflate(R.layout.dialog_universities_act_register,null);
                TextView tvTitle = view1.findViewById(R.id.tv_title_dialog_act_register);
                ListView lvList = view1.findViewById(R.id.lv_university_dialog_act_register);
                lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    boolean selectingProvince = true;//是否正在选择学校所在省份
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (selectingProvince){
                            //正在选择学校所在省份
                            String province = provinces.get(i);
                            listAdapter.clear();
                            listAdapter.addAll(UniversityUtils.getUniversities(province));
                            listAdapter.notifyDataSetChanged();
                            selectingProvince = !selectingProvince;
                        }else {
                            //正在选择学校
                            mEtUniversity.setText(listAdapter.getItem(i));
                            dialog.dismiss();
                        }
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
        mEtAccountNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    if (!RegisterUtils.checkAccountNumber(getString(mEtAccountNumber))){
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
                if (!RegisterUtils.checkAccountNumber(getString(mEtAccountNumber))){
                    showToast("请输入正确的手机/邮箱！");
                }else if (!RegisterUtils.checkPassword(getString(mEtPassword))){
                    showToast("密码位数必须不小于"+Constant.PASSWORD_NUM+"位");
                }if (TextUtils.isEmpty(getString(mEtNickname))){
                    showToast("昵称不能为空！");
                } else{
                    User user = new User();
                    user.setUsername(getString(mEtAccountNumber));
                    user.setPassword(getString(mEtPassword));
                    user.setNickname(getString(mEtNickname));
                    user.setUniversity(getString(mEtUniversity));
                    presenter.register(user);
                }
            }
        });
    }


    @Override
    protected void initListener() {

    }

    @Override
    public void registerSuccess() {
        showToast("注册成功！");
        finish();
    }

    @Override
    public void registerError(String message) {
        showToast(message);
    }
}
