package com.yanhui.qktx.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.VideoAdapter;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.constants.TencentLiMeng;
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.models.event.TabRefreshCompletedEvent;
import com.yanhui.qktx.models.event.TabRefreshEvent;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.ConstanceValue;
import com.yanhui.qktx.utils.Logger;
import com.yanhui.qktx.utils.NetWorkUtils;
import com.yanhui.qktx.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * Created by liupanpan on 2017/9/6.
 */

public class FragmentVideoList extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private TipView mTipView;
    private BGARefreshLayout mRefreshLayout;
    private FrameLayout mFlContent;
    private PowerfulRecyclerView mRvNews;
    private View list_view_loading;
    private List<ArticleListBean.DataBean> videolist = new ArrayList<>();   //下拉刷新数据集合
    private List<ArticleListBean.DataBean> videomorelist = new ArrayList<>(); //加载更多数据集合
    //腾讯广告数据字段
    private NativeExpressAD mADManager;
    private List<NativeExpressADView> mAdViewList;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap = new HashMap<NativeExpressADView, Integer>();

    //用于标记是否是首页的底部刷新，如果是加载成功后发送完成的事件
    private boolean isVideoTabRefresh;
    private String mCateId;
    private VideoAdapter mvideoadapter = null;
    private int pagenumber = 2;

    /**
     * 是否是点击底部标签进行刷新的标识
     */
    private boolean isClickVideoRefreshing;
    private RotateAnimation mRotataanima;


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
        mRefreshLayout.setFocusable(false);
        mRvNews.setFocusable(false);
        mRvNews.setHasFixedSize(false);
        mRefreshLayout.setDelegate(this);
        mRvNews.setLayoutManager(new GridLayoutManager(mActivity, 1));
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
        Logger.e("cateid", "" + mCateId);
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
        getFindpagerData(1, 1, true);
        super.lazyLoad();
    }

    //下拉刷新
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //setData();
        getFindpagerData(1, 1, false);

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
//        refreshLayout.beginLoadingMore();
        getFindpagerData(0, pagenumber, false);
        return true;
    }


    public void SetDataAdapter() {
        if (mvideoadapter == null) {
            mvideoadapter = new VideoAdapter(mActivity, mCateId, mRvNews, mRefreshLayout, mAdViewPositionMap);
        }
        mvideoadapter.setData(videolist);
        mRvNews.setAdapter(mvideoadapter);
        mRvNews.setEmptyView(list_view_loading);
    }

    public void getFindpagerData(int refreshType, int pagenum, boolean isrefresh) {
//        ToastUtils.showToast(pagenum + "");
        HttpClient.getInstance().getFindPage(refreshType, mCateId, "2", pagenum, Constant.PAGER_SIZE, new NetworkSubscriber<ArticleListBean>(this) {
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
                    list_view_loading.setVisibility(View.GONE);
                    mStateView.showContent();
                    if (refreshType == 1) {
//                        Collections.reverse(data.getData());//倒叙
                        for (int i = 0; i < data.getData().size(); i++) {
                            if (i == 0) {
                                data.getData().get(i).setisFinally(1);
                            } else {
                                data.getData().get(i).setisFinally(0);
                            }
                            if (!data.getData().get(i).getType().equals("ad")) {
                                //当为广告时候 不加入显示集合
                                videolist.add(0, data.getData().get(i));
                            } else {
                                Logger.e("position_i++", "" + i);
                                initNativeExpressAD(1, i);
                            }
                        }
                        SetDataAdapter();
                        mRefreshLayout.endRefreshing();
                        mTipView.show("「趣看天下」为您推荐了" + data.getData().size() + "篇视频");
                        if (isVideoTabRefresh) {
                            postRefreshCompletedEvent();//发送加载完成的事件
                        }
                    } else {
                        pagenumber++;
                        videomorelist.clear();
                        for (int i = 0; i < data.getData().size(); i++) {
                            if (!data.getData().get(i).getType().equals("ad")) {
                                //判断该条数据是否是广告
                                videomorelist.add(data.getData().get(i));
                            } else {
                                initNativeExpressAD(0, i);
                            }
                        }
                        //Collections.reverse(videomorelist);//倒叙
                        mvideoadapter.addData(videomorelist);
                        mRefreshLayout.endLoadingMore();
                        postRefreshCompletedEvent();//发送加载完成的事件
                    }

                } else {
                    mStateView.showContent();
                    mRefreshLayout.endLoadingMore();
                    mRefreshLayout.endRefreshing();
                    postRefreshCompletedEvent();//发送加载完成的事件
                }
            }
        });

    }

    /**
     * 腾讯广告数据拉取方法
     *
     * @param reshtype 刷新方式,上拉或者下拉 (1下拉,其他上拉)
     *                 position  广告位值
     */
    private void initNativeExpressAD(int reshtype, int position) {
        final float density = getResources().getDisplayMetrics().density;     //290,上文下图,左文右图 90
        ADSize adSize = new ADSize((int) (getResources().getDisplayMetrics().widthPixels / density), 300); // 宽、高的单位是dp。ADSize不支持MATCH_PARENT or WRAP_CONTENT，必须传入实际的宽高
        mADManager = new NativeExpressAD(getActivity(), adSize, TencentLiMeng.APPID, TencentLiMeng.NativeVideoPosID, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(AdError adError) {
                Logger.i("guangdian", String.format("onNoAD, error code: %d, error msg: %s", adError.getErrorCode(),
                        adError.getErrorMsg()));
            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                mAdViewList = list;
                int naposition = new Random().nextInt(list.size());
                int video_list_size;
                Logger.e("ad_list", "" + list.size() + "____" + position);
                if (list.size() != 0) {
                    video_list_size = mvideoadapter.getItemCount() + 2;
                    Logger.e("video_position_size", "" + video_list_size);
//                    Collections.reverse(data);//倒叙
                    if (reshtype == 1 && naposition < list.size()) {     //下拉刷新
                        Logger.e("position_i", "" + position);
                        mAdViewPositionMap.put(mAdViewList.get(naposition), position); // 把每个广告在列表中位置记录下来
                        mvideoadapter.addADViewToPosition(position, mAdViewList.get(naposition));
                    } else {
                        //上拉加载
                        Logger.e("video_position_size", "" + video_list_size);
                        mAdViewPositionMap.put(mAdViewList.get(naposition), position + video_list_size - 10); // 把每个广告在列表中位置记录下来
                        mvideoadapter.addADViewToPosition(position + video_list_size - 10, mAdViewList.get(naposition));
                    }
                } else {
                    video_list_size = mvideoadapter.getItemCount() + list.size();
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

            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                if (mvideoadapter != null) {
                    int removedPosition = mAdViewPositionMap.get(nativeExpressADView);
                    mvideoadapter.removeADView(removedPosition, nativeExpressADView);
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
        mADManager.loadAD(8); //每次拉取多少条数据
    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus(FragmentVideoList.this);
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterEventBus(FragmentVideoList.this);
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

    /**
     * 接收到点击底部首页页签下拉刷新的事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoRefreshEvent(TabRefreshEvent event) {
        if (event.getChannelCode().equals(mCateId) && mRefreshLayout.getCurrentRefreshStatus() != BGARefreshLayout.RefreshStatus.REFRESHING) {
            Logger.e("resh_video", "mCateId" + mCateId + "---" + "isHomeTab" + event.isHomeTab());
            //如果和当前的频道码一致并且不是刷新中,进行下拉刷新
            if (!NetWorkUtils.isNetworkAvailable(mActivity)) {
                //网络不可用弹出提示
                mTipView.show();
                return;
            }
            isClickVideoRefreshing = true;

            if (!event.isHomeTab()) {
                //如果页签是首页，则换成就加载的图标并执行动画
                BottomBarItem bottomBarItem = event.getBottomBarItem();
                bottomBarItem.setIconSelectedResourceId(R.drawable.icon_bottom_select_refresh);//更换成加载图标
                bottomBarItem.getTextView().setText("刷新");
                bottomBarItem.setStatus(true);

                //播放旋转动画
                if (mRotataanima == null) {
                    mRotataanima = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    mRotataanima.setDuration(800);
                    mRotataanima.setRepeatCount(-1);
                }
                ImageView bottomImageView = bottomBarItem.getImageView();
                bottomImageView.setAnimation(mRotataanima);
                bottomImageView.startAnimation(mRotataanima);//播放旋转动画
            }
            isVideoTabRefresh = !event.isHomeTab();//是否是视频页

            mRvNews.scrollToPosition(0);//滚动到顶部
            mRefreshLayout.beginRefreshing();//开始下拉刷新
        }
    }

    //发送刷新数据完成后的消息发送
    private void postRefreshCompletedEvent() {
        if (isClickVideoRefreshing) {
            //如果是点击底部刷新获取到数据的,发送加载完成的事件
            EventBus.getDefault().post(new TabRefreshCompletedEvent(Constant.isVideoEndResh));
            isClickVideoRefreshing = false;
        }
    }

}
