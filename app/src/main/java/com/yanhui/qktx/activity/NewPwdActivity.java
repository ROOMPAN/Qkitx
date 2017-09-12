package com.yanhui.qktx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yanhui.qktx.R;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;

/**
 * Created by liupanpan on 2017/9/1.
 * 设置密码页面
 */

public class NewPwdActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_new_pwd, et_new_pwd_next;
    private Button bt_show_new_pwd;
    private Button bt_submit;
    private boolean eyeOpen = false;
    private String msg_code;
    private String mobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pwd);
        setTitleText("设置密码");
    }

    @Override
    public void findViews() {
        super.findViews();
        msg_code = getIntent().getStringExtra("code");
        mobile = getIntent().getStringExtra("mobile");
        et_new_pwd = (EditText) findViewById(R.id.activity_new_pwd);
        et_new_pwd_next = (EditText) findViewById(R.id.activity_new_pwd_next);
        bt_show_new_pwd = (Button) findViewById(R.id.ctivity_new_pwd_show_pwd);
        bt_submit = (Button) findViewById(R.id.activity_new_pwd_submit);
    }

    @Override
    public void bindData() {
        super.bindData();
    }

    @Override
    public void bindListener() {
        super.bindListener();
        bt_show_new_pwd.setOnClickListener(this);
        bt_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ctivity_new_pwd_show_pwd:
                if (!StringUtils.isEmpty(et_new_pwd_next.getText().toString())) {
                    if (eyeOpen) {
                        et_new_pwd_next.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        bt_show_new_pwd.setText(R.string.show_pwd);
                        eyeOpen = false;
                    } else {
                        et_new_pwd_next.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        bt_show_new_pwd.setText(R.string.gone_pwd);
                        eyeOpen = true;
                    }
                }
                break;
            case R.id.activity_new_pwd_submit:
                if (!StringUtils.isEmpty(et_new_pwd.getText().toString()) && !StringUtils.isEmpty(et_new_pwd_next.getText().toString()) && et_new_pwd.getText().toString().equals(et_new_pwd_next.getText().toString()
                )) {
                    HttpClient.getInstance().getForgetPwd(mobile, et_new_pwd_next.getText().toString(), msg_code, new NetworkSubscriber<BaseEntity>(this) {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                ToastUtils.showToast(data.mes);
                                finish();
                            } else {
                                ToastUtils.showToast(data.mes);
                            }
                        }
                    });
                } else {
                    ToastUtils.showToast("密码不一致");
                }

                break;
        }
    }
}
