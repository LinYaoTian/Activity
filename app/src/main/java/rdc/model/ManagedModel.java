package rdc.model;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rdc.bean.Activity;
import rdc.bean.User;
import rdc.contract.IManagedContract;

/**
 * Created by asus on 18-4-14.
 */

public class ManagedModel implements IManagedContract.Model {

    @Override
    public void getManagedActivity(final IManagedContract.Presenter presenter) {
      User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Activity> query = new BmobQuery<Activity>();
        query.addWhereEqualTo("manager",new BmobPointer(user));
        query.findObjects(new FindListener<Activity>() {
            @Override
            public void done(List<Activity> list, BmobException e) {
               if (e==null){
                   presenter.setManagedActivity(list);
               }else {
                   presenter.setManagedActivity(new ArrayList<Activity>());
                   presenter.onError();
               }

            }
        });
    }
}
