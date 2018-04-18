package rdc.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import rdc.bean.Activity;
import rdc.bean.User;
import rdc.contract.ITripContract;

/**
 * Created by asus on 18-4-14.
 */

public class TripModel implements ITripContract.Model {
    private static final String TAG = "TripModel";
    @Override
    public void getMyTripActivity(final ITripContract.Presenter presenter) {
        BmobQuery<Activity> query = new BmobQuery<Activity>();
        User user = BmobUser.getCurrentUser(User.class);
        query.addWhereRelatedTo("trip",new BmobPointer(user));
        query.findObjects(new FindListener<Activity>() {
            @Override
            public void done(List<Activity> list, BmobException e) {
                if (e==null){
                    presenter.setTripActivity(list);
                }else {
                }
            }
        });

    }


    @Override
    public void getRecommenedTripActivity(final ITripContract.Presenter presenter) {
        BmobQuery<Activity> query = new BmobQuery<Activity>();
        query.findObjects(new FindListener<Activity>() {
            @Override
            public void done(List<Activity> list, BmobException e) {
                if (e==null){
                    presenter.setRecommenedTripActivity(list);
                }else {
                }
            }
        });
    }
}
