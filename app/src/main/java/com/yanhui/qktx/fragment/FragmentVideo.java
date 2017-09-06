package com.yanhui.qktx.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.ChannelVideoAdapter;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.models.entity.Channel;
import com.yanhui.qktx.utils.UIUtils;
import com.yanhui.qktx.view.colortrackview.ColorTrackTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/8/14.
 */

public class FragmentVideo extends BaseFragment implements View.OnClickListener {
    private ImageView iv_seach_operation;
    private ColorTrackTabLayout tab_vedio_layout;
    private ViewPager vp_vedio_pager;

    private List<Channel> mChannelList = new ArrayList<>();
    private List<FragmentVideoList> mFrgamentList = new ArrayList<>();

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_video;
    }

    @Override
    public void findViews() {
        super.findViews();
        iv_seach_operation = mRoomView.findViewById(R.id.fragment_video_iv_operation);
        tab_vedio_layout = mRoomView.findViewById(R.id.fragment_video_tab_layout);
        vp_vedio_pager = mRoomView.findViewById(R.id.fragment_video_vp_content);

    }

    @Override
    public void bindData() {
        super.bindData();
        initChannelData();
        initChannelFragments();
        ChannelVideoAdapter channelAdapter = new ChannelVideoAdapter(mFrgamentList, mChannelList, getChildFragmentManager());
        vp_vedio_pager.setAdapter(channelAdapter);
        vp_vedio_pager.setOffscreenPageLimit(mFrgamentList.size());
        tab_vedio_layout.setSelectedTabIndicatorHeight(0);
        tab_vedio_layout.setTabPaddingLeftAndRight(UIUtils.dip2Px(10), UIUtils.dip2Px(10));
        tab_vedio_layout.setupWithViewPager(vp_vedio_pager);
    }

    private void initChannelData() {
        String[] channels = getResources().getStringArray(R.array.channel_video);
        String[] channelCodes = getResources().getStringArray(R.array.channel_code_video);
        mChannelList.clear();
        for (int i = 0; i < channelCodes.length; i++) {
            String title = channels[i];
            String code = channelCodes[i];
            mChannelList.add(new Channel(title, code));
        }
    }

    private void initChannelFragments() {
        for (Channel channel : mChannelList) {
            FragmentVideoList fragmentVideoList = new FragmentVideoList();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.CHANNEL_CODE, channel.TitleCode);
            bundle.putBoolean(Constant.IS_VIDEO_LIST, true);//是否是视频列表页面,]true
            fragmentVideoList.setArguments(bundle);
            mFrgamentList.add(fragmentVideoList);//添加到集合中
        }
    }

    @Override
    public void bindListener() {
        super.bindListener();
        iv_seach_operation.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_video_iv_operation:
                //搜索
                break;
        }
    }
}
