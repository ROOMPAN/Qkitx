package com.yanhui.qktx.utils;

import android.widget.Toast;

import com.yanhui.qktx.MyApplication;

public class ToastUtils {

    private static Toast mToast;

    /**
     * 显示Toast
     */
    public static void showToast(CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }


}
