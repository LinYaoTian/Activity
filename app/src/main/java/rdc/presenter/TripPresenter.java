package rdc.presenter;

import java.util.List;

import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.contract.ITripContract;
import rdc.model.TripModel;

/**
 * Created by asus on 18-4-14.
 */

public class TripPresenter extends BasePresenter<ITripContract.View> implements ITripContract.Presenter{
    private TripModel mModel;

    public TripPresenter() {
        mModel = new TripModel();
    }

    @Override
    public void setTripActivity(List<Activity> list) {
        if (list.size()==0){

        }
         getMvpView().setTripActivity(list);
    }

    @Override
    public void getMyTripActivity() {
         mModel.getMyTripActivity(this);
    }
}
