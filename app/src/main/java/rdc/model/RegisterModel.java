package rdc.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rdc.bean.ItemActivity;
import rdc.bean.ItemTag;
import rdc.bean.User;
import rdc.constant.Constant;
import rdc.contract.RegisterContract;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public class RegisterModel implements RegisterContract.Model {

    private RegisterContract.Presenter mPresenter;

    public RegisterModel(RegisterContract.Presenter presenter){
        this.mPresenter = presenter;
    }

    @Override
    public void register(User user) {
        //添加默认订阅的标签
        List<ItemTag> list = new ArrayList<>();
        String [] defaultTagsOrder = Constant.DEFAULT_TAGS_ORDER.split("，");
        for (String s : defaultTagsOrder) {
            ItemTag itemTag = new ItemTag(s,true);
            list.add(itemTag);
        }
        Gson gson = new Gson();
        user.setTagsOrder(gson.toJson(list));
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User u, BmobException e) {
                if (e == null){
                    mPresenter.registerResult(true,null);
                }else if (e.getMessage().startsWith("username")){
                    mPresenter.registerResult(false,"用户名已存在!");
                }else if (e.getMessage().startsWith("The network is not")){
                    mPresenter.registerResult(false,"无网络！");
                }else {
                    mPresenter.registerResult(false,e.getMessage());
                }
            }
        });
    }
}
