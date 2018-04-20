package rdc.contract;

import cn.bmob.v3.datatype.BmobDate;
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
        String getSendTime();
        BmobDate getExpirationDate();
        void showProgressDialog();
        void dismissProgressDialog();
    }

    interface IModel {
        void release(User manager, String university, String imagePath, String place, Integer sawnum, String sendtime, String tag, String time, String title,
                     String content, BmobRelation attcipator, BmobDate expirationDate);
        void addTrip();
    }

    interface IPresenter {
        void release();
        void releaseResult(Boolean state,String message);
        void addTrip();
    }
}
