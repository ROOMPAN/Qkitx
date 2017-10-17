package com.yanhui.qktx.network;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.just.library.AgentWeb;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.onkeyshare.ShareContext;
import com.yanhui.qktx.utils.GetPhoneNumberUtils;
import com.yanhui.qktx.utils.GsonToJsonUtil;
import com.yanhui.qktx.utils.SendMssUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UpdataImageUtils;
import com.yanhui.qktx.view.RewritePopwindow;

import java.io.File;

import static com.yanhui.qktx.constants.Constant.ARTICLETYPE;
import static com.yanhui.qktx.constants.Constant.SHOW_BUTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.TASKID;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/10/17.
 * webview  页面 js调用原生方法
 */

public class AndroidWebInterface {
    private Handler deliver = new Handler(Looper.getMainLooper());
    private AgentWeb agentWeb;
    private Activity activity;

    public AndroidWebInterface(AgentWeb agentWeb, Activity activity) {
        this.agentWeb = agentWeb;
        this.activity = activity;
    }

    /**
     * H5邀请好友js方法 输入邀请码邀请页面
     *
     * @param title
     * @param context
     * @param jumpurl
     * @param img_url
     */
    @JavascriptInterface
    public void ShareInvitingfriends(String title, String context, String jumpurl, String img_url) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                RewritePopwindow mPopwindow = new RewritePopwindow(activity, title, context, img_url, jumpurl);
                mPopwindow.show(new View(activity));
                Log.e("h5sharetitle", "---" + title + "--" + "context" + context + "-----" + jumpurl + "--" + "img_url" + img_url);
            }
        });
    }

    /**
     * 邀请徒弟到微信(分享图片)
     *
     * @param img_url
     */
    @JavascriptInterface
    public void ShareApprenticeWx(String title, String img_url, String skip_url, String context) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                Log.e("h5sharetitle", "img_url" + img_url);
                ShareContext.setShareWxFriends(activity, title, context, img_url, skip_url);
            }
        });
    }

    /**
     * 邀请徒弟到朋友圈
     *
     * @param title
     * @param img_url
     */
    @JavascriptInterface
    public void ShareApprenticeWxCircleFriends(String title, String img_url, String skip_url, String context) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                Log.e("h5sharetitle", "---" + title + "--" + "img_url" + img_url);
                ShareContext.setShareWxCirclefriends(activity, title, context, img_url, skip_url);
//
            }
        });
    }

    /**
     * 分享图片到 QQ 好友
     *
     * @param img_url
     */
    @JavascriptInterface
    public void ShareApprenticeQQ(String img_url) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                Log.e("h5sharetitle", "--" + "img_url" + img_url);
                UpdataImageUtils updataImageUtils = new UpdataImageUtils(activity, img_url, new ImageDownLoadCallBack() {
                    @Override
                    public void onDownLoadSuccess(File file) {
                        Log.e("DOWNLOAD", "3");
                        Log.e("下载成功", "" + file.getPath());
                        String[] imagepath = {file.getPath()};
                        ShareContext.setShareWxCircleFriendbyBitmapList(activity, "大好时机肯定会就卡死", imagepath);
//                            UmShare.shareImage(WebViewActivity.this, SHARE_MEDIA.QQ, file);
                    }

                    @Override
                    public void onDownLoadSuccess(Bitmap bitmap) {
//                            ToastUtils.showToast("下载成功");
                        Log.e("DOWNLOAD", "2");
                    }

                    @Override
                    public void onDownLoadFailed() {
                        Log.e("DOWNLOAD", "1");
                    }
                });
                new Thread(updataImageUtils).start();
            }
        });
    }

    /**
     * 分享内容 到 短信
     *
     * @param title
     */
    @JavascriptInterface
    public void ShareApprenticeMessage(String title) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                if (title != null) {
                    SendMssUtils.sendSMS(title, activity);
                }
                Log.e("h5sharetitle", "--" + "context" + title);
            }
        });
    }

    /**
     * 相关资讯
     *
     * @param taskId
     * @param taskUrl
     */
    @JavascriptInterface
    public void webCorrelationArticleItem(String taskId, String taskUrl) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity.getApplicationContext(), WebViewActivity.class);
                intent.putExtra(WEB_VIEW_LOAD_URL, taskUrl);
                intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
                intent.putExtra(TASKID, taskId);
                intent.putExtra(ARTICLETYPE, "");
                activity.startActivity(intent);
            }
        });

    }

    /**
     * 识别徒弟
     */
    @JavascriptInterface
    public void webRecognitionApprentice() {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                if (GetPhoneNumberUtils.getAllContacts(activity) != null && GetPhoneNumberUtils.getAllContacts(activity).size() != 0) {
                    agentWeb.getJsEntraceAccess().quickCallJs("recognizeUsers", GsonToJsonUtil.toJson(GetPhoneNumberUtils.getAllContacts(activity)));
                } else {
                    ToastUtils.showToast("通讯录无数据");
                }
            }
        });

    }
}
