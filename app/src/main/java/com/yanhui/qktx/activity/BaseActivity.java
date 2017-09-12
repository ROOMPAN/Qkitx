package com.yanhui.qktx.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.message.PushAgent;
import com.yanhui.qktx.R;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.business.FindviewInterFace;
import com.yanhui.qktx.business.LoadingInterface;

import org.greenrobot.eventbus.EventBus;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import rx.Subscription;


/**
 * Created by liupanpan on 2017/7/31.
 * 所有 activity 的 主 activity
 */

public class BaseActivity extends SwipeBackActivity implements FindviewInterFace, LoadingInterface {
    protected Subscription subscription;
    private TextView title_text;
    private RelativeLayout mRoomView;
    private FrameLayout contrans;
    private RelativeLayout base_top_bar;
    public ImageView img_topbar_back_left, img_topbar_right;
    private RelativeLayout mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();//获取 APP 启动 友盟
        mRoomView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        title_text = mRoomView.findViewById(R.id.activity_base_title_text);
        contrans = mRoomView.findViewById(R.id.activity_base_contrans);
        base_top_bar = mRoomView.findViewById(R.id.base_topbar);
        img_topbar_back_left = mRoomView.findViewById(R.id.activity_base_topbar_left_back_img);
        img_topbar_right = mRoomView.findViewById(R.id.activity_base_topbar_right_img);
        mLoadingView = mRoomView.findViewById(R.id.common_loading_view);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, contrans);
        super.setContentView(mRoomView);
        findViews();
        bindListener();
        bindData();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁的时候释放 避免内存溢出
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void findViews() {

    }

    @Override
    public void bindListener() {
        img_topbar_back_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void bindData() {

    }

    @Override
    public void reLoad() {

    }

    @Override
    public void showLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoadingView() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView(Throwable e) {
        mLoadingView.setVisibility(View.GONE);

    }

    @Override
    public void loadSuccess() {
        if (mLoadingView.getVisibility() != View.GONE) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置 title 文字
     *
     * @param titleText
     */
    public void setTitleText(String titleText) {
        if (!TextUtils.isEmpty(titleText)) {
            title_text.setText(titleText);
        }
    }

    /**
     * 设置 title 文字颜色
     */
    public void setTitleTextColor(int resid_color) {
        if (resid_color != 0) {
            title_text.setTextColor(getResources().getColor(resid_color));
        }

    }

    /**
     * 设置标题 隐藏
     */
    public void setGoneTopBar() {
        if (base_top_bar != null) {
            base_top_bar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏的颜色
     */
    public void setTopBarColor(int resid) {
        if (resid != 0) {
            base_top_bar.setBackgroundResource(resid);
        }
    }

    /**
     * 设置返回按钮图片
     */
    public void setBackLeft(int img_resid) {
        if (img_resid != 0) {
            img_topbar_back_left.setImageResource(img_resid);
        }
    }

    public void setRightImg(int img_resid) {
        if (img_resid != 0) {
            img_topbar_right.setImageResource(img_resid);
        }
    }

    public void setGoneRight() {
        if (img_topbar_right != null) {
            img_topbar_right.setVisibility(View.GONE);
        }
    }


    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    public void onEventMainThread(BusEvent event) {
    }

}
