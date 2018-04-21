package rdc.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rdc.adapter.OrganizationActivityListAdapter;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.Organization;
import rdc.bean.OrganizationActivity;
import rdc.contract.IOrganizationDetailsContract;
import rdc.presenter.OrganizationDetailsPresenter;
import rdc.util.ACacheUtil;
import rdc.util.LoadingDialogUtil;
import rdc.util.ObjectCastUtil;
import rdc.widget.ObservableScrollView;

import static rdc.configs.OrganizationItemType.sORGANIZATION;

public class OrganizationDetailsActivity extends BaseActivity<OrganizationDetailsPresenter> implements IOrganizationDetailsContract.View {

    @BindView(R.id.rv_organization)
    RecyclerView mActivityRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.scrollView)
    ObservableScrollView mScrollView;
    @BindView(R.id.spite_line)
    View mSpiteLine;
    @BindView(R.id.lv_header)
    RelativeLayout mHeader;

    @BindView(R.id.imv_photo)
    ImageView mPhoto;
    @BindView(R.id.imv_image)
    ImageView mImage;
    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.tv_introduction)
    TextView mIntroduction;
    private List<OrganizationActivity> mActivities;
    private ACacheUtil mACacheUtil;
    private OrganizationActivityListAdapter mAdapter;
    private String mId;
    private Dialog mLoadingDialog;
    private String mImageUrl;
    private String mPhotoUrl;
    private String mNames;
    private String mIntroductions;


    @Override
    protected void initData() {
        //获取个人信息类
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mImageUrl =bundle.getString("image");
        mPhotoUrl =bundle.getString("photo");
        mNames =bundle.getString("name");
        mIntroductions =bundle.getString("introduction");
        mId = bundle.getString("id");

        mACacheUtil = ACacheUtil.get(getApplicationContext());
        mActivities = new ArrayList<>();
        mAdapter = new OrganizationActivityListAdapter(mActivities, this);
        //先从缓存中读取，缓存没有联网获取数据

        if (ObjectCastUtil.cast(mACacheUtil.getAsObject(mId)) != null) {
            mActivities.addAll((ArrayList) ObjectCastUtil.cast(mACacheUtil.getAsObject(mId)));
            mAdapter.notifyDataSetChanged();
        } else {
            presenter.getManagedActivity(mId);
            mLoadingDialog = LoadingDialogUtil.createLoadingDialog(OrganizationDetailsActivity.this, "正在加载数据...");

        }

    }

    @Override
    protected void initView() {
        mActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mActivityRecyclerView.setAdapter(mAdapter);


        initViews();
        initHeaderOrganization();


    }

    /**
     * 初始化ScrollView的滑动监听
     */
    private void initViews() {
        //获取dimen属性中 标题和头部的高度
        final float title_height = getResources().getDimension(R.dimen.title_height);
        final float head_height = getResources().getDimension(R.dimen.head_height);

        mScrollView.setOnScrollListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScroll(int oldy, int dy, boolean isUp) {
                float move_distance = head_height - title_height;
                if (!isUp && dy <= move_distance) {
                    //手指往上滑
                    // 标题栏逐渐从透明变成不透明

                    mToolbar.setBackgroundColor(ContextCompat.getColor(OrganizationDetailsActivity.this, R.color.colorPrimary));
                    TitleAlphaChange(dy, move_distance);
                    HeaderTranslate(dy);

                } else if (!isUp && dy > move_distance) {
                    //手指往上滑
                    //设置不透明百分比为100%
                    TitleAlphaChange(1, 1);
                    HeaderTranslate(head_height);
                    mBack.setImageResource(R.drawable.iv_back_dark);
                    mSpiteLine.setVisibility(View.VISIBLE);

                } else if (isUp && dy > move_distance) {


                } else if (isUp && dy <= move_distance) {
                    //标题栏逐渐从不透明变成透明

                    TitleAlphaChange(dy, move_distance);
                    HeaderTranslate(dy);

                    mBack.setImageResource(R.drawable.iv_back_white);
                    mSpiteLine.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     *隐藏头部
     * @param distance
     */
    private void HeaderTranslate(float distance) {
        mHeader.setTranslationY(-distance);
    }

    /**
     * 根据滑动的位置显示TitleBar,设置背景色
     * @param dy
     * @param mHeaderHeight_px
     */
    private void TitleAlphaChange(int dy, float mHeaderHeight_px) {

        float percent = (float) Math.abs(dy) / Math.abs(mHeaderHeight_px);
        int alpha = (int) (percent * 1.4 * 255);
        if (alpha > 255) {
            alpha = 255;
        }
        mToolbar.getBackground().setAlpha(alpha);


    }

    @Override
    protected void initListener() {


        mAdapter.setClickListener(new OrganizationActivityListAdapter.OnClickListener() {
            @Override
            public void click(OrganizationActivity activity) {
                DetailActivity.actionStart(OrganizationDetailsActivity.this, activity.getId());
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


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
     * @param list
     */

    @Override
    public void setManagedActivity(List<OrganizationActivity> list) {
        mACacheUtil.put(mId, (ArrayList) list);//缓存
        mActivities.addAll(list);
        mAdapter.notifyDataSetChanged();
        LoadingDialogUtil.closeDialog(mLoadingDialog);
    }
    //初始个人主页的信息
    private void initHeader(OrganizationActivity activity) {
        if (!TextUtils.isEmpty(activity.getPhoto())){
            Glide.with(this).load(activity.getPhoto()).into(mPhoto);

        }else{
            Glide.with(this).load(R.drawable.iv_cover_launch).into(mPhoto);
        }
        if (!TextUtils.isEmpty(activity.getImage())){
            Glide.with(this).load(activity.getImage()).into(mImage);

        }else {
            Glide.with(this).load(R.drawable.iv_app_ic_blue).into(mImage);

        }
        mName.setText(activity.getName());
        mIntroduction.setText(activity.getIntroduction());
    }

    @Override
    public OrganizationDetailsPresenter getInstance() {
        return new OrganizationDetailsPresenter();
    }

    /**
     * 显示个人主页的信息
     * @param
     */
    private void initHeaderOrganization() {
        OrganizationActivity activity = new OrganizationActivity();
        activity.setPhoto(mPhotoUrl);
        activity.setImage(mImageUrl);
        activity.setName(mNames);
        activity.setType(sORGANIZATION);
        activity.setIntroduction(mIntroductions);
        initHeader(activity);

    }

    /**
     * 设置状态栏顶上
     * @return
     */
    @Override
    protected int setLayoutResID() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getColor(R.color.color_dark_transparent));   //这里动态修改颜色
        }

        return R.layout.activity_organization_details;
    }

    /**
     * 启动方法
     * @param context
     * @param
     */
    public static void actionStart(Context context,String imageUrl,String photoUrl,String names,String introductions,String id) {
        Intent intent = new Intent(context, OrganizationDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("image",imageUrl);
        bundle.putString("photo",photoUrl);
        bundle.putString("name",names);
        bundle.putString("introduction",introductions);
        bundle.putString("id",id);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    public void setOnError() {
        Toast.makeText(this,"获取数据失败!",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivities.clear();
    }
}
