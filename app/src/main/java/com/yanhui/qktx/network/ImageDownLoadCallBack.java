package com.yanhui.qktx.network;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by liupanpan on 2017/10/16.
 */

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);

    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}
