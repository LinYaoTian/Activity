package rdc.contract;

import java.util.List;

import rdc.bean.Activity;
import rdc.bean.Trip;


/**
 * Created by asus on 18-4-14.
 */

public interface ITripContract {
    interface View {
        void setTripActivity(List<Trip> list);
        void setRecommenedTripActivity(List<Trip> list);
        void setOnError();

    }

    interface Model{
        void getMyTripActivity(Presenter presenter);
        void getRecommenedTripActivity(Presenter presenter);
    }

    interface Presenter{
        void setTripActivity(List<Activity> list);
        void getMyTripActivity();

        void setRecommenedTripActivity(List<Activity> list);
        void getRecommenedTripActivity();
        void onError();


    }

}
