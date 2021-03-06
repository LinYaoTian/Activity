package rdc.fragment;

import android.content.Intent;
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

import java.util.List;

import butterknife.BindView;
import rdc.activity.DetailActivity;
import rdc.activity.MainActivity;
import rdc.activity.ReleaseActivity;
import rdc.adapter.ActivitiesRvAdapter;
import rdc.avtivity.R;
import rdc.base.BaseLazyLoadFragment;
import rdc.bean.Activity;
import rdc.contract.ActivityFragmentContract;
import rdc.listener.OnClickRecyclerViewListener;
import rdc.presenter.ActivityFragmentPresenter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Lin Yaotian on 2018/4/12.
 */

public class ActivityFragment extends BaseLazyLoadFragment<ActivityFragmentPresenter> implements
        ActivityFragmentContract.View{

    @BindView(R.id.rv_activities_fragment)
    RecyclerView mRvActivities;
    @BindView(R.id.srl_refresh_fragment)
    SwipeRefreshLayout mSrlRefresh;

    public static final String TAG = "LYT";

    private ProgressBar mPbLoading;
    private TextView mTvLoadTip;
    private TextView mTvNoData;
    private ActivitiesRvAdapter mActivityListAdapter;
    private LinearLayoutManager mActivityRvLayoutManager;
    private int mDistance;
    private boolean mFabIsVisible;//Fab按钮是否可见
    private boolean isPrepare;//View 是否初始化好
    private boolean isLazyLoadFinished;//是否懒加载完数据
    private boolean hasMoreData;//没有更多数据
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
        outState.putString("ItemTag", mTag);
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
        isLazyLoadFinished = false;
        hasMoreData = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle != null){
            mTag = bundle.getString("ItemTag");
        }
        lazyLoad();
    }

    @Override
    protected void initView() {
        mActivityListAdapter = new ActivitiesRvAdapter();
        mActivityRvLayoutManager = new LinearLayoutManager(mBaseActivity,LinearLayoutManager.VERTICAL, false);
        mRvActivities.setLayoutManager(mActivityRvLayoutManager);
        mRvActivities.setAdapter(mActivityListAdapter);
        View mViewLoadMore = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_loadmore, mRvActivities, false);
        mActivityListAdapter.setFooterView(mViewLoadMore);
        mPbLoading = mViewLoadMore.findViewById(R.id.pb_loading_layout);
        mTvLoadTip = mViewLoadMore.findViewById(R.id.tv_load_tip_layout);
        View noDataView = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_none, mRvActivities,false);
        mTvNoData = noDataView.findViewById(R.id.tv_no_data_layout_none);
        mActivityListAdapter.setNoneView(noDataView);

        mSrlRefresh.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    protected void setListener() {
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTvNoData.setVisibility(View.GONE);
                mPbLoading.setVisibility(View.VISIBLE);
                mTvLoadTip.setVisibility(View.GONE);
                presenter.refresh(mTag);
            }
        });

        mActivityListAdapter.setOnRecyclerViewListener(new OnClickRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                DetailActivity.actionStart(mBaseActivity,mActivityListAdapter.getActivityObjectId(position));
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }

            @Override
            public void onFooterViewClick() {
                if (hasMoreData){
                    mPbLoading.setVisibility(View.VISIBLE);
                    mTvLoadTip.setVisibility(View.GONE);
                    presenter.getMore(mTag);
                }
            }
        });

        mRvActivities.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisiblePosition = mActivityRvLayoutManager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= mActivityRvLayoutManager.getItemCount() - 1){
                        //发起网络请求获取数据
                        presenter.getMore(mTag);
                    }
                }
            }

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
        if (!isLazyLoadFinished){
            mTvNoData.setVisibility(View.GONE);
            mSrlRefresh.setRefreshing(true);
            presenter.refresh(mTag);
        }
    }

    @Override
    public void refresh(List<Activity> list) {
        mActivityListAdapter.updateData(list);
        isLazyLoadFinished = true;
        mSrlRefresh.setRefreshing(false);
    }

    @Override
    public void append(List<Activity> list) {
        mActivityListAdapter.appendData(list);
    }

    @Override
    public void refreshError(String message) {
        mTvNoData.setVisibility(View.VISIBLE);
        mSrlRefresh.setRefreshing(false);
        showToast(message);
    }

    @Override
    public void getMoreError(String message) {
        mPbLoading.setVisibility(View.GONE);
        mTvLoadTip.setVisibility(View.VISIBLE);
        mTvLoadTip.setText(getResources().getString(R.string.load_error));
        showToast(message);
    }

    @Override
    public void noMoreData() {
        mTvNoData.setVisibility(View.VISIBLE);
        hasMoreData = false;
        mPbLoading.setVisibility(View.GONE);
        mTvLoadTip.setVisibility(View.VISIBLE);
        mTvLoadTip.setText(getResources().getString(R.string.no_more_data));
    }

    /**
     * 设置页面名字
     * @param name
     */
    public void setTagName(String name){
        this.mTag = name;
    }

    /**
     * 用于MainActivity通知ActivityFragment刷新
     * @param tag ActivityFragment的tag
     */
    public void needRefresh(String tag){
        if (tag.equals(mTag) || mTag.equals(getResources().getString(R.string.homePage))){
            if (isLazyLoadFinished){
                mSrlRefresh.setRefreshing(true);
                mTvNoData.setVisibility(View.GONE);
                presenter.refresh(mTag);
            }
        }
    }

}
