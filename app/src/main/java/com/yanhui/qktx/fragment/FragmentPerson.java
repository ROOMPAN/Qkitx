package com.yanhui.qktx.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.FavoritesActivity;
import com.yanhui.qktx.activity.HistoryRecordActivity;
import com.yanhui.qktx.activity.LoginActivity;
import com.yanhui.qktx.activity.SettingActivity;
import com.yanhui.qktx.activity.UserInforActivity;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.adapter.TestNomalAdapter;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.business.BusinessManager;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.models.PersonBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.umlogin.UMLoginThird;
import com.yanhui.qktx.utils.SharedPreferencesMgr;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.functions.Action1;

import static com.yanhui.qktx.constants.Constant.ABOUT;
import static com.yanhui.qktx.constants.Constant.GONE_BUTTOM;
import static com.yanhui.qktx.constants.Constant.ISNEWBIETASK;
import static com.yanhui.qktx.constants.Constant.PROTOCOL;
import static com.yanhui.qktx.constants.Constant.SHOW_CLEAR;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_CLEAR;
import static com.yanhui.qktx.constants.Constant.USER_LOGIN_REQUEST_CODE;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/8/14.
 */

public class FragmentPerson extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
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
    private TextView tv_user_info_mess_identification, tv_mission_identification;//小圆点
    private PersonBean.DataBeanX.MenuBean menubean;


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
        //小红圆点
        tv_user_info_mess_identification = mRoomView.findViewById(R.id.fragment_use_info_mess_identification);
        tv_mission_identification = include_mission_system.findViewById(R.id.fragment_preson_include_identification);
        bindReshLayout();

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
        //vp_person_img.setOnItemClickListener(this);
        user_linner_gold.setOnClickListener(this);
        linner_user_money.setOnClickListener(this);
        include_invitation.setOnClickListener(this);
        include_my_comment.setOnClickListener(this);
        include_newbie_task.setOnClickListener(this);
        include_invitation_code.setOnClickListener(this);
        include_income_statement.setOnClickListener(this);
        include_common_problem.setOnClickListener(this);
        include_mission_system.setOnClickListener(this);
        include_withdrawals.setOnClickListener(this);
        setRxBindClickView();

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

    //登录返回请求接口
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USER_LOGIN_REQUEST_CODE) {
            ToastUtils.showToast("" + requestCode);
            getPointData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        getPointData();
    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus(FragmentPerson.this);
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterEventBus(FragmentPerson.this);
    }
    //轮播图的点击事件
//    @Override
//    public void onItemClick(int position) {
//        ToastUtils.showToast(position + "");
//
//    }

    /**
     * evevbus 提示刷新用户信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshProintEvent(BusEvent event) {
        switch (event.what) {
            case EventConstants.EVEN_PROINT_REFRESH:
                getPointData();
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.include_invitation:
                //邀请好友收徒
                if (!StringUtils.isEmpty(menubean.getInviteApprentice())) {
                    getActivity().startActivityForResult(new Intent(mActivity, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, menubean.getInviteApprentice()).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM), Constant.USER_REQUST_CODE);
                }
                break;
            case R.id.include_mission_system:
                //任务系统
                //Log.e("url_新手任务", "" + getMeunUrl(7));
                if (!StringUtils.isEmpty(menubean.getActivity())) {
                    getActivity().startActivityForResult(new Intent(mActivity, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, menubean.getActivity()).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM), Constant.USER_REQUST_CODE);
                }
                break;
            case R.id.include_invitation_code:
                //输入邀请码
                // Log.e("url_邀请码", "" + getMeunUrl(1));
                if (!StringUtils.isEmpty(menubean.getInviteCode())) {
                    getActivity().startActivityForResult(new Intent(mActivity, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, menubean.getInviteCode()).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM), Constant.USER_REQUST_CODE);
                }
                break;
            case R.id.include_income_statement:
                //收入明细
                //Log.e("url_收入明细", "" + getMeunUrl(6));
                if (!StringUtils.isEmpty(menubean.getIncome())) {
                    getActivity().startActivityForResult(new Intent(mActivity, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, menubean.getIncome()).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM), Constant.USER_REQUST_CODE);
                }
                break;
            case R.id.include_common_problem:
                // Log.e("url_问题中心", "" + getMeunUrl(2));
                if (!StringUtils.isEmpty(menubean.getHelp())) {
                    getActivity().startActivityForResult(new Intent(mActivity, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, menubean.getHelp()).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM), Constant.USER_REQUST_CODE);
                }
                break;
            case R.id.include_withdrawals:
                //兑换体现
                if (!StringUtils.isEmpty(menubean.getWithdraw())) {
                    getActivity().startActivityForResult(new Intent(mActivity, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, menubean.getWithdraw()).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM), Constant.USER_REQUST_CODE);
                }
                break;
            case R.id.include_my_comment:
                //我的评论
                if (!StringUtils.isEmpty(menubean.getComment())) {
                    getActivity().startActivityForResult(new Intent(mActivity, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, menubean.getComment()).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM).putExtra(SHOW_WEB_VIEW_CLEAR, SHOW_CLEAR).putExtra(ISNEWBIETASK, 2), Constant.USER_REQUST_CODE);
                }
                break;
            case R.id.fragment_person_linner_gold:
                if (!StringUtils.isEmpty(menubean.getIncome())) {
                    getActivity().startActivityForResult(new Intent(mActivity, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, menubean.getIncome()).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM), Constant.USER_REQUST_CODE);
                }
                break;
            case R.id.fragment_person_linner_money:
                if (!StringUtils.isEmpty(menubean.getIncome())) {
                    getActivity().startActivityForResult(new Intent(mActivity, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, menubean.getIncome()).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM), Constant.USER_REQUST_CODE);
                }
                break;
        }
    }

    public void getPointData() {
        if (BusinessManager.getInstance().isLogin()) {
            HttpClient.getInstance().getPoint(new NetworkSubscriber<PersonBean>(this) {
                @Override
                public void onNext(PersonBean data) {
                    super.onNext(data);
                    if (data.isOKResult() && data.getData() != null) {
                        menubean = data.getData().getMenu();
                        setBianImage(data.getData().getBanner());
//                      Log.e("userdata", data.getData().getUser().toString());
                        setUserData(data.getData().getUser());
                        setPointData(data.getData().getData());
                        mRefreshLayout.endRefreshing();
                    } else if (data.isNotResult()) {
                        startActivityForResult(new Intent(mActivity, LoginActivity.class), USER_LOGIN_REQUEST_CODE);
                        mRefreshLayout.endRefreshing();
                    }

                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    mRefreshLayout.endRefreshing();
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
        if (data.getMess() != 0) {
            tv_user_info_mess_identification.setVisibility(View.VISIBLE);
        } else {
            tv_user_info_mess_identification.setVisibility(View.INVISIBLE);
        }
        if (data.getTask() != 0) {
            tv_mission_identification.setVisibility(View.VISIBLE);
        } else {
            tv_mission_identification.setVisibility(View.GONE);
        }
        SharedPreferencesMgr.setInt("info", data.getInfo());
        EventBus.getDefault().post(new BusEvent(EventConstants.EVEN_ISPUSH_IDENT_INFO));//切换底部小圆点
    }

    /**
     * 设置用户数据参数显示
     */
    private void setUserData(PersonBean.DataBeanX.UserBean user) {
        SharedPreferencesMgr.setString("username", user.getName());
        SharedPreferencesMgr.setString("headurl", user.getHeadUrl());
        SharedPreferencesMgr.setInt("age", user.getAge());
        tv_user_name.setText(user.getName());
        if (!StringUtils.isEmpty(user.getHeadUrl())) {
            ImageLoad.intoNullPlace(mActivity, user.getHeadUrl(), img_user_photo);
        }
        if (!StringUtils.isEmpty(user.getOpenId())) {
            include_invitation_bandWx.setVisibility(View.GONE);
        } else {
            include_invitation_bandWx.setVisibility(View.VISIBLE);
        }
        if (user.getParentUserId() != -99) {
            include_invitation_code.setVisibility(View.GONE);
        } else {
            include_invitation_code.setVisibility(View.VISIBLE);
            //个人中心 弹出轮播 popwinds
//            UserInforEventPopWindow mPopwindow = new UserInforEventPopWindow(getActivity());
//            mPopwindow.show(new View(getActivity()));
        }

    }

    private void setBianImage(List<PersonBean.DataBeanX.BannerBean> bnnerlist) {
        vp_person_img.setAdapter(new TestNomalAdapter(mActivity, bnnerlist));
        vp_person_img.setHintView(new ColorPointHintView(mActivity, Color.RED, Color.WHITE));
    }

    public void setRxBindClickView() {
        //收藏
        RxView.clicks(include_collection)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startActivity(new Intent(mActivity, FavoritesActivity.class));
                    }
                });
        //历史记录
        RxView.clicks(include_historical_record)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startActivity(new Intent(mActivity, HistoryRecordActivity.class));
                    }
                });
        //绑定微信
        RxView.clicks(include_invitation_bandWx)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        new UMLoginThird(mActivity, mRefreshLayout);
                    }
                });
        //设置
        RxView.clicks(user_setting)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (!StringUtils.isEmpty(menubean.getAbout()) && !StringUtils.isEmpty(menubean.getProtocol())) {
                            getActivity().startActivityForResult(new Intent(mActivity, SettingActivity.class).putExtra(PROTOCOL, menubean.getProtocol()).putExtra(ABOUT, menubean.getAbout()), Constant.USER_REQUST_CODE);
                        }
                    }
                });
        //消息
        RxView.clicks(user_message)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (!StringUtils.isEmpty(menubean.getMessage())) {
                            getActivity().startActivityForResult(new Intent(mActivity, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, menubean.getMessage()).putExtra(SHOW_WEB_VIEW_CLEAR, SHOW_CLEAR).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM).putExtra(ISNEWBIETASK, 1), Constant.USER_REQUST_CODE);
                        }
                    }
                });
        //修改个人消息
        RxView.clicks(bt_user_setting)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        getActivity().startActivityForResult(new Intent(mActivity, UserInforActivity.class), Constant.USER_REQUST_CODE);
                    }
                });
    }

}
