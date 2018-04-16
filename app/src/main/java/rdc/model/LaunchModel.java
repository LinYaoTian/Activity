package rdc.model;

import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import rdc.app.App;
import rdc.bean.User;
import rdc.contract.LaunchContract;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public class LaunchModel implements LaunchContract.Model {
    @Override
    public void updateUser() {
        User user = BmobUser.getCurrentUser(User.class);
        if (user != null){
            //这里的作用主要是更新本地的User数据
            user.login(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                }
            });
        }
    }
}
