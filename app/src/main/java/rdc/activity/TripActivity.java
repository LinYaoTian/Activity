package rdc.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import rdc.bean.ItemActivity;
import rdc.bean.Trip;

import static rdc.configs.TripItemType.sACTIVITY;
import static rdc.configs.TripItemType.sDIVIDER;


public class TripActivity extends BaseActivity {
    @BindView(R.id.weekCalendar)
    WeekCalendar mWeekCalendar;
    @BindView(R.id.btn_pre_week)
    Button mPreWeekButton;
    @BindView(R.id.btn_next_week)
    Button mNextWeekButton;
    @BindView(R.id.rv_activity)
    RecyclerView mActivityRecyclerView;

    private List<Trip> mTripList;
    private TripListRvAdapter mAdapter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_trip;
    }

    @Override
    protected void initData() {
        mTripList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Trip item = new Trip();
            item.setLocation("广东省广州市白云区广州大道北1883");
            item.setTime("2017年4月30号 9:30至11:00");
            item.setTitle("【DIY】亲手制作一支大牌口红，自己唇色自己调！");
            item.setSawNum(256);
            item.setCoverImageUrl(R.drawable.iv_test_cover + "");
            item.setType(sACTIVITY);
            mTripList.add(item);

        }
        Trip trip = new Trip();
        trip.setType(sDIVIDER);
        mTripList.add(trip);
        for (int i = 0; i < 10; i++) {

            Trip item1 = new Trip();
            item1.setLocation("广东省广州市先烈中路76号中侨大厦13A层H");
            item1.setTime("2018年5月30号 7:30至11:00");
            item1.setTitle("油纸伞彩绘DIY|最美的雨季,我们不见不'伞'");
            item1.setSawNum(120);
            item1.setCoverImageUrl(R.drawable.iv_test_folwer + "");
            mTripList.add(item1);
            item1.setType(sACTIVITY);
        }


    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(TripActivity.this, LinearLayoutManager.VERTICAL, false);
        mActivityRecyclerView.setLayoutManager(manager);
        mAdapter = new TripListRvAdapter(mTripList);
        mActivityRecyclerView.setAdapter(mAdapter);
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
    public BasePresenter getInstance() {
        return null;
    }
}