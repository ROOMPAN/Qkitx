package com.yanhui.qktx.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.SeachActivity;
import com.yanhui.qktx.adapter.ChannelPagerAdapter;
import com.yanhui.qktx.business.OnChannelListener;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.models.CateNameBean;
import com.yanhui.qktx.models.entity.Channel;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.CommonUtil;
import com.yanhui.qktx.utils.ConstanceValue;
import com.yanhui.qktx.utils.GsonToJsonUtil;
import com.yanhui.qktx.utils.SharedPreferencesMgr;
import com.yanhui.qktx.view.colortrackview.ColorTrackTabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.yanhui.qktx.utils.ConstanceValue.TITLE_SELECTED;
import static com.yanhui.qktx.utils.ConstanceValue.TITLE_UNSELECTED;


/**
 * Created by liupanpan on 2017/8/14.
 * 主页面的 fragment
 */

public class FragmentHome extends BaseFragment implements View.OnClickListener, OnChannelListener {
    private ColorTrackTabLayout add_trackTabLayout;
    private ImageView iv_operation;
    private ViewPager vp_content;
    private List<Channel> mSelectedChannels = new ArrayList<>();
    private List<Channel> mUnSelectedChannels = new ArrayList<>();
    private ArrayList<NewsListFragment> mChannelFragments = new ArrayList<>();
    private ChannelPagerAdapter channelPagerAdapter;
    private TextView tv_seach;
    private List<CateNameBean.DataBean> mCate_list = new ArrayList<>();

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void findViews() {
        super.findViews();
        add_trackTabLayout = mRoomView.findViewById(R.id.tab_channel);
        iv_operation = mRoomView.findViewById(R.id.iv_operation);
        vp_content = mRoomView.findViewById(R.id.vp_content);
        tv_seach = mRoomView.findViewById(R.id.fragement_home_tv_search);
    }

    @Override
    public void bindData() {
        super.bindData();
        iv_operation.setOnClickListener(this);
        tv_seach.setOnClickListener(this);

    }

    @Override
    public void bindListener() {
        super.bindListener();
        getData();
    }

    @Override
    public void refresh() {
        super.refresh();

    }

    private void getData() {
        HttpClient.getInstance().getCate(new NetworkSubscriber<CateNameBean>(this) {
            @Override
            public void onNext(CateNameBean data) {
                super.onNext(data);
                if (data.isOKResult()) {
                    mCate_list = data.getData();
                    initChannelFragments();
                    Log.e("cates", "" + data.getData().get(0).toString());

                }
            }
        });
    }

    public String getCurrentChannelCode() {
        int currentItem = vp_content.getCurrentItem();
        return mSelectedChannels.get(currentItem).TitleCode;
    }

    //初始化频道数据
    private void initChannelData() {
        String selectTitle = SharedPreferencesMgr.getString(TITLE_SELECTED, "");
        String unselectTitle = SharedPreferencesMgr.getString(TITLE_UNSELECTED, "");
        if (TextUtils.isEmpty(selectTitle)) {
            //默认添加了全部频道
            for (int i = 0; i < mCate_list.size(); i++) {
                String title = mCate_list.get(i).getCateName();
                String code = String.valueOf(mCate_list.get(i).getCateId());
                mSelectedChannels.add(new Channel(title, code));
            }
            String selectedStr = GsonToJsonUtil.toJson(mSelectedChannels);
            SharedPreferencesMgr.setString(TITLE_SELECTED, selectedStr);
        } else {
            //之前添加过
            List<Channel> selecteData = GsonToJsonUtil.fromJson(selectTitle, new TypeToken<List<Channel>>() {
            }.getType());
            List<Channel> unselecteData = GsonToJsonUtil.fromJson(unselectTitle, new TypeToken<List<Channel>>() {
            }.getType());
            mSelectedChannels.addAll(selecteData);
            mUnSelectedChannels.addAll(unselecteData);
        }

    }

    /**
     * 初始化已选频道的fragment的集合
     */
    private void initChannelFragments() {
        initChannelData();
        for (int i = 0; i < mSelectedChannels.size(); i++) {
            NewsListFragment fragment = NewsListFragment.newInstance(mSelectedChannels.get(i).TitleCode);
            mChannelFragments.add(fragment);
        }
        channelPagerAdapter = new ChannelPagerAdapter(getChildFragmentManager(), mChannelFragments, mSelectedChannels);
        vp_content.setAdapter(channelPagerAdapter);
        vp_content.setOffscreenPageLimit(mSelectedChannels.size());
        //隐藏指示器
        add_trackTabLayout.setSelectedTabIndicatorHeight(0);
        add_trackTabLayout.setTabPaddingLeftAndRight(CommonUtil.dip2px(10), CommonUtil.dip2px(10));
        add_trackTabLayout.setupWithViewPager(vp_content);

        //String[] channelCodes = getResources().getStringArray(R.array.home_title_code);
//        for (Channel channel : mSelectedChannels) {
//            NewsListFragment newsFragment = new NewsListFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString(Constant.CHANNEL_CODE, channel.TitleCode);
//            bundle.putBoolean(Constant.IS_VIDEO_LIST, channel.TitleCode.equals(channelCodes[1]));//是否是视频列表页面,根据判断频道号是否是视频
//            newsFragment.setArguments(bundle);
//            mChannelFragments.add(newsFragment);//添加到集合中
//        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_operation:
                ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(mSelectedChannels, mUnSelectedChannels);
                dialogFragment.setOnChannelListener(this);
                dialogFragment.show(getChildFragmentManager(), "CHANNEL");
                dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (mSelectedChannels.size() != 0) {
                            channelPagerAdapter.notifyDataSetChanged();
                            vp_content.setOffscreenPageLimit(mSelectedChannels.size());
                            add_trackTabLayout.setCurrentItem(add_trackTabLayout.getSelectedTabPosition());
                            ViewGroup slidingTabStrip = (ViewGroup) add_trackTabLayout.getChildAt(0);
                            //注意：因为最开始设置了最小宽度，所以重新测量宽度的时候一定要先将最小宽度设置为0
                            slidingTabStrip.setMinimumWidth(0);
                            slidingTabStrip.measure(0, 0);
                            //保存选中和未选中的channel
                            SharedPreferencesMgr.setString(ConstanceValue.TITLE_SELECTED, GsonToJsonUtil.toJson(mSelectedChannels));
                            SharedPreferencesMgr.setString(ConstanceValue.TITLE_UNSELECTED, GsonToJsonUtil.toJson(mUnSelectedChannels));
                        }
                    }

                });
                break;
            case R.id.fragement_home_tv_search:
                startActivity(new Intent(mActivity, SeachActivity.class).putExtra(Constant.SEACH_TYPE, Constant.SEACH_AIRTS));
                break;
        }
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        listMove(mSelectedChannels, starPos, endPos);
        listMove(mChannelFragments, starPos, endPos);
    }

    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {
        //移动到我的频道
        Channel channel = mUnSelectedChannels.remove(starPos);
        mSelectedChannels.add(endPos, channel);
        mChannelFragments.add(NewsListFragment.newInstance(channel.TitleCode));
    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {
        //移动到推荐频道
        mUnSelectedChannels.add(endPos, mSelectedChannels.remove(starPos));
        mChannelFragments.remove(starPos);
    }

    private void listMove(List datas, int starPos, int endPos) {
        Object o = datas.get(starPos);
        //先删除之前的位置
        datas.remove(starPos);
        //添加到现在的位置
        datas.add(endPos, o);
    }

}

