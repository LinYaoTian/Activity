package rdc.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobRelation;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.contract.DetailContract;
import rdc.presenter.DetailPresenter;
import rdc.util.UserUtil;

/**
 * Created by WaxBerry on 2018/4/13.
 */

public class DetailActivity extends BaseActivity<DetailPresenter> implements DetailContract.IView {

    private String TAG = "DetailActivity";

    @BindView(R.id.activity_detail_forward_imageView) ImageView activity_detail_forward_imageView;
    @BindView(R.id.activity_detail_poster_imageView) ImageView activity_detail_poster_imageView;
    @BindView(R.id.activity_detail_title_textView) TextView activity_detail_title_textView;
    @BindView(R.id.activity_detail_seeNum_textView) TextView activity_detail_seeNum_textView;
    @BindView(R.id.activity_detail_attendNum_textView) TextView activity_detail_attendNum_textView;
    @BindView(R.id.activity_detail_time_textView) TextView activity_detail_time_textView;
    @BindView(R.id.activity_detail_school_textView) TextView activity_detail_school_textView;
    @BindView(R.id.activity_detail_place_textView) TextView activity_detail_place_textView;
    @BindView(R.id.activity_detail_tag_textView) TextView activity_detail_tag_textView;
    @BindView(R.id.activity_detail_manager_relativeLayout) RelativeLayout activity_detail_manager_relativeLayout;
    @BindView(R.id.activity_detail_manager_imageView) ImageView activity_detail_manager_imageView;
    @BindView(R.id.activity_detail_manager_name_textView) TextView activity_detail_manager_name_textView;
    @BindView(R.id.activity_detail_manager_introduction_textView) TextView activity_detail_manager_introduction_textView;
//    @BindView(R.id.activity_detail_add_textView) TextView activity_detail_add_textView;
    @BindView(R.id.activity_detail_content_textView) TextView activity_detail_content_textView;
    @BindView(R.id.activity_detail_consult_textView) TextView activity_detail_consult_textView;
    @BindView(R.id.activity_detail_signUp_textView) TextView activity_detail_signUp_textView;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    private Activity activity;
    private String objectId;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initData() {
        activity = new Activity();
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        presenter.getDetail(objectId);
    }

    @Override
    protected void initView() {
        initToolBar();
    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.activity_detail_forward_imageView)
    public void onForward() {

    }

    @OnClick(R.id.activity_detail_consult_textView)
    public void onConsult() {

    }

    @OnClick(R.id.activity_detail_signUp_textView)
    public void onSignUp() {
        presenter.onSignUp();
    }

    @OnClick(R.id.activity_detail_manager_relativeLayout)
    public void goToUserDetail() {

    }

    @Override
    public void setDetail(Activity activity) {
        this.activity = activity;
        String[] timeArray = activity.getTime().split("~");
        Glide.with(this).load(activity.getImage().getFileUrl()).into(activity_detail_poster_imageView);
        activity_detail_title_textView.setText(activity.getTitle() + "");
        activity_detail_seeNum_textView.setText(activity.getSawnum() + "");
        activity_detail_attendNum_textView.setText(activity.getAttcipator().getObjects().size() + "");
        activity_detail_time_textView.setText(timeArray[0] + " 至 " + timeArray[1]);
        activity_detail_school_textView.setText(activity.getUniversity());
        activity_detail_place_textView.setText(activity.getPlace());
        activity_detail_tag_textView.setText(activity.getTag());
        Glide.with(this).load(activity.getManager().getUserImg().getFileUrl()).into(activity_detail_manager_imageView);
        if (activity.getManager().getNickname() == null || Objects.equals(activity.getManager().getNickname(), "")) {
            activity_detail_manager_name_textView.setText("暂无名称");
        }else {
            activity_detail_manager_name_textView.setText(activity.getManager().getNickname() + "");
        }
        if (activity.getManager().getIntroduction() == null || Objects.equals(activity.getManager().getIntroduction(), "")) {
            activity_detail_manager_introduction_textView.setText("暂无简介");
        }else {
            activity_detail_manager_introduction_textView.setText(activity.getManager().getIntroduction());
        }
        activity_detail_content_textView.setText(activity.getContent());
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "获取详情成功");
    }

    @Override
    public void onError(String errMeg) {
        Log.d(TAG, "获取活动详情错误， " + errMeg);
    }

    @Override
    public String getobjectId() {
        return objectId;
    }

    @Override
    public BmobRelation getNewBmobRelation() {
        BmobRelation bmobRelation = activity.getAttcipator();
        bmobRelation.add(UserUtil.getUser());
        return bmobRelation;
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
    public DetailPresenter getInstance() {
        return new DetailPresenter();
    }
}
