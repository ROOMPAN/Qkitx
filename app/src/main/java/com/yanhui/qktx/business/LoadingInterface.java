package com.yanhui.qktx.business;

/**
 * Created by liupanpan on 2017/8/7.
 */

public interface LoadingInterface {
    void showLoadingView();

    void hideLoadingView();

    void showErrorView(Throwable e);

    void loadSuccess();
}
