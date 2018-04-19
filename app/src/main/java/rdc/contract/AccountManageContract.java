package rdc.contract;

/**
 * Created by Lin Yaotian on 2018/4/18.
 */

public interface AccountManageContract {
    interface View{
        void changePasswordSuccess();
        void changePasswordError(String error);
    }

    interface Presenter{
        void changePassword(String oldPassword,String newPassword);
        void changePasswordSuccess();
        void changePasswordError(String error);
    }

    interface Model{
        void changePassword(String oldPassword,String newPassword);
    }
}
