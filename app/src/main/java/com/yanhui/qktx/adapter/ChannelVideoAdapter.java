package com.yanhui.qktx.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yanhui.qktx.fragment.FragmentVideoList;
import com.yanhui.qktx.models.entity.Channel;

import java.util.List;

/**
 * Created by liupanpan on 2017/9/6.
 */

public class ChannelVideoAdapter extends FragmentStatePagerAdapter {
    private List<FragmentVideoList> fragmentList;
    private List<Channel> mChannels;

    public ChannelVideoAdapter(List<FragmentVideoList> fragmentList, List<Channel> channelList, FragmentManager fm) {
        super(fm);
        this.fragmentList = fragmentList;
        this.mChannels = channelList;

    }

    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mChannels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels == null ? "" : mChannels.get(position).Title;
    }
}
