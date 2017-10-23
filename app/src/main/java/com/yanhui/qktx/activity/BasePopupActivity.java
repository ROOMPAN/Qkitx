package com.yanhui.qktx.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.yanhui.qktx.R;
import com.yanhui.qktx.business.FindviewInterFace;
import com.yanhui.qktx.business.LoadingInterface;

/**
 * Created by liupanpan on 2017/10/23.
 */

public class BasePopupActivity extends FragmentActivity implements FindviewInterFace, LoadingInterface {
    private FrameLayout mRootView;
    private FrameLayout mContainerLayout;
    private RelativeLayout mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.activity_popup_activity_loading_container, null);
        mContainerLayout = mRootView.findViewById(R.id.activity_root_view_container);
        mLoadingView = mRootView.findViewById(R.id.common_loading_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, mContainerLayout);
        super.setContentView(mRootView);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        findViews();
        bindListener();
        bindData();
    }

    @Override
    public void hideLoadingView() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorView(Throwable e) {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void findViews() {

    }

    @Override
    public void bindListener() {

    }

    @Override
    public void bindData() {

    }

    @Override
    public void reLoad() {

    }
}
