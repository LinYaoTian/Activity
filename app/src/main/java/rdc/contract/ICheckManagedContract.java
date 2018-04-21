package rdc.contract;

import java.util.List;

import rdc.bean.Activity;
import rdc.bean.User;

/**
 * Created by asus on 18-4-20.
 */

public interface ICheckManagedContract {
    interface IView {
        void setDetail(Activity activity);
        void setDetailAttcipator(List<User> userList);
        void onError(String errMeg);
        String getobjectId();
        void setDeleteSuccess();
    }

    interface IModel{
        void getDetail( IPresenter iPresenter, String objectId);
        void addSawNum(IPresenter iPresenter, int currentNum, String objectId);
        void deleteActivity(IPresenter presenter,String activityId);
    }

    interface IPresenter{
        void setDetail(Activity activity);
        void setDetailAttcipator(List<User> userList);
        void getDetail(String objectId);
        void addSawNum(int currentNum);
        void deleteActivity(String activityId);
        void setDeleteSuccess();

    }
}
