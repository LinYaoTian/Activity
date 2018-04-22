package rdc.presenter;

import java.util.List;

import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.bean.User;
import rdc.contract.DetailContract;
import rdc.contract.ICheckManagedContract;
import rdc.model.CheckManagedModel;
import rdc.model.DetailModel;

/**
 * Created by asus on 18-4-20.
 */

public class CheckManagedPresenter extends BasePresenter<ICheckManagedContract.IView> implements ICheckManagedContract.IPresenter {

    private CheckManagedModel mModel;

    public CheckManagedPresenter() {
        mModel = new CheckManagedModel();
    }

    @Override
    public void setDetail(Activity activity) {
        getMvpView().setDetail(activity);
    }

    @Override
    public void setDetailAttcipator(List<User> userList) {
        if (isAttachView()){
            getMvpView().setDetailAttcipator(userList);

        }
    }

    @Override
    public void getDetail(String objectId) {
        mModel.getDetail(this, objectId);
    }



    @Override
    public void addSawNum(int currentNum) {
        mModel.addSawNum(this, currentNum, getMvpView().getobjectId());
    }




    @Override
    public void deleteActivity(String activityId) {
        mModel.deleteActivity(this,activityId);
    }

    @Override
    public void setDeleteSuccess() {
          getMvpView().setDeleteSuccess();
    }
}
