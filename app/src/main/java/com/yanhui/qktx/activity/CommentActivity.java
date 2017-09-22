package com.yanhui.qktx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yanhui.qktx.R;

/**
 * Created by liupanpan on 2017/9/22.
 * 评论页面
 */

public class CommentActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setTitleText("点赞是一种态度");
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
