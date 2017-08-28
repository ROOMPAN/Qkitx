package com.yanhui.qktx.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.yanhui.qktx.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/8/14.
 * 主页面的 viewpager 的适配器
 */

public class MainFragmentPageAdapter extends FragmentStatePagerAdapter {
    private ArrayList<BaseFragment> mFragmentList;
    private List<String> pagertitle;

    public MainFragmentPageAdapter(FragmentManager fm, ArrayList<BaseFragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    public MainFragmentPageAdapter(FragmentManager fm, ArrayList<BaseFragment> fragmentList, List<String> pagertitle) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.pagertitle = pagertitle;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList == null ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagertitle.get(position);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}
