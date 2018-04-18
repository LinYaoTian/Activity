package rdc.constant;

/**
 * Created by Lin Yaotian on 2018/4/14.
 */

public class Constant {
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_PHONE_NUMBER = "0?(13|14|15|18)[0-9]{9}";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 密码位数
     */
    public static final Integer PASSWORD_NUM = 6;

    /**
     * 每日的必应背景图
     */
    public static final String BI_YING_BG_PIC = "http://guolin.tech/api/bing_pic";

    public static final String RELEASE_IMAGE_FILE_LOCATION = "file:///sdcard/bbbb.jpg";            //Environment.getExternalStorageDirectory().getPath();

}
