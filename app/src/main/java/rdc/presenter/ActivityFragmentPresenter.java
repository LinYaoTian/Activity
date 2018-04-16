package rdc.presenter;

import rdc.base.BasePresenter;
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
    public void refresh(String tag) {
        model.refresh(tag);
    }

    @Override
    public void getMore() {
        model.getMore();
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
}
