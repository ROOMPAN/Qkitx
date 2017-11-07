package com.yanhui.qktx.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.business.BusinessManager;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.models.UserBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.CommonUtil;
import com.yanhui.qktx.utils.SharedPreferencesMgr;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;
import com.yanhui.statusbar_lib.flyn.Eyes;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liupanpan on 2017/8/25.
 * 登录页面
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
                if (CommonUtil.checkPhone(et_mobile.getText().toString()) && !StringUtils.isEmpty(et_pwd.getText().toString())) {
                    HttpClient.getInstance().getLogin(et_mobile.getText().toString(), et_pwd.getText().toString(), new NetworkSubscriber<UserBean>(this) {
                        @Override
                        public void onNext(UserBean data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                BusinessManager.getInstance().login();
                                InputMethodManager imm = (InputMethodManager) LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(et_pwd.getWindowToken(), 0);
                                Log.e("login", data.getData().toString() + "");
                                SharedPreferencesMgr.setString("token", data.getData().getToken());
                                SharedPreferencesMgr.setInt("userid", data.getData().getUserId());
                                SharedPreferencesMgr.setString("username", data.getData().getName());
                                ToastUtils.showToast(data.mes);
                                EventBus.getDefault().post(new BusEvent(EventConstants.EVENT_SWITCH_TO_HOME, 0));//切换到首页
                                if (data.getData().getIsFirstLogin() == 1) {
                                    EventBus.getDefault().post(new BusEvent(EventConstants.EVENT_SWITCH_TO_HOME, data.getData().getHbAmount()));//切换到首页
                                }
                                EventBus.getDefault().post(new BusEvent(EventConstants.EVEN_HOME_CATE));//切换fragmenthome 标题刷新
                                finish();
                            } else {
                                ToastUtils.showToast(data.mes);
                            }
                        }

                    });

                } else {
                    ToastUtils.showToast("账号或密码错误");
                }
                //UmengTool.getSignature(this);
//                new UMLoginThird(this);
                break;
            //注册
            case R.id.activity_login_regester_relay:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.activity_login_retrieve_pwd:
                startActivity(new Intent(this, RegesterPwdActivity.class));
                finish();
                break;
            case R.id.activity_login_about_us:
                startActivity(new Intent(this, WebViewActivity.class).putExtra(Constant.WEB_VIEW_LOAD_URL, "http://statics.qukantianxia.com/html/h5/qukantianxia/about.html"));
                break;
        }
    }
}
