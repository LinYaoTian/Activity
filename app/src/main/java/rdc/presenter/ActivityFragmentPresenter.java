package rdc.presenter;

import android.util.Log;

import java.util.List;

import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.contract.ActivityFragmentContract;
import rdc.model.ActivityFragmentModel;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public class ActivityFragmentPresenter extends BasePresenter<ActivityFragmentContract.View>
        implements ActivityFragmentContract.Presenter {

    private ActivityFragmentContract.Model model;

    public ActivityFragmentPresenter(){
        model = new ActivityFragmentModel(this);
    }

    @Override
    public void refreshSuccess(List<Activity> list) {
        if (isAttachView()){
            getMvpView().refresh(list);
        }
    }

    @Override
    public void appendSuccess(List<Activity> list) {
        if (isAttachView()){
            getMvpView().append(list);
        }
    }

    @Override
    public void refresh(String tag) {
        model.refresh(tag);
    }

    @Override
    public void getMore(String tag) {
        model.getMore(tag);
    }

    @Override
    public void refreshError(String message) {
        if (isAttachView()){
            getMvpView().refreshError(message);
        }
    }

    @Override
    public void getMoreError(String message) {
        if (isAttachView()){
            getMvpView().getMoreError(message);
        }
    }

    @Override
    public void noMoreData() {
        if (isAttachView()){
            getMvpView().noMoreData();
        }
    }
}
