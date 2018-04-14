package rdc.contract;

import rdc.bean.User;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public interface RegisterContract {

    interface View{
        void registerSuccess();
        void registerError(String message);
    }

    interface Model{
        void register(User user);
    }

    interface Presenter{
        void register(User user);
        void registerResult(Boolean state,String message);
    }
}
