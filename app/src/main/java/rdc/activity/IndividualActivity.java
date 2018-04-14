package rdc.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class IndividualActivity extends BaseActivity {
    @BindView(R.id.rl_image)
    RelativeLayout mImageLayout;
    @BindView(R.id.rl_name)
    RelativeLayout mNameLayout;
    @BindView(R.id.rl_introduction)
    RelativeLayout mIntroductionLayout;
    @BindView(R.id.rl_certificate)
    RelativeLayout mCertificateLayout;
    @BindView(R.id.rl_account)
    RelativeLayout mAccountLayout;
    @BindView(R.id.tv_name)
    TextView mNameTextView;
    @BindView(R.id.tv_introduction)
    TextView mIntroductionTextView;

    private Dialog mDialog;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_individual;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        mImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        mNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeNameActivity.actionStart(IndividualActivity.this, mNameTextView.getText().toString());
            }
        });

        mIntroductionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeIntroductionActivity.actionStart(IndividualActivity.this, mIntroductionTextView.getText().toString());
            }
        });
    }

    public void showDialog() {
        mDialog = new Dialog(IndividualActivity.this, R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_select_photo, null);
        Button choosePhoto = inflate.findViewById(R.id.choosePhoto);
        Button takePhoto = inflate.findViewById(R.id.takePhoto);
        Button cancel = inflate.findViewById(R.id.btn_cancel);
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.setContentView(inflate);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        lp.width = display.getWidth();
        dialogWindow.setAttributes(lp);
        mDialog.show();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, IndividualActivity.class);
        context.startActivity(intent);
    }

    @Override
    public BasePresenter getInstance() {
        return null;
    }
}
