package rdc.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ScrollView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rdc.app.App;
import rdc.fragment.ShareFragment;

public class ShareUtil {

    /**
     * 获取分享应用列表
     *
     * @return
     */
    public static List<ResolveInfo> getShareList(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 0);
        //调整顺序，把微信、QQ提到前面来
        Collections.sort(list, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo resolveInfo, ResolveInfo t1) {
                ActivityInfo activityInfo1 = resolveInfo.activityInfo;
                ActivityInfo activityInfo2 = t1.activityInfo;
                if (activityInfo1.packageName.contains("com.tencent.")
                        && !activityInfo2.packageName.contains("com.tencent.")) {
                    return -1;
                } else if (!activityInfo1.packageName.contains("com.tencent.")
                        && activityInfo2.packageName.contains("com.tencent.")) {
                    return 1;
                }
                return 0;
            }
        });
        return list;
    }

    /**
     * 打开分享界面
     *
     * @param fragmentManager
     * @param title 文章的标题
     * @param uri 图片
     */
    public static void share(FragmentManager fragmentManager, String title,Uri uri) {
        ShareFragment.getInstance(title,uri).show(fragmentManager, "dialog");
    }

    /**
     * 将图片存到本地
     * @param bm 图片
     * @param picName 图片名
     * @return 图片所在的Uri
     */
    public static Uri saveBitmap(Bitmap bm, String picName) {
        Uri uri;
        try {
            String dir= Environment.getExternalStorageDirectory().getAbsolutePath()+"/activity/"+picName+".jpg";
            File f = new File(dir);
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 70, out);
            out.flush();
            out.close();
            if (Build.VERSION.SDK_INT >= 24){
                uri = FileProvider.getUriForFile(App.getmContext(),"rdc.avtivity.fileprovider",f);
            }else {
                uri = Uri.fromFile(f);
            }
            return uri;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();    }
        return null;
    }

    /**
     * 获取View的截图
     * @param rootView 要截屏的根View
     * @return Bitmap
     */
    public static Bitmap getBitmapByView(ScrollView rootView) {
        int h = 0;
        for (int i = 0; i < rootView.getChildCount(); i++) {
            h += rootView.getChildAt(i).getHeight();}
        Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas c = new Canvas(bitmap);
        c.drawColor(Color.WHITE);
        rootView.draw(c);
        return bitmap;
    }


}
