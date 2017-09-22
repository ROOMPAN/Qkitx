package com.yanhui.qktx.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.umeng.socialize.UMShareAPI;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.CommentExampleAdapter;
import com.yanhui.qktx.utils.DataUtil;
import com.yanhui.qktx.utils.UIUtils;
import com.yanhui.qktx.view.RewritePopwindow;

/**
 * Created by liupanpan on 2017/9/22.
 * 评论页面
 */

public class CommentActivity extends BaseActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private PowerfulRecyclerView mrv_recy_view;
    private TextView tvStickyHeaderView;
    private RelativeLayout rela_back, rela_collection, rela_share, rela_more, rela_et_message;
    private LinearLayout rela_send_mess;
    private EditText et_message;
    private Button bt_send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setTitleText("点赞是一种态度");
    }

    private void bindReshLayout() {
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, false);
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
        mrv_recy_view = (PowerfulRecyclerView) findViewById(R.id.rv_sticky_example);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.activityt_comment_refresh_layout);
        tvStickyHeaderView = (TextView) findViewById(R.id.tv_sticky_header_view);
        rela_back = (RelativeLayout) findViewById(R.id.activity_comment_back);
        rela_collection = (RelativeLayout) findViewById(R.id.activity_comment_collection);
        rela_share = (RelativeLayout) findViewById(R.id.activity_comment_news_share);
        rela_more = (RelativeLayout) findViewById(R.id.activity_comment_news_more);
        rela_et_message = (RelativeLayout) findViewById(R.id.activity_comment_et_relayout);
        rela_send_mess = (LinearLayout) findViewById(R.id.activity_comment_send_mess_linner);
        et_message = (EditText) findViewById(R.id.activity_comment_message);
        bt_send = (Button) findViewById(R.id.activity_comment_message_send);
        bindReshLayout();
        initRecyclerView();
    }

    @Override
    public void bindData() {
        super.bindData();
    }

    @Override
    public void bindListener() {
        super.bindListener();
        rela_back.setOnClickListener(this);
        rela_collection.setOnClickListener(this);
        rela_share.setOnClickListener(this);
        rela_more.setOnClickListener(this);
        rela_et_message.setOnClickListener(this);
        bt_send.setOnClickListener(this);
    }

    private void initRecyclerView() {

        mrv_recy_view.setLayoutManager(new LinearLayoutManager(this));
        mrv_recy_view.setAdapter(new CommentExampleAdapter(this, DataUtil.getData()));
        mrv_recy_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View stickyInfoView = recyclerView.findChildViewUnder(tvStickyHeaderView.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    if (TextUtils.equals(stickyInfoView.getContentDescription(), "最热评论")) {
                        tvStickyHeaderView.setBackground(getResources().getDrawable(R.drawable.shape_comment_handle_red_bg));
                    } else {
                        tvStickyHeaderView.setBackground(getResources().getDrawable(R.drawable.shape_comment_handle));
                    }
                    tvStickyHeaderView.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                View transInfoView = recyclerView.findChildViewUnder(
                        tvStickyHeaderView.getMeasuredWidth() / 2, tvStickyHeaderView.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {

                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - tvStickyHeaderView.getMeasuredHeight();

                    if (transViewStatus == CommentExampleAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            tvStickyHeaderView.setTranslationY(dealtY);
                        } else {
                            tvStickyHeaderView.setTranslationY(0);
                        }
                    } else if (transViewStatus == CommentExampleAdapter.NONE_STICKY_VIEW) {
                        tvStickyHeaderView.setTranslationY(0);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_comment_back:
                //返回
                finish();
                break;
            case R.id.activity_comment_collection:
//                //返回
//                finish();
                break;
            case R.id.activity_comment_news_share:
                //分享
                RewritePopwindow mPopwindow = new RewritePopwindow(this);
                mPopwindow.show(view);
                break;
            case R.id.activity_comment_news_more:
                //更多
                break;
            case R.id.activity_comment_et_relayout:
                rela_send_mess.setVisibility(View.VISIBLE);
                et_message.setText("");
                showSoftInputFromWindow(this, et_message, true);
                break;
            case R.id.activity_comment_message_send:
                //发送
                et_message.setText("");
                showSoftInputFromWindow(this, et_message, false);
                break;
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText, boolean open) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (open) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            //关闭软键盘
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载
        return false;
    }
}
