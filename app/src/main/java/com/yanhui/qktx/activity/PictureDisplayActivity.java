package com.yanhui.qktx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.PictureViewpagerAdapter;

/**
 * Created by liupanpan on 2017/11/3.
 * 图片展示页面((web)新闻列表图片)
 */

public class PictureDisplayActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager picture_view_pager;
    private TextView tv_title;
    private ImageView iv_left_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_display);
        setGoneTopBar();
    }

    @Override
    public void findViews() {
        super.findViews();
        tv_title = (TextView) findViewById(R.id.activity_picture_title_text);
        iv_left_back = (ImageView) findViewById(R.id.activity_picture_topbar_left_back_img);
        picture_view_pager = (ViewPager) findViewById(R.id.activity_picture_display_viewpager);

    }

    @Override
    public void bindData() {
        super.bindData();
        picture_view_pager.setAdapter(new PictureViewpagerAdapter(null, this));
        picture_view_pager.addOnPageChangeListener(this);
    }

    @Override
    public void bindListener() {
        super.bindListener();
        iv_left_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_picture_topbar_left_back_img:
                finish();
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int pin = picture_view_pager.getCurrentItem() + 1;
        tv_title.setText(pin + "/" + "5");
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
