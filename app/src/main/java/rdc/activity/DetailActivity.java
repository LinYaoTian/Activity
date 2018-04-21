package rdc.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.bean.Organization;
import rdc.bean.User;
import rdc.contract.DetailContract;
import rdc.presenter.DetailPresenter;
import rdc.util.ACacheUtil;
import rdc.util.ShareUtil;
import rdc.util.UserUtil;

/**
 * Created by WaxBerry on 2018/4/13.
 */

public class DetailActivity extends BaseActivity<DetailPresenter> implements DetailContract.IView {

    private String TAG = "DetailActivity";
    @BindView(R.id.iv_forward_detail) ImageView activity_detail_forward_imageView;
    @BindView(R.id.iv_poster_detail) ImageView activity_detail_poster_imageView;
    @BindView(R.id.tv_title_detail) TextView activity_detail_title_textView;
    @BindView(R.id.tv_seeNum_detail) TextView activity_detail_seeNum_textView;
    @BindView(R.id.tv_attendNum_detail) TextView activity_detail_attendNum_textView;
    @BindView(R.id.tv_time_detail) TextView activity_detail_time_textView;
    @BindView(R.id.tv_school_detail) TextView activity_detail_school_textView;
    @BindView(R.id.tv_place_detail) TextView activity_detail_place_textView;
    @BindView(R.id.tv_tag_detail) TextView activity_detail_tag_textView;
    @BindView(R.id.rv_manager_detail) RelativeLayout activity_detail_manager_relativeLayout;
    @BindView(R.id.iv_manager_detail) ImageView activity_detail_manager_imageView;
    @BindView(R.id.tv_manager_name_detail) TextView activity_detail_manager_name_textView;
    @BindView(R.id.tv_manager_introduction_detail) TextView activity_detail_manager_introduction_textView;
    @BindView(R.id.tv_add_detail) TextView activity_detail_add_textView;
    @BindView(R.id.tv_content_detail) TextView activity_detail_content_textView;
    @BindView(R.id.tv_consult_detail) TextView activity_detail_consult_textView;
    @BindView(R.id.tv_signUp_detail) TextView activity_detail_signUp_textView;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.activity_detail_scrollView)
    ScrollView activity_rootView_scrollView;
    private Activity activity;
    private String objectId;
    private boolean hasSignUp = false;
    private boolean hasFocus = false;
    //    private List<User> focusUserList;
    private ACacheUtil mACacheUtil;


    @Override
    protected int setLayoutResID() {
        return R.layout.activity_detail;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        activity = new Activity();
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        presenter.getDetail(objectId);
        mACacheUtil = ACacheUtil.get(getApplicationContext());

    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        initToolBar();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth  = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams lp = activity_detail_poster_imageView.getLayoutParams();
        lp.width = screenWidth;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        activity_detail_poster_imageView.setLayoutParams(lp);
        activity_detail_poster_imageView.setMaxWidth(screenWidth);
        activity_detail_poster_imageView.setMaxHeight((int)(screenWidth * 0.7));
    }

    @Override
    protected void initListener() {

    }

    /**
     * 分享
     */
    @OnClick(R.id.iv_forward_detail)
    public void onForward() {
        Uri uri = ShareUtil.saveBitmap(ShareUtil.getBitmapByView(activity_rootView_scrollView),"share");
        ShareUtil.share(getSupportFragmentManager(),"活动信息",uri);
    }

    /**
     * 联系发布者
     */
    @OnClick(R.id.tv_consult_detail)
    public void onConsult() {
        if (activity.getManager().getMobilePhoneNumber() == null || activity.getManager().getMobilePhoneNumber().equals("")) {
            Toast.makeText(this, "该发布者还未设置联系电话！", Toast.LENGTH_SHORT).show();
        } else {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + activity.getManager().getMobilePhoneNumber()));
            startActivity(dialIntent);
        }
    }

    /**
     * 关注发布者
     */
    @OnClick(R.id.tv_add_detail)
    public void onAdd() {
        presenter.addFocus(activity.getManager().getObjectId(), hasFocus);
    }

    /**
     * 报名
     */
    @OnClick(R.id.tv_signUp_detail)
    public void onSignUp() {
        presenter.onSignUp(hasSignUp);
    }

    /**
     * 跳转到发布者界面
     */
    @OnClick(R.id.rv_manager_detail)
    public void goToUserDetail() {
        Organization organization = new Organization();
        organization.setId(activity.getManager().getObjectId());
        organization.setImage(activity.getManager().getUserImg());
        organization.setPhoto(activity.getManager().getUserPhoto());
        organization.setName(activity.getManager().getNickname());
        organization.setIntroduction(activity.getManager().getIntroduction());
        OrganizationDetailsActivity.actionStart(DetailActivity.this,organization.getImage()
                        !=null?organization.getImage().getUrl():"",organization.getPhoto()!=null?organization.getPhoto().getUrl():"",
                organization.getName(),organization.getIntroduction(),organization.getId());
    }

    /**
     * 设置详情的信息，增加该详情阅读数量，获取该用户关注列表
     * @param activity
     */
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

        if (activity.getManager().getUserImg().getUrl() == null) {
            activity_detail_manager_imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_gery_40dp));
        } else {
            Glide.with(this).load(activity.getManager().getUserImg().getFileUrl()).into(activity_detail_manager_imageView);
        }
        if (activity.getManager().getNickname() == null || Objects.equals(activity.getManager().getNickname(), "")) {
            activity_detail_manager_name_textView.setText("暂无名称");
        } else {
            activity_detail_manager_name_textView.setText(activity.getManager().getNickname() + "");
        }
        if (activity.getManager().getIntroduction() == null || Objects.equals(activity.getManager().getIntroduction(), "")) {
            activity_detail_manager_introduction_textView.setText("暂无简介");
        } else {
            activity_detail_manager_introduction_textView.setText(activity.getManager().getIntroduction());
        }
        activity_detail_content_textView.setText(activity.getContent());

        int currentSawNum = activity.getSawnum();
        presenter.addSawNum(currentSawNum);
        presenter.getUserconcernedList();

        if (Objects.equals(activity.getManager().getObjectId(), BmobUser.getCurrentUser().getObjectId())) {
            activity_detail_add_textView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置起始的时候用户是否已经报名该活动
     * @param userList
     */
    @Override
    public void setDetailAttcipator(List<User> userList) {
        if (userList == null || userList.size() == 0) {
            activity_detail_attendNum_textView.setText(0 + "");
        } else {
            activity_detail_attendNum_textView.setText(userList.size() + "");
            for (int i = 0; i < userList.size(); i++) {
                if (Objects.equals(userList.get(i).getObjectId(), BmobUser.getCurrentUser().getObjectId())) {
                    hasSignUp = true;
                    activity_detail_signUp_textView.setText("取消报名");
                    activity_detail_signUp_textView.setBackgroundColor(getResources().getColor(R.color.darkgray));
                    return;
                }
            }
        }
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "获取详情成功");
    }

    /**
     * 关注/取消关注成功
     */
    @Override
    public void onSingOrUnSingUpSuccess() {
        int attendNum = Integer.parseInt(activity_detail_attendNum_textView.getText().toString());
        if (hasSignUp) {
            activity_detail_signUp_textView.setText("我要报名");
            activity_detail_signUp_textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            hasSignUp = false;
            attendNum--;
            activity_detail_attendNum_textView.setText(attendNum + "");
            Toast.makeText(this, "取消报名成功!", Toast.LENGTH_SHORT).show();
        } else {
            activity_detail_signUp_textView.setText("取消报名");
            activity_detail_signUp_textView.setBackgroundColor(getResources().getColor(R.color.darkgray));
            hasSignUp = true;
            attendNum++;
            activity_detail_attendNum_textView.setText(attendNum + "");
            Toast.makeText(this, "报名成功!", Toast.LENGTH_SHORT).show();
            mACacheUtil.clear();
        }
    }

    @Override
    public void onError(String errMeg) {
        Log.d(TAG, "获取活动详情错误， " + errMeg);
        showConfirmDialog();
        Toast.makeText(this, "抱歉，遇到了一个预料之外的错误！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getobjectId() {
        return objectId;
    }

    @Override
    public void setUserconcernedList(List<User> userconcernedList) {
        Log.d(TAG, "我的ID ：" + BmobUser.getCurrentUser().getObjectId());
        Log.d(TAG, "我关注的人数 ：" + userconcernedList.size());
//        String userId = BmobUser.getCurrentUser().getObjectId();
        if (userconcernedList != null && userconcernedList.size() != 0) {
            for (int i = 0; i < userconcernedList.size(); i++) {
                Log.d(TAG, "我关注的人的ID ：" + userconcernedList.get(i).getObjectId());
                if (userconcernedList.get(i).getObjectId().equals(activity.getManager().getObjectId())) {
                    hasFocus = true;
                    activity_detail_add_textView.setText("已关注");
                    activity_detail_add_textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_blue_24dp, 0);
                    return;
                }
            }
        }
    }

    @Override
    public void onConcernedSuccess() {
        if (hasFocus) {
            activity_detail_add_textView.setText("关注");
            activity_detail_add_textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_add_blue_24dp, 0);
            hasFocus = false;
            Toast.makeText(this, "取消关注成功", Toast.LENGTH_SHORT).show();
        } else {
            activity_detail_add_textView.setText("已关注");
            activity_detail_add_textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_blue_24dp, 0);
            hasFocus = true;
            Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
            mACacheUtil.clear();
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

    /**
     * 启动详情界面时调用该方法
     * @param context
     * @param objectId
     */
    public static void actionStart(Context context, String objectId) {
        Intent intent = new Intent(context, DetailActivity.class);
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

    public void showConfirmDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_detial_delete, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("抱歉！")//设置对话框的标题
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();

                    }
                }).create();
        dialog.show();
    }

    /**
     * 获取Presenter实例
     */
    @Override
    public DetailPresenter getInstance() {
        return new DetailPresenter();
    }
}
