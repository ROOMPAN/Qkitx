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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.umeng.socialize.UMShareAPI;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.CommentExampleAdapter;
import com.yanhui.qktx.adapter.StickyExampleModel;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.CommentBean;
import com.yanhui.qktx.models.TaskShareBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.Logger;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;
import com.yanhui.qktx.view.RewritePopwindow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.yanhui.qktx.constants.Constant.ARTICLETYPE;
import static com.yanhui.qktx.constants.Constant.COMMENTID;
import static com.yanhui.qktx.constants.Constant.ISCONN;
import static com.yanhui.qktx.constants.Constant.TASKID;

/**
 * Created by liupanpan on 2017/9/22.
 * 评论页面
 */

public class CommentActivity extends BaseActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private PowerfulRecyclerView mrv_recy_view;
    private TextView tvStickyHeaderView;
    private RelativeLayout rela_back, rela_collection, rela_share, rela_more, rela_et_message;
    private ImageView iv_image_collection;
    private LinearLayout rela_send_mess;
    private EditText et_message;
    private Button bt_send;
    private int taskId, isconn, articleType;
    private List<CommentBean.DataBean> commentBeanList = new ArrayList<>();
    private int hot_list_size;
    private int new_comment_list_size;
    private boolean iscollection = true;
    private int PagerSize = 8; //数据多少
    private int PagerNO = 2;  //页面
    private CommentExampleAdapter commentExampleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setTitleText("点赞是一种态度");

    }

    private void bindReshLayout() {
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(CommentActivity.this, true);
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
        taskId = getIntent().getIntExtra(TASKID, 0);
        isconn = getIntent().getIntExtra(ISCONN, 0);
        articleType = getIntent().getIntExtra(ARTICLETYPE, 0);
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
        iv_image_collection = (ImageView) findViewById(R.id.activity_comment_image_collection);
        mrv_recy_view.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
        bindReshLayout();
    }

    @Override
    public void bindData() {
        super.bindData();
        if (isconn == 1) {
            iv_image_collection.setImageResource(R.drawable.icon_news_detail_star_selected);
        } else {
            iv_image_collection.setImageResource(R.drawable.icon_news_detail_star_normal);
        }
        getHotComment();

    }

    @Override
    public void bindListener() {
        super.bindListener();
        rela_back.setOnClickListener(this);
        rela_collection.setOnClickListener(this);
        rela_share.setOnClickListener(this);
        rela_more.setOnClickListener(this);
        rela_et_message.setOnClickListener(this);
        iv_image_collection.setOnClickListener(this);
    }

    private void initRecyclerView() {

        mrv_recy_view.setLayoutManager(new LinearLayoutManager(this));
        commentExampleAdapter = new CommentExampleAdapter(this, getData(), et_message, rela_send_mess, bt_send, mRefreshLayout);
        mrv_recy_view.setAdapter(commentExampleAdapter);
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
                        tvStickyHeaderView.getMeasuredWidth() / 2, tvStickyHeaderView.getMeasuredHeight() + 4);

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
                HttpClient.getInstance().getTaskShareInfo(taskId, new NetworkSubscriber<TaskShareBean>(this) {
                    @Override
                    public void onNext(TaskShareBean data) {
                        super.onNext(data);
                        if (data.isOKResult()) {
                            RewritePopwindow mPopwindow = new RewritePopwindow(CommentActivity.this, data.getData().getShareTitle(), data.getData().getShareDesc(), data.getData().getShareImg(), data.getData().getShareUrl());
                            mPopwindow.show(view);
                        } else {
                            ToastUtils.showToast(data.mes);
                        }
                    }
                });
                break;
            case R.id.activity_comment_news_more:
                //更多
                startActivity(new Intent(this, CommentUserShowAllActivity.class).putExtra(TASKID, taskId).putExtra(COMMENTID, 14));
                break;
            case R.id.activity_comment_et_relayout:
                rela_send_mess.setVisibility(View.VISIBLE);
                et_message.setText("");
                showSoftInputFromWindow(this, et_message, true);
                break;
            case R.id.activity_comment_image_collection:
                if (isconn != 1) {
                    HttpClient.getInstance().getAddConnection(taskId, articleType, new NetworkSubscriber<BaseEntity>(this) {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                ToastUtils.showToast(data.mes);
                                isconn = 1;
                                iv_image_collection.setImageResource(R.drawable.icon_news_detail_star_selected);
                                EventBus.getDefault().post(new BusEvent(EventConstants.EVEN_ISCONN, 1));//点赞
                            } else if (data.isNotResult()) {
                                startActivity(new Intent(CommentActivity.this, LoginActivity.class));
                            }
                        }
                    });
                } else {
                    HttpClient.getInstance().getDeleteConnection(taskId, new NetworkSubscriber<BaseEntity>(this) {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                ToastUtils.showToast(data.mes);
                                isconn = 0;
                                iv_image_collection.setImageResource(R.drawable.icon_news_detail_star_normal);
                                EventBus.getDefault().post(new BusEvent(EventConstants.EVEN_ISCONN, 0));
                            } else if (data.isNotResult()) {
                                startActivity(new Intent(CommentActivity.this, LoginActivity.class));
                            }
                        }
                    });
                }
                //收藏
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
        commentBeanList.clear();
        PagerNO = 2;
        getHotComment();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getNewComments(1, PagerNO);
        return true;
    }

    public void getHotComment() {
        HttpClient.getInstance().getHotComments(taskId, new NetworkSubscriber<CommentBean>(this) {
            @Override
            public void onNext(CommentBean data) {
                super.onNext(data);
                if (data.isOKResult() && data.getData().size() != 0) {
                    hot_list_size = data.getData().size();
                    for (int i = 0; i < data.getData().size(); i++) {
                        commentBeanList.add(data.getData().get(i));
                    }
                    Logger.e("comment_list_size", "" + commentBeanList.size());
                }
                getNewComments(0, 1);
            }
        });

    }

    //最新评论接口访问
    public void getNewComments(int isloadmore, int pagerNO) {
        HttpClient.getInstance().getNewComments(taskId, pagerNO, Constant.PAGER_SIZE, new NetworkSubscriber<CommentBean>(this) {
            @Override
            public void onNext(CommentBean data) {
                super.onNext(data);
                if (data.isOKResult() && data.getData().size() != 0) {
                    new_comment_list_size = data.getData().size();
                    commentBeanList.addAll(data.getData());
                    Logger.e("comment_list_size", "" + commentBeanList.size());
                    if (isloadmore == 1) {
                        PagerNO++;
                    }
                }
                if (isloadmore != 1) {
                    initRecyclerView();
                } else {
                    commentExampleAdapter.addAll(getData());
//                    commentampleAdapter.addAll(getData());
                }
            }
        });
    }

    public List<StickyExampleModel> getData() {
        List<StickyExampleModel> stickyExampleModels = new ArrayList<>();
        for (int index = 0; index < commentBeanList.size(); index++) {
            if (index < hot_list_size) {
                stickyExampleModels.add(new StickyExampleModel("最热评论", commentBeanList));
            } else {
                stickyExampleModels.add(new StickyExampleModel("最新评论", commentBeanList));
            }
        }
        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();
        Logger.e("comment_list", "" + stickyExampleModels.size());
        return stickyExampleModels;
    }
}
