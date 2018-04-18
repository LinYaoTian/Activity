package rdc.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.User;
import rdc.contract.IIndividualContract;
import rdc.presenter.IndividualPresenter;
import rdc.util.LoadingDialogUtil;

import static rdc.configs.PhotoChooseType.sCHOOSEALBUM;
import static rdc.configs.PhotoChooseType.sTAKEPHOTO;
import static rdc.configs.PictureType.sIMAGE;
import static rdc.configs.PictureType.sPHOTO;

public class IndividualActivity extends BaseActivity<IndividualPresenter> implements IIndividualContract.View {
    @BindView(R.id.rl_photo)
    RelativeLayout mPhotoLayout;
    @BindView(R.id.rl_image)
    RelativeLayout mImageLayout;
    @BindView(R.id.rl_name)
    RelativeLayout mNameLayout;
    @BindView(R.id.rl_introduction)
    RelativeLayout mIntroductionLayout;
    @BindView(R.id.rl_university)
    RelativeLayout mUnversityLayout;


    @BindView(R.id.tv_name)
    TextView mNameTextView;
    @BindView(R.id.tv_introduction)
    TextView mIntroductionTextView;
    @BindView(R.id.imv_image)
    ImageView mImageView;
    @BindView(R.id.imv_photo)
    ImageView mPhotoView;
    @BindView(R.id.tv_university)
    TextView mUniversityTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_commit)
    Button mCommitButton;


    private String mCommonPath;
    private String mImagePath;
    private String mPhotoPath;
    private Bundle mSave;
   private Dialog mDialog ;

    private Dialog mUpLoadingDialog;
    private int mType = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mSave = savedInstanceState;

        }


    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_individual;
    }

    @Override
    protected void initData() {
        mCommonPath = "";
        mPhotoPath = "";
        mImagePath = "";

    }

    @Override
    protected void initView() {
        initToolBar();
        initUserInfo();
        mCommitButton.setEnabled(false);

    }

    @Override
    protected void initListener() {
        mPhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mType = sPHOTO;
                showDialog();
            }
        });
        mImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mType = sIMAGE;
                showDialog();
            }
        });
        mNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog("名称", mNameTextView.getText().toString());

            }
        });

        mIntroductionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog("简介", mIntroductionTextView.getText().toString());
            }
        });
        mUnversityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog("学校", mUniversityTextView.getText().toString());
            }
        });
        mCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    mUpLoadingDialog = LoadingDialogUtil.createLoadingDialog(IndividualActivity.this,"正在上传...");
                    presenter.updateUser();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case sTAKEPHOTO:
                if (resultCode == RESULT_OK) {

                    setPhotoImage();
                }
                break;
            case sCHOOSEALBUM:
                if (resultCode == RESULT_OK) {
                    presenter.setAlbumPhoto(data);
                }
                break;
            default:
                break;

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void showEditDialog(final String title, final String input) {
        View view = getLayoutInflater().inflate(R.layout.dialog_edit, null);
        final EditText editText = view.findViewById(R.id.et_input);
        editText.setClickable(false);
        editText.setText(input);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)//设置对话框的标题
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input = editText.getText().toString();
                        if (!TextUtils.isEmpty(input)){
                            switch (title) {
                                case "名称":
                                    mNameTextView.setText(input);
                                    break;
                                case "简介":
                                    mIntroductionTextView.setText(input);

                                    break;
                                case "学校":
                                    mUniversityTextView.setText(input);
                                    break;
                                default:
                                    break;
                            }
                            User user = BmobUser.getCurrentUser(User.class);
                            if (!mNameTextView.getText().toString().equals(user.getNickname())||
                                    !mIntroductionTextView.getText().toString().equals(user.getIntroduction())||
                                    !mUniversityTextView.getText().toString().equals(user.getUniversity())){
                                mCommitButton.setEnabled(true);
                            }else {
                                mCommitButton.setEnabled(false);

                            }
                            dialog.dismiss();
                        }


                    }
                }).create();
        dialog.show();
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
                if (ContextCompat.checkSelfPermission(IndividualActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(IndividualActivity.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    presenter.chooseAlbum();
                    mDialog.dismiss();
                }
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.takePhoto();
                mDialog.dismiss();


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

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void setUserInfo(User userInfo) {
        Glide.with(this).load(userInfo.getUserPhoto().getUrl()).into(mPhotoView);
        Glide.with(this).load(userInfo.getUserImg().getUrl()).into(mImageView);
        mNameTextView.setText(userInfo.getNickname());
        mIntroductionTextView.setText(userInfo.getIntroduction());
    }


    private void initUserInfo(){
        User user = User.getCurrentUser(User.class);
        mNameTextView.setText(user.getNickname());
        mIntroductionTextView.setText(user.getIntroduction());
        mUniversityTextView.setText(user.getUniversity());
        if (user.getUserImg() == null) {
            Glide.with(this).load(R.drawable.photo).into(mImageView);

        } else {
            Glide.with(this).load(user.getUserImg().getUrl()).into(mImageView);
        }

        if (user.getUserPhoto() != null) {
            Glide.with(this).load(user.getUserPhoto().getUrl()).into(mPhotoView);
        } else {
            Glide.with(this).load(user.getUserPhoto().getUrl()).into(mPhotoView);

        }
    }
    @Override
    public void takePhoto(Uri uri) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mCommonPath = uri.getPath();
        startActivityForResult(intent, sTAKEPHOTO);
    }

    @Override
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        startActivityForResult(intent, sCHOOSEALBUM);
    }

    @Override
    public void setPhotoImage() {
        if (mSave != null) {
            mCommonPath = mSave.getString("commonPath");
        }


        if (mType == sIMAGE) {
            mImagePath = mCommonPath;
            Glide.with(this).load(mImagePath).into(mImageView);
        } else if (mType == sPHOTO) {
            mPhotoPath = mCommonPath;
            Glide.with(this).load(mPhotoPath).into(mPhotoView);
        }
            mCommitButton.setEnabled(true);

    }

    @Override
    public void setAlbumImage(String imagePath) {
        mCommonPath = imagePath;

        if (mType == sIMAGE) {
            mImagePath = mCommonPath;
            Glide.with(this).load(mImagePath).into(mImageView);
        } else if (mType == sPHOTO) {
            mPhotoPath = mCommonPath;
            Glide.with(this).load(mPhotoPath).into(mPhotoView);
        }
        mCommitButton.setEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("commonPath", mCommonPath);

    }

    @Override
    public String getImageFilePath() {
        return mImagePath;
    }

    @Override
    public String getPhotoFilePath() {
        return mPhotoPath;
    }

    @Override
    public String getName() {
        return mNameTextView.getText().toString();
    }

    @Override
    public String getIntroduction() {
        return mIntroductionTextView.getText().toString();
    }

    @Override
    public String getUniversity() {
        return mUniversityTextView.getText().toString();
    }

    @Override
    public void back() {
        LoadingDialogUtil.closeDialog(mUpLoadingDialog);
        finish();
    }

    @Override
    public IndividualPresenter getInstance() {
        return new IndividualPresenter();
    }
}
