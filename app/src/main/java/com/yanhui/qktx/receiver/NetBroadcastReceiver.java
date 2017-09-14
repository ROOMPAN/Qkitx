package com.yanhui.qktx.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.utils.CommonUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liupanpan on 2017/9/14.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            // 接口回调传过去状态的类型
            Log.e("receiver", "change" + CommonUtil.isWifi(context));
//            EventBus.getDefault().post(new BusEvent(EventConstants.EVENT_SWITCH_TO_HOME));//切换到首页
            EventBus.getDefault().post(new BusEvent(CommonUtil.isWifi(context)));
        }
    }

}
