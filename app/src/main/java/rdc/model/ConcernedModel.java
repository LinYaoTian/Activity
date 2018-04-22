package rdc.model;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rdc.bean.User;
import rdc.contract.IConcernedContract;

/**
 * Created by asus on 18-4-14.
 */

public class ConcernedModel implements IConcernedContract.Model {
    private static final String TAG = "ConcernedModel";
    @Override
    public void getConcernedOrganization(final IConcernedContract.Presenter presenter) {
        User user = User.getCurrentUser(User.class);
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereRelatedTo("conncerned",new BmobPointer(user));
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
               if (e==null){
                   presenter.setOrganization(list);
               }else {
                    presenter.onError();
               }
            }
        });
    }
}
