package com.yanhui.qktx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.CommentUserShowAdapter;
import com.yanhui.qktx.models.CommentBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;

import static com.yanhui.qktx.constants.Constant.COMMENTID;
import static com.yanhui.qktx.constants.Constant.TASKID;

/**
 * Created by liupanpan on 2017/9/28.
 */

public class CommentUserShowAllActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private BGARefreshLayout mRefreshLayout;
    private PowerfulRecyclerView mRv_view;
    private int taskId, commentId;
    private CommentUserShowAdapter commentUserShowAdapter;
    private RelativeLayout show_all_et_relayout;
    private LinearLayout show_all_et_news_send_mess_linner;
    private EditText et_message;
    private Button bt_send;
    private int pagernos = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_show_all);
        setTitleText("更多评论");
    }

    @Override
    public void findViews() {
        super.findViews();
        taskId = getIntent().getIntExtra(TASKID, 0);
        commentId = getIntent().getIntExtra(COMMENTID, 0);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.refresh_layout);
        mRv_view = (PowerfulRecyclerView) findViewById(R.id.rv_recycle_view);
        show_all_et_news_send_mess_linner = (LinearLayout) findViewById(R.id.show_all_et_news_send_mess_linner);
        show_all_et_relayout = (RelativeLayout) findViewById(R.id.show_all_et_relayout);
        et_message = (EditText) findViewById(R.id.show_all_et_news_message);
        bt_send = (Button) findViewById(R.id.show_all_bt_news_message_send);
        mRefreshLayout.setDelegate(this);
        mRv_view.setLayoutManager(new GridLayoutManager(this, 1));
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.pull_refresh_bg);//背景色
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(mRv_view);
    }

    @Override
    public void bindData() {
        super.bindData();
        commentUserShowAdapter = new CommentUserShowAdapter(CommentUserShowAllActivity.this, mRv_view, et_message, show_all_et_news_send_mess_linner, bt_send);
        getShowAllComment(false, pagernos);
    }

    @Override
    public void bindListener() {
        super.bindListener();
        show_all_et_relayout.setOnClickListener(this);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉
        getShowAllComment(false, pagernos);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉
        getShowAllComment(true, pagernos);
        return true;
    }

    public void getShowAllComment(boolean isloadmore, int pagerno) {

        HttpClient.getInstance().getShowAllComments(168772, commentId, 1, 20, new NetworkSubscriber<CommentBean>(this) {
            @Override
            public void onNext(CommentBean data) {
                super.onNext(data);
                if (data.isOKResult()) {
                    if (isloadmore) {
                        commentUserShowAdapter.AddAll(data.getData().get(0).getList(), data.getData());
                        pagernos++;
                    } else {
                        commentUserShowAdapter.setData(data.getData().get(0).getList(), data.getData());
                        pagernos = 2;
                    }
                    mRv_view.setAdapter(commentUserShowAdapter);
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    ToastUtils.showToast(data.mes);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        //底部评论
        commentUserShowAdapter.setCommentForActivity();
    }
}
