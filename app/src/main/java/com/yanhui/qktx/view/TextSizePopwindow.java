package com.yanhui.qktx.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.just.library.AgentWeb;
import com.yanhui.qktx.R;
import com.yanhui.qktx.utils.ToastUtils;

/**
 * Created by liupanpan on 2017/10/5.
 */

public class TextSizePopwindow extends PopupWindow implements View.OnClickListener {
    private Activity activity;
    private AgentWeb agentWeb;
    private View mView;
    private TextView tv_size_xiao, tv_size_zhong, tv_size_da;
    private LinearLayout linner_dislike, linner_complaint, linner_finsh;


    public TextSizePopwindow(Activity activity, AgentWeb agentWeb) {
        super(activity);
        this.activity = activity;
        this.agentWeb = agentWeb;
        initView(activity);
    }

    public void show(View view) {
        if (!isShowing()) {
            showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    private void initView(Activity context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(R.layout.view_text_size_for_html, null);
        tv_size_xiao = mView.findViewById(R.id.tv_text_size_xiao);
        tv_size_zhong = mView.findViewById(R.id.tv_text_size_zhong);
        tv_size_da = mView.findViewById(R.id.tv_text_size_da);
        linner_dislike = mView.findViewById(R.id.text_size_linner_dislike);
        linner_complaint = mView.findViewById(R.id.text_size_linner_complaint);
        linner_finsh = mView.findViewById(R.id.text_size_finsh);
        tv_size_xiao.setOnClickListener(this);
        tv_size_zhong.setOnClickListener(this);
        tv_size_da.setOnClickListener(this);

        linner_complaint.setOnClickListener(this);
        linner_dislike.setOnClickListener(this);
        linner_finsh.setOnClickListener(this);

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
            case R.id.tv_text_size_xiao:
                tv_size_xiao.setTextColor(activity.getResources().getColor(R.color.white));
                tv_size_zhong.setTextColor(activity.getResources().getColor(R.color.login_bg));
                tv_size_da.setTextColor(activity.getResources().getColor(R.color.login_bg));
                tv_size_xiao.setBackground(activity.getResources().getDrawable(R.drawable.shape_text_size_select_xiao_bg));
                tv_size_zhong.setBackground(activity.getResources().getDrawable(R.drawable.shape_text_size_zhong_namol_bg));
                tv_size_da.setBackground(activity.getResources().getDrawable(R.drawable.shape_text_size_da_namol_bg));
                agentWeb.getJsEntraceAccess().quickCallJs("aaaa");
                break;
            case R.id.tv_text_size_zhong:
                tv_size_zhong.setTextColor(activity.getResources().getColor(R.color.white));
                tv_size_xiao.setTextColor(activity.getResources().getColor(R.color.login_bg));
                tv_size_da.setTextColor(activity.getResources().getColor(R.color.login_bg));
                tv_size_xiao.setBackground(activity.getResources().getDrawable(R.drawable.shape_text_size_xiao_namol_bg));
                tv_size_zhong.setBackground(activity.getResources().getDrawable(R.drawable.shape_text_size_zhong_select_bg));
                tv_size_da.setBackground(activity.getResources().getDrawable(R.drawable.shape_text_size_da_namol_bg));
                agentWeb.getJsEntraceAccess().quickCallJs("aaaa");
                break;
            case R.id.tv_text_size_da:
                tv_size_da.setTextColor(activity.getResources().getColor(R.color.white));
                tv_size_zhong.setTextColor(activity.getResources().getColor(R.color.login_bg));
                tv_size_xiao.setTextColor(activity.getResources().getColor(R.color.login_bg));
                tv_size_xiao.setBackground(activity.getResources().getDrawable(R.drawable.shape_text_size_xiao_namol_bg));
                tv_size_zhong.setBackground(activity.getResources().getDrawable(R.drawable.shape_text_size_zhong_namol_bg));
                tv_size_da.setBackground(activity.getResources().getDrawable(R.drawable.text_size_select_da_bg));
                agentWeb.getJsEntraceAccess().quickCallJs("aaaa");
                break;
            case R.id.text_size_linner_dislike:
                dismiss();
                ToastUtils.showToast("以后减少此类内容的推荐");
                break;
            case R.id.text_size_linner_complaint:
                //投诉
                WebView mWebView = agentWeb.getWebCreator().get();
                mWebView.reload();
                break;
            case R.id.text_size_finsh:
                dismiss();
                break;
        }
    }
}
