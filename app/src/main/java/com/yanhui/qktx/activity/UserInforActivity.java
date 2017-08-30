package com.yanhui.qktx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yanhui.qktx.R;

/**
 * 用户信息修改
 * Created by liupanpan on 2017/8/30.
 */

public class UserInforActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);
        setTitleText("完善资料");
        setGoneRight();
    }
}
