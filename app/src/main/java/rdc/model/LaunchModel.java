package rdc.model;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import rdc.app.App;
import rdc.bean.User;
import rdc.contract.LaunchContract;
import rdc.util.UserUtil;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public class LaunchModel implements LaunchContract.Model {

    private LaunchContract.Presenter mPresenter;

    public LaunchModel(LaunchContract.Presenter presenter){
        mPresenter = presenter;
    }

    @Override
    public void updateUser() {
        User user = UserUtil.getUser();
        BmobUser.logOut();
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null){
                    mPresenter.updateSuccess();
                }else {
                    mPresenter.updateError();
                }
            }
        });
    }
}

