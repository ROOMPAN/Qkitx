package com.yanhui.qktx.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;

import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.VideoFavoritesAdapter;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.models.HistoryListBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by liupanpan on 2017/9/13.
 */

public class VideoFavoritesFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private PowerfulRecyclerView mrv_view;
    private BGARefreshLayout mRefreshLayout;
    private View empty_view;
    private Button bt_setcurrent_to_home;
    private List<HistoryListBean.DataBean> listBean;
    private VideoFavoritesAdapter madapter;
    private int pagerNos = 2;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_video_favorites;
    }

    @Override
    public void findViews() {
        super.findViews();
        mrv_view = mRoomView.findViewById(R.id.fragment_video_favorites_rv_news);
        mRefreshLayout = mRoomView.findViewById(R.id.fragment_video_favorites_refresh_layout);
        empty_view = mRoomView.findViewById(R.id.fragment_video_favorites_empty_view);
        bt_setcurrent_to_home = mRoomView.findViewById(R.id.fragment_favorites_setcurrent);
        mRefreshLayout.setDelegate(this);
        mrv_view.setLayoutManager(new GridLayoutManager(mActivity, 1));
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mActivity, true);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.home_chanel_bar_bg);//背景色
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(mrv_view);
    }

    @Override
    public void bindListener() {
        super.bindListener();
        bt_setcurrent_to_home.setOnClickListener(this);
    }

    @Override
    public void bindData() {
        super.bindData();
        getConnVedio(1);
        madapter = new VideoFavoritesAdapter(mActivity, listBean);
        mrv_view.setAdapter(madapter);
        mrv_view.setEmptyView(empty_view);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getConnVedio(1);
        //刷新
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getConnVedio(pagerNos);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_favorites_setcurrent:
                EventBus.getDefault().post(new BusEvent(EventConstants.EVENT_SWITCH_TO_HOME));//切换到首页
                mActivity.finish();
                break;
        }
    }

    public void getConnVedio(int pagerNo) {
        HttpClient.getInstance().getConnVedio(pagerNo, Constant.PAGER_SIZE, new NetworkSubscriber<HistoryListBean>(this) {
            @Override
            public void onNext(HistoryListBean data) {
                super.onNext(data);
                if (data.isOKResult()) {
                    if (pagerNo == 1) {
                        listBean = data.getData();
                        mRefreshLayout.endRefreshing();
                        madapter.setData(listBean);
                        pagerNos = 2;
                    } else {
                        madapter.addData(data.getData());
                        mRefreshLayout.endLoadingMore();
                        pagerNos++;
                    }
                } else {
                    mRefreshLayout.endRefreshing();
                }
            }
        });
    }
}
