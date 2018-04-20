package rdc.contract;

import java.util.List;

import rdc.bean.Activity;
import rdc.bean.User;

/**
 * Created by WaxBerry on 2018/4/18.
 */

public interface DetailContract {

    interface IView {
        void setDetail(Activity activity);
        void setDetailAttcipator(List<User> userList);
        void onSuccess();
        void onSingOrUnSingUpSuccess();
        void onError(String errMeg);
        String getobjectId();
        void setUserconcernedList(List<User> userconcernedList);
        void onConcernedSuccess();
    }

    interface IModel{
        void getDetail(IPresenter iPresenter, String objectId);
        void onSignUp(IPresenter iPresenter, String objectId, boolean hasSignUp);
        void addSawNum(IPresenter iPresenter, int currentNum, String objectId);
        void addFocus(IPresenter iPresenter, String toFocusUserObjectId, boolean hasFocus);
        void getUserconcernedList(IPresenter iPresenter);
    }

    interface IPresenter{
        void setDetail(Activity activity);
        void setDetailAttcipator(List<User> userList);
        void getDetail(String objectId);
        void releaseResult(Boolean state,String message);
        void onSignUp(boolean hasSignUp);
        void onSingOrUnSingUpSuccess();
        void addSawNum(int currentNum);
        void addFocus(String toFocusUserObjectId, boolean hasFocus);
        void getUserconcernedList();
        void setUserconcernedList(List<User> userconcernedList);
        void onConcernedSuccess();
    }
}
