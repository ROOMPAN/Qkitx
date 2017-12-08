package com.yanhui.qktx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

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
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.ConstanceValue;
import com.yanhui.qktx.utils.Logger;
import com.yanhui.qktx.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
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
    private boolean isHomeTabRefresh;
    private String mCateId;
    private TextView new_list_tv;
    private VideoAdapter mvideoadapter = null;
    private int pagenumber = 2;

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
        mRvNews.setHasFixedSize(true);
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
        Logger.e("cateid", "" + mCateId);
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
                        Collections.reverse(data.getData());//倒叙
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
                        mTipView.show("为您推荐了" + data.getData().size() + "篇视频");
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
                    }

                } else {
                    mStateView.showContent();
                    mRefreshLayout.endLoadingMore();
                    mRefreshLayout.endRefreshing();
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
                        mAdViewPositionMap.put(mAdViewList.get(naposition), position - 2); // 把每个广告在列表中位置记录下来
                        mvideoadapter.addADViewToPosition(position - 2, mAdViewList.get(naposition));
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
        });
        mADManager.loadAD(10); //每次拉取多少条数据
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
}
