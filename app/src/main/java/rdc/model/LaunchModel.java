package rdc.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rdc.bean.User;
import rdc.constant.Constant;
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
//                    String tagsOrder = user.getTagsOrder();
//                    if (tagsOrder == null){
//                        tagsOrder = Constant.DEFAULT_TAGS_ORDER;
//                    }
                    mPresenter.updateSuccess();
                }else {
                    mPresenter.updateError();
                }
            }
        });
    }
}

