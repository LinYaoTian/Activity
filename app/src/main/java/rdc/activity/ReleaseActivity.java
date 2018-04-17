package rdc.activity;

import android.app.Dialog;
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
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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

import com.zxy.tiny.callback.FileCallback;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.contract.ReleaseContract;
import rdc.presenter.ReleasePresenter;
import rdc.util.CustomDatePicker;
import rdc.util.ImageUtil;
import rdc.util.UniversityUtils;
import rdc.util.UserUtil;

import static rdc.util.ImageUtil.camera;
import static rdc.util.ImageUtil.cropImageUri;
import static rdc.util.ImageUtil.gallery;

/**
 * Created by WaxBerry on 2018/4/13.
 */

public class ReleaseActivity extends BaseActivity<ReleasePresenter> implements ReleaseContract.IView, View.OnClickListener {

    private String TAG = "ReleaseActivity";
    @BindView(R.id.activity_release_poster_imageView) ImageView activity_release_poster_imageView;
    @BindView(R.id.activity_release_release_textView) TextView activity_release_release_textView;
    @BindView(R.id.activity_release_theme_editText) EditText activity_release_theme_editText;
    @BindView(R.id.activity_release_place_editText) EditText activity_release_place_editText;
    @BindView(R.id.activity_release_time_start_textView) TextView activity_release_time_start_textView;
    @BindView(R.id.activity_release_time_end_textView) TextView activity_release_time_end_textView;
    @BindView(R.id.activity_release_university_textView2) TextView activity_release_university_textView2;
    @BindView(R.id.activity_release_tag_textView2) TextView activity_release_tag_textView2;
    @BindView(R.id.activity_release_content_editText) EditText activity_release_content_editText;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    private Dialog mDialog;
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/cccc.jpg";
    private Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);
    public static final int CUT_GALLERY_PICTURE = 1;
    public static final int CUT_CAMERA_PICTURE = 8;
    public static final int SHOW_PICTURE = 2;
    public static final int SELECT_TAG = 3;
    public static final int RELEASE_TAG_RESULT_CODE = 4;

    private CustomDatePicker timePicker;
    private String time;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_release;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_release_release_textView:
                release();
                break;
            case R.id.activity_release_poster_imageView:
                showDialog();
                break;
            case R.id.activity_release_time_start_textView:
                showTime(activity_release_time_start_textView);
                break;
            case R.id.activity_release_time_end_textView:
                showTime(activity_release_time_end_textView);
                break;
            case R.id.activity_release_university_textView2:
                chooseUniversity();
                break;
            case R.id.activity_release_tag_textView2:
                Intent intent = new Intent(ReleaseActivity.this, ReleaseTagActivity.class);
                startActivityForResult(intent, SELECT_TAG);
        }
    }

    private void release() {
        if (TextUtils.isEmpty(activity_release_theme_editText.getText()) || TextUtils.isEmpty(activity_release_time_start_textView.getText()) ||
                TextUtils.isEmpty(activity_release_time_end_textView.getText()) || TextUtils.isEmpty(activity_release_university_textView2.getText()) ||
                TextUtils.isEmpty(activity_release_place_editText.getText()) || TextUtils.isEmpty(activity_release_tag_textView2.getText()) ||
                TextUtils.isEmpty(activity_release_content_editText.getText())) {
            Toast.makeText(this, "请填写完整的内容 ！ " , Toast.LENGTH_SHORT).show();
        }else {
            presenter.release();
        }
    }

    private void showTime(final TextView v) {
        timePicker = new CustomDatePicker(this, "请选择时间", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                v.setText(time);
            }
        }, time, "2027-12-31 23:59");
        timePicker.showSpecificTime(true);
        timePicker.setIsLoop(true);
        timePicker.show(time);
    }

    private void chooseUniversity() {
        final AlertDialog dialog = new AlertDialog.Builder(ReleaseActivity.this).create();
        final List<String> provinces = UniversityUtils.getProvinces();
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<>(ReleaseActivity.this, android.R.layout.simple_list_item_1, provinces);
        View view1 = LayoutInflater.from(ReleaseActivity.this).inflate(R.layout.dialog_universities_act_register,null);
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
                    activity_release_university_textView2.setText(listAdapter.getItem(i));
                    dialog.dismiss();
                }
            }
        });
        tvTitle.setText("请选择所在的学校");
        lvList.setAdapter(listAdapter);
        dialog.show();
        dialog.getWindow().setContentView(view1);}

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        initToolBar();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "发布成功！" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, "发布失败" + message, Toast.LENGTH_SHORT).show();
    }

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
                camera(ReleaseActivity.this, imageUri);
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
                finish();
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
    public ReleasePresenter getInstance() {
        return new ReleasePresenter();
    }

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
    public String getActivityTheme() {
        return activity_release_theme_editText.getText().toString();
    }
}
