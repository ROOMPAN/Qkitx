package com.yanhui.qktx.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yanhui.qktx.R;


/**
 * Created by liupanpan on 2017/10/26.
 * 首页点击红包跳出页面
 */

public class OpenWalletPopActivity extends BasePopupActivity implements View.OnClickListener {
    private ImageButton iv_bt_close;
    private ImageView iv_bt_open;
    private FrameLayout flContainer;
    private AnimatorSet mFrontAnimator;
    private AnimatorSet mBackAnimator;
    private LinearLayout front_linner, back_linner;
    private Button bt_wallet_money_close;
    private boolean isShowFront = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_open_wallet);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

    @Override
    public void findViews() {
        super.findViews();
        iv_bt_close = findViewById(R.id.iv_bt_open_wallet_close);
        iv_bt_open = findViewById(R.id.acctivity_pop_wallet_image_open_bt);
        flContainer = findViewById(R.id.activity_pop_open_wallet_framelayout);
        front_linner = findViewById(R.id.view_pop_wallet_front);
        bt_wallet_money_close = findViewById(R.id.activity_activity_pop_wallet_bt_close);
        back_linner = findViewById(R.id.view_pop_wallet_back_liner);
        initAnimator();
        setAnimatorListener();
        setCameraDistance();
    }

    /**
     * 1.初始化动画
     */
    private void initAnimator() {
        mFrontAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.anim_in);
        mBackAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.anim_out);
    }

    /**
     * 2.设置视角间距，防止旋转时超出边界区域
     */

    private void setCameraDistance() {
        int distance = 5000;
        float scale = getResources().getDisplayMetrics().density * distance;
        front_linner.setCameraDistance(scale);
        back_linner.setCameraDistance(scale);
    }

    @Override
    public void bindData() {
        super.bindData();
    }

    @Override
    public void bindListener() {
        super.bindListener();
        iv_bt_close.setOnClickListener(this);
        iv_bt_open.setOnClickListener(this);
        bt_wallet_money_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bt_open_wallet_close:
                finish();
                break;
            case R.id.acctivity_pop_wallet_image_open_bt:
                iv_bt_open.setClickable(false);
                startAnimation();
                break;
            case R.id.activity_activity_pop_wallet_bt_close:
                finish();
                iv_bt_open.setClickable(true);
                break;
        }
    }

    /**
     * 3.设置动画监听事件
     */
    private void setAnimatorListener() {

        mFrontAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                flContainer.setClickable(false);
            }
        });

        mBackAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                flContainer.setClickable(false);
            }
        });
    }

    /**
     * 4.开启动画
     */
    public void startAnimation() {
        //显示正面
//        if (!isShowFront) {
//            mFrontAnimator.setTarget(front_linner);
//            mBackAnimator.setTarget(back_linner);
//            mFrontAnimator.start();
//            mBackAnimator.start();
//            isShowFront = true;

        mFrontAnimator.setTarget(back_linner);
        mBackAnimator.setTarget(front_linner);
        mFrontAnimator.start();
        mBackAnimator.start();
    }

}
