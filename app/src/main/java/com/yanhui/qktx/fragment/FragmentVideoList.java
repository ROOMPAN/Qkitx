package com.yanhui.qktx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.models.News;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.ConstanceValue;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liupanpan on 2017/9/6.
 */

public class FragmentVideoList extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private TipView mTipView;
    private BGARefreshLayout mRefreshLayout;
    private FrameLayout mFlContent;
    private PowerfulRecyclerView mRvNews;
    private View list_view_loading;
    private List<ArticleListBean.DataBean> videolist = new ArrayList<>();

    //用于标记是否是首页的底部刷新，如果是加载成功后发送完成的事件
    private boolean isHomeTabRefresh;
    private String mCateId;
    private TextView new_list_tv;
    private VideoAdapter mvideoadapter = null;
    private int isrefresh = 1;
    private int pagenumber = 1;
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

    public static FragmentVideoList newInstance(String code) {
        FragmentVideoList fragment = new FragmentVideoList();
        Bundle bundle = new Bundle();
        bundle.putString(ConstanceValue.DATA, code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void findViews() {
        super.findViews();
        mTipView = mRoomView.findViewById(R.id.fragment_video_tip_view);
        mRefreshLayout = mRoomView.findViewById(R.id.fragment_video_refresh_layout);
        mFlContent = mRoomView.findViewById(R.id.fl_content);
        mRvNews = mRoomView.findViewById(R.id.fragment_video_rv_news);
        list_view_loading = mRoomView.findViewById(R.id.fragment_video_loading);
        new_list_tv = mRoomView.findViewById(R.id.fragment_video_id);
        mRefreshLayout.setDelegate(this);
        mRvNews.setLayoutManager(new LinearLayoutManager(mActivity));
//        setData();
        // SetDataAdapter();
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
        mCateId = getArguments().getString(ConstanceValue.DATA);
        Log.e("cateid", "" + mCateId);
        new_list_tv.setText(mCateId + "");
    }

    @Override
    public void bindListener() {
        super.bindListener();

    }

    /**
     * 当 fragment可见的时候 请求数据加载
     */
    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getFindpagerData(1, 1);
    }

    //下拉刷新
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //setData();
        getFindpagerData(1, 1);

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
//        refreshLayout.beginLoadingMore();
        getFindpagerData(0, pagenumber);
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
        mvideoadapter = new VideoAdapter(mActivity, mCateId, mRvNews);
        mRvNews.setAdapter(mvideoadapter);
        mvideoadapter.setData(videolist);
        mRvNews.setEmptyView(list_view_loading);
    }

    public void getFindpagerData(int refreshType, int pagenum) {
        ToastUtils.showToast(pagenum + "");
        HttpClient.getInstance().getFindPage(refreshType, mCateId, "2", pagenum, 8, new NetworkSubscriber<ArticleListBean>(this) {
            @Override
            public void onNext(ArticleListBean data) {
                super.onNext(data);
                if (data.isOKResult()) {
                    if (refreshType == 1) {
                        for (int i = 0; i < data.getData().size(); i++) {
                            videolist.add(0, data.getData().get(i));
                        }
                        SetDataAdapter();
                        mRefreshLayout.endRefreshing();
                    } else {
                        pagenumber++;
                        mvideoadapter.addData(data.getData());
                        mRefreshLayout.endLoadingMore();
                    }

                }
            }
        });

    }

}
