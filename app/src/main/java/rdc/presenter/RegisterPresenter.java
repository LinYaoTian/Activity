package rdc.presenter;

import rdc.base.BasePresenter;
import rdc.bean.User;
import rdc.contract.RegisterContract;
import rdc.model.RegisterModel;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter {

    private RegisterContract.Model model;

    public RegisterPresenter(){
        model = new RegisterModel(this);
    }


    @Override
    public void register(User user) {
        model.register(user);
    }

    @Override
    public void registerResult(Boolean state, String message) {
        if (isAttachView()){
            if (state){
                //注册成功
                getMvpView().registerSuccess();
            }else {
                getMvpView().registerError(message);
            }
        }
    }


}
