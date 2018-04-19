package rdc.util;

import android.content.Context;
import android.content.SharedPreferences;

import rdc.app.App;
import rdc.bean.User;

/**
 * Created by Lin Yaotian on 2018/4/16.
 */

public class UserUtil {

    /**
     * 保存用户帐号和密码
     * @param userName
     * @param password
     */
    public static void saveUser(String userName,String password){
        SharedPreferences.Editor editor = App.getmContext().getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("username",userName);
        editor.putString("password",password);
        editor.apply();
    }

    /**
     * 获取用户的帐号和密码
     * @return
     */
    public static User getUser(){
        User user = new User();
        SharedPreferences sp = App.getmContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        user.setUsername(sp.getString("username",""));
        user.setPassword(sp.getString("password",""));
        return user;
    }

    /**
     * 清除保存的帐号和密码
     */
    public static void clearUser(){
        SharedPreferences.Editor editor = App.getmContext().getSharedPreferences("user",0).edit();
        editor.putString("username","");
        editor.putString("password","");
        editor.apply();
    }

    /**
     * 保存用户的标签顺序
     * @param tagsOrder
     */
    public static void saveTagsOrder(String tagsOrder){
        SharedPreferences.Editor editor = App.getmContext().getSharedPreferences("tagsOrder",0).edit();
        editor.putString("order",tagsOrder);
        editor.apply();
    }

    /**
     * 获取用户保存的标签顺序
     * @return
     */
    public static String getTagsOrder(){
        SharedPreferences sp = App.getmContext().getSharedPreferences("tagsOrder",0);
        return sp.getString("order","");
    }


}
