package rdc.presenter;

import rdc.base.BasePresenter;
import rdc.bean.Activity;
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
    public void getDetail(String objectId) {
        mModel.getDetail(this, objectId);
    }

    @Override
    public void onSignUp() {
//        mModel.onSignUp(this, getMvpView().getobjectId(), getMvpView().getNewBmobRelation());
        mModel.onSignUp(this, getMvpView().getobjectId());
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
