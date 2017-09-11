package com.yanhui.qktx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.yanhui.qktx.R;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;

/**
 * Created by liupanpan on 2017/9/11.
 */

public class SeachActivity extends BaseActivity implements View.OnClickListener {
    private PowerfulRecyclerView rv_view;
    private TipView mTipView;
    private BGARefreshLayout mRefreshLayout;
    private ImageView iv_back;
    private EditText et_seach;
    private TextView tv_seach_go;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        setGoneTopBar();
    }

    @Override
    public void findViews() {
        super.findViews();
        rv_view = (PowerfulRecyclerView) findViewById(R.id.activity_seach_rv_news);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.activity_seach_refresh_layout);
        iv_back = (ImageView) findViewById(R.id.activity_seach_topbar_left_back_img);
        et_seach = (EditText) findViewById(R.id.activity_seach_edit);
        tv_seach_go = (TextView) findViewById(R.id.activity_seach_seach_go);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.pull_refresh_bg);//背景色
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(rv_view);
    }

    @Override
    public void bindData() {
        super.bindData();
    }

    @Override
    public void bindListener() {
        super.bindListener();
        iv_back.setOnClickListener(this);
        tv_seach_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_seach_topbar_left_back_img:
                finish();
                break;
            case R.id.activity_seach_seach_go:
                if (!StringUtils.isEmpty(et_seach.getText().toString())) {
                    ToastUtils.showToast("" + et_seach.getText().toString());
                }
                break;
        }
    }
}
