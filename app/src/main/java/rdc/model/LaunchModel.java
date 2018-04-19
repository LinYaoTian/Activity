package rdc.model;

import android.content.res.TypedArray;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import rdc.app.App;
import rdc.bean.ItemTag;
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
                    String tagsOrder = user.getTagsOrder();
                    if (tagsOrder == null){
                        tagsOrder = Constant.DEFAULT_TAGS_ORDER;
                    }else {
                        tagsOrder = "首页，热门，"+ tagsOrder;
                    }
                    List<String> list = Arrays.asList(tagsOrder.split("，"));
                    List<ItemTag> list1 = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        ItemTag itemTag = new ItemTag(list.get(i),true);
                        list1.add(itemTag);
                    }
                    Gson gson = new Gson();
                    Log.d("LYT", "done1: "+gson.toJson(list1));
                    Log.d("LYT", "done2: "+gson.fromJson(gson.toJson(list1),new TypeToken<List<ItemTag>>(){}.getType()).toString());
                    mPresenter.updateSuccess(tagsOrder);
                }else {
                    mPresenter.updateError();
                }
            }
        });
    }
}

