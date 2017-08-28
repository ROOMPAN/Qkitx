package com.yanhui.qktx.onkeyshare;

/**
 * Created by 51425 on 2017/6/12.
 */

public interface GetResultListener<T> {
    void onSuccess(T t);
    void onError();
}
