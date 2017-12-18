package com.yanhui.qktx.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.SeachActivity;
import com.yanhui.qktx.adapter.ChannelVideoAdapter;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.models.CateNameBean;
import com.yanhui.qktx.models.entity.Channel;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
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
    private RelativeLayout mLoadingView;

    private List<Channel> mChannelList = new ArrayList<>();
    private List<FragmentVideoList> mFrgamentList = new ArrayList<>();
    private List<CateNameBean.DataBean> mCate_list = new ArrayList<>();

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_video;
    }

    @Override
    public void findViews() {
        super.findViews();
        mLoadingView = mRoomView.findViewById(R.id.fragment_video_common_loading_view);
        iv_seach_operation = mRoomView.findViewById(R.id.fragment_video_iv_operation);
        tab_vedio_layout = mRoomView.findViewById(R.id.fragment_video_tab_layout);
        vp_vedio_pager = mRoomView.findViewById(R.id.fragment_video_vp_content);
    }

    @Override
    public void bindData() {
        super.bindData();

    }

    /**
     * 当 fragment 可见下 加载数据
     */
    @Override
    protected void lazyLoad() {
        getVedioCate();
        super.lazyLoad();
    }
    //    private void initChannelData() {
//        String[] channels = getResources().getStringArray(R.array.channel_video);
//        String[] channelCodes = getResources().getStringArray(R.array.channel_code_video);
//        mChannelList.clear();
//        for (int i = 0; i < mCate_list.size(); i++) {
//            String title = channels[i];
//            String code = channelCodes[i];
//            mChannelList.add(new Channel(title, code));
//        }
//    }

    private void initChannelFragments() {
        for (int i = 0; i < mCate_list.size(); i++) {
            FragmentVideoList fragmentVideoList = FragmentVideoList.newInstance(String.valueOf(mCate_list.get(i).getCateId()));
            mFrgamentList.add(fragmentVideoList);//添加到集合中
        }
    }

    public String getCurrentVideoChannelCode() {
        if (vp_vedio_pager != null && mCate_list.size() != 0) {
            int currentItem = vp_vedio_pager.getCurrentItem();
            return String.valueOf(mCate_list.get(currentItem).getCateId());
        } else {
            return null;
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
                startActivity(new Intent(mActivity, SeachActivity.class).putExtra(Constant.SEACH_TYPE, Constant.SEACH_VIDEO));
                break;
        }
    }

    public void getVedioCate() {
        HttpClient.getInstance().getVedioCate(new NetworkSubscriber<CateNameBean>(this) {
            @Override
            public void onStart() {
                super.onStart();
//                mLoadingView.setVisibility(View.VISIBLE);
                mStateView.showLoading();
            }

            @Override
            public void onNext(CateNameBean data) {
                super.onNext(data);
                if (data.isOKResult()) {
                    mCate_list = data.getData();
                    initChannelFragments();
                    ChannelVideoAdapter channelAdapter = new ChannelVideoAdapter(mFrgamentList, mCate_list, getChildFragmentManager());
                    vp_vedio_pager.setAdapter(channelAdapter);
                    vp_vedio_pager.setOffscreenPageLimit(mFrgamentList.size());
                    tab_vedio_layout.setSelectedTabIndicatorHeight(0);
                    tab_vedio_layout.setTabPaddingLeftAndRight(UIUtils.dip2Px(10), UIUtils.dip2Px(10));
                    tab_vedio_layout.setupWithViewPager(vp_vedio_pager);
//                    mLoadingView.setVisibility(View.GONE);
                    mStateView.showContent();
                }
            }
        });
    }

}
