package com.yanhui.qktx.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.nukc.stateview.StateView;
import com.yanhui.qktx.R;
import com.yanhui.qktx.business.FindviewInterFace;
import com.yanhui.qktx.business.LoadingInterface;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liupanpan on 2017/7/24.
 * 所有的fragment 的主fragment
 */

public abstract class BaseFragment extends LazyLoadFragment implements FindviewInterFace, LoadingInterface {
    protected View mRoomView;
    private FrameLayout mContainerLayout;
    protected Activity mActivity;

    protected StateView mStateView;//用于显示加载中、网络异常，空布局、内容布局

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoomView == null) {
            mRoomView = inflater.inflate(provideContentViewId(), container, false);
            //mContainerLayout = mRoomView.findViewById(R.id.fragment_root_view_container);
            mStateView = StateView.inject(getStateViewRoot());
            if (mStateView != null) {
                mStateView.setLoadingResource(R.layout.page_loading);
                mStateView.setRetryResource(R.layout.page_net_error);
                mStateView.setEmptyResource(R.layout.fragment_person);
            }
            findViews();
            bindListener();
            bindData();
            return mRoomView;
        } else {
            ViewGroup parent = (ViewGroup) mRoomView.getParent();
            if (parent != null) {
                parent.removeView(mRoomView);
            }
        }
        return mRoomView;
    }

    /**
     * StateView的根布局，默认是整个界面，如果需要变换可以重写此方法
     */
    public View getStateViewRoot() {
        return mRoomView;
    }

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    @Override
    protected void onFragmentFirstVisible() {
        bindData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRoomView = null;
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

    public void refresh() {

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
}
