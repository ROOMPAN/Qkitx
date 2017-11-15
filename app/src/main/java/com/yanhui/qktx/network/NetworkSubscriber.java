package com.yanhui.qktx.network;

import com.yanhui.qktx.business.LoadingInterface;
import com.yanhui.qktx.utils.Logger;

import rx.Subscriber;

/**
 * Created by liupanpan on 17/6/29.
 */
public class NetworkSubscriber<T> extends Subscriber<T> {
    LoadingInterface mLoadingInterface;

    public NetworkSubscriber() {
    }

    public NetworkSubscriber(LoadingInterface loadingInterface) {
        this.mLoadingInterface = loadingInterface;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mLoadingInterface != null && isShowCommonLoading()) {
            mLoadingInterface.showLoadingView();
        }
    }

    @Override
    public void onCompleted() {
        if (mLoadingInterface != null && isShowCommonLoading()) {
            mLoadingInterface.hideLoadingView();
        }
        if (mLoadingInterface != null) {
            mLoadingInterface.loadSuccess();
        }
    }

    @Override
    public void onError(Throwable e) {
        Logger.e("data_erro", e.toString() );
//        ToastUtils.showToast("服务器异常,请稍后重试!");
        if (mLoadingInterface != null && isShowCommonLoading()) {
            mLoadingInterface.showErrorView(e);
        }
    }

    @Override
    public void onNext(T data) {
        if (mLoadingInterface != null && isShowCommonLoading()) {
            mLoadingInterface.hideLoadingView();

        }
    }

    public boolean isShowCommonLoading() {
        return true;
    }
}
