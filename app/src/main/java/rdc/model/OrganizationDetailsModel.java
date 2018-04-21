package rdc.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rdc.bean.Activity;
import rdc.bean.Organization;
import rdc.bean.User;
import rdc.contract.IOrganizationDetailsContract;

/**
 * Created by asus on 18-4-18.
 */

public class OrganizationDetailsModel implements IOrganizationDetailsContract.Model {

    private static final String TAG = "OrganizationDetailsMode";
    @Override
    public void getManagedActivity(final IOrganizationDetailsContract.Presenter presenter,String id) {
        BmobQuery<Activity> query = new BmobQuery<>();
        User user = new User();
        user.setObjectId(id);
        query.addWhereEqualTo("manager",new BmobPointer(user));
        query.findObjects(new FindListener<Activity>() {
            @Override
            public void done(List<Activity> list, BmobException e) {
                if (e==null){
                    presenter.setManagedActivity(list);
                }
            }
        });
    }
}
