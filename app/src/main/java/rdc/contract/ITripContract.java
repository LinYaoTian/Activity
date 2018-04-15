package rdc.contract;

import java.util.List;

import rdc.bean.Activity;


/**
 * Created by asus on 18-4-14.
 */

public interface ITripContract {
    interface View {
        void setTripActivity(List<Activity> list);
    }

    interface Model{
        void getMyTripActivity(Presenter presenter);
    }

    interface Presenter{
        void setTripActivity(List<Activity> list);
        void getMyTripActivity();

    }

}
