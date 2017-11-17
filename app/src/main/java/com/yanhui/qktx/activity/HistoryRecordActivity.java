package com.yanhui.qktx.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.HistoryRecordAdapter;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.HistoryListBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/9/13.
 */

public class HistoryRecordActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private BGARefreshLayout mRefreshLayout;
    private PowerfulRecyclerView mrv_recy_view;
    private View mEmpty_view;
    private TextView bt_clean;
    private View include_current;
    private Button bt_curreent_to_home;
    private ImageView iv_back;
    private HistoryRecordAdapter madapter;
    private int pagenumber = 2;
    private List<HistoryListBean.DataBean> HistoryBean = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_record);
        setGoneTopBar();
    }

    private void bindReshLayout() {
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.white);//背景色
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(mrv_recy_view);

    }

    @Override
    public void findViews() {
        super.findViews();
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.activityt_history_refresh_layout);
        mrv_recy_view = (PowerfulRecyclerView) findViewById(R.id.activity_history_rv_news);
        mEmpty_view = findViewById(R.id.activity_history_empty_view);
        bt_clean = (TextView) findViewById(R.id.activity_history_topbar_right_clean);
        include_current = findViewById(R.id.activity_history_empty_view);
        bt_curreent_to_home = include_current.findViewById(R.id.fragment_history_setcurrent);
        iv_back = (ImageView) findViewById(R.id.activity_history_topbar_left_back_img);
        bindReshLayout();
    }

    @Override
    public void bindData() {
        super.bindData();
        pagenumber = 1;
        getHistoryRead(false);
    }

    @Override
    public void bindListener() {
        super.bindListener();
        bt_clean.setOnClickListener(this);
        bt_curreent_to_home.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        pagenumber = 1;
        getHistoryRead(false);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getHistoryRead(true);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_history_topbar_right_clean:
                //清空
                showNormalDialog();
                break;
            case R.id.fragment_history_setcurrent:
                EventBus.getDefault().post(new BusEvent(EventConstants.EVENT_SWITCH_TO_HOME));//切换到首页
                finish();
                break;
            case R.id.activity_history_topbar_left_back_img:
                finish();
                break;
        }
    }

    public void getHistoryRead(boolean isloadmore) {
        String clearLastTime = String.valueOf(System.currentTimeMillis());
        HttpClient.getInstance().getReadRecord(clearLastTime, pagenumber, Constant.PAGER_SIZE, new NetworkSubscriber<HistoryListBean>(this) {
            @Override
            public void onNext(HistoryListBean data) {
                super.onNext(data);
                if (data.isOKResult()) {
                    setHistoryAdapter(data.getData(), isloadmore);
                } else {
                    ToastUtils.showToast(data.mes);
                }
            }
        });
    }

    private void showNormalDialog() {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setTitle("清空历史记录??");
        normalDialog.setMessage("是否清空历史记录?");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HttpClient.getInstance().getDeleteHisto(new NetworkSubscriber<BaseEntity>(HistoryRecordActivity.this) {
                    @Override
                    public void onNext(BaseEntity data) {
                        super.onNext(data);
                        if (data.isOKResult()) {
                            pagenumber = 1;
                            getHistoryRead(false);
                            ToastUtils.showToast(data.mes);
                        }
                    }
                });
            }
        });
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // 显示
        normalDialog.show();
    }

    public void setHistoryAdapter(List<HistoryListBean.DataBean> data, boolean isloadmore) {
        if (madapter == null) {
            madapter = new HistoryRecordAdapter(HistoryRecordActivity.this);
        }
        if (isloadmore) {
            if (data.size() != 0) {
                mrv_recy_view.scrollToPosition(madapter.getItemCount() - data.size());
                madapter.addData(data);
                pagenumber++;
                mRefreshLayout.endLoadingMore();
            } else {
                mrv_recy_view.scrollToPosition(madapter.getItemCount() - 1);
                mRefreshLayout.endLoadingMore();
                ToastUtils.showToast("已经到底了~~");
            }
        } else {
            pagenumber = 2;
            madapter.setData(data);
            mRefreshLayout.endRefreshing();
        }
        mrv_recy_view.setAdapter(madapter);
        mrv_recy_view.setEmptyView(mEmpty_view);
    }
}
