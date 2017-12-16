package com.yanhui.qktx.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chaychan.library.BottomBarItem;
import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;
import com.sogou.feedads.AdOuterListener;
import com.sogou.feedads.AdRequest;
import com.sogou.feedads.AdView;
import com.sogou.feedads.NetWorkStateReceiver;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.NewsAdapter;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.constants.SouGouConstants;
import com.yanhui.qktx.constants.TencentLiMeng;
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.models.event.TabRefreshCompletedEvent;
import com.yanhui.qktx.models.event.TabRefreshEvent;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.ConstanceValue;
import com.yanhui.qktx.utils.Logger;
import com.yanhui.qktx.utils.NetWorkUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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

    /**
     * 是否是点击底部标签进行刷新的标识
     */
    private boolean isClickTabRefreshing;
    private RotateAnimation mRotateAnimation;

    private NewsAdapter mnewsAdapter;

    //腾讯广告数据字段
    private NativeExpressAD mADManager;
    private List<NativeExpressADView> mAdViewList;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap = new HashMap<NativeExpressADView, Integer>();
    //搜狗广告
    private ArrayList<AdView> adViewList = new ArrayList<>();
    private AdRequest adRequest;
    private NetWorkStateReceiver netWorkStateReceiver;
    //原生数据
    private List<ArticleListBean.DataBean> articlist = new ArrayList<>(); //下拉加载数据集合
    private List<ArticleListBean.DataBean> articlistmore = new ArrayList<>();//上拉加载数据集合

    private int pagenumber = 2;

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
        view_empty_loading = mRoomView.findViewById(R.id.view_empty_loading);
        mRefreshLayout.setFocusable(false);
        mRvNews.setFocusable(false);
        mRvNews.setHasFixedSize(false);
        mRefreshLayout.setDelegate(this);
        mRvNews.setLayoutManager(new GridLayoutManager(mActivity, 1));
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mActivity, true);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.pull_refresh_bg);//背景色
//        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
//        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
//        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(mRvNews);
    }

    @Override
    public void bindData() {
        super.bindData();
        mTitleCode = getArguments().getString(ConstanceValue.DATA);
        // isVideoList = getArguments().getBoolean(Constant.IS_VIDEO_LIST, false);
//        String[] channelCodes = UIUtils.getStringArr(R.array.home_title_code);
//        isRecommendChannel = mChannelCode.equals(channelCodes[0]);//是否是推荐频道
        Logger.e("code", "" + mTitleCode);
        //
        if (mnewsAdapter != null) {
            articlist.clear();//清空数据
            pagenumber = 2;
            getData(1, 1, true);
        }
    }

    @Override
    public void bindListener() {
        super.bindListener();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        //fragment 可见下 请求数据
        getData(1, 1, true);
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
                bottomBarItem.setIconSelectedResourceId(R.drawable.icon_bottom_select_refresh);//更换成加载图标
                bottomBarItem.getTextView().setText("刷新");
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
    public void onResume() {
        super.onResume();
        // 创建NetWorkStateReceiver
        netWorkStateReceiver = new NetWorkStateReceiver();
        // 将adView注册到NetWorkStateReceiver（有多个adView时要注册多个）
        for (AdView adView : adViewList) {
            if (adView != null) {
                netWorkStateReceiver.registerListener(adView);
            }
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        // 注册NetWorkStateReceiver
        mActivity.registerReceiver(netWorkStateReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        for (AdView adView : adViewList) {
            if (adView != null) {
                adView.onPause();
            }
        }
        // 注销NetWorkStateReceiver
        mActivity.unregisterReceiver(netWorkStateReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 使用完了每一个NativeExpressADView之后都要释放掉资源。
        if (mAdViewList != null) {
            for (NativeExpressADView view : mAdViewList) {
                view.destroy();
            }
        }
    }

    //开始刷新
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        Logger.e("NewsListFragment", "开始刷新");
        if (!NetWorkUtils.isNetworkAvailable(mActivity)) {
            //网络不可用弹出提示
            mTipView.show();
            if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
                mRefreshLayout.endRefreshing();
            }
            mStateView.showRetry();//显示重试的布局
            return;
        }
        getData(1, 1, false);

    }

    //加载更多
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getData(0, pagenumber, false);
        return true;
    }

    //发送刷新数据完成后的消息发送
    private void postRefreshCompletedEvent() {
        if (isClickTabRefreshing) {
            //如果是点击底部刷新获取到数据的,发送加载完成的事件
            EventBus.getDefault().post(new TabRefreshCompletedEvent());
            isClickTabRefreshing = false;
        }
    }

    private void getData(int refreshType, int pagenum, boolean isrefresh) {
        HttpClient.getInstance().getFindPage(refreshType, mTitleCode, "1", pagenum, Constant.PAGER_SIZE, new NetworkSubscriber<ArticleListBean>(this) {
            @Override
            public void onStart() {
                super.onStart();
                if (isrefresh) {
                    mStateView.showLoading();
                }
            }

            @Override
            public void onNext(ArticleListBean data) {
                super.onNext(data);
                if (data.isOKResult() && data.getData().size() != 0) {
                    view_empty_loading.setVisibility(View.GONE);
                    mStateView.showContent();
                    if (refreshType == 1) {
                        mRvNews.scrollToPosition(0);
                        //Collections.reverse(data.getData());// 使集合 倒叙排列(解决数据源倒叙的问题)
                        for (int i = 0; i < data.getData().size(); i++) {
                            if (i == 0) {
                                data.getData().get(i).setisFinally(1);
                            } else {
                                data.getData().get(i).setisFinally(0);
                            }
                            if (!data.getData().get(i).getType().equals("ad")) {
                                //当为广告时候 不加入显示集合
                                articlist.add(0, data.getData().get(i));
                            } else if (!isrefresh) {
                                if (data.getData().get(i).getAdSource() == 0) {
                                    initNativeExpressAD(1, i);
                                } else if (data.getData().get(i).getAdSource() == 1) {
                                    initNativeSouGouAD(1, i);
                                }
                            }
                        }
                        SetDataAdapter();
                        mRefreshLayout.endRefreshing();// 加载完毕后在 UI 线程结束下拉刷新
                        mTipView.show("「趣看天下」为您推荐了" + data.getData().size() + "篇文章");
                    } else {
                        pagenumber++;
                        articlistmore.clear();
                        for (int i = 0; i < data.getData().size(); i++) {
                            if (!data.getData().get(i).getType().equals("ad")) {
                                //判断该条数据是否是广告
                                articlistmore.add(data.getData().get(i));
                            } else if (!isrefresh) {
                                if (data.getData().get(i).getAdSource() == 0) {
                                    initNativeExpressAD(0, i);
                                } else if (data.getData().get(i).getAdSource() == 1) {
                                    initNativeSouGouAD(0, i);
                                }
                            }
                        }
                        mnewsAdapter.addData(articlistmore);
                        mRefreshLayout.endLoadingMore();
                    }
                    if (isHomeTabRefresh) {
                        postRefreshCompletedEvent();//发送加载完成的事件
                    }
                } else {
//                    mTipView.show(data.mes);
                    mStateView.showContent();
                    mRefreshLayout.endLoadingMore();
                    //收起刷新
                    if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
                        mRefreshLayout.endRefreshing();

                    }
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
                    mRefreshLayout.endLoadingMore();
                }
                postRefreshCompletedEvent();//发送加载完成的事件
            }
        });

    }


    public void SetDataAdapter() {
        if (mnewsAdapter == null) {
            mnewsAdapter = new NewsAdapter(mActivity, mTitleCode, mRvNews, mRefreshLayout, mAdViewPositionMap);
        }
        mnewsAdapter.setData(articlist);
        mRvNews.setAdapter(mnewsAdapter);
        mRvNews.setEmptyView(view_empty_loading);
    }

    /**
     * 腾讯广告拉取方法
     *
     * @param reshtype
     * @param position
     */

    private void initNativeExpressAD(int reshtype, int position) {
        final float density = getResources().getDisplayMetrics().density;     //290,上文下图,左文右图 90
        ADSize adSize = new ADSize((int) (getResources().getDisplayMetrics().widthPixels / density), 95); // 宽、高的单位是dp。ADSize不支持MATCH_PARENT or WRAP_CONTENT，必须传入实际的宽高
        mADManager = new NativeExpressAD(getActivity(), adSize, TencentLiMeng.APPID, TencentLiMeng.NativePosID, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(AdError adError) {
                Logger.i("guang_article", String.format("onNoAD, error code: %d, error msg: %s", adError.getErrorCode(),
                        adError.getErrorMsg()));
            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                mAdViewList = list;
                int naposition = new Random().nextInt(list.size());
                int video_list_size;
                Logger.e("ad_list", "" + list.size() + "____" + position);
                if (list.size() != 0) {
                    video_list_size = mnewsAdapter.getItemCount() + 2;
                    Logger.e("video_position_size", "" + video_list_size);
//                    Collections.reverse(data);//倒叙
                    if (reshtype == 1 && naposition < list.size()) {     //下拉刷新
                        Logger.e("position_i", "" + position);
                        mAdViewPositionMap.put(mAdViewList.get(naposition), position); // 把每个广告在列表中位置记录下来
                        mnewsAdapter.addADViewToPosition(position, mAdViewList.get(naposition));
                    } else {
                        //上拉加载
                        Logger.e("video_position_size", "" + video_list_size);
                        mAdViewPositionMap.put(mAdViewList.get(naposition), position + video_list_size - 10); // 把每个广告在列表中位置记录下来
                        mnewsAdapter.addADViewToPosition(position + video_list_size - 10, mAdViewList.get(naposition));
                    }
                    mnewsAdapter.notifyDataSetChanged();
                } else {
                    video_list_size = mnewsAdapter.getItemCount() + list.size();
                }
            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {
                mnewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                if (mnewsAdapter != null) {
                    int removedPosition = mAdViewPositionMap.get(nativeExpressADView);
                    mnewsAdapter.removeADView(removedPosition, nativeExpressADView);
                    mnewsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

            }
        });
        mADManager.loadAD(10); //每次拉取多少条数据
    }

    /**
     * 搜狗广告拉取方法
     *
     * @param reshtype
     * @param position
     */
    private void initNativeSouGouAD(int reshtype, int position) {
        try {
            final AdView adView = new AdView(mActivity);
            adRequest = new AdRequest(mActivity);
//         三图的，应用id：1105，代码位id：753，尺寸480×360。
//         大图的，应用id：1105，代码位id：752，尺寸680×410。
            adRequest.setPid(SouGouConstants.PID);
            adRequest.setMid(SouGouConstants.MID);
            adRequest.addTemplates(102, 480, 360);
            if (0 != adView.getAd(adRequest, new AdOuterListener() {
                @Override
                public void onGetAdSucc() {
                    Logger.e("ad_success", "" + adView.adReady);
                    updateList(adView, reshtype, position);
                }

                @Override
                public void onGetAdFailed() {
                    Log.i("SogouSDKDemo", "get ad failed.");
                }
            })) {
                Log.i("SogouSDKDemo", "get ad failed.");
            }
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }

    }

    public void updateList(AdView adView, int reshtype, int position) {
        adViewList.add(adView);
        int sougou_adview_size;
        if (adView.adReady) {
            sougou_adview_size = mnewsAdapter.getItemCount() + 2;
            if (reshtype == 1) {     //下拉刷新
                Logger.e("position_i", "" + position);
                mnewsAdapter.addSouGouADViewToPosition(position, adView);
                mnewsAdapter.notifyDataSetChanged();
            } else {
                mnewsAdapter.addSouGouADViewToPosition(position + sougou_adview_size - 10, adView);
                mnewsAdapter.notifyDataSetChanged();
            }
        } else {
            sougou_adview_size = mnewsAdapter.getItemCount();
        }
//        adListener.registerListener(adView);

    }

}
