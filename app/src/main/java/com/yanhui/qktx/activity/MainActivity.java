package com.yanhui.qktx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.umeng.analytics.MobclickAgent;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.MainFragmentPageAdapter;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.business.BusinessManager;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.fragment.BaseFragment;
import com.yanhui.qktx.fragment.FragmentHome;
import com.yanhui.qktx.fragment.FragmentPerson;
import com.yanhui.qktx.fragment.FragmentVideo;
import com.yanhui.qktx.models.event.TabRefreshCompletedEvent;
import com.yanhui.qktx.models.event.TabRefreshEvent;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;
import com.yanhui.qktx.view.widgets.MainViewPager;
import com.yanhui.statusbar_lib.flyn.Eyes;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * 主页面的入口 mainActivity
 */

public class MainActivity extends BaseActivity {


    private BottomBarLayout mBottomBarLayout;
    private MainViewPager viewPager;
    private ArrayList<BaseFragment> fragmentArrayList;
    private FragmentHome fragment_home;
    private FragmentPerson fragment_person;
    private FragmentVideo fragment_video;
    private BottomBarItem bottomBarItem;
    private int[] mStatusColors = new int[]{
            R.color.status_color_red,
            R.color.status_color_grey,
            R.color.status_color_grey,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //统计场景设置
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        setSwipeBackEnable(false);//设置 activity 侧滑关闭
        setGoneTopBar();
        setStatusBarColor(0);
    }

    @Override
    public void findViews() {
        super.findViews();
        viewPager = (MainViewPager) findViewById(R.id.activity_main_viewpager);
        mBottomBarLayout = (BottomBarLayout) findViewById(R.id.main_bottom_bar);
        bottomBarItem = (BottomBarItem) findViewById(R.id.main_bottom_user);
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
        bottomBarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!BusinessManager.getInstance().isLogin()) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    setStatusBarColor(2);
                    viewPager.getAdapter().notifyDataSetChanged();
                    mBottomBarLayout.setCurrentItem(2);
                }
            }
        });
        //设置条目点击的监听
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int position) {
                setStatusBarColor(position);//设置状态栏颜色
                //JCVideoPlayer.releaseAllVideos();//底部页签切换或者是下拉刷新，释放资源
                if (position == 0) {
                    //如果点击的是首页
                    if (mBottomBarLayout.getCurrentItem() == position) {
                        //如果当前页码和点击的页码一致,进行下拉刷新
                        String channelCode = "";
                        if (position == 0) {
                            channelCode = ((FragmentHome) fragmentArrayList.get(0)).getCurrentChannelCode();//获取到首页当前显示的fragment的频道
                            ToastUtils.showToast(channelCode);
                        }
                        postTabRefreshEvent(bottomBarItem, position, channelCode);//发送下拉刷新的事件
                    }
                    return;
                }
                //如果点击了其他条目
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                bottomItem.setIconSelectedResourceId(R.drawable.tab_home_selected);//更换为原来的图标
                cancelTabLoading(bottomItem);//停止旋转动画

            }
        });
    }

    private void setStatusBarColor(int position) {
        if (position == 2) {
            //如果是我的页面，状态栏设置为透明状态栏
            Eyes.translucentStatusBar(MainActivity.this, true);
        } else {
            Eyes.setStatusBarColor(MainActivity.this, UIUtils.getColor(mStatusColors[position]));
        }
    }

    private void postTabRefreshEvent(BottomBarItem bottomBarItem, int position, String channelCode) {
        TabRefreshEvent event = new TabRefreshEvent();
        event.setChannelCode(channelCode);
        event.setBottomBarItem(bottomBarItem);
        event.setHomeTab(position == 0);
        EventBus.getDefault().post(event);//发送下拉刷新事件
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshCompletedEvent(TabRefreshCompletedEvent event) {
        //接收到刷新完成的事件，取消旋转动画，更换底部首页页签图标
        BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);

        cancelTabLoading(bottomItem);//停止旋转动画

        bottomItem.setIconSelectedResourceId(R.drawable.tab_home_selected);//更换成首页原来图标
        bottomItem.setStatus(true);//刷新图标
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
//        if (JCVideoPlayer.backPress()) {
//            return;
//        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // JCVideoPlayer.releaseAllVideos();
        //友盟统计
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计
        MobclickAgent.onResume(this);
    }

}



