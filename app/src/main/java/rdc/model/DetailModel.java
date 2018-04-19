package rdc.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
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
    public void getDetail(final DetailContract.IPresenter iPresenter, final String objectId) {
        BmobQuery<Activity> query = new BmobQuery<>();
        query.include("manager");
        query.getObject(objectId, new QueryListener<Activity>() {
            @Override
            public void done(Activity activity, BmobException e) {
                if (e == null) {
                    iPresenter.setDetail(activity);
                    iPresenter.releaseResult(true, null);
                    Log.d(TAG, "获取详情成功");
                    BmobQuery<User> userBmobQuery = new BmobQuery<>();
                    Activity activity1 = new Activity();
                    activity1.setObjectId(objectId);
                    userBmobQuery.addWhereRelatedTo("attcipator", new BmobPointer(activity1));
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null) {
                                Log.d(TAG, "获取详情参与者成功");
                                iPresenter.setDetailAttcipator(list);
                            }else {
                                Log.d(TAG, "获取详情参与者失败");
                                iPresenter.releaseResult(false, e.getMessage());
                            }
                        }
                    });
                }else {
                    Log.d(TAG, "获取详情失败，" + e.getMessage() + " , " + e.getErrorCode());
                    iPresenter.releaseResult(false, e.getMessage());
                }
            }
        });
    }

    @Override
    public void onSignUp(final DetailContract.IPresenter iPresenter, String objectId, boolean hasSignUp) {

        User currentUser = BmobUser.getCurrentUser(User.class);
        Activity activity = new Activity();
        activity.setObjectId(objectId);
        BmobRelation relation = new BmobRelation();
        if (hasSignUp) {
            relation.remove(currentUser);
        }else {
            relation.add(currentUser);
        }
        activity.setAttcipator(relation);
        activity.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d(TAG, "更新成功");
                    iPresenter.onSingOrUnSingUpSuccess();
                }else {
                    Log.d(TAG, "更新失败，" + e.getMessage() + " , " + e.getErrorCode());
                    iPresenter.releaseResult(false, e.getMessage());
                }
            }
        });
    }

    @Override
    public void addSawNum(DetailContract.IPresenter iPresenter, int currentNum, String objectId) {
        Activity activity = new Activity();
        activity.setSawnum(currentNum + 1);
        activity.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d(TAG, "增加查看数量成功");
                }else {
                    Log.d(TAG, "增加查看数量失败");
                }
            }
        });
    }
}
