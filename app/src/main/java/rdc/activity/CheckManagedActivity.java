package rdc.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.Activity;
import rdc.bean.Organization;
import rdc.bean.User;
import rdc.contract.DetailContract;
import rdc.contract.ICheckManagedContract;
import rdc.presenter.CheckManagedPresenter;
import rdc.presenter.DetailPresenter;
import rdc.util.ACacheUtil;
import rdc.util.ShareUtil;

public class CheckManagedActivity extends BaseActivity<CheckManagedPresenter> implements ICheckManagedContract.IView {

    private String TAG = "DetailActivity";
    @BindView(R.id.activity_detail_forward_imageView)
    ImageView activity_detail_forward_imageView;
    @BindView(R.id.activity_detail_poster_imageView)
    ImageView activity_detail_poster_imageView;
    @BindView(R.id.activity_detail_title_textView)
    TextView activity_detail_title_textView;
    @BindView(R.id.activity_detail_seeNum_textView)
    TextView activity_detail_seeNum_textView;
    @BindView(R.id.activity_detail_attendNum_textView)
    TextView activity_detail_attendNum_textView;
    @BindView(R.id.activity_detail_time_textView)
    TextView activity_detail_time_textView;
    @BindView(R.id.activity_detail_school_textView)
    TextView activity_detail_school_textView;
    @BindView(R.id.activity_detail_place_textView)
    TextView activity_detail_place_textView;
    @BindView(R.id.activity_detail_tag_textView)
    TextView activity_detail_tag_textView;




    @BindView(R.id.activity_detail_content_textView)
    TextView activity_detail_content_textView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Activity activity;
    private String objectId;
    private ACacheUtil mACacheUtil;


    @Override
    protected int setLayoutResID() {
        return R.layout.activity_check_managed;
    }

    @Override
    protected void initData() {
        activity = new Activity();
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        presenter.getDetail(objectId);
        mACacheUtil = ACacheUtil.get(getApplicationContext());

    }

    @Override
    protected void initView() {
        initToolBar();
    }

    @Override
    protected void initListener() {
        activity_detail_forward_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog(objectId);
            }
        });
    }
    public void showConfirmDialog(final String objectId) {
        View view = getLayoutInflater().inflate(R.layout.dialog_delete_confrim, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("确定删除该活动？")//设置对话框的标题
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
                        presenter.deleteActivity(objectId);

              }
                }).create();
        dialog.show();
    }


    @Override
    public void setDeleteSuccess() {
        mACacheUtil.clear();
        finish();
    }

    @Override
    public void setDetail(Activity activity) {
        this.activity = activity;
        String[] timeArray = activity.getTime().split("~");
        Glide.with(this).load(activity.getImage().getFileUrl()).into(activity_detail_poster_imageView);
        activity_detail_title_textView.setText(activity.getTitle() + "");
        activity_detail_seeNum_textView.setText(activity.getSawnum() + "");
        activity_detail_time_textView.setText(timeArray[0] + " 至 " + timeArray[1]);
        activity_detail_school_textView.setText(activity.getUniversity());
        activity_detail_place_textView.setText(activity.getPlace());
        activity_detail_tag_textView.setText(activity.getTag());


        activity_detail_content_textView.setText(activity.getContent());

        int currentSawNum = activity.getSawnum();
        presenter.addSawNum(currentSawNum);
        presenter.getUserconcernedList();


    }

    @Override
    public void setDetailAttcipator(List<User> userList) {
        if (userList == null || userList.size() == 0) {
            activity_detail_attendNum_textView.setText(0 + "");
        } else {
            activity_detail_attendNum_textView.setText(userList.size() + "");
            for (int i = 0; i < userList.size(); i++) {
                if (Objects.equals(userList.get(i).getObjectId(), BmobUser.getCurrentUser().getObjectId())) {

                    return;
                }
            }
        }
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "获取详情成功");
    }

    @Override
    public void onSingOrUnSingUpSuccess() {

    }

    @Override
    public void onError(String errMeg) {
        Log.d(TAG, "获取活动详情错误， " + errMeg);
        Toast.makeText(this, "抱歉，遇到了一个预料之外的错误！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getobjectId() {
        return objectId;
    }

    @Override
    public void setUserconcernedList(List<User> userconcernedList) {

    }

    @Override
    public void onConcernedSuccess() {

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

    public static void actionStart(Context context, String objectId) {
        Intent intent = new Intent(context, CheckManagedActivity.class);
        intent.putExtra("objectId", objectId);
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
    public CheckManagedPresenter getInstance() {
        return new CheckManagedPresenter();
    }
}