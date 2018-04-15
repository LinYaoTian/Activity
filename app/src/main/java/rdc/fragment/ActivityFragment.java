package rdc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rdc.activity.MainActivity;
import rdc.adapter.ActivityListRvAdapter;
import rdc.avtivity.R;
import rdc.base.BaseLazyLoadFragment;
import rdc.bean.Activity;
import rdc.bean.ItemActivity;
import rdc.contract.ActivityFragmentContract;
import rdc.listener.OnClickRecyclerViewListener;
import rdc.presenter.ActivityFragmentPresenter;

/**
 * Created by Lin Yaotian on 2018/4/12.
 */

public class ActivityFragment extends BaseLazyLoadFragment<ActivityFragmentPresenter> implements ActivityFragmentContract.View {

    @BindView(R.id.rv_activity_list_fragment)
    RecyclerView mRvActivityList;
    @BindView(R.id.srl_refresh_fragment)
    SwipeRefreshLayout mSrlRefresh;

    private View mViewLoadMore;
    private ActivityListRvAdapter mActivityListAdapter;
    private List<ItemActivity> mActivityList;
    private int mDistance;
    private boolean mFabIsVisible;//Fab按钮是否可见
    private boolean isPrepare;
    private boolean isLoaded;//数据是否加载完成
    private String mTabName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        isPrepare = true;
        lazyLoad();
        return view;
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
        for (int i = 0; i < 10; i++) {
            ItemActivity item = new ItemActivity();
            item.setLocation("广东省广州市白云区广州大道北1883");
            item.setTime("2017年4月30号 9:30至11:00");
            item.setTitle("【DIY】亲手制作一支大牌口红，自己唇色自己调！");
            item.setSawNum(256);
            item.setCoverImageUrl(R.drawable.iv_test_cover+"");
            mActivityList.add(item);
            ItemActivity item1 = new ItemActivity();
            item1.setLocation("广东省广州市先烈中路76号中侨大厦13A层H");
            item1.setTime("2018年5月30号 7:30至11:00");
            item1.setTitle("油纸伞彩绘DIY|最美的雨季,我们不见不'伞'");
            item1.setSawNum(120);
            item1.setCoverImageUrl(R.drawable.iv_test_folwer+"");
            mActivityList.add(item1);
        }
    }

    @Override
    protected void initView() {

        mActivityListAdapter = new ActivityListRvAdapter();
        mRvActivityList.setLayoutManager(
                new LinearLayoutManager(mBaseActivity,LinearLayoutManager.VERTICAL, false));
        mRvActivityList.setAdapter(mActivityListAdapter);
        mViewLoadMore = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_loadmore,mRvActivityList,false);
        mActivityListAdapter.setFooterView(mViewLoadMore);

        mSrlRefresh.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    protected void setListener() {
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //获取数据
                mActivityListAdapter.updateData(mActivityList);
                isLoaded = true;
                mSrlRefresh.setRefreshing(false);
            }
        });

        mActivityListAdapter.setOnRecyclerViewListener(new OnClickRecyclerViewListener() {
            int i = 0;
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }

            @Override
            public void onFooterViewClick() {
                List<ItemActivity> list = new ArrayList<>();
                ItemActivity item = new ItemActivity();
                item.setLocation("广东省广州市白云区广州大道北1883");
                item.setTime("2017年4月30号 9:30至11:00");
                item.setTitle("【DIY】亲手制作一支大牌口红，自己唇色自己调！");
                item.setSawNum(i++);
                item.setCoverImageUrl(R.drawable.iv_test_cover+"");
                list.add(item);
                mActivityListAdapter.appendData(list);
            }

            @Override
            public void onHeaderViewClick() {

            }
        });

        mRvActivityList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepare || !isVisible){
            return;
        }
        if (!isLoaded){
            mSrlRefresh.setRefreshing(true);
            mActivityListAdapter.updateData(mActivityList);
            isLoaded = true;
            mSrlRefresh.setRefreshing(false);
        }
    }

    /**
     * 设置页面名字
     * @param name
     */
    public void setTabName(String name){
        this.mTabName = name;
    }

    @Override
    public void update(List<Activity> list) {

    }

    @Override
    public void append(List<Activity> list) {

    }

    @Override
    public void updateError(String message) {

    }

    @Override
    public void getMoreError(String message) {

    }
}
