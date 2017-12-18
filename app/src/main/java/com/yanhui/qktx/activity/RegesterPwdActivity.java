package com.yanhui.qktx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.yanhui.qktx.R;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.CommonUtil;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.view.widgets.TimeButton;

/**
 * Created by liupanpan on 2017/9/1.
 * 修改密码页面
 */

public class RegesterPwdActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_mobile, et_msg_code;
    private Button bt_next;
    private ImageButton bt_mobile_clean;
    private TimeButton timeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester_pwd);
        setTitleTextColor(R.color.black);
        setTopBarColor(R.color.white);
        setTitleText("找回密码");

    }

    @Override
    public void findViews() {
        super.findViews();
        timeButton = new TimeButton(this);
        et_mobile = (EditText) findViewById(R.id.activity_regrter_pwd_et_mobile);
        et_msg_code = (EditText) findViewById(R.id.activity_regeter_et_msg_code);
        timeButton = (TimeButton) findViewById(R.id.regeter_pwd__btn_get_msg_code);
        bt_next = (Button) findViewById(R.id.activity_regester_next);
        bt_mobile_clean = (ImageButton) findViewById(R.id.image_regeter_pwd_clean);
    }

    @Override
    public void bindListener() {
        super.bindListener();
        bt_mobile_clean.setOnClickListener(this);
        bt_next.setOnClickListener(this);
    }

    @Override
    public void bindData() {
        super.bindData();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtil.checkPhone(et_mobile.getText().toString())) {
                    timeButton.SendMsg(et_mobile.getText().toString(), RegesterPwdActivity.this, 1);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_regester_next:
                if (CommonUtil.checkPhone(et_mobile.getText().toString()) && !StringUtils.isEmpty(et_msg_code.getText().toString())) {
                    Intent intent = new Intent(this, NewPwdActivity.class).putExtra("code", et_msg_code.getText().toString()).putExtra("mobile", et_mobile.getText().toString());
                    HttpClient.getInstance().getvalidateCode(et_mobile.getText().toString(), et_msg_code.getText().toString(), new NetworkSubscriber<BaseEntity>(this) {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                startActivity(intent);
                                finish();
                            } else {
                                ToastUtils.showToast(data.mes);
                            }
                        }
                    });

                } else {
                    ToastUtils.showToast("手机号和验证码不能为空");
                }
                break;
            case R.id.image_regeter_pwd_clean:
                if (!StringUtils.isEmpty(et_mobile.getText().toString())) {
                    et_mobile.setText("");
                }
                break;
        }
    }
}
