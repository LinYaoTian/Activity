package rdc.model;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rdc.bean.User;
import rdc.constant.Constant;
import rdc.contract.LoginContract;
import rdc.util.UserUtil;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public class LoginModel implements LoginContract.Model {

    private LoginContract.Presenter mPresenter;

    public LoginModel(LoginContract.Presenter presenter){
        mPresenter = presenter;
    }

    @Override
    public void login(final String userName, final String password) {
        User u = new User();
        u.setUsername(userName);
        u.setPassword(password);
        u.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null){
                    UserUtil.saveUser(userName, password);
                    mPresenter.loginSuccess();
                }else if (e.getMessage().startsWith("username or password incorrect.")){
                    mPresenter.loginError("用户名或密码错误！");
                }else if (e.getMessage().startsWith("The network is not available")){
                    mPresenter.loginError("无网络！");
                }else {
                    mPresenter.loginError(e.getMessage());
                }
            }
        });
    }
}
