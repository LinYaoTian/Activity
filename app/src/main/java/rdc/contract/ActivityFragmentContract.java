package rdc.contract;

import java.util.List;

import rdc.bean.Activity;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public interface ActivityFragmentContract {
    interface View{
        void update(List<Activity> list);
        void append(List<Activity> list);
        void updateError(String message);
        void getMoreError(String message);
    }

    interface Model{
        void update(String tag);
        void getMore(Activity lastActivity);
    }

    interface Presenter{
        void update(List<Activity> list);
        void append(List<Activity> list);
        void updateError(String message);
        void getMoreError(String message);
    }
}
