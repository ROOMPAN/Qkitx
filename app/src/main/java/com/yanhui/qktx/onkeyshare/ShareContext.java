package com.yanhui.qktx.onkeyshare;

import android.app.Activity;
import android.net.Uri;

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
        mShareFragmentPresenter = new ShareFragmentPresenter();
        mShareFragmentPresenter.throughSdkShareWXFriends(activity, shareTitle, shareContent, shareImageUrl, jumpUrl, 1);
    }

    /**
     * 分享图片到朋友圈
     *
     * @param shareBitmapTitle
     * @param bitmapuri
     */
    public static void setShareWxCircleFriendsbyBitmap(String shareBitmapTitle, Uri bitmapuri) {
        ShareUtils.throughIntentShareWXCircle(shareBitmapTitle, bitmapuri);
    }
}

