package com.yanhui.qktx.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.UserPopBannerAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liupanpan on 2017/11/2.
 * 个人中心页面的 弹出框 图片
 */

public class UserInforEventPopWindow extends PopupWindow {
    private View mView;
    private CircleImageView iv_banner_close;
    private RollPagerView pop_user_infor_event_vp;
    private Activity context;

    public UserInforEventPopWindow(Activity context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public void show(View view) {
        if (!isShowing()) {
            showAtLocation(view, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    private void initView(Activity context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(R.layout.view_user_infor_even_pop_window, null);
        iv_banner_close = mView.findViewById(R.id.iv_user_info_pop_close);
        pop_user_infor_event_vp = mView.findViewById(R.id.pop_user_infor_event_vp);
        pop_user_infor_event_vp.setAdapter(new UserPopBannerAdapter(context));
        pop_user_infor_event_vp.setHintView(new ColorPointHintView(context, Color.RED, Color.WHITE));
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
        ColorDrawable dw = new ColorDrawable(0x11111111);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha(context, 0.5f);//0.0-1.0
        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(context, 1f);
            }
        });
        iv_banner_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
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


}
