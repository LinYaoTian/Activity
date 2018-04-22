package rdc.model;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import rdc.bean.Activity;
import rdc.bean.User;
import rdc.contract.ReleaseContract;

/**
 * Created by WaxBerry on 2018/4/16.
 */

public class ReleaseModel implements ReleaseContract.IModel{

    private String TAG = "ReleaseModel";

    private ReleaseContract.IPresenter mPresenter;

    public ReleaseModel(ReleaseContract.IPresenter presenter){
        mPresenter = presenter;
    }

    @Override
    public void release(final User manager, final String university, final String imagePath, final String place, final Integer sawnum, final String sendtime, final String tag, final String time, final String title,
                        final String content, final BmobRelation attcipator, final BmobDate expirationDate) {
        Log.d(TAG, "RELEASE");

        final BmobFile photoBmobFile = new BmobFile(new File(Environment.getExternalStorageDirectory().getPath(),"cccc.jpg"));
        final Activity activity = new Activity();
        photoBmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    activity.setManager(manager);
                    activity.setUniversity(university);
                    activity.setImage(photoBmobFile);
                    activity.setPlace(place);
                    activity.setSawnum(sawnum);
                    activity.setSendtime(sendtime);
                    activity.setTag(tag);
                    activity.setTime(time);
                    activity.setTitle(title);
                    activity.setExpirationDate(expirationDate);
                    activity.setContent(content);
                    activity.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {

                                mPresenter.releaseResult(true, null);
                                mPresenter.addTrip();   //...
                            }else {

                                mPresenter.releaseResult(false, "发布失败！" + e.getMessage());
                            }
                        }
                    });
                }else {

                }
            }
        });
    }

    @Override
    public void addTrip() {

        final User currentUser = BmobUser.getCurrentUser(User.class);
        BmobQuery<Activity> query = new BmobQuery<>();
        query.order("-sendtime");
        query.addWhereEqualTo("manager", currentUser);
        query.setLimit(1);
        query.findObjects(new FindListener<Activity>() {
            @Override
            public void done(List<Activity> activityList, BmobException e) {
                if(e == null) {
                    Activity activityJustSend = activityList.get(0);
                    String activityJustSendObjectId = activityJustSend.getObjectId();
                    Activity activity = new Activity();
                    activity.setObjectId(activityJustSendObjectId);
                    BmobRelation relation = new BmobRelation();
                    relation.add(activity);
                    currentUser.setTrip(relation);
                    currentUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){

                            }else{

                            }
                        }
                    });
                    String newSendTime = activityJustSend.getSendtime();
                    User currentUser = BmobUser.getCurrentUser(User.class);
                    currentUser.setNewSendTime(newSendTime);
                    currentUser.update(currentUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                            }else {
                            }
                        }
                    });
                }else {

                }
            }
        });
    }
}
