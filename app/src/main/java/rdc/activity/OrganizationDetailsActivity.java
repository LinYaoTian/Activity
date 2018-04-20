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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    ImageView ivBack;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.spite_line)
    View spiteLine;
    @BindView(R.id.lv_header)
    RelativeLayout lvHeader;

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
    private Organization organization;
    private Dialog mLoadingDialog;


    @Override
    protected void initData() {
        organization = (Organization) getIntent().getBundleExtra("bundle").get("organization");
        mId = organization.getId();
        mACacheUtil = ACacheUtil.get(getApplicationContext());
        mActivities = new ArrayList<>();
        mAdapter = new OrganizationActivityListAdapter(mActivities, this);
        if (ObjectCastUtil.cast(mACacheUtil.getAsObject(mId)) != null) {
            mActivities.addAll((ArrayList) ObjectCastUtil.cast(mACacheUtil.getAsObject(mId)));
            mAdapter.notifyDataSetChanged();
        } else {
            presenter.getManagedActivity(organization);
            mLoadingDialog = LoadingDialogUtil.createLoadingDialog(OrganizationDetailsActivity.this, "正在加载数据...");

        }

    }

    @Override
    protected void initView() {
        mActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mActivityRecyclerView.setAdapter(mAdapter);


        initViews();
        initHeaderOrganization(organization);



    }

    private void initViews() {

        final float title_height = getResources().getDimension(R.dimen.title_height);
        final float head_height = getResources().getDimension(R.dimen.head_height);

        scrollView.setOnScrollListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScroll(int oldy, int dy, boolean isUp) {
                float move_distance = head_height - title_height;
                if (!isUp && dy <= move_distance) {

                    mToolbar.setBackgroundColor(ContextCompat.getColor(OrganizationDetailsActivity.this, R.color.colorPrimary));
                    TitleAlphaChange(dy, move_distance);
                    HeaderTranslate(dy);

                } else if (!isUp && dy > move_distance) {
                    TitleAlphaChange(1, 1);

                    HeaderTranslate(head_height);
                    ivBack.setImageResource(R.mipmap.ic_back_dark);
                    spiteLine.setVisibility(View.VISIBLE);

                } else if (isUp && dy > move_distance) {


                } else if (isUp && dy <= move_distance) {

                    TitleAlphaChange(dy, move_distance);
                    HeaderTranslate(dy);

                    ivBack.setImageResource(R.mipmap.ic_back);
                    spiteLine.setVisibility(View.GONE);
                }
            }
        });
    }

    private void HeaderTranslate(float distance) {
        lvHeader.setTranslationY(-distance);
    }

    private void TitleAlphaChange(int dy, float mHeaderHeight_px) {

        float percent = (float) Math.abs(dy) / Math.abs(mHeaderHeight_px);
        int alpha = (int) (percent*1.4 * 255);
        if (alpha>255){
            alpha=255;
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
        ivBack.setOnClickListener(new View.OnClickListener() {
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




    @Override
    public void setManagedActivity(List<OrganizationActivity> list) {
        mACacheUtil.put(mId, (ArrayList) list);

        mActivities.addAll(list);
        mAdapter.notifyDataSetChanged();
        LoadingDialogUtil.closeDialog(mLoadingDialog);
    }
private void initHeader(OrganizationActivity activity){
    Glide.with(this).load(activity.getPhoto()).into(mPhoto);
    Glide.with(this).load(activity.getImage()).into(mImage);
    mName.setText(activity.getName());
    mIntroduction.setText(activity.getIntroduction());
}
    @Override
    public OrganizationDetailsPresenter getInstance() {
        return new OrganizationDetailsPresenter();
    }


    private void initHeaderOrganization(Organization organization) {
        OrganizationActivity activity = new OrganizationActivity();
        activity.setPhoto(organization.getPhoto().getUrl());
        activity.setImage(organization.getImage().getUrl());
        activity.setName(organization.getName());
        activity.setType(sORGANIZATION);
        activity.setIntroduction(organization.getIntroduction());
        initHeader(activity);

    }

    @Override
    protected int setLayoutResID() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);   //这里动态修改颜色
        }

        return R.layout.activity_organization_details;
    }

    public static void actionStart(Context context, Organization organization) {
        Intent intent = new Intent(context, OrganizationDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("organization", organization);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivities.clear();
    }
}
