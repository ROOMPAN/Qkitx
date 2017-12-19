package com.yanhui.qktx.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.umeng.analytics.MobclickAgent;
import com.yanhui.qktx.MyApplication;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.MainFragmentPageAdapter;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.business.BusinessManager;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.fragment.BaseFragment;
import com.yanhui.qktx.fragment.FragmentHome;
import com.yanhui.qktx.fragment.FragmentPerson;
import com.yanhui.qktx.fragment.FragmentVideo;
import com.yanhui.qktx.models.event.TabRefreshCompletedEvent;
import com.yanhui.qktx.models.event.TabRefreshEvent;
import com.yanhui.qktx.utils.SharedPreferencesMgr;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.UIUtils;
import com.yanhui.qktx.view.widgets.MainViewPager;
import com.yanhui.statusbar_lib.flyn.Eyes;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

import static com.yanhui.qktx.constants.Constant.OPEN_WALLET_MONEY;
import static com.yanhui.qktx.constants.Constant.USER_LOGIN_REQUEST_CODE;

/**
 * 主页面的入口 mainActivity
 */

public class MainActivity extends BaseActivity {

    private BottomBarLayout mBottomBarLayout;
    private ImageView iv_float_bt;
    private TextView bottom_user_identification;
    private MainViewPager viewPager;
    private ArrayList<BaseFragment> fragmentArrayList;
    private FragmentHome fragment_home;
    private FragmentPerson fragment_person;
    private FragmentVideo fragment_video;
    private BottomBarItem bottomBarItem;
    private TranslateAnimation alphaAnimation;
    private int[] mStatusColors = new int[]{
            R.color.status_bar,
            R.color.status_bar,
            R.color.user_person_top_bar_bg,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPushActivity(MainActivity.this);
        setContentView(R.layout.activity_main);
        //统计场景设置
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        setSwipeBackEnable(false);//设置 activity 侧滑关闭
        setGoneTopBar();
        setStatusBarColor(0);
        NotificationManager();//通知权限.

    }

    @Override
    public void findViews() {
        super.findViews();
        viewPager = (MainViewPager) findViewById(R.id.activity_main_viewpager);
        mBottomBarLayout = (BottomBarLayout) findViewById(R.id.main_bottom_bar);
        bottomBarItem = (BottomBarItem) findViewById(R.id.main_bottom_user);
        iv_float_bt = (ImageView) findViewById(R.id.activity_main_float_image);
        bottom_user_identification = (TextView) findViewById(R.id.activity_main_bottom_user_identification);
        fragmentArrayList = new ArrayList<>(3);
        fragment_home = new FragmentHome();
        fragment_video = new FragmentVideo();
        fragment_person = new FragmentPerson();
        fragmentArrayList.add(fragment_home);
        fragmentArrayList.add(fragment_video);
        fragmentArrayList.add(fragment_person);
        viewPager.setAdapter(new MainFragmentPageAdapter(getSupportFragmentManager(), fragmentArrayList));
        viewPager.setOffscreenPageLimit(fragmentArrayList.size());
        mBottomBarLayout.setViewPager(viewPager);
    }

    @Override
    public void bindListener() {
        super.bindListener();
        //浮动按钮点击事件
        RxView.clicks(iv_float_bt)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startActivity(new Intent(MainActivity.this, OpenWalletPopActivity.class));
                    }
                });
        RxView.clicks(bottomBarItem)
                .throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
//                alphaAnimation.cancel();//浮动按钮晃动
//                iv_float_bt.setVisibility(View.GONE);//浮动钮显示隐藏
                if (!BusinessManager.getInstance().isLogin()) {
                    startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), USER_LOGIN_REQUEST_CODE);
                } else {
                    setStatusBarColor(2);
                    viewPager.getAdapter().notifyDataSetChanged();
                    mBottomBarLayout.setCurrentItem(2);
                    EventBus.getDefault().post(new BusEvent(EventConstants.EVEN_PROINT_REFRESH));//刷新用户信息
                }
            }
        });
        //设置条目点击的监听
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int position) {
                setStatusBarColor(position);//设置状态栏颜色
                //JCVideoPlayer.releaseAllVideos();//底部页签切换或者是下拉刷新，释放资源
//                alphaAnimation.cancel();//浮动按钮晃动
//                iv_float_bt.setVisibility(View.VISIBLE);
                if (position == 0) {
                    //如果点击的是首页
                    if (mBottomBarLayout.getCurrentItem() == position) {
                        //如果当前页码和点击的页码一致,进行下拉刷新
                        String channelCode = "";
                        if (position == 0 && fragmentArrayList.size() != 0) {
                            channelCode = ((FragmentHome) fragmentArrayList.get(0)).getCurrentChannelCode();//获取到首页当前显示的fragment的频道
//                            ToastUtils.showToast(channelCode);
                        }
                        if (!StringUtils.isEmpty(channelCode)) {
                            postTabRefreshEvent(bottomBarItem, position, channelCode, 0);//发送下拉刷新的事件
                        }
                    }
                    return;
                } else if (position == 1) {
                    if (mBottomBarLayout.getCurrentItem() == position) {
                        //如果当前页码和点击的页码一致,进行下拉刷新
                        String channelCode = "";
                        if (position == 1 && fragmentArrayList.size() != 0) {
                            channelCode = ((FragmentVideo) fragmentArrayList.get(1)).getCurrentVideoChannelCode();//获取到首页当前显示的fragment的频道
//                            ToastUtils.showToast(channelCode);
                        }
                        if (!StringUtils.isEmpty(channelCode)) {
                            postTabRefreshEvent(bottomBarItem, position, channelCode, 1);//发送下拉刷新的事件
                        }
                    }
                    return;
                }
                //如果点击了其他条目
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                bottomItem.setIconSelectedResourceId(R.drawable.icon_bottom_home_nomail);//更换为原来的图标
                bottomItem.getTextView().setText("首页");
                cancelTabLoading(bottomItem);//停止旋转动画

            }
        });
    }

    @Override
    public void bindData() {
        super.bindData();
        //打开更新页面
        if (!BusinessManager.getInstance().isLogin()) {
            iv_float_bt.setVisibility(View.VISIBLE);
            setImageAnmation();//悬浮 image 晃动动画
        } else {
            iv_float_bt.setVisibility(View.GONE);
        }
        Intent intent = getIntent();
        if (intent.getIntExtra(Constant.AD_IS_UPTADA, 0) == 1) {
            Intent appUpdateIntent = new Intent(MainActivity.this, AppUpdateActivity.class);
            appUpdateIntent.putExtras(intent);
            startActivity(appUpdateIntent);
        }
    }

    private void setStatusBarColor(int position) {
        Eyes.setStatusBarColor(MainActivity.this, UIUtils.getColor(mStatusColors[position]));

    }

    private void postTabRefreshEvent(BottomBarItem bottomBarItem, int position, String channelCode, int currentItem) {
        TabRefreshEvent event = new TabRefreshEvent();
        event.setChannelCode(channelCode);
        event.setBottomBarItem(bottomBarItem);
        if (currentItem == 0) {
            event.setHomeTab(position == 0);
            EventBus.getDefault().post(event);//发送下拉刷新事件
        } else if (currentItem == 1) {
            event.setHomeTab(false);
            EventBus.getDefault().post(event);//发送下拉刷新事件
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshCompletedEvent(TabRefreshCompletedEvent event) {
        switch (event.what) {
            case Constant.isHomeEndResh:
                //首页接收到刷新完成的事件，取消旋转动画，更换底部首页页签图标
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                cancelTabLoading(bottomItem);//停止旋转动画
                bottomItem.setIconSelectedResourceId(R.drawable.icon_bottom_select_refresh);//更换成首页原来图标
                bottomItem.getTextView().setText("首页");
                bottomItem.setStatus(true);//刷新图标
                break;
            case Constant.isVideoEndResh:
                //视频接收到刷新完成的事件，取消旋转动画，更换底部首页页签图标
                BottomBarItem bottomItemvideo = mBottomBarLayout.getBottomItem(1);
                cancelTabLoading(bottomItemvideo);//停止旋转动画
                bottomItemvideo.setIconSelectedResourceId(R.drawable.icon_bottom_select_refresh);//更换成首页原来图标
                bottomItemvideo.getTextView().setText("视频");
                bottomItemvideo.setStatus(true);//刷新图标
                break;
        }

    }

    /**
     * 停止首页页签的旋转动画
     */
    private void cancelTabLoading(BottomBarItem bottomItem) {
        Animation animation = bottomItem.getImageView().getAnimation();
        if (animation != null) {
            animation.cancel();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeTabCurrent(BusEvent busEvent) {
        switch (busEvent.what) {
            case EventConstants.EVENT_SWITCH_TO_HOME:
                setStatusBarColor(0);
                viewPager.getAdapter().notifyDataSetChanged();
                mBottomBarLayout.setCurrentItem(0);
                //浮动按钮隐藏
                //第一次登陆,打开红包
                if (busEvent != null && busEvent.HbMoney != null) {
                    if (busEvent.HbMoney.doubleValue() > 0) {
                        startActivity(new Intent(MainActivity.this, OpenWalletPopActivity.class).putExtra(OPEN_WALLET_MONEY, busEvent.HbMoney.toString()));
                    }
                }
                break;
            case EventConstants.EVEN_ISPUSH_IDENT_INFO:
                //底部的"我"页面小圆点
                if (SharedPreferencesMgr.getInt("info", 0) != 0) {
                    bottom_user_identification.setVisibility(View.VISIBLE);
                } else {
                    bottom_user_identification.setVisibility(View.INVISIBLE);
                }
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerEventBus(MainActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterEventBus(MainActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟统计
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BusinessManager.getInstance().isLogin()) {
            iv_float_bt.clearAnimation();//清除动画防止无法隐藏
            iv_float_bt.setVisibility(View.GONE);
        } else {
            iv_float_bt.setVisibility(View.VISIBLE);
        }
        //友盟统计
        MobclickAgent.onResume(this);
    }

    private long firstTime = System.currentTimeMillis();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次返回键退出应用", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;// 更新firstTime
                return true;
            } else {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showDialog(boolean isOpen) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("开启通知权限?").setCancelable(false).setNeutralButton("暂不开启", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }).setNegativeButton("立即开启", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", MyApplication.getContext().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        if (!isOpen) {
            alert.show();
        }
    }

    private void NotificationManager() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(MyApplication.getContext());
        boolean isOpened = manager.areNotificationsEnabled();
        // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
        showDialog(isOpened);
    }

    private void setImageAnmation() {
        alphaAnimation = new TranslateAnimation(10f, 40f, 0, 0);
        alphaAnimation.setDuration(100);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        iv_float_bt.setAnimation(alphaAnimation);
        alphaAnimation.start();
        iv_float_bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                alphaAnimation.cancel();
            }
        }, 3000);

    }

    /**
     * 用户中心页面 打开某个模块关闭回调
     * 刷新个人中心
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.USER_REQUST_CODE) {
            EventBus.getDefault().post(new BusEvent(EventConstants.EVEN_PROINT_REFRESH));//刷新用户信息
        }
    }
}



