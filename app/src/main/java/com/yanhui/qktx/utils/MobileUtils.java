package com.yanhui.qktx.utils;

import android.app.Activity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.yanhui.qktx.MyApplication;

/**
 * Created by liupan.
 */
public class MobileUtils {

    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) MyApplication.getContext().getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            String imei = tm.getDeviceId();
            if (TextUtils.isEmpty(imei)) {
                return "";
            } else {
                return imei;
            }
        }
        return null;
    }

}
