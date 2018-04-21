package rdc.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;

import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.Activity;
import rdc.bean.User;
import rdc.contract.ICheckManagedContract;
import rdc.presenter.CheckManagedPresenter;
import rdc.util.ACacheUtil;
import rdc.util.LoadingDialogUtil;

public class CheckManagedActivity extends BaseActivity<CheckManagedPresenter> implements ICheckManagedContract.IView {

    private String TAG = "DetailActivity";
    @BindView(R.id.imv_delete)
    ImageView mDelete;
    @BindView(R.id.imv_poster)
    ImageView mPoster;
    @BindView(R.id.tv_details_title)
    TextView mDetialsTitle;
    @BindView(R.id.tv_sawNum)
    TextView mDetialsSawNum;
    @BindView(R.id.tv_attendNum)
    TextView mDetialsAttendNum;
    @BindView(R.id.tv_details_time)
    TextView mDetialsTime;
    @BindView(R.id.tv_details_school)
    TextView mDetialsSchool;
    @BindView(R.id.tv_details_place)
    TextView mDetialsPlace;
    @BindView(R.id.tv_details_tag)
    TextView mDetialsTag;




    @BindView(R.id.tv_content)
    TextView mContent;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Activity activity;
    private String objectId;
    private ACacheUtil mACacheUtil;
    private Dialog mDialog;


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
        mDialog = LoadingDialogUtil.createLoadingDialog(this,"正在加载...");

    }

    @Override
    protected void initView() {
        initToolBar();
    }

    @Override
    protected void initListener() {

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog(objectId);
            }
        });
    }

    /**
     * 弹出确认框
     * @param objectId
     */
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

    /**
     * 删除成功后的回调
     */
    @Override
    public void setDeleteSuccess() {
        mACacheUtil.clear();//删除成功后，也要清除缓存，以便更新数据
        finish();
    }

    /**
     * 显示详情
     * @param activity
     */
    @Override
    public void setDetail(Activity activity) {
        this.activity = activity;
        String[] timeArray = activity.getTime().split("~");
        Glide.with(this).load(activity.getImage().getFileUrl()).into(mPoster);
        mDetialsTitle.setText(activity.getTitle() + "");
        mDetialsSawNum.setText(activity.getSawnum() + "");
        mDetialsTime.setText(timeArray[0] + " 至 " + timeArray[1]);
        mDetialsSchool.setText(activity.getUniversity());
        mDetialsPlace.setText(activity.getPlace());
        mDetialsTag.setText(activity.getTag());

        mContent.setText(activity.getContent());
        int currentSawNum = activity.getSawnum();
        presenter.addSawNum(currentSawNum);
        LoadingDialogUtil.closeDialog(mDialog);


    }

    /**
     * 显示详情中的阅读人数
     * @param userList
     */
    @Override
    public void setDetailAttcipator(List<User> userList) {
        if (userList == null || userList.size() == 0) {
            mDetialsAttendNum.setText(0 + "");
        } else {
            mDetialsAttendNum.setText(userList.size() + "");
            for (int i = 0; i < userList.size(); i++) {
                if (Objects.equals(userList.get(i).getObjectId(), BmobUser.getCurrentUser().getObjectId())) {

                    return;
                }
            }
        }
    }



    /**
     * 获取详情失败后的回调
     */

    @Override
    public void onError(String errMeg) {
        Toast.makeText(this, "抱歉，遇到了一个预料之外的错误！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getobjectId() {
        return objectId;
    }




    /**
     * 设置ToolBar的返回点击
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /**
     * 启动方法
     * @param context
     * @param objectId
     */
    public static void actionStart(Context context, String objectId) {
        Intent intent = new Intent(context, CheckManagedActivity.class);
        intent.putExtra("objectId", objectId);
        context.startActivity(intent);
    }

    /**
     * 初始化ToolBar
     */

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