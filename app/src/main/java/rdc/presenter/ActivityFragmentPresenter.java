package rdc.presenter;

import java.util.List;

import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.contract.ActivityFragmentContract;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public class ActivityFragmentPresenter extends BasePresenter<ActivityFragmentContract.View>
        implements ActivityFragmentContract.Presenter {


    @Override
    public void update(List<Activity> list) {

    }

    @Override
    public void append(List<Activity> list) {

    }

    @Override
    public void updateError(String message) {

    }

    @Override
    public void getMoreError(String message) {

    }
}
