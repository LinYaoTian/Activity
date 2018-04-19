package rdc.model;

import android.util.Log;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rdc.bean.User;
import rdc.contract.AccountManageContract;

/**
 * Created by Lin Yaotian on 2018/4/18.
 */

public class AccountManageModel implements AccountManageContract.Model {

    private AccountManageContract.Presenter mPresenter;

    public AccountManageModel(AccountManageContract.Presenter presenter){
        mPresenter = presenter;
    }

    @Override
    public void changePassword(final String oldPassword, final String newPassword) {
        User user = BmobUser.getCurrentUser(User.class);
        user.setPassword(oldPassword);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User o, BmobException e) {
                if (e == null){
                    //旧密码正确，进行修改密码！
                    User user = BmobUser.getCurrentUser(User.class);
                    user.setPassword(newPassword);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                mPresenter.changePasswordSuccess();
                            }else {
                                mPresenter.changePasswordError(e.getMessage());
                            }
                        }
                    });
                }else if (e.getMessage().startsWith("The network is not available")){
                    mPresenter.changePasswordError("无网络！");
                }else if (e.getMessage().startsWith("username or password incorrect.")){
                    mPresenter.changePasswordError("旧密码错误！");
                }
            }
        });

    }
}
