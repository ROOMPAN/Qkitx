package com.yanhui.qktx;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yanhui.qktx.constants.WxConstant;
import com.yanhui.qktx.utils.ChannelUtil;
import com.yanhui.qktx.utils.SharedPreferencesMgr;

/**
 * Created by xuyanjun on 15/10/20.
 */
public class MyApplication extends Application {

    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Context mContext;//上下文
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler
    private String channel;

    @Override
    public void onCreate() {
        super.onCreate();
        //对全局属性赋值
        mContext = getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();
        SharedPreferencesMgr.init(getApplicationContext(), "qktx");
//        getConfig();
        initX5();
        InitUmMobclick();
        initPushAgent();


    }

    /**
     * 友盟初始化
     */
    private void InitUmMobclick() {
        //友盟分享登录初始化
        UMShareAPI.get(getContext());
        PlatformConfig.setWeixin(WxConstant.WX_APP_ID, WxConstant.WX_APP_SCREACT);
        Config.DEBUG = true;// log 调试日志开关
        channel = ChannelUtil.getChannel(this, "default channel");//获取渠道名
        MobclickAgent.startWithConfigure(new UMAnalyticsConfig(this, WxConstant.UM_APP_KEY, channel));
    }

    //    private void getConfig() {
//        HttpClient.getInstance().getConfig(new NetworkSubscriber<Config>() {
//            @Override
//            public void onNext(Config config) {
//                super.onNext(config);
//                if (config.isOKCode()) {
//                    SharedPreferencesUtils.setObjectData("config", config);
//                    if (!SharedPreferencesUtils.constainsKey("token")) {
//                        SharedPreferencesUtils.setData("token", config.data.userToken);
//                    }
//                    Toast.makeText(MyApplication.this, config.data.appUrl, Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
    public static Context getContext() {
        return mContext;
    }

    /**
     * 重启当前应用
     */
    public static void restart() {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    public static void setContext(Context mContext) {
        MyApplication.mContext = mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        MyApplication.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        MyApplication.mMainThreadId = mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static void setMainThreadLooper(Looper mMainLooper) {
        MyApplication.mMainLooper = mMainLooper;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void setMainHandler(Handler mHandler) {
        MyApplication.mHandler = mHandler;
    }

    private void initX5() {
//        Intent intent = new Intent(this, PreLoadX5Service.class);
//        startService(intent);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);


    }

    /**
     * 初始化友盟推送
     */
    public void initPushAgent() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setMessageChannel(channel);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                Log.e("deviceToken",""+s);

            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }
}
