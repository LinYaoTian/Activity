package rdc.model;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rdc.bean.Activity;
import rdc.bean.User;
import rdc.contract.ActivityFragmentContract;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public class ActivityFragmentModel implements ActivityFragmentContract.Model {

    public static final String TAG = "LYT";

    private ActivityFragmentContract.Presenter mPresenter;
    private boolean isRefreshing;//是否正在刷新
    private boolean isLoadingMore;//是否正在加载更多
    private boolean hasMoreData;//没有更多数据
    private Activity mLastActivity;

    public ActivityFragmentModel(ActivityFragmentContract.Presenter presenter){
        mPresenter = presenter;
        isRefreshing = false;
        isLoadingMore = false;
        hasMoreData = true;
    }

    @Override
    public void refresh(String tag) {
        if (!isRefreshing){
            isRefreshing = true;
            isLoadingMore = false;
            hasMoreData = true;
            User user = BmobUser.getCurrentUser(User.class);
            BmobQuery<Activity> query = new BmobQuery<>();
            query.order("-createdAt");
            query.addQueryKeys("title,time,tag,sawnum,place,image,createdAt");
            query.addWhereEqualTo("tag",tag);
            query.addWhereEqualTo("university",user.getUniversity());
            query.setLimit(15);
            query.findObjects(new FindListener<Activity>() {
                @Override
                public void done(List<Activity> list, BmobException e) {
                    isRefreshing = false;
                    if (e == null){
                        mPresenter.refreshSuccess(list);
                        mLastActivity = list.get(list.size()-1);
                        if (list.size() < 15){
                            hasMoreData = false;
                            mPresenter.noMoreData();
                        }
                    }else {
                        mPresenter.refreshError(e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void getMore(String tag) {
        if (!isRefreshing && !isLoadingMore && hasMoreData){
            Log.d(TAG, "getMore: 进入加载更多");
            isLoadingMore = true;
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date  = null;
            try {
                date = sdf.parse(mLastActivity.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            User user = BmobUser.getCurrentUser(User.class);
            BmobQuery<Activity> query = new BmobQuery<>();
            query.order("-createdAt");
            query.addQueryKeys("title,time,tag,sawnum,place,image,createdAt");
            query.addWhereEqualTo("tag",tag);
            query.addWhereEqualTo("university",user.getUniversity());
            query.addWhereGreaterThan("createdAt",new BmobDate(date));
            query.setLimit(15);
            query.findObjects(new FindListener<Activity>() {
                @Override
                public void done(List<Activity> list, BmobException e) {
                    if (e == null){
                        Log.d(TAG, "done: "+list.size());
                        mPresenter.appendSuccess(list);
                        mLastActivity = list.get(list.size()-1);
                        if (list.size() < 15){
                            hasMoreData = false;
                            mPresenter.noMoreData();
                        }
                    }else {
                        mPresenter.getMoreError(e.getMessage());
                    }
                    isLoadingMore = false;
                }
            });
        }
    }
}
