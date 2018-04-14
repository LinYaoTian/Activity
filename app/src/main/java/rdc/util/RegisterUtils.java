package rdc.util;

import android.widget.Toast;

import rdc.app.App;
import rdc.constant.Constant;

/**
 * Created by Lin Yaotian on 2018/4/14.
 */

public class RegisterUtils {
    /**
     * 检查帐号格式
     * @param accountNumber 帐号
     * @return true or false
     */
    public static boolean checkAccountNumber(String accountNumber){
        return accountNumber.matches(Constant.REGEX_EMAIL) ||
                accountNumber.matches(Constant.REGEX_PHONE_NUMBER);
    }

    /**
     * 检查密码位数
     * @param password 密码
     * @return true or false
     */
    public static boolean checkPassword(String password){
        if (password.length() < Constant.PASSWORD_NUM){
            return false;
        }
        return true;
    }
}
