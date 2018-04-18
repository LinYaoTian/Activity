package rdc.contract;

import cn.bmob.v3.datatype.BmobRelation;
import rdc.bean.Activity;
import rdc.bean.User;

/**
 * Created by WaxBerry on 2018/4/18.
 */

public interface DetailContract {

    interface IView {
        void setDetail(Activity activity);
        void onSuccess();
        void onError(String errMeg);
        String getobjectId();
        BmobRelation getNewBmobRelation();
    }

    interface IModel{
        void getDetail(IPresenter iPresenter, String objectId);
//        void onSignUp(IPresenter iPresenter, String objectId, BmobRelation bmobRelation);
        void onSignUp(IPresenter iPresenter, String objectId);
    }

    interface IPresenter{
        void setDetail(Activity activity);
        void getDetail(String objectId);
        void releaseResult(Boolean state,String message);
        void onSignUp();
    }
}
