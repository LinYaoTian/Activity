package rdc.base;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public abstract class BaseLazyLoadFragment extends BaseFragment {

    protected boolean isVisible;//Fragment是否可见
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * Fragment 可见时回调方法
     */
    protected void onVisible(){
        lazyLoad();
    }

    /**
     * 加载数据
     */
    protected abstract void lazyLoad();

    /**
     * Fragment 不可见时回调
     */
    protected void onInvisible(){
    }
}
