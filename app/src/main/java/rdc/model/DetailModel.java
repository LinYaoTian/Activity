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
                    BmobQuery<User> userBmobQuery = new BmobQuery<>();
                    Activity activity1 = new Activity();
                    activity1.setObjectId(objectId);
                    userBmobQuery.addWhereRelatedTo("attcipator", new BmobPointer(activity1));
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null) {

                                iPresenter.setDetailAttcipator(list);
                            }else {

                                iPresenter.releaseResult(false, e.getMessage());
                            }
                        }
                    });
                }else {

                    iPresenter.releaseResult(false, e.getMessage());
                }
            }
        });
    }

    @Override
    public void getUserconcernedList(final DetailContract.IPresenter iPresenter) {
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        User currentUser = new User();
        currentUser.setObjectId(BmobUser.getCurrentUser().getObjectId());
        userBmobQuery.addWhereRelatedTo("conncerned", new BmobPointer(currentUser));
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {

                    iPresenter.setUserconcernedList(list);
                }else {

                    iPresenter.releaseResult(false, e.getMessage());
                }
            }
        });
    }

    @Override
    public void onSignUp(final DetailContract.IPresenter iPresenter, String objectId, final boolean hasSignUp) {
        final User currentUser = BmobUser.getCurrentUser(User.class);
        final Activity activity = new Activity();
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

                    iPresenter.onSingOrUnSingUpSuccess();
                    BmobRelation relation = new BmobRelation();
                    if (hasSignUp) {
                        relation.remove(activity);
                    }else {
                        relation.add(activity);
                    }
                    currentUser.setTrip(relation);
                    currentUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){

                            }else{

                            }
                        }
                    });
                }else {

                    iPresenter.releaseResult(false, e.getMessage());
                }
            }
        });
    }

    @Override
    public void addFocus(final DetailContract.IPresenter iPresenter, String toFocusUserObjectId, boolean hasFocus) {
        User currentUser = BmobUser.getCurrentUser(User.class);
        User toFocusUser = new User();
        toFocusUser.setObjectId(toFocusUserObjectId);
        BmobRelation relation = new BmobRelation();
        if (hasFocus) {
            relation.remove(toFocusUser);
        }else {
            relation.add(toFocusUser);
        }
        currentUser.setConncerned(relation);
        currentUser.update(BmobUser.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    iPresenter.onConcernedSuccess();
                }else {

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

                }else {

                }
            }
        });
    }
}
