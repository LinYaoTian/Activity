package rdc.presenter;

import rdc.base.BasePresenter;
import rdc.contract.AccountManageContract;
import rdc.model.AccountManageModel;

/**
 * Created by Lin Yaotian on 2018/4/18.
 */

public class AccountManagePresenter extends BasePresenter<AccountManageContract.View> implements AccountManageContract.Presenter {

    private AccountManageContract.Model model;

    public AccountManagePresenter(){
        model = new AccountManageModel(this);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        model.changePassword(oldPassword, newPassword);
    }

    @Override
    public void changePasswordSuccess() {
        if (isAttachView()){
            getMvpView().changePasswordSuccess();
        }
    }

    @Override
    public void changePasswordError(String error) {
        if (isAttachView()){
            getMvpView().changePasswordError(error);
        }
    }
}
