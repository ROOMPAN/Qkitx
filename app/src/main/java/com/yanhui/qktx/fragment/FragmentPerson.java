package com.yanhui.qktx.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.LoginActivity;
import com.yanhui.qktx.adapter.TestNomalAdapter;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;

/**
 * Created by liupanpan on 2017/8/14.
 */

public class FragmentPerson extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, OnItemClickListener, View.OnClickListener {
    private BGARefreshLayout mRefreshLayout;
    private RollPagerView vp_person_img;
    private LinearLayout fragment_person_vp_linner;
    private RelativeLayout fragment_person_linner;
    private View include_invitation, include_newbie_task, include_invitation_code, include_invitation_envelopes, include_mission_system;
    private View include_common_problem, include_withdrawals, include_income_statement, include_collection, include_historical_record;
    private TextView tv_invitation_title, tv_invitation_context; //收徒 标题文字,内容文字
    private TextView tv_newbie_task_title, tv_newbie_task_context; //新手任务 标题,  内容
    private TextView tv_invitation_code_title, tv_invitation_code_context; //邀请码 标题,  内容
    private TextView tv_invitation_envelope_title, tv_invitation_envelope_context; //红包 标题,  内容
    private TextView tv_mission_system_title, tv_mission_system_context; //任务系统 标题,  内容
    private TextView tv_common_problem_title, tv_common_problem_context; //常见问题 标题,  内容
    private TextView tv_withdrawals_title, tv_withdrawals_context; //兑现 标题,  内容
    private TextView tv_income_statement_title, tv_income_statement_context; //收入明细标题,  内容
    private TextView tv_collection_title, tv_collection_context; //收藏标题,  内容
    private TextView tv_historical_record_title, tv_historical_record_context; //历史记录标题,  内容


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_person;
    }

    @Override
    public void findViews() {
        super.findViews();
        mRefreshLayout = mRoomView.findViewById(R.id.fragment_person_refresh_layout);
        fragment_person_linner = mRoomView.findViewById(R.id.fragment_person_linner);
        vp_person_img = mRoomView.findViewById(R.id.fragment_person_vp);
        fragment_person_vp_linner = mRoomView.findViewById(R.id.fragment_person_vp_linner);
        //include
        include_invitation = mRoomView.findViewById(R.id.include_invitation);
        include_newbie_task = mRoomView.findViewById(R.id.include_newbie_task);
        include_invitation_code = mRoomView.findViewById(R.id.include_invitation_code);
        include_invitation_envelopes = mRoomView.findViewById(R.id.include_invitation_envelopes);
        include_mission_system = mRoomView.findViewById(R.id.include_mission_system);
        include_common_problem = mRoomView.findViewById(R.id.include_common_problem);
        include_withdrawals = mRoomView.findViewById(R.id.include_withdrawals);
        include_income_statement = mRoomView.findViewById(R.id.include_income_statement);
        include_collection = mRoomView.findViewById(R.id.include_collection);
        include_historical_record = mRoomView.findViewById(R.id.include_historical_record);
        //include 标题
        tv_invitation_title = include_invitation.findViewById(R.id.nvitation_title);//收徒 标题
        tv_invitation_context = include_invitation.findViewById(R.id.nvitation_context);//收徒 内容

        tv_newbie_task_title = include_newbie_task.findViewById(R.id.txt_person_page_title); //新手任务
        tv_newbie_task_context = include_newbie_task.findViewById(R.id.txt_person_page_title_introduce);//新手任务 内容

        tv_invitation_code_title = include_invitation_code.findViewById(R.id.txt_person_page_title); //输入邀请码
        tv_invitation_code_context = include_invitation_code.findViewById(R.id.txt_person_page_title_introduce);//内容

        tv_invitation_envelope_title = include_invitation_envelopes.findViewById(R.id.txt_person_page_title); //红包
        tv_invitation_envelope_context = include_invitation_envelopes.findViewById(R.id.txt_person_page_title_introduce);// 内容

        tv_mission_system_title = include_mission_system.findViewById(R.id.txt_person_page_title); //任务系统
        tv_mission_system_context = include_mission_system.findViewById(R.id.txt_person_page_title_introduce);// 内容

        tv_common_problem_title = include_common_problem.findViewById(R.id.txt_person_page_title); //常见问题
        tv_common_problem_context = include_common_problem.findViewById(R.id.txt_person_page_title_introduce);// 内容

        tv_withdrawals_title = include_withdrawals.findViewById(R.id.txt_person_page_title); //兑换提现
        tv_withdrawals_context = include_withdrawals.findViewById(R.id.txt_person_page_title_introduce);// 内容

        tv_income_statement_title = include_income_statement.findViewById(R.id.txt_person_page_title); //收入明细
        tv_income_statement_context = include_income_statement.findViewById(R.id.txt_person_page_title_introduce);// 内容

        tv_collection_title = include_collection.findViewById(R.id.txt_person_page_title); //收藏
        tv_collection_context = include_collection.findViewById(R.id.txt_person_page_title_introduce);// 内容

        tv_historical_record_title = include_historical_record.findViewById(R.id.txt_person_page_title); //历史记录
        tv_historical_record_context = include_historical_record.findViewById(R.id.txt_person_page_title_introduce);// 内容

        bindReshLayout();
        vp_person_img.setAdapter(new TestNomalAdapter());
        vp_person_img.setHintView(new ColorPointHintView(mActivity, Color.RED, Color.WHITE));
    }

    private void bindReshLayout() {
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mActivity, false);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.person_bg);//背景色
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void bindData() {
        super.bindData();
        tv_newbie_task_title.setText("新手任务");
        tv_newbie_task_context.setText("可轻松获得几千金币");
        tv_invitation_code_title.setText("输入邀请码");
        tv_invitation_code_context.setText("再领取0.5元红包");
        tv_invitation_envelope_title.setText("邀请红包");
        tv_invitation_envelope_context.setText("奖励1元现金红包");
        tv_mission_system_title.setText("任务系统");
        tv_mission_system_context.setText("签到领金币,开宝箱");
        tv_common_problem_title.setText("常见问题");
        tv_common_problem_context.setText("趣看天下怎么玩?");
        tv_withdrawals_title.setText("兑换提现");
        tv_withdrawals_context.setText("兑换赢100元红包");
        tv_income_statement_title.setText("收入明细");
        tv_income_statement_context.setText("查看收益,排行榜");
        tv_collection_title.setText("我的收藏");
        tv_collection_context.setText("");
        tv_historical_record_title.setText("历史记录");
        tv_historical_record_context.setText("");
    }

    @Override
    public void bindListener() {
        super.bindListener();
        vp_person_img.setOnItemClickListener(this);
        include_invitation.setOnClickListener(this);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新执行方法
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    //轮播图的点击事件
    @Override
    public void onItemClick(int position) {
        ToastUtils.showToast(position + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.include_invitation:
                startActivity(new Intent(mActivity, LoginActivity.class));
                break;
        }
    }
}
