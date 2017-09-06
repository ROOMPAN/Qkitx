package com.yanhui.qktx.view.widgets;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.yanhui.qktx.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by liupanpan on 2017/8/30.
 */

public class TimeButton extends Button implements View.OnClickListener {
    private long lenght = 60 * 1000;// 倒计时长度,这里默认60秒
    private String textafter = "后重发";
    private String textbefore = "重发验证码";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    private long time;
    private boolean SENDMSG = false;


    Map<String, Long> map = new HashMap<String, Long>();

    public TimeButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    private void clearTimer() {
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }

    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            TimeButton.this.setText((time / 1000 + "s") + textafter);
            time -= 1000;
            if (time < 0) {
                TimeButton.this.setEnabled(true);
                TimeButton.this.setText(textbefore);
                ColorStateList whiteColor = getResources().getColorStateList(R.color.white);
                TimeButton.this.setTextColor(whiteColor);
                TimeButton.this.setBackgroundResource(R.drawable.shape_register_get_verification_code);
                clearTimer();
            }
        }
    };

    private void initTimer() {
        time = lenght;
        t = new Timer();
        tt = new TimerTask() {

            @Override
            public void run() {
                han.sendEmptyMessage(0x01);
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (mOnclickListener != null)
            mOnclickListener.onClick(v);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimeButton) {
            super.setOnClickListener(l);
        } else
            this.mOnclickListener = l;
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        if (TimeApplation.map == null)
            TimeApplation.map = new HashMap<String, Long>();
        TimeApplation.map.put(TIME, time);
        TimeApplation.map.put(CTIME, System.currentTimeMillis());
        clearTimer();
    }

    /**
     * 和activity的onCreate()方法同步
     */
    public void onCreate(Bundle bundle) {
        if (TimeApplation.map == null)
            return;
        if (TimeApplation.map.size() <= 0)// 这里表示没有上次未完成的计时
            return;
        long time = System.currentTimeMillis() - TimeApplation.map.get(CTIME)
                - TimeApplation.map.get(TIME);
        TimeApplation.map.clear();
        if (time > 0)
            return;
        else {
            initTimer();
            this.time = Math.abs(time);
            t.schedule(tt, 0, 1000);
            this.setText(time + textafter);
            this.setEnabled(false);
        }
    }

    public static class TimeApplation extends Application {
        // 用于存放倒计时时间
        public static Map<String, Long> map;
    }

    /**
     * 设置计时时候显示的文本
     */
    public TimeButton setTextAfter(String text1) {
        this.textafter = text1;
        return this;
    }

    /**
     * 设置点击之前的文本
     */
    public TimeButton setTextBefore(String text0) {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }

    /**
     * 设置到计时长度
     *
     * @param lenght 时间 默认毫秒
     * @return
     */
    public TimeButton setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }

    public void SendMsg(String number, Context context, int type) {
//        HttpClient.getInstance().getMsgCode(number, new NetworkSubscriber<BaseEntity>() {
//            @Override
//            public void onNext(BaseEntity data) {
//                Log.e("message", "" + data.result);
//                if (data.isOKCode()) {
//                    SetTime();
//                    ToastUtils.showToast("发送验证码成功");
//                } else {
//                    ToastUtils.showToast("发送验证码失败");
//                }
//            }
//        });


    }

    private void SetTime() {
        initTimer();
        this.setText(time / 1000 + textafter);
        this.setEnabled(false);
        ColorStateList relacolor = getResources().getColorStateList(R.color.forget_password);
        this.setTextColor(relacolor);
        this.setBackgroundResource(R.drawable.shape_register_get_ver_code_not_click);
        t.schedule(tt, 0, 1000);
    }

}
