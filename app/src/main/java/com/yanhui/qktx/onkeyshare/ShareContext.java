package com.yanhui.qktx.onkeyshare;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

import com.yanhui.qktx.utils.AppUtils;
import com.yanhui.qktx.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by liupanpan on 2017/8/28.
 */

public class ShareContext {

    private static ShareFragmentPresenter mShareFragmentPresenter;
    private static String shareTitle = "有问题吗？真的有问题吗？";
    private static String shareContent = "请点击查看答案";
    private static String shareImageUrl = "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg";
    private static String jumpUrl = "http://fuck.czchanfu.com/fo2.htm";

    /**
     * 分享到微信好友
     *
     * @param activity
     * @param shareTitle
     * @param shareContent
     * @param shareImageUrl
     * @param jumpUrl
     */
    public static void setShareWxFriends(Activity activity, String shareTitle, String shareContent, String shareImageUrl, String jumpUrl) {
        if (!AppUtils.checkApkExist("com.tencent.mm")) {
            ToastUtils.showToast("微信未安装,请先安装微信,再重试!!!");
            return;
        }
        mShareFragmentPresenter = new ShareFragmentPresenter();
        mShareFragmentPresenter.throughSdkShareWXFriends(activity, shareTitle, shareContent, shareImageUrl, jumpUrl, 0);
    }

    /**
     * 分享到微信朋友圈
     *
     * @param activity
     * @param shareTitle
     * @param shareContent
     * @param shareImageUrl
     * @param jumpUrl
     */
    public static void setShareWxCirclefriends(Activity activity, String shareTitle, String shareContent, String shareImageUrl, String jumpUrl) {
        if (!AppUtils.checkApkExist("com.tencent.mm")) {
            ToastUtils.showToast("微信未安装,请先安装微信,再重试!!!");
            return;
        }
        mShareFragmentPresenter = new ShareFragmentPresenter();
        mShareFragmentPresenter.throughSdkShareWXFriends(activity, shareTitle, shareContent, shareImageUrl, jumpUrl, 1);
    }

    /**
     * 分享图片到朋友圈
     *
     * @param shareBitmapTitle
     * @param bitmapuri        调用方法 String[] imagepath = {"/storage/emulated/0/qk/temp/1504604667104.jpg", "/storage/emulated/0/qk/temp/1504604775039.jpg", "/storage/emulated/0/qk/share/1504604776.jpg"};
     *                         ShareContext.setShareWxCircleFriendbyBitmapList(activity, imagepath);
     *                         String[] imagepath = {file.getPath()};
     *                         ShareContext.setShareWxCircleFriendbyBitmapList(WebViewActivity.this, "大好时机肯定会就卡死", imagepath);
     */


    public static void setShareWxCircleFriendsbyBitmap(String shareBitmapTitle, Uri bitmapuri) {
        if (!AppUtils.checkApkExist("com.tencent.mm")) {
            ToastUtils.showToast("微信未安装,请先安装微信,再重试!!!");
            return;
        }
        ShareUtils.throughIntentShareWXCircle(shareBitmapTitle, bitmapuri);
    }

    public static void setShareWxCircleFriendbyBitmapList(Activity context, String titlecontext, String[] picPaths) {

        if (!AppUtils.checkApkExist("com.tencent.mm")) {
            ToastUtils.showToast("微信未安装,请先安装微信,再重试!!!");
            return;
        }
        Intent weChatIntent = new Intent();
        weChatIntent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        ArrayList imageList = new ArrayList();
        for (String path : picPaths) {
            File file = new File(path);
            if (file.exists()) {
                imageList.add(Uri.fromFile(file));
            }
        }
        if (imageList.size() == 0) return;
        weChatIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        weChatIntent.setType("image/*");
        weChatIntent.putExtra(Intent.EXTRA_STREAM, imageList);
        weChatIntent.putExtra("Kdescription", titlecontext); //分享描述
        context.startActivity(weChatIntent);
    }
}

