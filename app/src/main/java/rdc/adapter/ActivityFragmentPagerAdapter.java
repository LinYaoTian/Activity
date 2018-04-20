package rdc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by Lin Yaotian on 2018/4/20.
 */

public class ActivityFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    private FragmentManager mFM;

    public ActivityFragmentPagerAdapter(FragmentManager fm, List<Fragment> list,List<String> list1) {
        super(fm);
        mFragmentList = list;
        mFM = fm;
        mTitleList = list1;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    public void setFragments(List<Fragment> fragments,List<String> titleList) {
        if (this.mFragmentList != null) {
            FragmentTransaction ft = mFM.beginTransaction();
            for (Fragment f : this.mFragmentList) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            mFM.executePendingTransactions();
        }
        this.mFragmentList = fragments;
        this.mTitleList = titleList;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
