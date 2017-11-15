package com.yanhui.qktx.onkeyshare;

import android.app.Activity;

import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yanhui.qktx.utils.Logger;
import com.yanhui.qktx.utils.ToastUtils;

import java.io.File;

/**
 * Created by liupanpan on 2017/9/5.
 * 友盟分享页面 分享跳转链接
 */

public class UmShare {


//    public static void shareContext(Activity activity, SHARE_MEDIA type, String shareText,
//                                    String shareTargetUrl, String shareTitle, String shareMediaImg) {
//        Config.isJumptoAppStore = true;//如果对应的平台没有安装 跳转到下载频道
//        UMImage umImage = new UMImage(activity, shareMediaImg);
//        new ShareAction(activity)
//                .withText(shareText)
//                .withMedia(umImage)
//                .setPlatform(type)
//                .setCallback(shareListener)//回调监听器
//                .share();
//
//    }

    /**
     * @param activity
     * @param type           QQ ,QQzone,sina
     * @param shareContext   分享的文本
     * @param shareTargetUrl 分享链接
     * @param shareTitle     分享标题
     * @param shareMediaImg  缩略图链接
     */

    public static void shareWebContext(Activity activity, SHARE_MEDIA type, String shareContext,
                                       String shareTargetUrl, String shareTitle, String shareMediaImg) {
        Config.isJumptoAppStore = true;//如果对应的平台没有安装 跳转到下载频道
        UMImage umImage = new UMImage(activity, shareMediaImg);
        UMWeb web = new UMWeb(shareTargetUrl);
        web.setThumb(umImage);  //缩略图
        web.setTitle(shareTitle);
        web.setDescription(shareContext);//描述
        new ShareAction(activity)
                .withMedia(web)//分享内容跳转链接
                .setPlatform(type)
                .setCallback(umShareListener)
                .share();
    }

    public static void shareImage(Activity activity, SHARE_MEDIA type, File file) {
        Config.isJumptoAppStore = true;//如果对应的平台没有安装 跳转到下载频道
        UMImage umImage = new UMImage(activity, file);
        new ShareAction(activity)
                .withMedia(umImage)//分享图片文件
                .setPlatform(type)
                .setCallback(umShareListener)
                .share();
    }

    //回调监听器
    public static UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Logger.d("plat", "platform" + platform);
            ToastUtils.showToast(platform + " 分享成功");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showToast(platform + "分享失败");
            if (t != null) {
                Logger.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showToast(platform + "分享取消");
        }
    };
}
