package com.yanhui.qktx;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.request.target.ViewTarget;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yanhui.qktx.activity.UserInforActivity;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.constants.WxConstant;
import com.yanhui.qktx.models.PushBean;
import com.yanhui.qktx.utils.ChannelUtil;
import com.yanhui.qktx.utils.SharedPreferencesMgr;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.yanhui.qktx.constants.Constant.ARTICLETYPE;
import static com.yanhui.qktx.constants.Constant.SHOW_BUTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.TASKID;
import static com.yanhui.qktx.constants.Constant.VIDEO_URL;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

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
    private static List<Activity> lists = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //加载热修复
        initialize();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //对全局属性赋值
        Initx5();
        ViewTarget.setTagId(R.id.tag_glide);//设置 tag,防止 glide setTag问题
        mContext = getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();
        SharedPreferencesMgr.init(getApplicationContext(), "qktx");
        InitUmMobclick();
        initPushAgent();
        initImagePicker();
// 热修复 queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
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
        //多渠道友盟统计配置
        MobclickAgent.startWithConfigure(new UMAnalyticsConfig(this, WxConstant.UM_APP_KEY, channel));
    }

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
            public void onSuccess(String pushtoken) {
                Log.e("deviceToken", "" + pushtoken);
                SharedPreferencesMgr.setString("pushtoken", pushtoken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        //友盟点击通知栏跳转到指定页面
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
//                Toast.makeText(context, msg.title, Toast.LENGTH_LONG).show();
//                Log.e("dealWithCustomAction", "" + msg.custom);
                PushBean pushBean = new Gson().fromJson(msg.custom, PushBean.class);
                Intent activity_intent = new Intent(context, WebViewActivity.class);
                activity_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity_intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
                activity_intent.putExtra(WEB_VIEW_LOAD_URL, pushBean.getTaskUrl());
                activity_intent.putExtra(VIDEO_URL, pushBean.getVideoUrl());
                activity_intent.putExtra(TASKID, pushBean.getTaskId());
                activity_intent.putExtra(ARTICLETYPE, pushBean.getArticleType());
                startActivity(activity_intent);
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        //友盟消息处理 通知样式自定义
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        EventBus.getDefault().post(new BusEvent(EventConstants.EVEN_ISPUSH_DIALOG, msg.text, msg.custom));
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        // myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        // myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);
                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);//设置推送消息回调
        mPushAgent.setDisplayNotificationNumber(5);//设置推送消息接收条数五条,多于5条,折叠.
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
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(1000);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(1000);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    /**
     * 腾讯 X5内核 初始化
     */
    public void Initx5() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    /**
     * 阿里热修复代码 初始化
     */
    public void initialize() {
        // initialize最好放在attachBaseContext最前面，初始化直接在Application类里面，切勿封装到其他类
        SophixManager.getInstance().setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            SophixManager.getInstance().killProcessSafely();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
    }


    /**
     * webviewActivity 到数据集合
     * 关闭多个 activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        lists.add(activity);
    }

    public static void clearActivity() {
        if (lists != null) {
            for (Activity activity : lists) {
                activity.finish();
            }
            lists.clear();
        }
    }
}
