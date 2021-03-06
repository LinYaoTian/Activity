package rdc.contract;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public interface LoginContract {
    interface View{
        void loginSuccess();
        void loginError(String message);
    }

    interface Model{
        void login(String userName,String password);
    }

    interface Presenter{
        void login(String userName,String password);
        void loginSuccess();
        void loginError(String error);
    }
}
