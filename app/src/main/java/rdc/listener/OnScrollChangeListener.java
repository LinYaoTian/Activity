package rdc.listener;

import android.view.View;
import android.widget.ScrollView;

/**
 * Created by asus on 18-4-20.
 */

public abstract class OnScrollChangeListener implements ScrollView.OnScrollChangeListener {
    @Override
    public void onScrollChange(View view,int x, int y, int oldx, int oldy) {

        if (oldy < y ) {// 手指向上滑动，屏幕内容下滑
            onScroll(oldy,y,false);

        } else if (oldy > y ) {// 手指向下滑动，屏幕内容上滑
          onScroll(oldy,y,true);
        }

    }




    public abstract void onScroll(int oldy, int y, boolean isUp);
}
