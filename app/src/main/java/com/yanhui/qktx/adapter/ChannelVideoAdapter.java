package com.yanhui.qktx.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yanhui.qktx.fragment.FragmentVideoList;
import com.yanhui.qktx.models.CateNameBean;

import java.util.List;

/**
 * Created by liupanpan on 2017/9/6.
 */

public class ChannelVideoAdapter extends FragmentStatePagerAdapter {
    private List<FragmentVideoList> fragmentList;
    private List<CateNameBean.DataBean> mCates;

    public ChannelVideoAdapter(List<FragmentVideoList> fragmentList, List<CateNameBean.DataBean> mCates, FragmentManager fm) {
        super(fm);
        this.fragmentList = fragmentList;
        this.mCates = mCates;

    }

    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mCates.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCates == null ? "" : mCates.get(position).getCateName();
    }
}
