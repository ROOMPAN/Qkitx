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

import com.yanhui.qktx.R;
import com.yanhui.qktx.business.FindviewInterFace;
import com.yanhui.qktx.business.LoadingInterface;

import org.greenrobot.eventbus.EventBus;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import rx.Subscription;


/**
 * Created by liupanpan on 2017/7/31.
 * 所有 activity 的 主 activity
 */

public class BaseActivity extends SwipeBackActivity implements FindviewInterFace, LoadingInterface, View.OnClickListener {
    protected Subscription subscription;
    private TextView title_text;
    private RelativeLayout mRoomView;
    private FrameLayout contrans;
    private RelativeLayout base_top_bar;
    public ImageView img_topbar_back_left, img_topbar_right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoomView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        title_text = mRoomView.findViewById(R.id.activity_base_title_text);
        contrans = mRoomView.findViewById(R.id.activity_base_contrans);
        base_top_bar = mRoomView.findViewById(R.id.base_topbar);
        img_topbar_back_left = mRoomView.findViewById(R.id.activity_base_topbar_left_back_img);
        img_topbar_right = mRoomView.findViewById(R.id.activity_base_topbar_right_img);
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
        //销毁的时候从集合中移除
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void findViews() {

    }

    @Override
    public void bindListener() {
        img_topbar_back_left.setOnClickListener(this);
    }

    @Override
    public void bindData() {

    }

    @Override
    public void reLoad() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void showErrorView(Throwable e) {

    }

    @Override
    public void loadSuccess() {

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_base_topbar_left_back_img:
                finish();
                break;
        }
    }
}
