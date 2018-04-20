package rdc.contract;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public interface LaunchContract {
    interface View{
        void updateUserSuccess();
        void updateUserError();
    }

    interface Model{
        void updateUser();
    }

    interface Presenter{
        void updateUser();
        void updateSuccess();
        void updateError();
    }
}
