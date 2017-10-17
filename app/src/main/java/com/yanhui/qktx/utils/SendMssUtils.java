package com.yanhui.qktx.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by liupanpan on 2017/10/17.
 * 发送短信
 */

public class SendMssUtils {
    /**
     * 发送短信
     *
     * @param smsBody
     */
    public static void sendSMS(String smsBody, Activity activity) {
        //"smsto:xxx" xxx是可以指定联系人的
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        //"sms_body"必须一样，smsbody是发送短信内容content
        intent.putExtra("sms_body", smsBody);
        activity.startActivity(intent);
    }
}
