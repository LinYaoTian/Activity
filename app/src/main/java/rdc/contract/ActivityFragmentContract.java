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
        void noMoreData();
    }

    interface Model{
        void refresh(String tag);
        void getMore(String tag);
    }

    interface Presenter{
        void refreshSuccess(List<Activity> list);
        void appendSuccess(List<Activity> list);
        void refresh(String tag);
        void getMore(String tag);
        void refreshError(String message);
        void getMoreError(String message);
        void noMoreData();
    }
}
