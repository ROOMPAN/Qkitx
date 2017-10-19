package com.yanhui.qktx.onkeyshare;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.yanhui.qktx.MyApplication;
import com.yanhui.qktx.constants.ShareThirdConstant;
import com.yanhui.qktx.utils.AppUtils;
import com.yanhui.qktx.utils.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * 作者： wr
 * 时间： 2017/4/19 18:27
 * 说明： 目前包含intent 分享，uc/qq浏览器分享，修改微信sdk分享，如果以后有新的办法我会继续更新
 */
public class ShareUtils {


//------------------------------------------------------------华丽的分割线------------------------------------------------------------
    //通过intent 分享

    /**
     * 通过intent 方式分享到微信好友（内容）
     * 这里需要说明下，经过百度查找加测试发现，如果单纯的通过intent分享内容给微信好友
     * 1.分享文字
     * 2.分享图片
     * 3.两者不能同时分享（如果可以同时的话，麻烦告诉我下谢谢了）
     */
    public static void throughIntentShareWXdesc(String share_word) {
        try {
            Intent intentFriend = new Intent();
            ComponentName compFriend = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            intentFriend.setComponent(compFriend);
            intentFriend.setAction(Intent.ACTION_SEND);
            intentFriend.setType("image/*");
            intentFriend.putExtra(Intent.EXTRA_TEXT, share_word);
            intentFriend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            MyApplication.getContext().startActivity(intentFriend);
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
        }

    }

    /**
     * 通过intent 方式分享内容到微信好友
     *
     * @param imagepath
     */
    public static void throughIntentShareWXImage(Context context, String imagepath) {
        try {
            File file = new File(imagepath);
            Uri photoUri = null;
            if (file.exists()) {
                if (Build.VERSION.SDK_INT >= 24) {
                    try {
                        //修复微信在7.0崩溃的问题
                        photoUri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getPath(), "bigbang.jpg", null));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    photoUri = Uri.fromFile(file);
                }
            }
            Intent intentFriend = new Intent();
            ComponentName compFriend = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            intentFriend.setComponent(compFriend);
            intentFriend.setAction(Intent.ACTION_SEND);
            intentFriend.setType("image/*");
            intentFriend.putExtra(Intent.EXTRA_STREAM, photoUri);
            intentFriend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intentFriend);
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
        }

    }

    /**
     * 通过intent方式分享内容到微信朋友圈（朋友圈可以是图片加文字一起分享）
     *
     * @param shareWord
     * @param fileUri
     */
    public static void throughIntentShareWXCircle(String shareWord, Uri fileUri) {
        if (!AppUtils.checkApkExist(MyApplication.getContext(), "com.tencent.mm")) {
            Toast.makeText(MyApplication.getContext(), "亲，你还没安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra("Kdescription", shareWord);
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            MyApplication.getContext().startActivity(intent);
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
        }


    }

    /**
     * 通过intent分享到QQ空间 ，这里需要先安装qq空间才能分享，注意：不是qq是qq空间
     *
     * @param desc
     * @param fileUri
     */
    public static void throughIntentShareQQZONE(String desc, String fileUri) {
        try {
            if (fileUri != null) {
                Intent intentQZ = new Intent();
                ComponentName componentFirendQZ = new ComponentName("com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity");
                intentQZ.setComponent(componentFirendQZ);
                intentQZ.setAction(Intent.ACTION_SEND);
                intentQZ.setType("image/*");
                intentQZ.putExtra(Intent.EXTRA_TEXT, desc);
                intentQZ.putExtra(Intent.EXTRA_STREAM, fileUri);
                intentQZ.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                MyApplication.getContext().startActivity(intentQZ);
            }
        } catch (Exception e) {
            // LogUtil.e(Log.getStackTraceString(e));
        }
    }

    /**
     * 通过intent 分享内容到QQ
     *
     * @param desc
     */
    public static void throughIntentShareQQDesc(String desc) {
        try {
            Intent intent = new Intent();
            ComponentName componentFirend = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
            intent.setComponent(componentFirend);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/*");
            intent.putExtra(Intent.EXTRA_TEXT, desc);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            MyApplication.getContext().startActivity(intent);
        } catch (Exception e) {
            //LogUtil.e(Log.getStackTraceString(e));
        }
    }

    /**
     * 通过intent 分享内容到QQ
     *
     * @param fileUri
     */
    public static void throughIntentShareQQImage(String fileUri) {
        try {
            if (fileUri != null) {
                Intent intent = new Intent();
                ComponentName componentFirend = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                intent.setComponent(componentFirend);
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, fileUri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                MyApplication.getContext().startActivity(intent);
            }
        } catch (Exception e) {
            //LogUtil.e(Log.getStackTraceString(e));
        }
    }
//------------------------------------------------------------华丽的分割线------------------------------------------------------------

    /**
     * 通过qq浏览器分享，具体的分享内容都是后端那里写的
     *
     * @param ucShareUrl 这个url 是跳转到后端写的一个界面，从这个界面再分享到微信或qq
     *                   当通过这种方式分享到微信好友时，点击返回是返回到浏览器而不是自己的应用
     *                   其实分享的内容什么的都是后端写好的android只是打开浏览器，并跳转到指定的一个页面
     *                   如果想更好的体验，就需要在这个界面加上个按钮通过点击这个按钮再返回自己的应用
     *                   这时就需要定义个scheme
     *                   目前这种方式只能分享到 qq好友，微信好友，微信朋友圈
     *                   这里只是简单举个例子，不建议使用
     *                   这种方式我在项目中已经弃用了...
     */
    public static void throughQQBShareWxCircle(String ucShareUrl) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri content_url = Uri.parse(ucShareUrl + "&type=weixinFriend");
        intent.setData(content_url);
        intent.setClassName(ShareThirdConstant.WEIXINAPPPACKAGEQQBROWSER, "com.tencent.mtt.MainActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        MyApplication.getContext().startActivity(intent);
    }


//------------------------------------------------------------华丽的分割线------------------------------------------------------------

    /**
     * 通过回调来实现
     *
     * @param AppId        来源id
     * @param packageName  来源包名
     * @param shareTitle   分享标题
     * @param shareContent 分享内容
     * @param shareUrl     分享链接
     * @param type         分享方式 0 好友 1 朋友圈 2 收藏
     * @param shareBitmap  分享图片
     */
    public static void shareWX(WeakReference<Activity> weakReference, String AppId, String packageName, String shareTitle, String shareContent, String shareUrl, int type, Bitmap shareBitmap, GetResultListener onShareLitener) {
        LogUtil.e("shareWX_______");
        Bitmap localBitmap2 = Bitmap.createScaledBitmap(shareBitmap, 150, 150, true);
        if (shareBitmap != null) {
            shareBitmap.recycle();
            shareBitmap = null;
        }
        //通过原始的微信sdk来组装参数
        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = shareUrl;
        WXMediaMessage localWXMediaMessage = new WXMediaMessage(localWXWebpageObject);
        localWXMediaMessage.title = shareTitle;
        localWXMediaMessage.description = shareContent;
        localWXMediaMessage.thumbData = (bmpToByteArray(localBitmap2, true));
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.transaction = System.currentTimeMillis() + "";
        localReq.message = localWXMediaMessage;
        localReq.scene = type;
        //在分享的时候不调用sdk中原有的分享代码，改调用自己的，这里需要注意不要使用新的jar包，里面有的方法已经取消了，就用项目里的
        WxShare.sendReq(weakReference, onShareLitener, localReq, AppId, packageName);
    }

    /**
     * 做分享前的准备，判断当前有哪个应用能进行分享 （使用回调方式）
     *
     * @param weakReference
     * @param shareTitle
     * @param share_word
     * @param shareUrl
     * @param type
     * @param bitmap
     */
    public static void shareWXReady(WeakReference<Activity> weakReference, String shareTitle, String share_word, String shareUrl, int type, Bitmap bitmap, GetResultListener onShareLitener) {
        try {
            if (AppUtils.checkApkExist(ShareThirdConstant.WEIXINAPPPACKAGEQQ)) {
                LogUtil.e("安装了QQ");
                ShareUtils.shareWX(weakReference, ShareThirdConstant.WEIXINAPPKEYQQ, ShareThirdConstant.WEIXINAPPPACKAGEQQ, shareTitle
                        , share_word, shareUrl, type, bitmap, onShareLitener
                );
            } else if (AppUtils.checkApkExist(ShareThirdConstant.WEIXINAPPPACKAGEUC)) {
                LogUtil.e("安装了uc");
                ShareUtils.shareWX(weakReference, ShareThirdConstant.WEIXINAPPKEYUC, ShareThirdConstant.WEIXINAPPPACKAGEUC, shareTitle
                        , share_word, shareUrl, type, bitmap, onShareLitener);
            } else if (AppUtils.checkApkExist(ShareThirdConstant.WEIXINAPPPACKAGEQQBROWSER)) {
                LogUtil.e("安装了qqBrowser");
                ShareUtils.shareWX(weakReference, ShareThirdConstant.WEIXINAPPKEYQQBROWSER, ShareThirdConstant.WEIXINAPPPACKAGEQQBROWSER, shareTitle
                        , share_word, shareUrl, type, bitmap, onShareLitener);
            } else if (AppUtils.checkApkExist(ShareThirdConstant.WEIXINAPPPACKAGENEWSTODAY)) {
                LogUtil.e("安装了今日头条");
                ShareUtils.shareWX(weakReference, ShareThirdConstant.WEIXINAPPKEYNEWSTODAY, ShareThirdConstant.WEIXINAPPPACKAGENEWSTODAY, shareTitle
                        , share_word, shareUrl, type, bitmap, onShareLitener);

            } else if (AppUtils.checkApkExist(ShareThirdConstant.WEIXINAPPPACKAGEBAIDU)) {
                LogUtil.e("安装了百度");
                ShareUtils.shareWX(weakReference, ShareThirdConstant.WEIXINAPPKEYBAIDU, ShareThirdConstant.WEIXINAPPPACKAGEBAIDU, shareTitle
                        , share_word, shareUrl, type, bitmap, onShareLitener);
            } else if (AppUtils.checkApkExist(ShareThirdConstant.WEIXINAPPPACKAGESINA)) {
                LogUtil.e("安装了sina");
                ShareUtils.shareWX(weakReference, ShareThirdConstant.WEIXINAPPKEYSINA, ShareThirdConstant.WEIXINAPPPACKAGESINA, shareTitle
                        , share_word, shareUrl, type, bitmap, onShareLitener);
            } else {
                LogUtil.e("没有其他的");
//                onShareLitener.onError();
                return;
            }
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
//            onShareLitener.onError();
        }
    }

    /**
     * 通过rxjava重构分享部分的代码，这里是简写，在真实项目中，可能根据需求的不同要嵌套好几层回调，所以改成rxjava来写
     *
     * @param weakReference
     * @param AppId
     * @param packageName
     * @param shareTitle
     * @param shareContent
     * @param shareUrl
     * @param type
     * @param shareBitmap
     */
    public static void shareWXRX(WeakReference<Activity> weakReference, String AppId, String packageName, String shareTitle, String shareContent, String shareUrl, int type, Bitmap shareBitmap) {
        Bitmap localBitmap2 = Bitmap.createScaledBitmap(shareBitmap, 150, 150, true);
        if (shareBitmap != null) {
            shareBitmap.recycle();
            shareBitmap = null;
        }

        //拼接参数还是用原生的sdk来弄
        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = shareUrl;
        WXMediaMessage localWXMediaMessage = new WXMediaMessage(localWXWebpageObject);
        localWXMediaMessage.title = shareTitle;
        localWXMediaMessage.description = shareContent;
        localWXMediaMessage.thumbData = (bmpToByteArray(localBitmap2, true));
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.transaction = System.currentTimeMillis() + "";
        localReq.message = localWXMediaMessage;
        localReq.scene = type;
        //在分享的时候不调用sdk中原有的分享代码，改调用自己的，这里需要注意不要使用新的jar包，里面有的方法已经取消了，就用我这项目里的
        WxShare.sendReq(weakReference, localReq, AppId, packageName);
    }

    /**
     * 根据手机上安装的软件返回具体的包名和AppID，这里面其实能做很多处理，我这只是安顺序写下来了，一般qq就返回去了。。。
     *
     * @param context
     * @param shareImage
     * @return
     */
    public static String[] shareWXReadyRx(Activity context, String shareImage) {
        String[] strings = new String[3];
        if (AppUtils.checkApkExist(context, ShareThirdConstant.WEIXINAPPPACKAGEQQ)) {
            LogUtil.e("安装了QQ");
            strings[0] = ShareThirdConstant.WEIXINAPPKEYQQ;
            strings[1] = ShareThirdConstant.WEIXINAPPPACKAGEQQ;
            strings[2] = shareImage;
            return strings;

        } else if (AppUtils.checkApkExist(context, ShareThirdConstant.WEIXINAPPPACKAGEUC)) {
            LogUtil.e("安装了uc");
            strings[0] = ShareThirdConstant.WEIXINAPPKEYUC;
            strings[1] = ShareThirdConstant.WEIXINAPPPACKAGEUC;
            strings[2] = shareImage;
            return strings;

        } else if (AppUtils.checkApkExist(context, ShareThirdConstant.WEIXINAPPPACKAGEQQBROWSER)) {
            strings[0] = ShareThirdConstant.WEIXINAPPKEYQQBROWSER;
            strings[1] = ShareThirdConstant.WEIXINAPPPACKAGEQQBROWSER;
            strings[2] = shareImage;
            LogUtil.e("安装了qqBrowser");
            return strings;

        } else if (AppUtils.checkApkExist(context, ShareThirdConstant.WEIXINAPPPACKAGENEWSTODAY)) {
            strings[0] = ShareThirdConstant.WEIXINAPPKEYNEWSTODAY;
            strings[1] = ShareThirdConstant.WEIXINAPPPACKAGENEWSTODAY;
            strings[2] = shareImage;
            LogUtil.e("安装了今日头条");
            return strings;

        } else if (AppUtils.checkApkExist(context, ShareThirdConstant.WEIXINAPPPACKAGEBAIDU)) {
            strings[0] = ShareThirdConstant.WEIXINAPPKEYBAIDU;
            strings[1] = ShareThirdConstant.WEIXINAPPPACKAGEBAIDU;
            strings[2] = shareImage;
            LogUtil.e("安装了百度");
            return strings;
        } else if (AppUtils.checkApkExist(context, ShareThirdConstant.WEIXINAPPPACKAGESINA)) {
            strings[0] = ShareThirdConstant.WEIXINAPPKEYSINA;
            strings[1] = ShareThirdConstant.WEIXINAPPPACKAGESINA;
            strings[2] = shareImage;
            LogUtil.e("安装了sina");
            return strings;
        }
        return null;
    }

    /**
     * @param shareImage
     * @return
     */
    public static Bitmap getHttpBitmap(String shareImage) {
        URL pictureUrl = null;
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            pictureUrl = new URL(shareImage);
            in = pictureUrl.openStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inSampleSize = 2;
            SoftReference<Bitmap> bitmapSoftReference = new SoftReference<>(BitmapFactory.decodeStream(in, null, options));
            bitmap = bitmapSoftReference.get();
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                LogUtil.e(Log.getStackTraceString(e));
            }
        }
        return bitmap;
    }


    /**
     * 跳转官方安装网址
     */
    public static void toInstallWebView(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setData(Uri.parse(url));
        MyApplication.getContext().startActivity(intent);
    }

    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        LogUtil.e("result___长度" + result.length);
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
