package rdc.contract;

import android.content.Intent;
import android.net.Uri;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import rdc.bean.User;

/**
 * Created by WaxBerry on 2018/4/16.
 */

public interface ReleaseContract {

    interface IView {
        void onSuccess();
        void onError(String message);

        String getImagePath();
        String getActivityTheme();
        String getStartTime();
        String getEndTime();
        String getUniversity();
        String getPlace();
        String getTag();
        String getContent();
    }

    interface IModel{
        void release(User manager, String university, String imagePath, String place, Integer sawnum, String sendtime, String tag, String time, String title,
                     String content, BmobRelation attcipator);
    }

    interface IPresenter{
        void release();
        void releaseResult(Boolean state,String message);
    }
}
