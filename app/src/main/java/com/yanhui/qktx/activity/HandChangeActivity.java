package com.yanhui.qktx.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.yanhui.qktx.R;

/**
 * Created by liupanpan on 2017/8/31.
 */

public class HandChangeActivity extends Activity implements View.OnClickListener {
    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private Uri photoUri;
    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_check_photo);
        btn_take_photo = findViewById(R.id.btn_take_photo);
        btn_pick_photo = findViewById(R.id.btn_pick_photo);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_photo:
                //照相机
                break;
            case R.id.btn_pick_photo:
                //图库
                break;
            case R.id.btn_cancel:
                finish();
                break;
            default:
                break;
        }
    }
}
