package rdc.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.app.FragmentManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rdc.fragment.ShareFragment;

public class ShareUtil {

    /**
     * 获取分享应用列表
     *
     * @return
     */
    public static List<ResolveInfo> getShareList(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
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
     */
    public static void share(FragmentManager fragmentManager, String title) {
        ShareFragment.getInstance(title).show(fragmentManager, "dialog");
    }


}
