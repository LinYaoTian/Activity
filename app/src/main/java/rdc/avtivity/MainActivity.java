package rdc.avtivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import rdc.base.BaseActivity;
import rdc.fragment.ActivityFragment;


public class MainActivity extends BaseActivity {

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
    private ActionBarDrawerToggle mDrawerToggle;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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
        mTabNameList.add(getResources().getString(R.string.homePage));
        mTabNameList.add(getResources().getString(R.string.hot));
        mTabNameList.add(getResources().getString(R.string.competition));
        mTabNameList.add(getResources().getString(R.string.public_welfare));
        mTabNameList.add(getResources().getString(R.string.lecture));
        mTabNameList.add(getResources().getString(R.string.outdoor_activities));

        mActivityFragmentList = new ArrayList<>();
        for (int i = 0; i < mTabNameList.size(); i++) {
            ActivityFragment activityFragment = new ActivityFragment();
            activityFragment.setTabName(mTabNameList.get(i));
            mActivityFragmentList.add(activityFragment);
        }

    }

    @Override
    protected void initView() {
        mVpActivities.setOffscreenPageLimit(mActivityFragmentList.size());
        mVpActivities.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        });
        mTlCategory.setupWithViewPager(mVpActivities);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

    }

    @Override
    protected void initListener() {
        //侧滑栏按钮的监听事件
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_trip:
                        Log.e(TAG, "onNavigationItemSelected: " );
                        break;
                    case R.id.nav_organization:
                        Log.e(TAG, "onNavigationItemSelected: " );
                        break;
                    case R.id.nav_individual:
                        Log.e(TAG, "onNavigationItemSelected: " );
                        break;
                    case R.id.nav_activity:
                        Log.e(TAG, "onNavigationItemSelected: ");
                        break;
                    default:
                        break;

                }
                return true;
            }
        });
    }

    /**
     *  使用动画隐藏FAB按钮
     */
    public void hideFabAnimation() {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f);
        ObjectAnimator.ofPropertyValuesHolder(mFabSend, pvhX, pvhY,pvhZ).setDuration(400).start();
    }

    /**
     * 使用动画显示FAB按钮
     */
    public void showFabAnimation() {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f);
        ObjectAnimator.ofPropertyValuesHolder(mFabSend, pvhX, pvhY,pvhZ).setDuration(400).start();
    }
}
