package com.yanhui.qktx;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yanhui.qktx.activity.UserInforActivity;
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
        InitUmMobclick();
        initPushAgent();
        initImagePicker();

    }

    /**
     * 友盟初始化
     */
    private void InitUmMobclick() {
        //友盟分享登录初始化
        UMShareAPI.get(getContext());
        PlatformConfig.setQQZone(WxConstant.QQ_SHARE_ID, WxConstant.QQ_QZONE_ID);
        PlatformConfig.setSinaWeibo(WxConstant.SIAN_SHARE_ID, WxConstant.SIAN_SHARE_SECRET, "http://sns.whalecloud.com");
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
                Log.e("deviceToken", "" + s);

            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }

    /**
     * 图片剪切初始化
     */
    public void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new UserInforActivity());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(500);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(500);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }
}
