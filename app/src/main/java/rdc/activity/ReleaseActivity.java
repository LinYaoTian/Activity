package rdc.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import cn.bmob.v3.datatype.BmobDate;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.contract.ReleaseContract;
import rdc.presenter.ReleasePresenter;
import rdc.util.ACacheUtil;
import rdc.util.CustomDatePicker;
import rdc.util.LoadingDialogUtil;

import static rdc.util.ImageUtil.camera;
import static rdc.util.ImageUtil.cropImageUri;
import static rdc.util.ImageUtil.gallery;

/**
 * Created by WaxBerry on 2018/4/13.
 */

public class ReleaseActivity extends BaseActivity<ReleasePresenter> implements ReleaseContract.IView, View.OnClickListener {

    public static final int RELEASE_ACTIVITY = 1;//发布成功！

    private String TAG = "ReleaseActivity";
    @BindView(R.id.iv_poster_release) ImageView activity_release_poster_imageView;
    @BindView(R.id.tv_release_release) TextView activity_release_release_textView;
    @BindView(R.id.et_theme_release) EditText activity_release_theme_editText;
    @BindView(R.id.et_place_release) EditText activity_release_place_editText;
    @BindView(R.id.tv_time_start_release) TextView activity_release_time_start_textView;
    @BindView(R.id.tv_time_end_release) TextView activity_release_time_end_textView;
    @BindView(R.id.tv_university2_release) TextView activity_release_university_textView2;
    @BindView(R.id.tv_tag2_release) TextView activity_release_tag_textView2;
    @BindView(R.id.rt_content_release) EditText activity_release_content_editText;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    private Dialog mDialog;
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/cccc.jpg";
    private Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);
    public static final int CUT_GALLERY_PICTURE = 1;
    public static final int CUT_CAMERA_PICTURE = 8;
    public static final int SHOW_PICTURE = 2;
    public static final int SELECT_TAG = 3;
    public static final int RELEASE_TAG_RESULT_CODE = 4;
    private boolean hasPicture = false;
    private boolean isReleaseSuccess = false;

    private CustomDatePicker timePicker;
    private String currentTime;
    private Dialog mUpLoadingDialog;
    private ACacheUtil mACacheUtil;
//    private boolean hasChooseStartTime = false;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_release;
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ReleaseActivity.class);
        activity.startActivityForResult(intent, RELEASE_ACTIVITY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_release_release:
                release();
                break;
            case R.id.iv_poster_release:
                showDialog();
                break;
            case R.id.tv_time_start_release:
                showTimeStart(activity_release_time_start_textView);
                activity_release_time_end_textView.setText("");
                break;
            case R.id.tv_time_end_release:
                if (TextUtils.isEmpty(activity_release_time_start_textView.getText())) {
                    Toast.makeText(this, "先选择活动开始时间噢", Toast.LENGTH_SHORT).show();
                }else {
                    showTimeEnd(activity_release_time_end_textView);
                }
                break;
            case R.id.tv_university2_release:
                chooseUniversity();
                break;
            case R.id.tv_tag2_release:
                Intent intent = new Intent(ReleaseActivity.this, ReleaseTagActivity.class);
                startActivityForResult(intent, SELECT_TAG);
        }
    }

    /**
     * 发布活动
     */
    private void release() {
        if (hasPicture) {
            if (TextUtils.isEmpty(activity_release_theme_editText.getText()) || TextUtils.isEmpty(activity_release_time_start_textView.getText()) ||
                    TextUtils.isEmpty(activity_release_time_end_textView.getText()) || TextUtils.isEmpty(activity_release_university_textView2.getText()) ||
                    TextUtils.isEmpty(activity_release_place_editText.getText()) || TextUtils.isEmpty(activity_release_tag_textView2.getText()) ||
                    TextUtils.isEmpty(activity_release_content_editText.getText())) {
                Toast.makeText(this, "请填写完整的内容 ！ " , Toast.LENGTH_SHORT).show();
            }else {
                presenter.release();
                showProgressDialog();
            }
        }else {
            Toast.makeText(this, "请添加活动海报！" , Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 时间选择控件初始化（开始）
     * @param v
     */
    private void showTimeStart(final TextView v) {
        timePicker = new CustomDatePicker(this, "请选择时间", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                v.setText(time);
            }
        }, currentTime, "2050-12-31 23:59");
        timePicker.showSpecificTime(true);
        timePicker.setIsLoop(true);
        timePicker.show(currentTime);
    }

    /**
     * 时间选择控件初始化（结束）
     * @param v
     */
    private void showTimeEnd(final TextView v) {
        timePicker = new CustomDatePicker(this, "请选择时间", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                v.setText(time);
            }
        },activity_release_time_start_textView.getText().toString(), "2050-12-31 23:59");
        timePicker.showSpecificTime(true);
        timePicker.setIsLoop(true);
        timePicker.show(currentTime);
    }

    /**
     * 选择大学
     */
    private void chooseUniversity() {
        final String [] schoolArray = {
                "广东工业大学","华南理工大学",
                "中山大学","广州大学",
                "广东外语外贸大学","广州中医药大学",
                "广东药科大学","华南师范大学",
                "广州美术学院","星海音乐学院"};
        final AlertDialog dialog = new AlertDialog.Builder(ReleaseActivity.this).create();
        final List<String> schoolList = Arrays.asList(schoolArray);
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<>(ReleaseActivity.this, android.R.layout.simple_list_item_1, schoolList);
        View view1 = LayoutInflater.from(ReleaseActivity.this)
                .inflate(R.layout.dialog_universities_act_register,null);
        TextView tvTitle = view1.findViewById(R.id.tv_title_dialog_act_register);
        ListView lvList = view1.findViewById(R.id.lv_university_dialog_act_register);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                activity_release_university_textView2.setText(listAdapter.getItem(i));
                dialog.dismiss();
            }
        });
        tvTitle.setText("请选择所在的学校");
        lvList.setAdapter(listAdapter);
        dialog.show();
        dialog.getWindow().setContentView(view1);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mACacheUtil = ACacheUtil.get(getApplicationContext());
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        currentTime = sdf.format(new Date());
        initToolBar();

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth  = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams lp = activity_release_poster_imageView.getLayoutParams();
        lp.width = screenWidth;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        activity_release_poster_imageView.setLayoutParams(lp);
        activity_release_poster_imageView.setMaxWidth(screenWidth);
        activity_release_poster_imageView.setMaxHeight((int)(screenWidth * 0.7));
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "发布成功！" , Toast.LENGTH_SHORT).show();
        mACacheUtil.clear();
        isReleaseSuccess = true;
        onBackPressed();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, "发布失败" + message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 选择照片的dialog
     */
    public void showDialog() {
        mDialog = new Dialog(ReleaseActivity.this, R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_select_photo, null);
        Button choosePhoto = inflate.findViewById(R.id.choosePhoto);
        Button takePhoto = inflate.findViewById(R.id.takePhoto);
        Button cancel = inflate.findViewById(R.id.btn_cancel);
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ReleaseActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReleaseActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    gallery(ReleaseActivity.this, imageUri);
                    mDialog.dismiss();
                }
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ReleaseActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReleaseActivity.this, new String[]{android.Manifest.permission.CAMERA}, 1);
                } else {
                    camera(ReleaseActivity.this, imageUri);
                    mDialog.dismiss();
                }
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

    /**
     * 拍摄照片/相册选择照片/裁剪/选择Tag的返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CUT_GALLERY_PICTURE:
                if (resultCode == RESULT_OK) {
                    cropImageUri(ReleaseActivity.this, data.getData(), imageUri, 800, 400, SHOW_PICTURE);
                }
                break;
            case SHOW_PICTURE:
                if (resultCode == RESULT_OK && imageUri != null) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        activity_release_poster_imageView.setImageBitmap(bitmap);
                        hasPicture = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CUT_CAMERA_PICTURE:
                cropImageUri(ReleaseActivity.this, imageUri, imageUri, 800, 400, SHOW_PICTURE);
                break;
            case SELECT_TAG:
                if (resultCode == RELEASE_TAG_RESULT_CODE) {
                    activity_release_tag_textView2.setText(data.getStringExtra("type"));
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {
        activity_release_release_textView.setOnClickListener(this);
        activity_release_theme_editText.setOnClickListener(this);
        activity_release_place_editText.setOnClickListener(this);
        activity_release_time_start_textView.setOnClickListener(this);
        activity_release_time_end_textView.setOnClickListener(this);
        activity_release_university_textView2.setOnClickListener(this);
        activity_release_tag_textView2.setOnClickListener(this);
        activity_release_poster_imageView.setOnClickListener(this);
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

    @Override
    public void onBackPressed() {
        if (isReleaseSuccess){
            Intent intent = new Intent();
            intent.putExtra("tag",getTag());
            setResult(RESULT_OK,intent);
        }
        super.onBackPressed();
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
    public ReleasePresenter getInstance() {
        return new ReleasePresenter();
    }

    /**
     * 以下看函数名就知道干嘛用的
     */
    @Override
    public String getImagePath() {
        return IMAGE_FILE_LOCATION;
    }

    @Override
    public String getStartTime() {
        return activity_release_time_start_textView.getText().toString();
    }

    @Override
    public String getEndTime() {
        return activity_release_time_end_textView.getText().toString();
    }

    @Override
    public String getUniversity() {
        return activity_release_university_textView2.getText().toString();
    }

    @Override
    public String getPlace() {
        return activity_release_place_editText.getText().toString();
    }

    @Override
    public String getTag() {
        return activity_release_tag_textView2.getText().toString();
    }

    @Override
    public String getContent() {
        return activity_release_content_editText.getText().toString();
    }

    @Override
    public String getSendTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Override
    public BmobDate getExpirationDate() {
        String expirationDate = activity_release_time_end_textView.getText().toString() + ":00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return new BmobDate(sdf.parse(expirationDate));
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return new BmobDate(new Date());
    }

    @Override
    public void showProgressDialog() {
        mUpLoadingDialog = LoadingDialogUtil.createLoadingDialog(ReleaseActivity.this,"正在上传...");
    }

    @Override
    public void dismissProgressDialog() {
        LoadingDialogUtil.closeDialog(mUpLoadingDialog);
    }

    @Override
    public String getActivityTheme() {
        return activity_release_theme_editText.getText().toString();
    }
}
