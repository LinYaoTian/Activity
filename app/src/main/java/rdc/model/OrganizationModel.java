package rdc.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import rdc.bean.User;
import rdc.contract.IOrganizationContract;

/**
 * Created by asus on 18-4-14.
 */

public class OrganizationModel implements IOrganizationContract.Model {
    private static final String TAG = "OrganizationModel";
    @Override
    public void getConcernedOrganization(final IOrganizationContract.Presenter presenter) {
        User user = User.getCurrentUser(User.class);
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereRelatedTo("conncerned",new BmobPointer(user));
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
               if (e==null){
                   presenter.setOrganization(list);
               }else {

               }
            }
        });
    }
}
