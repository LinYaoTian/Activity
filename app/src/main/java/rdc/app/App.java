package rdc.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.os.StrictMode;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;


/**
 * Created by asus on 18-4-10.
 */
public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private static File sCacheDir;
    private static ContentResolver sContentResolver;

    @Override
    public void onCreate() {
        super.onCreate();

        BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("4e50fe02409073ad4ad79f2cedcebde0")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024*1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);

        mContext = getApplicationContext();
        sCacheDir = getExternalCacheDir();
        sContentResolver = getContentResolver();

        //7.0以上照片崩溃？
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        builder.detectFileUriExposure();
        StrictMode.setVmPolicy(builder.build());
    }

    public static Context getmContext() {
        return mContext;
    }

    public static File getsCacheDir() {
        return sCacheDir;
    }

    public static ContentResolver getsContentResolver() {
        return sContentResolver;
    }
}