package com.yanhui.qktx.onkeyshare;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yanhui.qktx.utils.LogUtil;
import com.yanhui.qktx.utils.StringUtils;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 51425 on 2017/6/12.
 */

public class ShareFragmentPresenter extends BasePresenter {
    //    private ShareFragment mShareFragment;
//    private IShareFragmentView mIShareFragmentView;
    private String shareAppId;
    private String shareAppPackageName;


    /**
     * 通过intent 分享内容给微信好友
     *
     * @param content 分享描述
     */
    public void throughIntentShareWXFriends(String content, int type) {
//        if (!AppUtils.checkApkExist(mShareFragment.getmActivity(), "com.tencent.mm")) {
//            Toast.makeText(App.getContext(), "亲，你还没安装微信", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (content == null || "".equals(content)) {
            return;
        }
        if (type == 0) {
            ShareUtils.throughIntentShareWXdesc(content);
        } else if (type == 1) {

        }


    }

    /**
     * 通过intent 分享图片给微信好友
     *
     * @param image 图片链接
     */
    public void throughIntentShareWXFriendsForimage(Context context, String image) {
//        if (!AppUtils.checkApkExist(mShareFragment.getmActivity(), "com.tencent.mm")) {
//            Toast.makeText(App.getContext(), "亲，你还没安装微信", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (image == null || "".equals(image)) {
            return;
        } else {
            ShareUtils.throughIntentShareWXImage(context, image);
        }

    }


    /**
     * 通过sdk 进行分享   这里只是提供一种写法，具体项目中怎么写还得看自己的情况，而且我rxjava也不怎么会2333
     *
     * @param shareTiele   分享的标题
     * @param shareContent 分享的内容
     * @param shareImage   分享的图片
     * @param jumpUrl      分享出去跳转的链接
     * @param type         分享到哪里
     */
    public void throughSdkShareWXFriends(final Activity context, final String shareTiele, final String shareContent, final String shareImage, final String jumpUrl, final int type) {
        Observable.just(ShareUtils.shareWXReadyRx(context, shareImage))
                .filter(new Func1<String[], Boolean>() {
                    @Override
                    public Boolean call(String[] strings) {
                        if (strings == null) {
                            // Toast.makeText(context, "没有任何可以分享的平台", Toast.LENGTH_SHORT).show();
                            LogUtil.e("没有任何可以分享的平台");
                            //这里根据需求来修改，我们是如果没有任何可以而分享的平台就走intent　方式进行分享，utils中也有，这里就不细写了
                            return false;
                        } else {
                            shareAppId = strings[0];
                            shareAppPackageName = strings[1];
                            LogUtil.e("分享的appId:::" + shareAppId + "////" + shareAppPackageName);
                            return true;
                        }
                    }
                })
                .map(new Func1<String[], Bitmap>() {
                    @Override
                    public Bitmap call(String[] strings) {
                        String sharImage = strings[2];
                        if (StringUtils.isEmpty(shareImage)) {
                            return BitmapFactory.decodeResource(context.getResources(), 0);
                        }
                        return ShareUtils.getHttpBitmap(sharImage);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {
//                        if (shareLoading != null) {
////                            shareLoading.dismissDialog(context);
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        if (shareLoading != null) {
////                            shareLoading.dismissDialog(context);
//                        }
                        LogUtil.e("error___");
                        //如果在分享的过程中出现错误也应该走intent 方式
                        LogUtil.e(android.util.Log.getStackTraceString(e));
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null) {
                            ShareUtils.shareWXRX(new WeakReference<Activity>(context), shareAppId, shareAppPackageName, shareTiele
                                    , shareContent, jumpUrl, type, bitmap
                            );
                        }
                    }
                });
    }
}
