package com.yanhui.qktx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yanhui.qktx.R;

/**
 * Created by liupanpan on 2017/9/1.
 * 关于趣看天下
 */

public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitleText("关于");
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
