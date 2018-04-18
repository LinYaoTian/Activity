package rdc.model;

import android.os.Environment;
import android.util.Log;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
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
                        final String content, final BmobRelation attcipator) {
        Log.d(TAG, "RELEASE");

//        final BmobFile photoBmobFile = new BmobFile(new File(imagePath));
        final BmobFile photoBmobFile = new BmobFile(new File(Environment.getExternalStorageDirectory().getPath(),"cccc.jpg"));
        final Activity activity = new Activity();
        photoBmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d(TAG, "DONE 0");
                }else {
                    Log.d(TAG, "DONE 1");
                }
                activity.setManager(manager);
                activity.setUniversity(university);
                activity.setImage(photoBmobFile);
                activity.setPlace(place);
                activity.setSawnum(sawnum);
                activity.setSendtime(sendtime);
                activity.setTag(tag);
                activity.setTime(time);
                activity.setTitle(title);
                activity.setContent(content);
                activity.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Log.d(TAG, "DONE 2");
                            mPresenter.releaseResult(true, null);
                        }else {
                            Log.d(TAG, "DONE 3");
                            mPresenter.releaseResult(false, "发布失败！" + e.getMessage());
                        }
                    }
                });
            }
        });
    }
}
