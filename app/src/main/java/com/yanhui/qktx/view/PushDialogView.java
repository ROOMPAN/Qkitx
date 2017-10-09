package com.yanhui.qktx.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;

import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/10/9.
 */

public class PushDialogView extends Dialog implements View.OnClickListener {
    private TextView tv_push_title;
    private Button bt_close, bt_star_web;
    private String title, url;
    private Context context;

    public PushDialogView(@NonNull Context context, String title, String url) {
        super(context);
        this.context = context;
        this.title = title;
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
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
                dismiss();
                break;
            case R.id.view_dialog_push_star_web:
                //跳转webview
                context.startActivity(new Intent(context, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, url));
                dismiss();
                break;
        }
    }
}
