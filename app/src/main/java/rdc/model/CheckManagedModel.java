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
import rdc.contract.ICheckManagedContract;

/**
 * Created by asus on 18-4-20.
 */

public class CheckManagedModel implements ICheckManagedContract.IModel{

    private static final String TAG = "DetailModel";

    @Override
    public void getDetail(final ICheckManagedContract.IPresenter iPresenter, final String objectId) {
        BmobQuery<Activity> query = new BmobQuery<>();
        query.include("manager");
        query.getObject(objectId, new QueryListener<Activity>() {
            @Override
            public void done(Activity activity, BmobException e) {
                if (e == null) {
                    iPresenter.setDetail(activity);
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


                            }
                        }
                    });
                }else {
                }
            }
        });
    }









    @Override
    public void addSawNum(ICheckManagedContract.IPresenter iPresenter, int currentNum, String objectId) {

    }

    @Override
    public void deleteActivity(final ICheckManagedContract.IPresenter presenter, String activityId) {
          Activity activity = new Activity();
          activity.setObjectId(activityId);
          activity.delete(new UpdateListener() {
              @Override
              public void done(BmobException e) {
                      if (e==null){
                          presenter.setDeleteSuccess();
                      }
              }
          });
    }
}
