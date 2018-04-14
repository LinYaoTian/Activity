package rdc.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;

public class ChangeIntroductionActivity extends BaseActivity{

    @BindView(R.id.btn_commit)
    Button mCommitButton;
    @BindView(R.id.et_input)
    EditText mInputEditText;


    @Override
    protected int setLayoutResID() {
        return R.layout.activity_change_introduction;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mInputEditText.setText(getIntent().getStringExtra("introduction"));
    }

    @Override
    protected void initListener() {
        mInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static void actionStart(Context context, String introduction){
        Intent intent = new Intent(context,ChangeIntroductionActivity.class);
        intent.putExtra("introduction",introduction);
        context.startActivity(intent);
    }

    @Override
    public BasePresenter getInstance() {
        return null;
    }
}
