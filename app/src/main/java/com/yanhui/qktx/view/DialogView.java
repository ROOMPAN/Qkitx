package com.yanhui.qktx.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.yanhui.qktx.R;

/**
 * Created by liupanpan on 2017/9/14.
 */

public class DialogView extends Dialog {
    private Button bt_close;

    public DialogView(@NonNull Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_dialog_net_work_erro);
        bt_close = findViewById(R.id.view_dialog_net_work_close);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
