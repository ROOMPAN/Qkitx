package com.yanhui.qktx.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;

import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.EssayFavoritesAdapter;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.models.HistoryListBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liupanpan on 2017/9/2.
 * 文章收藏 fragment
 */

public class EssayFavoritesFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private PowerfulRecyclerView mrv_view;
    private BGARefreshLayout mRefreshLayout;
    private View empty_view;
    private Button bt_setcurrent_to_home;
    private EssayFavoritesAdapter madapter;
    private HistoryListBean listBean;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_essay_favorites;
    }

    @Override
    public void findViews() {
        super.findViews();
        mrv_view = mRoomView.findViewById(R.id.fragment_essay_favorites_rv_news);
        mRefreshLayout = mRoomView.findViewById(R.id.fragment_essay_favorites_refresh_layout);
        empty_view = mRoomView.findViewById(R.id.fragment_essay_favorites_empty_view);
        bt_setcurrent_to_home = mRoomView.findViewById(R.id.fragment_favorites_setcurrent);
        mRefreshLayout.setDelegate(this);
        mrv_view.setLayoutManager(new GridLayoutManager(mActivity, 1));
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mActivity, true);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.pull_refresh_bg);//背景色
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(mrv_view);
    }

    @Override
    public void bindData() {
        super.bindData();
        getConnArticle();
        madapter = new EssayFavoritesAdapter(mActivity);
        mrv_view.setAdapter(madapter);
        mrv_view.setEmptyView(empty_view);
    }

    @Override
    public void bindListener() {
        super.bindListener();
        bt_setcurrent_to_home.setOnClickListener(this);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
        getConnArticle();

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    public void getConnArticle() {
        HttpClient.getInstance().getConnArticle(new NetworkSubscriber<HistoryListBean>(this) {
            @Override
            public void onNext(HistoryListBean data) {
                super.onNext(data);
                if (data.isOKResult()) {
                    listBean = data;
                    madapter.setData(listBean);
                    mRefreshLayout.endRefreshing();
                } else {
                    ToastUtils.showToast(data.mes);
                }
            }
        });
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
}
