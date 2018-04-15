package rdc.listener;


public interface OnClickRecyclerViewListener {

    /**
     * 点击item
     * @param position
     */
    void onItemClick(int position);

    /**
     * 长按item
     * @param position
     * @return
     */
    boolean onItemLongClick(int position);

    /**
     * 点击 footerView
     */
    void onFooterViewClick();

    /**
     * 点击 headerView
     */
    void onHeaderViewClick();
}
