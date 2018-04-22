package rdc.presenter;

import java.util.List;

import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.bean.User;
import rdc.contract.DetailContract;
import rdc.model.DetailModel;

/**
 * Created by WaxBerry on 2018/4/18.
 */

public class DetailPresenter extends BasePresenter<DetailContract.IView> implements DetailContract.IPresenter {

    private DetailModel mModel;

    public DetailPresenter() {
        mModel = new DetailModel();
    }

    @Override
    public void setDetail(Activity activity) {
        if (isAttachView()) {
            getMvpView().setDetail(activity);
        }
    }

    @Override
    public void setDetailAttcipator(List<User> userList) {
        if (isAttachView()) {
            getMvpView().setDetailAttcipator(userList);
            getMvpView().dismissProgressDialog();
        }
    }

    @Override
    public void getDetail(String objectId) {
        mModel.getDetail(this, objectId);
    }

    @Override
    public void onSignUp(boolean hasSignUp) {
        if (isAttachView()) {
            mModel.onSignUp(this, getMvpView().getobjectId(), hasSignUp);
        }
    }

    @Override
    public void onSingOrUnSingUpSuccess() {
        getMvpView().onSingOrUnSingUpSuccess();
    }

    @Override
    public void addSawNum(int currentNum) {
        if (isAttachView()) {
            mModel.addSawNum(this, currentNum, getMvpView().getobjectId());
        }
    }

    @Override
    public void addFocus(String toFocusUserObjectId, boolean hasFocus) {
        mModel.addFocus(this, toFocusUserObjectId, hasFocus);
    }

    @Override
    public void getUserconcernedList() {
        mModel.getUserconcernedList(this);
    }

    @Override
    public void setUserconcernedList(List<User> userconcernedList) {
        if (isAttachView()) {
            getMvpView().setUserconcernedList(userconcernedList);
        }
    }

    @Override
    public void onConcernedSuccess() {
        if (isAttachView()) {
            getMvpView().onConcernedSuccess();
        }
    }

    @Override
    public void onError(String errMeg) {
        if (isAttachView()) {
            getMvpView().onError(errMeg);
        }
    }

    @Override
    public void releaseResult(Boolean state, String message) {
        if (state) {
            getMvpView().onSuccess();
        }else {
            getMvpView().onError(message);
        }
    }
}
