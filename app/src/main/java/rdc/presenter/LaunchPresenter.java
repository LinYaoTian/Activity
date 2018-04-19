package rdc.presenter;

import javax.xml.transform.sax.TemplatesHandler;

import rdc.base.BasePresenter;
import rdc.contract.LaunchContract;
import rdc.model.LaunchModel;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public class LaunchPresenter extends BasePresenter<LaunchContract.View> implements LaunchContract.Presenter {

    private LaunchContract.Model model;

    public LaunchPresenter(){
        model = new LaunchModel(this);
    }

    @Override
    public void updateUser() {
        model.updateUser();
    }

    @Override
    public void updateSuccess(String tagsOrder) {
        if (isAttachView()){
            getMvpView().updateUserSuccess(tagsOrder);
        }
    }

    @Override
    public void updateError() {
        if (isAttachView()){
            getMvpView().updateUserError();
        }
    }
}
