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
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import noman.weekcalendar.WeekCalendar;
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
import rdc.util.LoadingDialogUtil;

import static rdc.configs.TripItemType.sACTIVITY;
import static rdc.configs.TripItemType.sDIVIDER;


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

    private List<Trip> mTripList;
    private TripListRvAdapter mAdapter;
    private Dialog mLoadingDialog;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_trip;
    }

    @Override
    protected void initData() {
        mTripList = new ArrayList<>();
        mAdapter = new TripListRvAdapter(mTripList);
        presenter.getMyTripActivity();



    }

    @Override
    protected void initView() {
        initToolBar();
        LinearLayoutManager manager = new LinearLayoutManager(TripActivity.this, LinearLayoutManager.VERTICAL, false);
        mActivityRecyclerView.setLayoutManager(manager);
        mActivityRecyclerView.setAdapter(mAdapter);
        mLoadingDialog = LoadingDialogUtil.createLoadingDialog(TripActivity.this,"正在加载数据...");
    }

    @Override
    protected void initListener() {
        mWeekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                Toast.makeText(TripActivity.this, "You Selected " + dateTime.toString(), Toast
                        .LENGTH_SHORT).show();
            }

        });


        mPreWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWeekCalendar.moveToPrevious();
                mWeekCalendar.moveToPrevious();
                mWeekCalendar.moveToPrevious();
                mWeekCalendar.moveToPrevious();
                mWeekCalendar.moveToPrevious();
                mWeekCalendar.moveToPrevious();
                mWeekCalendar.moveToPrevious();


            }
        });


        mNextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWeekCalendar.moveToNext();
                mWeekCalendar.moveToNext();
                mWeekCalendar.moveToNext();
                mWeekCalendar.moveToNext();
                mWeekCalendar.moveToNext();
                mWeekCalendar.moveToNext();
                mWeekCalendar.moveToNext();
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TripActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setTripActivity(List<Activity> list) {
        for (int i=0;i<list.size();i++){
            Activity activity = list.get(i);
            Trip trip = new Trip();
            trip.setTitle(activity.getTitle());
            trip.setTime(activity.getTime());
            trip.setSawNum(activity.getSawnum());
            trip.setLocation(activity.getPlace());
            trip.setType(sACTIVITY);
            mTripList.add(trip);
        }
        mAdapter.notifyDataSetChanged();
        LoadingDialogUtil.closeDialog(mLoadingDialog);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    private void  initToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public TripPresenter getInstance() {
        return new TripPresenter();
    }
}