package com.yanhui.qktx.view.widgets;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;
import com.yanhui.qktx.R;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

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
            TimeButton.this.setText((time / 1000 + "秒") + textafter);
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
        final Dialog dialog1 = new Dialog(context);
        View contentView1 = LayoutInflater.from(context).inflate(
                R.layout.view_dialog_msg_code_chak, null);
        dialog1.setContentView(contentView1);
        dialog1.setTitle("配送费用");
        dialog1.setCanceledOnTouchOutside(true);
        ImageButton bt_resh_img = contentView1.findViewById(R.id.resh_img_bt);
        ImageButton bt_close = contentView1.findViewById(R.id.view_dialog_close);
        Button price_true = contentView1.findViewById(R.id.view_msg_code_ok);
        ImageView imageView = contentView1.findViewById(R.id.msg_code_imag);//展示验证码图片
        EditText msg_code_chak_et = contentView1.findViewById(R.id.msg_code_chak_et);
        RxView.clicks(price_true)
                .throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (!StringUtils.isEmpty(msg_code_chak_et.getText().toString())) {
                    HttpClient.getInstance().getMsgCode(number, msg_code_chak_et.getText().toString(), new NetworkSubscriber<BaseEntity>() {
                        @Override
                        public void onNext(BaseEntity data) {
                            if (data.isOKResult()) {
                                dialog1.dismiss();
                                SetTime();
                                ToastUtils.showToast(data.mes);
                            } else {
                                ToastUtils.showToast(data.mes);
                            }
                        }
                    });
                } else {
                    ToastUtils.showToast("图形验证码不能为空");
                }
            }
        });
        bt_resh_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                createGraphImage(context, number, imageView);
            }
        });
        bt_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        createGraphImage(context, number, imageView);
        dialog1.show();
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

    public void createGraphImage(Context context, String number, ImageView imageView) {
        if (!StringUtils.isEmpty(number)) {
            String img_url = "http://app.qukantianxia.com/user/createGraph.do?token=" + Math.random() + "&mobile=" + number;
            ImageLoad.intoNullPlace(context, img_url, imageView);
//            Glide.with(context).asBitmap().load(img_url).into(imageView);
        } else {
            ToastUtils.showToast("电话号码不能为空");
        }
    }

}
