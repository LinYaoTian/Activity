package rdc.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import rdc.bean.Activity;
import rdc.bean.User;
import rdc.contract.DetailContract;
import rdc.util.UserUtil;

/**
 * Created by WaxBerry on 2018/4/18.
 */

public class DetailModel implements DetailContract.IModel{

    private static final String TAG = "DetailModel";

    @Override
    public void getDetail(final DetailContract.IPresenter iPresenter, String objectId) {
        BmobQuery<Activity> query = new BmobQuery<>();
        query.include("manager");
        query.getObject(objectId, new QueryListener<Activity>() {
            @Override
            public void done(Activity activity, BmobException e) {
                if (e == null) {
                    iPresenter.setDetail(activity);
                    iPresenter.releaseResult(true, null);
                    Log.d(TAG, "获取详情成功");
                }else {
                    Log.d(TAG, "获取详情失败，" + e.getMessage() + " , " + e.getErrorCode());
                    iPresenter.releaseResult(false, e.getMessage());
                }
            }
        });
    }

//    @Override
//    public void onSignUp(DetailContract.IPresenter iPresenter, String objectId, BmobRelation bmobRelation) {
//        Activity activity = new Activity();
//        activity.setAttcipator(bmobRelation);
//        activity.update(objectId, new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    Log.d(TAG, "更新成功");
//                }else {
//                    Log.d(TAG, "更新失败，" + e.getMessage() + " , " + e.getErrorCode());
//                }
//            }
//        });
//    }

    @Override
//    public void onSignUp(DetailContract.IPresenter iPresenter, String objectId, BmobRelation bmobRelation) {
    public void onSignUp(DetailContract.IPresenter iPresenter, String objectId) {

        User user = UserUtil.getUser();
        Activity activity = new Activity();
        activity.setObjectId(objectId);
        BmobRelation relation = new BmobRelation();
        relation.add(user);
        activity.setAttcipator(relation);
        activity.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d(TAG, "更新成功");
                }else {
                    Log.d(TAG, "更新失败，" + e.getMessage() + " , " + e.getErrorCode());
                }
            }
        });
    }
}
