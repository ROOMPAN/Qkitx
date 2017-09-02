package com.yanhui.qktx.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.umlogin.UMLoginThird;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.UIUtils;
import com.yanhui.statusbar_lib.flyn.Eyes;

/**
 * Created by liupanpan on 2017/8/25.
 * 登录页面
 *
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_mobile;
    private ImageButton image_login_clean;
    private EditText et_pwd;
    private Button bt_show_pwd;
    private Button activity_login;
    private RelativeLayout bt_login_regester;
    private boolean eyeOpen = false;
    public static final int ACTIVITY_GET_IMAGE = 0;
    private TextView tv_retrieve_pwd, tv_about_us;

    private static String shareTitle = "有问题吗？真的有问题吗？";
    private static String shareContent = "请点击查看答案";
    private static String shareImageUrl = "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg";
    private static String sharejumpUrl = "https://www.baidu.com/index.php?tn=monline_3_dg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.status_color_red));
        setTitleText("登录");
        setGoneRight();
    }

    @Override
    public void findViews() {
        super.findViews();
        et_mobile = (EditText) findViewById(R.id.activity_login_et_mobile);
        image_login_clean = (ImageButton) findViewById(R.id.image_login_clean);
        et_pwd = (EditText) findViewById(R.id.activity_login_et_pwd);
        bt_show_pwd = (Button) findViewById(R.id.ctivity_login_show_pwd);
        activity_login = (Button) findViewById(R.id.activity_login);
        bt_login_regester = (RelativeLayout) findViewById(R.id.activity_login_regester_relay);
        tv_retrieve_pwd = (TextView) findViewById(R.id.activity_login_retrieve_pwd);
        tv_about_us = (TextView) findViewById(R.id.activity_login_about_us);
        tv_about_us.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tv_retrieve_pwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tv_about_us.getPaint().setAntiAlias(true);//抗锯齿
        tv_retrieve_pwd.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    public void bindListener() {
        super.bindListener();
        image_login_clean.setOnClickListener(this);
        bt_show_pwd.setOnClickListener(this);
        activity_login.setOnClickListener(this);
        bt_login_regester.setOnClickListener(this);
        tv_retrieve_pwd.setOnClickListener(this);
        tv_about_us.setOnClickListener(this);
    }

    @Override
    public void bindData() {
        super.bindData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            清楚手机号
            case R.id.image_login_clean:
                et_mobile.setText("");
                break;
            //显示密码
            case R.id.ctivity_login_show_pwd:
                if (!StringUtils.isEmpty(et_pwd.getText().toString())) {
                    if (eyeOpen) {
                        et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        bt_show_pwd.setText(R.string.show_pwd);
                        eyeOpen = false;
                    } else {
                        et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        bt_show_pwd.setText(R.string.gone_pwd);
                        eyeOpen = true;
                    }
                }
                break;
            //登录
            case R.id.activity_login:
//                HttpClient.getInstance().getVirLi("0", "8181", new NetworkSubscriber<VirtualBean>(this) {
//                    @Override
//                    public void onNext(VirtualBean data) {
//                        super.onNext(data);
//                        if (data.isOKCode()) {
//                            Log.e("datas", "" + data.getBody().getBrandList().get(0).getBrandName());
//                        }
//                    }
//                });

                //UmengTool.getSignature(this);
                new UMLoginThird(this);
                break;
            //注册
            case R.id.activity_login_regester_relay:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.activity_login_retrieve_pwd:
                startActivity(new Intent(this, RegesterPwdActivity.class));
                break;
            case R.id.activity_login_about_us:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
    }
}
