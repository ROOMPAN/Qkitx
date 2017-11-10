package com.yanhui.qktx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.business.BusinessManager;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.models.UserBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.CommonUtil;
import com.yanhui.qktx.utils.SharedPreferencesMgr;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;
import com.yanhui.qktx.view.widgets.TimeButton;
import com.yanhui.statusbar_lib.flyn.Eyes;

import org.greenrobot.eventbus.EventBus;

import static com.yanhui.qktx.constants.Constant.GONE_BUTTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;


/**
 * Created by liupanpan on 2017/8/26.
 * 注册页面
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private TimeButton bt_send_msg;//发送验证码.
    private EditText et_mobile, et_pwd, et_msg_code;//电话号码
    private ImageView img_clean_pwd;
    private Button bt_show_pwd, bt_register;
    private boolean eyeOpen = false;
    private String mobile, pwd, msgcode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.status_color_red));
        setTitleText("注册");
    }

    @Override
    public void findViews() {
        super.findViews();
        bt_send_msg = new TimeButton(this);
        bt_send_msg = (TimeButton) findViewById(R.id.register_btn_get_msg_code);
        et_mobile = (EditText) findViewById(R.id.activity_register_et_mobile);
        et_pwd = (EditText) findViewById(R.id.activity_register_et_pwd);
        img_clean_pwd = (ImageView) findViewById(R.id.image_register_clean);
        et_msg_code = (EditText) findViewById(R.id.activity_register_et_msg_code);
        bt_show_pwd = (Button) findViewById(R.id.ctivity_register_show_pwd);
        bt_register = (Button) findViewById(R.id.activity_register_bt);
    }

    @Override
    public void bindData() {
        super.bindData();
        bt_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtil.checkPhone(et_mobile.getText().toString())) {
                    bt_send_msg.SendMsg(et_mobile.getText().toString(), RegisterActivity.this, 1);
                }
            }
        });


    }

    @Override
    public void bindListener() {
        super.bindListener();
        img_clean_pwd.setOnClickListener(this);
        bt_show_pwd.setOnClickListener(this);
        bt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_register_clean:
                if (!StringUtils.isEmpty(et_mobile.getText().toString())) {
                    et_mobile.setText("");
                }
                break;
            case R.id.ctivity_register_show_pwd:
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
            case R.id.activity_register_bt:
                mobile = et_mobile.getText().toString();
                pwd = et_pwd.getText().toString();
                msgcode = et_msg_code.getText().toString();
                if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(pwd) && !StringUtils.isEmpty(msgcode)) {
                    if (et_pwd.getText().length() >= 6) {
                        HttpClient.getInstance().getRegister(mobile, pwd, msgcode, new NetworkSubscriber<UserBean>(this) {
                            @Override
                            public void onNext(UserBean data) {
                                super.onNext(data);
                                if (data.isOKResult()) {
                                    ToastUtils.showToast("注册成功");
                                    Log.e("login", data.getData().toString() + "");
                                    Log.e("invite_code======", SharedPreferencesMgr.getString("invite_code", "") + data.getData().getIsFirstLogin());
                                    SharedPreferencesMgr.setString("token", data.getData().getToken());
                                    SharedPreferencesMgr.setInt("userid", data.getData().getUserId());
                                    SharedPreferencesMgr.setString("username", data.getData().getName());
                                    SharedPreferencesMgr.setString("headurl", data.getData().getHeadUrl());
                                    SharedPreferencesMgr.setInt("age", data.getData().getAge());
                                    BusinessManager.getInstance().login();
                                    EventBus.getDefault().post(new BusEvent(EventConstants.EVENT_SWITCH_TO_HOME, data.getData().getHbAmount()));//切换到首页
                                    startActivity(new Intent(RegisterActivity.this, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, SharedPreferencesMgr.getString("invite_code", "")).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM));
                                    finish();
                                } else {
                                    ToastUtils.showToast(data.mes);
                                }
                            }
                        });
                    } else {
                        ToastUtils.showToast("密码长度太短,至少6位");
                    }
                } else {
                    ToastUtils.showToast("手机号或密码不能为空!!");
                }
                break;
        }
    }
}
