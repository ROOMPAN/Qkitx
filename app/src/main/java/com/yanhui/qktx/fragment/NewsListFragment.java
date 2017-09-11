package com.yanhui.qktx.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.library.BottomBarItem;
import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.NewsAdapter;
import com.yanhui.qktx.models.News;
import com.yanhui.qktx.models.VirtualBean;
import com.yanhui.qktx.models.event.TabRefreshCompletedEvent;
import com.yanhui.qktx.models.event.TabRefreshEvent;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.ConstanceValue;
import com.yanhui.qktx.utils.NetWorkUtils;
import com.yanhui.qktx.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by liupanpan on 2017/8/18.
 */

public class NewsListFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private TipView mTipView;
    private BGARefreshLayout mRefreshLayout;
    private FrameLayout mFlContent;
    private PowerfulRecyclerView mRvNews;
    private View view_empty_loading;

    //用于标记是否是首页的底部刷新，如果是加载成功后发送完成的事件
    private boolean isHomeTabRefresh;
    private String mTitleCode;
    private TextView new_list_tv;

    /**
     * 是否是点击底部标签进行刷新的标识
     */
    private boolean isClickTabRefreshing;
    private RotateAnimation mRotateAnimation;

    private ArrayList<News> newsList = new ArrayList<>();
    private ArrayList<News> titlelist = new ArrayList<>();
    private NewsAdapter mnewsAdapter;
    private ArrayList<News> moreData = new ArrayList<>();
    /**
     * 是否是推荐频道
     */
    private boolean isRecommendChannel;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        onCreateView(inflater.inflate(R.layout.fragment_news_list, null));
//        return mRoomView;
//    }


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_news_list;
    }

    public static NewsListFragment newInstance(String code) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstanceValue.DATA, code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void findViews() {
        super.findViews();
        mTipView = mRoomView.findViewById(R.id.tip_view);
        mRefreshLayout = mRoomView.findViewById(R.id.refresh_layout);
        mFlContent = mRoomView.findViewById(R.id.fl_content);
        mRvNews = mRoomView.findViewById(R.id.rv_news);
        new_list_tv = mRoomView.findViewById(R.id.new_list_tv);
        view_empty_loading = mRoomView.findViewById(R.id.view_empty_loading);
        mRefreshLayout.setDelegate(this);
        mRvNews.setLayoutManager(new GridLayoutManager(mActivity, 1));
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
        SetDataAdapter();
    }

    @Override
    public void bindData() {
        super.bindData();
        mTitleCode = getArguments().getString(ConstanceValue.DATA);
        // isVideoList = getArguments().getBoolean(Constant.IS_VIDEO_LIST, false);
//        String[] channelCodes = UIUtils.getStringArr(R.array.home_title_code);
//        isRecommendChannel = mChannelCode.equals(channelCodes[0]);//是否是推荐频道
        Log.e("code", "" + mTitleCode);
        new_list_tv.setText(mTitleCode);

    }

    @Override
    public void bindListener() {
        super.bindListener();
    }

    /**
     * 接收到点击底部首页页签下拉刷新的事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(TabRefreshEvent event) {
        if (event.getChannelCode().equals(mTitleCode) && mRefreshLayout.getCurrentRefreshStatus() != BGARefreshLayout.RefreshStatus.REFRESHING) {
            //如果和当前的频道码一致并且不是刷新中,进行下拉刷新
            if (!NetWorkUtils.isNetworkAvailable(mActivity)) {
                //网络不可用弹出提示
                mTipView.show();
                return;
            }
            isClickTabRefreshing = true;

            if (event.isHomeTab()) {
                //如果页签是首页，则换成就加载的图标并执行动画
                BottomBarItem bottomBarItem = event.getBottomBarItem();
                bottomBarItem.setIconSelectedResourceId(R.drawable.tab_loading);//更换成加载图标
                bottomBarItem.setStatus(true);

                //播放旋转动画
                if (mRotateAnimation == null) {
                    mRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    mRotateAnimation.setDuration(800);
                    mRotateAnimation.setRepeatCount(-1);
                }
                ImageView bottomImageView = bottomBarItem.getImageView();
                bottomImageView.setAnimation(mRotateAnimation);
                bottomImageView.startAnimation(mRotateAnimation);//播放旋转动画
            }
            isHomeTabRefresh = event.isHomeTab();//是否是首页

            mRvNews.scrollToPosition(0);//滚动到顶部
            mRefreshLayout.beginRefreshing();//开始下拉刷新

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus(NewsListFragment.this);
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterEventBus(NewsListFragment.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        KLog.e("onDestroy" + mChannelCode);
    }

    //开始刷新
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        Log.e("NewsListFragment", "开始刷新");
        if (!NetWorkUtils.isNetworkAvailable(mActivity)) {
            //网络不可用弹出提示
            mTipView.show();
            if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
                mRefreshLayout.endRefreshing();
            }
            mStateView.showRetry();//显示重试的布局
            return;
        }
        getData();

    }

    //加载更多 不用
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        initMoreData();
        mnewsAdapter.addData(moreData);
        refreshLayout.endLoadingMore();
        return false;
    }

    //发送刷新数据完成后的消息发送
    private void postRefreshCompletedEvent() {
        if (isClickTabRefreshing) {
            //如果是点击底部刷新获取到数据的,发送加载完成的事件
            EventBus.getDefault().post(new TabRefreshCompletedEvent());
            isClickTabRefreshing = false;
        }
    }

    private void getData() {

        HttpClient.getInstance().getVirLi("0", "?", new NetworkSubscriber<VirtualBean>() {
            @Override
            public void onCompleted() {
                super.onCompleted();
                //mStateView.showLoading();
            }

            @Override
            public void onStart() {
                super.onStart();
                mStateView.showLoading();
            }

            @Override
            public void onNext(VirtualBean data) {
                super.onNext(data);
                if (data.isOKCode()) {
                    mStateView.showContent();
                    mRefreshLayout.endRefreshing();// 加载完毕后在 UI 线程结束下拉刷新
                    mTipView.show("为您推荐了" + data.getBody().getBrandList().size() + "篇文章");
                    if (isHomeTabRefresh) {
                        postRefreshCompletedEvent();//发送加载完成的事件
                    }
                } else {
                    mTipView.show(data.msg);
                    mStateView.showContent();
                    //收起刷新
                    if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
                        mRefreshLayout.endRefreshing();
                    }
                    setData("下拉");
                    mnewsAdapter.setData(newsList);
                    postRefreshCompletedEvent();//发送加载完成的事件
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mTipView.show();//弹出提示
                //如果一开始进入没有数据
//                mStateView.showRetry();//显示重试的布局
                //收起刷新
                if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
                    mRefreshLayout.endRefreshing();
                }

                postRefreshCompletedEvent();//发送加载完成的事件
            }
        });
    }

    public void setData(String title) {
        News news = new News();
        for (int m = 0; m < 2; m++) {
            news.setTitle(title + m);
            newsList.add(0, news);//每条数据都处于最顶端
        }

    }

    //初始化加载更多数据
    private void initMoreData() {
        News news = new News();
        for (int i = 0; i < 3; i++) {
            news.setTitle("mmmmmmmmmmm" + i);
            moreData.add(news);
        }
    }

    public void SetDataAdapter() {
        //setData("初始化");
        if (mnewsAdapter == null) {
            mnewsAdapter = new NewsAdapter(mActivity, mTitleCode, mRvNews, newsList);
            mRvNews.setAdapter(mnewsAdapter);
            mRvNews.setEmptyView(view_empty_loading);
        } else {
            mnewsAdapter.setData(newsList);
        }
    }
}
