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
import com.yanhui.qktx.activity.FavoritesActivity;
import com.yanhui.qktx.activity.HistoryRecordActivity;
import com.yanhui.qktx.activity.SettingActivity;
import com.yanhui.qktx.activity.UserInforActivity;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.adapter.TestNomalAdapter;
import com.yanhui.qktx.business.BusinessManager;
import com.yanhui.qktx.models.PersonBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.umlogin.UMLoginThird;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.yanhui.qktx.constants.Constant.GONE_BUTTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/8/14.
 */

public class FragmentPerson extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, OnItemClickListener, View.OnClickListener {
    private BGARefreshLayout mRefreshLayout;
    private RollPagerView vp_person_img;
    private LinearLayout fragment_person_vp_linner, user_setting, user_message, user_linner_gold, user, linner_user_money;
    private RelativeLayout fragment_person_linner;
    private CircleImageView img_user_photo;
    private TextView tv_user_name, bt_user_setting, tv_gold, tv_money;
    private View include_invitation, include_newbie_task, include_invitation_code, include_invitation_envelopes, include_mission_system;
    private View include_common_problem, include_withdrawals, include_income_statement, include_collection, include_historical_record, include_invitation_bandWx, include_my_comment;
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
    private TextView tv_bindwx_title, tv_bindwx_context; //绑定微信
    private TextView tv_my_comment_title;


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
        img_user_photo = mRoomView.findViewById(R.id.fragment_person_user_logo);
        user_message = mRoomView.findViewById(R.id.user_message);
        user_setting = mRoomView.findViewById(R.id.user_setting);
        tv_user_name = mRoomView.findViewById(R.id.fragment_person_user_name);
        bt_user_setting = mRoomView.findViewById(R.id.fragment_person_setting_user);
        tv_gold = mRoomView.findViewById(R.id.fragment_person_gold);
        tv_money = mRoomView.findViewById(R.id.fragment_person_money);
        linner_user_money = mRoomView.findViewById(R.id.fragment_person_linner_money);
        user_linner_gold = mRoomView.findViewById(R.id.fragment_person_linner_gold);
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
        include_invitation_bandWx = mRoomView.findViewById(R.id.include_invitation_bandWx);
        include_my_comment = mRoomView.findViewById(R.id.include_my_comment);
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
        tv_bindwx_title = include_invitation_bandWx.findViewById(R.id.txt_person_page_title);
        tv_bindwx_context = include_invitation_bandWx.findViewById(R.id.txt_person_page_title_introduce);// 内容

        tv_my_comment_title = include_my_comment.findViewById(R.id.txt_person_page_title);
        bindReshLayout();
        vp_person_img.setAdapter(new TestNomalAdapter());
        vp_person_img.setHintView(new ColorPointHintView(mActivity, Color.RED, Color.WHITE));
        getPointData();
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
        tv_bindwx_title.setText("绑定微信");
        tv_bindwx_context.setText("绑定微信送10元红包");
        tv_historical_record_context.setText("");
        tv_my_comment_title.setText("我的评论");


    }

    @Override
    public void bindListener() {
        super.bindListener();
        vp_person_img.setOnItemClickListener(this);
        include_invitation.setOnClickListener(this);
        user_setting.setOnClickListener(this);
        user_message.setOnClickListener(this);
        bt_user_setting.setOnClickListener(this);
        include_collection.setOnClickListener(this);
        include_invitation_bandWx.setOnClickListener(this);
        include_historical_record.setOnClickListener(this);
        include_my_comment.setOnClickListener(this);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getPointData();
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
        getPointData();
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
                //邀请好友
                break;
            case R.id.user_message:
                //消息
                break;
            case R.id.user_setting:
                startActivity(new Intent(mActivity, SettingActivity.class));
                break;
            case R.id.fragment_person_setting_user:
                startActivity(new Intent(mActivity, UserInforActivity.class));
                break;
            case R.id.include_collection:
                //收藏
                startActivity(new Intent(mActivity, FavoritesActivity.class));
                break;
            case R.id.include_invitation_bandWx:
                new UMLoginThird(mActivity);
                break;
            case R.id.include_historical_record:
                startActivity(new Intent(mActivity, HistoryRecordActivity.class));
                break;
            case R.id.include_my_comment:
                startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, "http://jqvv.esep.org.cn/detail/2017/09/27/2717400.html?content_id=2717400&key=c46fq4cwqviOevVpJxBrYCg638TIWcRYp3vpYBtAStb3fjLAGvKbHjIM4czFe0vzy7bN6avnzDPS-Tgp0YSXuZVkyku-EKUppsl06KKT4zuj3_efJqaWNYcL-RBbRY3fsVc5iOgSDfs9wi60&pv_id=&cid=3&fr=1&i=1972330022").putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM));
                break;
        }
    }

    public void getPointData() {
        if (BusinessManager.getInstance().isLogin()) {
            HttpClient.getInstance().getPoint(new NetworkSubscriber<PersonBean>(this) {
                @Override
                public void onNext(PersonBean data) {
                    super.onNext(data);
                    if (data.isOKResult()) {
//                    Log.e("userdata", data.getData().getUser().toString());
                        setUserData(data.getData().getUser());
                        setPointData(data.getData().getData());
                        mRefreshLayout.endRefreshing();
                    } else {
                        mRefreshLayout.endRefreshing();
                    }
                }
            });
        }
    }

    /**
     * 设置用户的金币和人民币
     *
     * @param data
     */
    private void setPointData(PersonBean.DataBeanX.DataBean data) {
        tv_gold.setText(data.getPoint() + "");
        tv_money.setText(data.getMoney() + "");
    }

    /**
     * 设置用户数据参数显示
     */
    private void setUserData(PersonBean.DataBeanX.UserBean user) {
        tv_user_name.setText(user.getName());
        ImageLoad.into(mActivity, user.getHeadUrl(), img_user_photo);
        if (!StringUtils.isEmpty(user.getOpenId())) {
            include_invitation_bandWx.setVisibility(View.GONE);
        }

    }

}
