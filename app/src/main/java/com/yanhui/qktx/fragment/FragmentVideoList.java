package com.yanhui.qktx.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.VideoAdapter;
import com.yanhui.qktx.models.News;
import com.yanhui.qktx.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by liupanpan on 2017/9/6.
 */

public class FragmentVideoList extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private TipView mTipView;
    private BGARefreshLayout mRefreshLayout;
    private FrameLayout mFlContent;
    private PowerfulRecyclerView mRvNews;
    private View list_view_loading;

    //用于标记是否是首页的底部刷新，如果是加载成功后发送完成的事件
    private boolean isHomeTabRefresh;
    private String mTitleCode;
    private TextView new_list_tv;
    private VideoAdapter mvideoadapter = null;
    private ArrayList<News> newsList = new ArrayList<>();

    /**
     * 是否是点击底部标签进行刷新的标识
     */
    private boolean isClickTabRefreshing;
    private RotateAnimation mRotateAnimation;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_video_list;
    }

    @Override
    public void findViews() {
        super.findViews();
        mTipView = mRoomView.findViewById(R.id.fragment_video_tip_view);
        mRefreshLayout = mRoomView.findViewById(R.id.fragment_video_refresh_layout);
        mFlContent = mRoomView.findViewById(R.id.fl_content);
        mRvNews = mRoomView.findViewById(R.id.fragment_video_rv_news);
        list_view_loading = mRoomView.findViewById(R.id.fragment_video_loading);
        mRefreshLayout.setDelegate(this);
        mRvNews.setLayoutManager(new LinearLayoutManager(mActivity));
//        setData();
        SetDataAdapter();
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mActivity, true);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.pull_refresh_bg);//背景色
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(mRvNews);

    }

    @Override
    public void bindData() {
        super.bindData();


    }

    @Override
    public void bindListener() {
        super.bindListener();
    }

    //下拉刷新
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        setData();
        mvideoadapter.setData(newsList);
        refreshLayout.endRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
//        refreshLayout.beginLoadingMore();

        return true;
    }

    public void setData() {

        News news = new News();
        for (int m = 0; m < 12; m++) {
            news.setTitle("dkasdjkajs" + m);
            newsList.add(0, news);
        }
    }

    public void SetDataAdapter() {
        mvideoadapter = new VideoAdapter(mActivity, mTitleCode, mRvNews, newsList);
        mRvNews.setAdapter(mvideoadapter);
        mRvNews.setEmptyView(list_view_loading);
    }
}
