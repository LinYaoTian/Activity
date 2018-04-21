package rdc.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import rdc.adapter.ActivityFragmentPagerAdapter;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.ItemTag;
import rdc.bean.User;
import rdc.contract.MainContract;
import rdc.fragment.ActivityFragment;
import rdc.presenter.MainPresenter;
import rdc.util.ActivityCollectorUtil;


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
    private List<Fragment> mActivityFragmentList;
    private ActivityFragmentPagerAdapter mFragmentPagerAdapter;
    private String mTagsOrder;
    private long mExitTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mTagsOrder = BmobUser.getCurrentUser(User.class).getTagsOrder();
        super.onCreate(savedInstanceState);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
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
                TagsActivity.actionStart(MainActivity.this,mTagsOrder);
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                if (resultCode == RESULT_OK){
                    String tagsOrder = data.getStringExtra("data_return");
                    if (!mTagsOrder.equals(tagsOrder)){
                        Log.d(TAG, "onActivityResult: ");
                        //用户订阅的菜单顺序或种类有变
                        mTagsOrder = tagsOrder;
                        initData();
                        mFragmentPagerAdapter.setFragments(mActivityFragmentList,mTabNameList);
                    }
                }
                break;
        }
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mTabNameList = new ArrayList<>();
        Gson gson = new Gson();
        List<ItemTag> tagList = gson.fromJson(mTagsOrder,new TypeToken<List<ItemTag>>(){}.getType());
        for (ItemTag itemTag : tagList) {
            if (itemTag.isChecked()){
                mTabNameList.add(itemTag.getTag());
            }
        }
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
        mFragmentPagerAdapter = new ActivityFragmentPagerAdapter(getSupportFragmentManager(),mActivityFragmentList,mTabNameList);
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
                        AccountManageActivity.actionStart(MainActivity.this);
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
                ReleaseActivity.actionStart(MainActivity.this);
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
            Glide.with(this).load(R.drawable.iv_app_ic_blue).into(image);

        } else {
            Glide.with(this).load(user.getUserImg().getUrl()).into(image);
        }

        if (user.getUserPhoto() != null) {
            Glide.with(this).load(user.getUserPhoto().getUrl()).into(photo);
        } else {
            Glide.with(this).load(R.drawable.iv_cover_launch).into(photo);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            showToast("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            ActivityCollectorUtil.finishAll();
        }
    }

}
