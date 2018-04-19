package rdc.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.User;
import rdc.contract.MainContract;
import rdc.fragment.ActivityFragment;
import rdc.presenter.MainPresenter;
import rdc.util.DisplayUtil;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.toolbar_act_main)
    Toolbar mToolbar;
    @BindView(R.id.vp_activity_list_act_main)
    ViewPager mVpActivities;
    @BindView(R.id.fab_send_act_main)
    FloatingActionButton mFabSend;
    @BindView(R.id.tl_category_act_main)
    TabLayout mTlCategory;
    @BindView(R.id.drawer_layout_act_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;


    private static final String TAG = "MainActivity";

    private List<String> mTabNameList;//顶部Tab名字列表
    private List<ActivityFragment> mActivityFragmentList;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private String mTagsOrder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mTagsOrder = getIntent().getStringExtra("tagsOrder");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserView();
    }

    @Override
    public MainPresenter getInstance() {
        return new MainPresenter();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more_menu_act_main:
                showToast("更多");
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mTabNameList = new ArrayList<>();
        mTabNameList.addAll(Arrays.asList(mTagsOrder.split("，")));
        mActivityFragmentList = new ArrayList<>();
        for (int i = 0; i < mTabNameList.size(); i++) {
            ActivityFragment activityFragment = new ActivityFragment();
            activityFragment.setTagName(mTabNameList.get(i));
            mActivityFragmentList.add(activityFragment);
        }
    }

    @Override
    protected void initView() {
        initToolbar();
        mVpActivities.setOffscreenPageLimit(mActivityFragmentList.size());
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mActivityFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mActivityFragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabNameList.get(position);
            }
        };
        mVpActivities.setAdapter(mFragmentPagerAdapter);
        mTlCategory.setupWithViewPager(mVpActivities);
        ActionBarDrawerToggle mDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);


    }

    @Override
    protected void initListener() {
        //侧滑栏按钮的监听事件
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_trip:
                        TripActivity.actionStart(MainActivity.this);
                        break;
                    case R.id.nav_organization:
                        ConcernedActivity.actionStart(MainActivity.this);
                        break;
                    case R.id.nav_center:
                        UserCenterActivity.actionStart(MainActivity.this);
                        break;
                    case R.id.nav_activity:
                        ManageActivity.actionStart(MainActivity.this);
                        break;
                    default:
                        break;

                }
                return true;
            }
        });

        mNavigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IndividualActivity.actionStart(MainActivity.this);
            }
        });
        mFabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReleaseActivity.class));
            }
        });
    }

    /**
     * 使用动画隐藏FAB按钮
     */
    public void hideFabAnimation() {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f);
        ObjectAnimator.ofPropertyValuesHolder(mFabSend, pvhX, pvhY, pvhZ).setDuration(400).start();
    }

    /**
     * 使用动画显示FAB按钮
     */
    public void showFabAnimation() {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f);
        ObjectAnimator.ofPropertyValuesHolder(mFabSend, pvhX, pvhY, pvhZ).setDuration(400).start();
    }

    /**
     * 初始化个人信息
     */
    public void initUserView() {
        View view = mNavigationView.getHeaderView(0);
        TextView name = view.findViewById(R.id.tv_name);
        TextView introduction = view.findViewById(R.id.tv_introduction);
        ImageView image = view.findViewById(R.id.imv_image);
        ImageView photo = view.findViewById(R.id.imv_photo);
        User user = BmobUser.getCurrentUser(User.class);
        name.setText(user.getNickname());
        introduction.setText(user.getIntroduction());
        if (user.getUserImg() == null) {
            Glide.with(this).load(R.drawable.photo).into(image);

        } else {
            Glide.with(this).load(user.getUserImg().getUrl()).into(image);
        }

        if (user.getUserPhoto() != null) {
            Glide.with(this).load(user.getUserPhoto().getUrl()).into(photo);
        } else {
            Glide.with(this).load(user.getUserPhoto().getUrl()).into(photo);

        }
    }

}
