package com.yanhui.qktx.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.yanhui.qktx.R;

/**
 * Created by liupanpan on 2017/12/19.
 * 用户注册提示 dialogview
 */

public class RegistrPromptsDialog extends Dialog {
    private Button bt_cancle, bt_go_login;
    private Activity lastactivity;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setCancelable(false);
        setContentView(R.layout.activity_user_register_prompts);
        bt_cancle = findViewById(R.id.bt_activity_user_register_prompts_cancle);
        bt_go_login = findViewById(R.id.bt_activity_user_register_prompts_go_login);
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        bt_go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
                lastactivity.finish();
                lastactivity.startActivity(new Intent(lastactivity, LoginActivity.class));
            }
        });
    }

    @SuppressLint("ValidFragment")
    public RegistrPromptsDialog(@NonNull Context context, Activity lastactivity) {
        super(context);
        this.lastactivity = lastactivity;
        this.context = context;
    }

}
