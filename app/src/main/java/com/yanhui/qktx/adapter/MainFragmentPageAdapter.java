package com.yanhui.qktx.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yanhui.qktx.fragment.BaseFragment;
import com.yanhui.qktx.utils.Logger;

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
        if (pagertitle != null && pagertitle.size() != 0) {
            return pagertitle.get(position);
        } else {
            return null;
        }
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    //解决android.support.v4.app.FragmentHostCallback.getHandler()' on a null object reference  goole bug
    @Override
    public void finishUpdate(ViewGroup container) {
        try {
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException) {
            Logger.d("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate", "");
        }
    }
}
