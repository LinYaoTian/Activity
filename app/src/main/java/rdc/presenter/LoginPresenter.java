package rdc.presenter;

import rdc.base.BasePresenter;
import rdc.bean.User;
import rdc.contract.LoginContract;
import rdc.model.LoginModel;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginContract.Model model;

    public LoginPresenter(){
        model = new LoginModel(this);
    }

    @Override
    public void login(String userName, String password) {
        model.login(userName, password);
    }

    @Override
    public void loginResult(Boolean state, String message) {
        if (isAttachView()){
            if (state){
                getMvpView().loginSuccess();
            }else {
                getMvpView().loginError(message);
            }
        }
    }
}
