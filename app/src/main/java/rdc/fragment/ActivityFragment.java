package rdc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rdc.activity.MainActivity;
import rdc.adapter.ActivitiesRvAdapter;
import rdc.avtivity.R;
import rdc.base.BaseLazyLoadFragment;
import rdc.bean.Activity;
import rdc.contract.ActivityFragmentContract;
import rdc.listener.OnClickRecyclerViewListener;
import rdc.presenter.ActivityFragmentPresenter;

/**
 * Created by Lin Yaotian on 2018/4/12.
 */

public class ActivityFragment extends BaseLazyLoadFragment<ActivityFragmentPresenter> implements ActivityFragmentContract.View {

    @BindView(R.id.rv_activities_fragment)
    RecyclerView mRvActivities;
    @BindView(R.id.srl_refresh_fragment)
    SwipeRefreshLayout mSrlRefresh;

    private ProgressBar mPbLoading;
    private TextView mTvLoadError;
    private View mViewLoadMore;
    private ActivitiesRvAdapter mActivityListAdapter;
    private List<Activity> mActivityList;
    private int mDistance;
    private boolean mFabIsVisible;//Fab按钮是否可见
    private boolean isPrepare;//View 是否初始化好
    private boolean isLoaded;//懒加载时判断数据是否加载过
    private String mTag;//所在标签页

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        isPrepare = true;
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tag", mTag);
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_activities;
    }

    @Override
    public ActivityFragmentPresenter getInstance() {
        return new ActivityFragmentPresenter();
    }

    @Override
    protected void initData(Bundle bundle) {
        mDistance = 0;
        mFabIsVisible = true;
        isPrepare = false;
        isLoaded = false;
        mActivityList = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            ItemActivity item = new ItemActivity();
//            item.setLocation("广东省广州市白云区广州大道北1883");
//            item.setTime("2017年4月30号 9:30至11:00");
//            item.setTitle("【DIY】亲手制作一支大牌口红，自己唇色自己调！");
//            item.setSawNum(256);
//            item.setCoverImageUrl(R.drawable.iv_test_cover+"");
//            mActivityList.add(item);
//            ItemActivity item1 = new ItemActivity();
//            item1.setLocation("广东省广州市先烈中路76号中侨大厦13A层H");
//            item1.setTime("2018年5月30号 7:30至11:00");
//            item1.setTitle("油纸伞彩绘DIY|最美的雨季,我们不见不'伞'");
//            item1.setSawNum(120);
//            item1.setCoverImageUrl(R.drawable.iv_test_folwer+"");
//            mActivityList.add(item1);
//        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle != null){
            mTag = bundle.getString("tag");
        }
        lazyLoad();
    }

    @Override
    protected void initView() {

        mActivityListAdapter = new ActivitiesRvAdapter();
        mRvActivities.setLayoutManager(
                new LinearLayoutManager(mBaseActivity,LinearLayoutManager.VERTICAL, false));
        mRvActivities.setAdapter(mActivityListAdapter);
        mViewLoadMore = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_loadmore, mRvActivities,false);
        mActivityListAdapter.setFooterView(mViewLoadMore);
        mPbLoading = mViewLoadMore.findViewById(R.id.pb_loading_layout);
        mTvLoadError = mViewLoadMore.findViewById(R.id.tv_load_error_layout);
        View noneView = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_none, mRvActivities,false);
        mActivityListAdapter.setNoneView(noneView);

        mSrlRefresh.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    protected void setListener() {
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoaded = true;
                mSrlRefresh.setRefreshing(false);
            }
        });

        mActivityListAdapter.setOnRecyclerViewListener(new OnClickRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }

            @Override
            public void onFooterViewClick() {
                mPbLoading.setVisibility(View.VISIBLE);
                mTvLoadError.setVisibility(View.GONE);
                presenter.getMore();
            }
        });

        mRvActivities.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mDistance < -ViewConfiguration.get(mBaseActivity).getScaledTouchSlop() && !mFabIsVisible){
                    //显示FAB
                    mFabIsVisible = true;
                    mDistance = 0;
                    ((MainActivity)mBaseActivity).showFabAnimation();
                }else if (mDistance > ViewConfiguration.get(mBaseActivity).getScaledTouchSlop() && mFabIsVisible){
                    //隐藏
                    mFabIsVisible = false;
                    mDistance = 0;
                    ((MainActivity)mBaseActivity).hideFabAnimation();
                }
                if ((dy > 0 && mFabIsVisible) || (dy < 0 && !mFabIsVisible)){
                    //向下滑并且可见  或者  向上滑并且不可见
                    mDistance += dy;
                }
            }


        });
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepare || !isVisible){
            return;
        }
        if (!isLoaded){
            mSrlRefresh.setRefreshing(true);
            presenter.refresh(mTag);
        }
    }

    /**
     * 设置页面名字
     * @param name
     */
    public void setTabName(String name){
        this.mTag = name;
    }

    @Override
    public void refresh(List<Activity> list) {
        mActivityListAdapter.updateData(list);
        isLoaded = true;
        mSrlRefresh.setRefreshing(false);
    }

    @Override
    public void append(List<Activity> list) {
        mActivityListAdapter.appendData(list);
    }

    @Override
    public void refreshError(String message) {
        mSrlRefresh.setRefreshing(false);
        showToast(message);
    }

    @Override
    public void getMoreError(String message) {
        mPbLoading.setVisibility(View.GONE);
        mTvLoadError.setVisibility(View.VISIBLE);
        showToast(message);
    }
}
