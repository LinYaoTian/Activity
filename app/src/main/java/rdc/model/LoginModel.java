package rdc.model;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rdc.bean.User;
import rdc.contract.LoginContract;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public class LoginModel implements LoginContract.Model {

    private LoginContract.Presenter mPresenter;

    public LoginModel(LoginContract.Presenter presenter){
        mPresenter = presenter;
    }

    @Override
    public void login(User user) {
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null){
                    mPresenter.loginResult(true,null);
                }else if (e.getMessage().startsWith("username or password incorrect.")){
                    mPresenter.loginResult(false,"用户名或密码错误！");
                }else if (e.getMessage().startsWith("The network is not available")){
                    mPresenter.loginResult(false,"无网络！");
                }else {
                    mPresenter.loginResult(false,e.getMessage());
                }
            }
        });
    }
}
