package rdc.contract;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public interface LaunchContract {
    interface View{
        void updateUserSuccess(String tagsOrder);
        void updateUserError();
    }

    interface Model{
        void updateUser();
    }

    interface Presenter{
        void updateUser();
        void updateSuccess(String tagsOrder);
        void updateError();
    }
}
