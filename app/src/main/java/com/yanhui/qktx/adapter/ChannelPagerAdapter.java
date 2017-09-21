package com.yanhui.qktx.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yanhui.qktx.fragment.BaseFragment;
import com.yanhui.qktx.fragment.NewsListFragment;
import com.yanhui.qktx.models.CateNameBean;
import com.yanhui.qktx.models.entity.Channel;

import java.util.List;


/**
 * @author ChayChan
 * @description: 频道的adapter
 * @date 2017/6/16  9:45
 */

public class ChannelPagerAdapter extends FragmentStatePagerAdapter {

    private final FragmentManager mFm;
    private List<NewsListFragment> fragments;
    private List<Channel> mChannels;
    private int mChildCount;
    private boolean[] fragmentsUpdateFlag;
    private List<CateNameBean.DataBean> mCate_list;

    public ChannelPagerAdapter(FragmentManager fm, List<NewsListFragment> fragments, List<Channel> channels) {
        super(fm);
        mFm = fm;
        this.fragments = fragments;
        this.mChannels = channels;
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return mChannels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels == null ? "" : mChannels.get(position).Title;
    }

//    @Override
//    public void notifyDataSetChanged() {
//        mChildCount = getCount();
//        super.notifyDataSetChanged();
//    }


    @Override
    public int getItemPosition(Object object) {
//        if (mChildCount > 0) {
//            mChildCount--;
        return POSITION_NONE;
//        }
//        return super.getItemPosition(object);
    }

}
