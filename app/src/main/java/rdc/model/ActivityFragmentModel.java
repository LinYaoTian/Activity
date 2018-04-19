package rdc.model;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rdc.app.App;
import rdc.avtivity.R;
import rdc.bean.Activity;
import rdc.bean.User;
import rdc.contract.ActivityFragmentContract;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public class ActivityFragmentModel implements ActivityFragmentContract.Model {

    public static final String TAG = "LYT";
    private static final int NUM_OF_ONE_PAGE = 3;

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
            query.addQueryKeys("title,time,ItemTag,sawnum,place,image,createdAt");
            if (!tag.equals(App.getmContext().getResources().getString(R.string.hot))
                    && !tag.equals(App.getmContext().getResources().getString(R.string.homePage))){
                query.addWhereEqualTo("ItemTag",tag);
            }
            if (tag.equals(App.getmContext().getResources().getString(R.string.hot))){
                query.order("-sawnum");
            }else {
                query.order("-createdAt");
            }
            //活动有效期大于当前时间
            query.addWhereGreaterThanOrEqualTo("expirationDate",new BmobDate(new Date()));
            //活动与当前用户在同一个学校
            query.addWhereEqualTo("university",user.getUniversity());
            query.setLimit(NUM_OF_ONE_PAGE);
            query.findObjects(new FindListener<Activity>() {
                @Override
                public void done(List<Activity> list, BmobException e) {
                    isRefreshing = false;
                    if (e == null){
                        mPresenter.refreshSuccess(list);
                        if (list.size() != 0){
                            mLastActivity = list.get(list.size()-1);
                        }
                        if (list.size() < NUM_OF_ONE_PAGE){
                            hasMoreData = false;
                            mPresenter.noMoreData();
                        }
                    }else if (e.getMessage().startsWith("The network is not available")){
                        mPresenter.refreshError("无网络！");
                    }else {
                        mPresenter.refreshError(e.getMessage());
                    }
                }
            });
        }
    }

//    private List<Activity> queryfromCache(String ItemTag){
//        User user = BmobUser.getCurrentUser(User.class);
//        BmobQuery<Activity> query = new BmobQuery<>();
//        query.addQueryKeys("title,time,ItemTag,sawnum,place,image,createdAt");
//        if (!ItemTag.equals(App.getmContext().getResources().getString(R.string.hot))
//                && !ItemTag.equals(App.getmContext().getResources().getString(R.string.homePage))){
//            query.addWhereEqualTo("ItemTag",ItemTag);
//        }
//        if (ItemTag.equals(App.getmContext().getResources().getString(R.string.hot))){
//            query.order("-sawnum");
//        }else {
//            query.order("-createdAt");
//        }
//        //活动有效期大于当前时间
//        query.addWhereGreaterThanOrEqualTo("expirationDate",new BmobDate(new Date()));
//        //活动与当前用户在同一个学校
//        query.addWhereEqualTo("university",user.getUniversity());
//        query.setLimit(NUM_OF_ONE_PAGE);
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_THEN_NETWORK);
//        query.setMaxCacheAge(TimeUnit.DAYS.toMillis(10));//缓存10天
//        query.findObjects(new FindListener<Activity>() {
//            @Override
//            public void done(List<Activity> list, BmobException e) {
//                isRefreshing = false;
//                if (e == null){
//                    mPresenter.refreshSuccess(list);
//                    if (list.size() != 0){
//                        mLastActivity = list.get(list.size()-1);
//                    }
//                    if (list.size() < NUM_OF_ONE_PAGE){
//                        hasMoreData = false;
//                        mPresenter.noMoreData();
//                    }
//                }else if (e.getMessage().startsWith("The network is not available")){
//                    mPresenter.refreshError("无网络！");
//                }else {
//                    mPresenter.refreshError(e.getMessage());
//                }
//            }
//        });
//    }

    @Override
    public void getMore(String tag) {
//        Log.d(TAG, "getMore: isRefreshing:"+isLoadingMore+",isLoadingMore:"+isLoadingMore+",hasMoreData:"+hasMoreData);
        if (!isRefreshing && !isLoadingMore && hasMoreData){
            isLoadingMore = true;
            Date date  = null;
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(mLastActivity.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            User user = BmobUser.getCurrentUser(User.class);
            BmobQuery<Activity> query = new BmobQuery<>();
            query.addQueryKeys("title,time,ItemTag,sawnum,place,image,createdAt");
            if (!tag.equals(App.getmContext().getResources().getString(R.string.hot))
                    && !tag.equals(App.getmContext().getResources().getString(R.string.homePage))){
                //除了热门和首页，其他都只获取对应类型的活动
                query.addWhereEqualTo("ItemTag",tag);
            }
            if (tag.equals(App.getmContext().getResources().getString(R.string.hot))){
                ///热门活动按查看数排序
                query.order("-sawnum");
                //分页
                query.addWhereGreaterThan("sawnum",mLastActivity.getSawnum());
            }else {
                //其他活动按时间降序排序
                query.order("-createdAt");
                //分页
                query.addWhereLessThan("createdAt",new BmobDate(date));
            }
            query.addWhereEqualTo("university",user.getUniversity());
            query.addWhereGreaterThanOrEqualTo("expirationDate",new BmobDate(new Date()));//活动有效期大于当前时间
            query.setLimit(NUM_OF_ONE_PAGE);
            query.findObjects(new FindListener<Activity>() {
                @Override
                public void done(List<Activity> list, BmobException e) {
                    if (e == null){
                        mPresenter.appendSuccess(list);
                        if (list.size() != 0){
                            mLastActivity = list.get(list.size()-1);
                        }
                        if (list.size() < NUM_OF_ONE_PAGE){
                            hasMoreData = false;
                            mPresenter.noMoreData();
                        }
                    }else if (e.getMessage().startsWith("The network is not available")){
                        mPresenter.getMoreError("无网络！");
                    }else {
                        mPresenter.getMoreError(e.getMessage());
                    }
                    isLoadingMore = false;
                }
            });
        }
    }
}
