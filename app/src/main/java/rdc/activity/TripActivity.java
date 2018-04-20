package rdc.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.eventbus.Event;
import noman.weekcalendar.listener.OnDateClickListener;
import rdc.adapter.TripListRvAdapter;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.bean.ItemActivity;
import rdc.bean.Trip;
import rdc.contract.ITripContract;
import rdc.presenter.TripPresenter;
import rdc.util.ACacheUtil;
import rdc.util.DateUtil;
import rdc.util.LoadingDialogUtil;
import rdc.util.ObjectCastUtil;
import rdc.util.SeparateActivityUtil;

import static rdc.configs.TripItemType.sACTIVITY;
import static rdc.configs.TripItemType.sDIVIDER;
import static rdc.util.DateUtil.getToday;


public class TripActivity extends BaseActivity<TripPresenter> implements ITripContract.View {
    @BindView(R.id.weekCalendar)
    WeekCalendar mWeekCalendar;
    @BindView(R.id.btn_pre_week)
    Button mPreWeekButton;
    @BindView(R.id.btn_next_week)
    Button mNextWeekButton;
    @BindView(R.id.rv_activity)
    RecyclerView mActivityRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mThisWeek;
    @BindView(R.id.tv_month)
    TextView mMonth;

    private List<Trip> mTripList;
    private TripListRvAdapter mAdapter;
    private Dialog mLoadingDialog;

    private DateTime mDateTime;
    private ACacheUtil mACacheUtil;



    private static final String TAG = "TripActivity";


    @Override
    protected int setLayoutResID() {
        return R.layout.activity_trip;
    }

    @Override
    protected void initData() {
        mTripList = new ArrayList<>();
        mAdapter = new TripListRvAdapter(mTripList,this);
        mDateTime = new DateTime();
        mACacheUtil = ACacheUtil.get(getApplicationContext());

        if (ObjectCastUtil.cast(mACacheUtil.getAsObject("trip"))!=null
                ||ObjectCastUtil.cast(mACacheUtil.getAsObject("RecommendTrip"))!=null){
            SeparateActivityUtil.getInstance().separate((ArrayList)ObjectCastUtil.cast(mACacheUtil.getAsObject("trip")));
            mTripList.addAll(SeparateActivityUtil.getInstance().getListByDate(getToday()));
            SeparateActivityUtil.getInstance().separateAll((ArrayList)ObjectCastUtil.cast(mACacheUtil.getAsObject("RecommendTrip")));
            mTripList.addAll(SeparateActivityUtil.getInstance().getRecommendListByDate(getToday()));
            mAdapter.notifyDataSetChanged();
        }else {
            presenter.getMyTripActivity();
            mLoadingDialog = LoadingDialogUtil.createLoadingDialog(TripActivity.this, "正在加载数据...");

        }






    }

    @Override
    protected void initView() {
        initToolBar();
        LinearLayoutManager manager = new LinearLayoutManager(TripActivity.this, LinearLayoutManager.VERTICAL, false);
        mActivityRecyclerView.setLayoutManager(manager);
        mActivityRecyclerView.setAdapter(mAdapter);
        mMonth.setText(DateUtil.getMonth(mDateTime));

    }

    @Override
    protected void initListener() {
        mWeekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                try {
                    mTripList.clear();
                    mTripList.addAll(((SeparateActivityUtil.getInstance().getListByDate(dateTime.toString().substring(5, 10)))));
                    mTripList.addAll(((SeparateActivityUtil.getInstance().getRecommendListByDate(dateTime.toString().substring(5, 10)))));
                    mAdapter.notifyDataSetChanged();
                    mDateTime = dateTime;
                    mMonth.setText(DateUtil.getMonth(mDateTime));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });


        mPreWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTripList.clear();
                mDateTime = mDateTime.plusDays(-7);
                mWeekCalendar.setSelectedDate(mDateTime);
                mMonth.setText(DateUtil.getMonth(mDateTime));

                mTripList.addAll(((SeparateActivityUtil.getInstance().getListByDate(mDateTime.toString().substring(5, 10)))));
                mTripList.addAll(((SeparateActivityUtil.getInstance().getRecommendListByDate(mDateTime.toString().substring(5, 10)))));
                mAdapter.notifyDataSetChanged();

            }
        });


        mNextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTripList.clear();
                mDateTime = mDateTime.plusDays(7);
                mWeekCalendar.setSelectedDate(mDateTime);
                mMonth.setText(DateUtil.getMonth(mDateTime));

                mTripList.addAll(((SeparateActivityUtil.getInstance().getListByDate(mDateTime.toString().substring(5, 10)))));
                mTripList.addAll(((SeparateActivityUtil.getInstance().getRecommendListByDate(mDateTime.toString().substring(5, 10)))));
                mAdapter.notifyDataSetChanged();


            }
        });

        mThisWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWeekCalendar.reset();
                mTripList.clear();
                mTripList.addAll(SeparateActivityUtil.getInstance().getListByDate(getToday()));
                mTripList.addAll(SeparateActivityUtil.getInstance().getRecommendListByDate(getToday()));
                mAdapter.notifyDataSetChanged();
                DateTime time = new DateTime();
                mMonth.setText(DateUtil.getMonth(time));



            }
        });

        mAdapter.setOnClickListener(new TripListRvAdapter.OnClickListener() {
            @Override
            public void click(Trip trip) {
                DetailActivity.actionStart(TripActivity.this,trip.getObjectId());
            }
        });

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TripActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setTripActivity(List<Trip> list) {
        mACacheUtil.put("trip",(ArrayList)list,60*5);
        SeparateActivityUtil.getInstance().separate(list);
        mTripList.addAll(SeparateActivityUtil.getInstance().getListByDate(getToday()));
        presenter.getRecommenedTripActivity();


    }


    @Override
    public void setRecommenedTripActivity(List<Trip> list) {
        mACacheUtil.put("RecommendTrip",(ArrayList)list,60*5);

        SeparateActivityUtil.getInstance().separateAll(list);
        mTripList.addAll(SeparateActivityUtil.getInstance().getRecommendListByDate(getToday()));
        mAdapter.notifyDataSetChanged();
        LoadingDialogUtil.closeDialog(mLoadingDialog);
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
    public TripPresenter getInstance() {
        return new TripPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTripList != null) {
            mTripList.clear();
            mTripList = null;
        }
        SeparateActivityUtil.getInstance().release();

    }
}