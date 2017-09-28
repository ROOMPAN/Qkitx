package com.yanhui.qktx.view;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yanhui.qktx.R;
import com.yanhui.qktx.onkeyshare.ShareContext;
import com.yanhui.qktx.onkeyshare.UmShare;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;

/**
 * Created by liupanpan on 2017/9/5.
 */

public class RewritePopwindow extends PopupWindow implements View.OnClickListener {
    private View mView;
    private LinearLayout pop_share_wx, pop_share_wx_friends, pop_share_QQ, pop_share_QQZone, pop_share_sina, pop_share_copy_url, pop_share_os;
    private TextView tv_cancle, top_share_cancle;
    private Activity activity;
    private String shareTitle = "有问题吗？真的有问题吗？";
    private String shareContent = "请点击查看答案";
    private String shareImageUrl = "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg";
    private String jumpUrl = "http://fuck.czchanfu.com/fo2.htm";

    public RewritePopwindow(Activity activity, String shareTitle, String shareContent, String shareImageUrl, String jumpUrl) {
        super(activity);
        this.activity = activity;
        this.shareContent = shareContent;
        this.shareTitle = shareTitle;
        this.shareImageUrl = shareImageUrl;
        this.jumpUrl = jumpUrl;
        initView(activity);
    }

    public void show(View view) {
        if (!isShowing()) {
            showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }


    private void initView(Activity context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(R.layout.popupwindow_share, null);

        pop_share_wx = mView.findViewById(R.id.pop_share_wx);
        pop_share_wx_friends = mView.findViewById(R.id.pop_share_wx_friends);
        pop_share_QQ = mView.findViewById(R.id.pop_share_qq);
        pop_share_QQZone = mView.findViewById(R.id.pop_share_qq_zone);
        pop_share_sina = mView.findViewById(R.id.pop_share_sina);
        pop_share_copy_url = mView.findViewById(R.id.pop_copy_url);
        pop_share_os = mView.findViewById(R.id.pop_os_share);
        tv_cancle = mView.findViewById(R.id.share_cancle);
        top_share_cancle = mView.findViewById(R.id.top_share_cancle);
        pop_share_wx.setOnClickListener(this);
        pop_share_wx_friends.setOnClickListener(this);
        pop_share_QQ.setOnClickListener(this);
        pop_share_QQZone.setOnClickListener(this);
        pop_share_sina.setOnClickListener(this);
        pop_share_copy_url.setOnClickListener(this);
        pop_share_os.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
        top_share_cancle.setOnClickListener(this);
        //设置SelectPicPopupWindow的View
        this.setContentView(mView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置PopupWindow可触摸
        this.setTouchable(true);
        //设置非PopupWindow区域是否可触摸
//    this.setOutsideTouchable(false);
        //设置SelectPicPopupWindow弹出窗体动画效果
//    this.setAnimationStyle(R.style.select_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha(context, 0.5f);//0.0-1.0

        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(context, 1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_share_wx:
                if (ShareConisNull(shareTitle, shareContent, shareImageUrl, jumpUrl)) {
                    ShareContext.setShareWxFriends(activity, shareTitle, shareContent, shareImageUrl, jumpUrl);
                    dismiss();
                } else {
                    ToastUtils.showToast("分享数据不能为空");
                }
                break;
            case R.id.pop_share_wx_friends:
                if (ShareConisNull(shareTitle, shareContent, shareImageUrl, jumpUrl)) {
                    ShareContext.setShareWxCirclefriends(activity, shareTitle, shareContent, shareImageUrl, jumpUrl);
                    dismiss();
                } else {
                    ToastUtils.showToast("分享数据不能为空");
                }
                break;
            case R.id.pop_share_qq:
                if (ShareConisNull(shareTitle, shareContent, shareImageUrl, jumpUrl)) {
                    UmShare.shareWebContext(activity, SHARE_MEDIA.QQ, shareContent, jumpUrl, shareTitle, shareImageUrl);
                    dismiss();
                } else {
                    ToastUtils.showToast("分享数据不能为空");
                }
                break;
            case R.id.pop_share_qq_zone:
                if (ShareConisNull(shareTitle, shareContent, shareImageUrl, jumpUrl)) {
                    UmShare.shareWebContext(activity, SHARE_MEDIA.QZONE, shareContent, jumpUrl, shareTitle, shareImageUrl);
                    dismiss();
                } else {
                    ToastUtils.showToast("分享数据不能为空");
                }
                break;
            case R.id.pop_share_sina:
                if (ShareConisNull(shareTitle, shareContent, shareImageUrl, jumpUrl)) {
                    UmShare.shareWebContext(activity, SHARE_MEDIA.SINA, shareContent, jumpUrl, shareTitle, shareImageUrl);
                    dismiss();
                } else {
                    ToastUtils.showToast("分享数据不能为空");
                }
//                ToastUtils.showToast("新浪微博");
                break;
            case R.id.pop_copy_url:
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", jumpUrl);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtils.showToast("已复制到剪切板");
                dismiss();
                break;
            case R.id.pop_os_share:
                if (ShareConisNull(shareTitle, shareContent, shareImageUrl, jumpUrl)) {
                    Intent textIntent = new Intent(Intent.ACTION_SEND);
                    textIntent.setType("text/plain");
                    textIntent.putExtra(Intent.EXTRA_TEXT, shareTitle + jumpUrl);
                    activity.startActivity(Intent.createChooser(textIntent, "分享"));
                    dismiss();
                } else {
                    ToastUtils.showToast("分享数据不能为空");
                }
                break;
            case R.id.share_cancle:
                //销毁弹出框
                dismiss();
                backgroundAlpha(activity, 1f);
                break;
            case R.id.top_share_cancle:
                //销毁弹出框
                dismiss();
                backgroundAlpha(activity, 1f);
                break;
        }

    }

    public boolean ShareConisNull(String shareTitle, String shareContent, String shareImageUrl, String jumpUrl) {
        if (!StringUtils.isEmpty(shareTitle) && !StringUtils.isEmpty(shareContent) && !StringUtils.isEmpty(shareImageUrl) && !StringUtils.isEmpty(jumpUrl)) {
            return true;
        } else {
            return false;
        }

    }
}
