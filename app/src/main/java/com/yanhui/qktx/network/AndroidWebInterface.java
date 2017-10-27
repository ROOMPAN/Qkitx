package com.yanhui.qktx.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.just.agentwebX5.AgentWeb;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yanhui.qktx.MyApplication;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.onkeyshare.ShareContext;
import com.yanhui.qktx.onkeyshare.UmShare;
import com.yanhui.qktx.utils.GetPhoneNumberUtils;
import com.yanhui.qktx.utils.GsonToJsonUtil;
import com.yanhui.qktx.utils.SendMssUtils;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UpdataImageUtils;
import com.yanhui.qktx.view.RewritePopwindow;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import static com.yanhui.qktx.constants.Constant.ARTICLETYPE;
import static com.yanhui.qktx.constants.Constant.GONE_BUTTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_BUTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.TASKID;
import static com.yanhui.qktx.constants.Constant.VIDEO_URL;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/10/17.
 * webview  H5页面 js调用原生(互调)方法
 */

public class AndroidWebInterface {
    private Handler deliver = new Handler(Looper.getMainLooper());
    private AgentWeb agentWeb;
    private Activity activity;
    private Context context;

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
     * 邀请徒弟到微信(图文)
     *
     * @param title
     * @param img_url
     * @param skip_url
     * @param context
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
     * 分享微信好友 (图片)
     *
     * @param imagurl
     */
    @JavascriptInterface
    public void ShareApprenticeWxForImage(String imagurl) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                Log.e("h5sharetitle", "img_url" + imagurl);
                if (!StringUtils.isEmpty(imagurl)) {
                    UpdataImageUtils updataImageUtils = new UpdataImageUtils(activity, imagurl, new ImageDownLoadCallBack() {
                        @Override
                        public void onDownLoadSuccess(File file) {
                            ShareContext.setShareWxFriendsForImage(activity, file.getPath());
                        }

                        @Override
                        public void onDownLoadSuccess(Bitmap bitmap) {

                        }

                        @Override
                        public void onDownLoadFailed() {

                        }
                    });
                    new Thread(updataImageUtils).start();
                }
            }
        });
    }

    /**
     * 邀请徒弟到朋友圈(图片)
     *
     * @param img_url
     */
    @JavascriptInterface
    public void ShareApprenticeWxCircleForImage(String img_url, String title) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                Log.e("h5sharetitle", "--" + "img_url" + img_url);
                if (!StringUtils.isEmpty(img_url)) {
                    UpdataImageUtils updataImageUtils = new UpdataImageUtils(activity, img_url, new ImageDownLoadCallBack() {
                        @Override
                        public void onDownLoadSuccess(File file) {
//                        Log.e("下载成功", "" + file.getPath());
                            String[] imagepath = {file.getPath()};
                            ShareContext.setShareWxCircleFriendbyBitmapList(activity, title, imagepath);
                        }

                        @Override
                        public void onDownLoadSuccess(Bitmap bitmap) {
//                            ToastUtils.showToast("下载成功");
                        }

                        @Override
                        public void onDownLoadFailed() {
                        }
                    });
                    new Thread(updataImageUtils).start();
                }
            }
        });
    }

    /**
     * 邀请徒弟到朋友圈(图文)
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
     * 分享图片到 QQ 好友(图片)
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
//                        Log.e("下载成功", "" + file.getPath());
//                        String[] imagepath = {file.getPath()};
//                        ShareContext.setShareWxCircleFriendbyBitmapList(activity, "大好时机肯定会就卡死", imagepath);
//                        ShareContext.setShareWxFriendsForImage(activity, file.getPath());
                        UmShare.shareImage(activity, SHARE_MEDIA.QQ, file);
                    }

                    @Override
                    public void onDownLoadSuccess(Bitmap bitmap) {
//                            ToastUtils.showToast("下载成功");
                    }

                    @Override
                    public void onDownLoadFailed() {
                    }
                });
                new Thread(updataImageUtils).start();
            }
        });
    }

    /**
     * 分享到 QQ (图文)
     *
     * @param title
     * @param img_url
     * @param skip_url
     * @param context
     */
    @JavascriptInterface
    public void ShareApprenticeQQForArticle(String title, String img_url, String skip_url, String context) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                UmShare.shareWebContext(activity, SHARE_MEDIA.QQ, context, skip_url, title, img_url);
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
     * @param taskId      文章 id
     * @param taskUrl     文章链接
     * @param articletype 文章类型
     */
    @JavascriptInterface
    public void webCorrelationArticleItem(String taskId, String taskUrl, String articletype, String video_url) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity.getApplicationContext(), WebViewActivity.class);
                intent.putExtra(WEB_VIEW_LOAD_URL, taskUrl);
                intent.putExtra(TASKID, Integer.parseInt(taskId));
                intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
                intent.putExtra(VIDEO_URL, video_url);
                intent.putExtra(ARTICLETYPE, Integer.parseInt(articletype));
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
                if (!chackPression(activity)) {
                    ToastUtils.showToast("请开启访问联系人权限");
                    return;
                }
                if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    if (GetPhoneNumberUtils.getAllContacts(activity, activity) != null && GetPhoneNumberUtils.getAllContacts(activity, activity).size() != 0) {
                        String mobilejson = GsonToJsonUtil.toJson(GetPhoneNumberUtils.getAllContacts(activity, activity));
                        Log.e("mobile", "" + mobilejson);
                        agentWeb.getJsEntraceAccess().quickCallJs("recognizeUsers(" + mobilejson + ")");
                    } else {
                        ToastUtils.showToast("通讯录无数据");
                    }
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
                }
            }
        });

    }

    /**
     * 唤醒徒弟
     */
    @JavascriptInterface
    public void webAwakenApprentice(String skip_url) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity.getApplicationContext(), WebViewActivity.class);
                intent.putExtra(WEB_VIEW_LOAD_URL, skip_url);
                intent.putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM);
                activity.startActivity(intent);
            }
        });

    }

    /**
     * H5跳转到首页
     */
    @JavascriptInterface
    public void switchHomePage() {
        deliver.post(new Runnable() {
            @Override
            public void run() {
//                ToastUtils.showToast("homePager");
                EventBus.getDefault().post(new BusEvent(EventConstants.EVENT_SWITCH_TO_HOME));//切换到首页
                activity.finish();
            }
        });
    }

    /**
     * 检查是否获取通讯录读取权限
     *
     * @param activity
     * @return
     */
    public static Boolean chackPression(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", MyApplication.getContext().getPackageName(), null);
            intent.setData(uri);
            activity.startActivity(intent);
        }
        return false;
    }


}
