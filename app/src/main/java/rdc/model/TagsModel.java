package rdc.model;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import rdc.app.App;
import rdc.avtivity.R;
import rdc.bean.ItemTag;
import rdc.bean.User;
import rdc.contract.TagsContract;

/**
 * Created by Lin Yaotian on 2018/4/19.
 */

public class TagsModel implements TagsContract.Model {

    private TagsContract.Presenter mPresenter;

    public TagsModel(TagsContract.Presenter presenter){
        mPresenter = presenter;
    }

    @Override
    public void saveTags(List<ItemTag> list) {
        list.add(0,new ItemTag(App.getmContext().getResources().getString(R.string.hot),true));
        list.add(0,new ItemTag(App.getmContext().getResources().getString(R.string.homePage),true));
        Gson gson = new Gson();
        User user = new User();
        user.setTagsOrder(gson.toJson(list));
        user.update(BmobUser.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null){
                    if (e.getMessage().startsWith("The network is not available")){
                        mPresenter.saveTagsError("无网络！");
                    }else {
                        mPresenter.saveTagsError(e.getMessage());
                    }
                }
            }
        });

    }
}
