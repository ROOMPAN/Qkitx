package com.yanhui.qktx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yanhui.qktx.R;
import com.yanhui.qktx.utils.UIUtils;
import com.yanhui.statusbar_lib.flyn.Eyes;

/**
 * Created by liupanpan on 2017/8/26.
 */

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.status_color_red));
        setTitleText("注册");
        setGoneRight();
    }

    @Override
    public void findViews() {
        super.findViews();
    }

    @Override
    public void bindData() {
        super.bindData();
    }

    @Override
    public void bindListener() {
        super.bindListener();
    }
}
