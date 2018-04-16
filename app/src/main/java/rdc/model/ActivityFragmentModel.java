package rdc.model;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import rdc.bean.Activity;
import rdc.bean.User;
import rdc.contract.ActivityFragmentContract;

/**
 * Created by Lin Yaotian on 2018/4/15.
 */

public class ActivityFragmentModel implements ActivityFragmentContract.Model {

    private ActivityFragmentContract.Presenter mPresenter;

    public ActivityFragmentModel(ActivityFragmentContract.Presenter presenter){
        mPresenter = presenter;
    }

    @Override
    public void refresh(String tag) {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Activity> query = new BmobQuery<Activity>();
        query.addQueryKeys("title,time,tag,sawnum,place,image");
        query.addWhereEqualTo("tag",tag);
        query.addWhereEqualTo("university",user.getUniversity());
    }

    @Override
    public void getMore() {

    }
}
