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
        getMvpView().setDetail(activity);
    }

    @Override
    public void setDetailAttcipator(List<User> userList) {
        getMvpView().setDetailAttcipator(userList);
        getMvpView().dismissProgressDialog();
    }

    @Override
    public void getDetail(String objectId) {
        mModel.getDetail(this, objectId);
    }

    @Override
    public void onSignUp(boolean hasSignUp) {
        mModel.onSignUp(this, getMvpView().getobjectId(), hasSignUp);
    }

    @Override
    public void onSingOrUnSingUpSuccess() {
        getMvpView().onSingOrUnSingUpSuccess();
    }

    @Override
    public void addSawNum(int currentNum) {
        mModel.addSawNum(this, currentNum, getMvpView().getobjectId());
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
        getMvpView().setUserconcernedList(userconcernedList);
    }

    @Override
    public void onConcernedSuccess() {
        getMvpView().onConcernedSuccess();
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
