package rdc.presenter;

import java.util.ArrayList;
import java.util.List;

import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.bean.Trip;
import rdc.contract.ITripContract;
import rdc.model.TripModel;

import static rdc.configs.TripItemType.sACTIVITY;

/**
 * Created by asus on 18-4-14.
 */

public class TripPresenter extends BasePresenter<ITripContract.View> implements ITripContract.Presenter{
    private TripModel mModel;
    private static final String TAG = "TripPresenter";

    public TripPresenter() {
        mModel = new TripModel();
    }
    /**
     * 在这里进行类型的转换
     * @param list
     */
    @Override
    public void setTripActivity(List<Activity> list) {
        List<Trip> activityList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            Activity activity = list.get(i);
            Trip trip = new Trip();
            trip.setCoverImageUrl(activity.getImage().getUrl());
            trip.setTitle(activity.getTitle());
            trip.setTime(activity.getTime().substring(5,10));
            trip.setSawNum(activity.getSawnum());
            trip.setLocation(activity.getPlace());
            trip.setObjectId(activity.getObjectId());
            trip.setType(sACTIVITY);
            activityList.add(trip);

        }
if (isAttachView()){
    getMvpView().setTripActivity(activityList);
}
    }
    /**
     * 在这里进行类型的转换
     * @param list
     */

    @Override
    public void setRecommenedTripActivity(List<Activity> list) {
        List<Trip> activityList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            Activity activity = list.get(i);
            Trip trip = new Trip();
            trip.setTitle(activity.getTitle());
            trip.setTime(activity.getTime().substring(5,10));
            trip.setCoverImageUrl(activity.getImage().getUrl());
            trip.setSawNum(activity.getSawnum());
            trip.setLocation(activity.getPlace());
            trip.setObjectId(activity.getObjectId());
            trip.setType(sACTIVITY);
            activityList.add(trip);

        }
        if (isAttachView()){
            getMvpView().setRecommenedTripActivity(activityList);

        }
    }

    @Override
    public void getRecommenedTripActivity() {
         mModel.getRecommenedTripActivity(this);
    }

    @Override
    public void onError() {
        if (isAttachView()){
            getMvpView().setOnError();
        }
    }
    @Override
    public void getMyTripActivity() {
         mModel.getMyTripActivity(this);
    }
}
