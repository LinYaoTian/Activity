package rdc.contract;

import java.util.List;

import rdc.bean.Activity;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public interface ActivityFragmentContract {
    interface View{
        void refresh(List<Activity> list);
        void append(List<Activity> list);
        void refreshError(String message);
        void getMoreError(String message);
    }

    interface Model{
        void refresh(String tag);
        void getMore();
    }

    interface Presenter{
        void refresh(String tag);
        void getMore();
        void refreshError(String message);
        void getMoreError(String message);
    }
}
