package com.yanhui.qktx.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.models.PushBean;

import static com.yanhui.qktx.constants.Constant.SHOW_BUTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.TASKID;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/10/9.
 */

public class PushDialogView extends Dialog implements View.OnClickListener {
    private TextView tv_push_title;
    private Button bt_close, bt_star_web;
    private String title, comust_json;
    private Context context;
    private static PushDialogView pushDialogView;

    public static PushDialogView getInstent(@NonNull Context context, String title, String comust_json) {
        if (pushDialogView == null) {
            pushDialogView = new PushDialogView(context, title, comust_json);
        }
        return pushDialogView;
    }

    public PushDialogView(@NonNull Context context, String title, String comust_json) {
        super(context);
        this.context = context;
        this.title = title;
        this.comust_json = comust_json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setCancelable(false);
        setContentView(R.layout.view_push_dialog);
        tv_push_title = findViewById(R.id.tv_push_title);
        bt_close = findViewById(R.id.view_dialog_push_close);
        bt_star_web = findViewById(R.id.view_dialog_push_star_web);
        bt_star_web.setOnClickListener(this);
        bt_close.setOnClickListener(this);
        tv_push_title.setText(title + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_dialog_push_close:
                //关闭
                if (isShowing()) {
                    dismiss();
                    pushDialogView = null;
                }
                break;
            case R.id.view_dialog_push_star_web:
                //跳转webview
                PushBean pushBean = new Gson().fromJson(comust_json, PushBean.class);
                Log.e("msg_custom", "" + pushBean.getTaskId() + "" + pushBean.getTaskUrl());
                context.startActivity(new Intent(context, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, pushBean.getTaskUrl()).putExtra(TASKID, pushBean.getTaskId()).putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM));
                pushDialogView = null;
                dismiss();
                break;
        }
    }
}
