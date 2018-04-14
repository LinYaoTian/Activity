package rdc.contract;

import rdc.bean.User;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public interface LoginContract {
    interface View{
        void loginSuccess();
        void loginError(String message);
    }

    interface Model{
        void login(User user);
    }

    interface Presenter{
        void login(User user);
        void loginResult(Boolean state,String message);
    }
}
