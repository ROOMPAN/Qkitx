package com.yanhui.qktx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.MainFragmentPageAdapter;
import com.yanhui.qktx.fragment.BaseFragment;
import com.yanhui.qktx.fragment.EssayFavoritesFragment;
import com.yanhui.qktx.view.widgets.MainViewPager;

import java.util.ArrayList;

/**
 * Created by liupanpan on 2017/9/2.
 * 收藏页面
 */

public class FavoritesActivity extends BaseActivity {
    private TabLayout tabLayout;
    private MainViewPager vp_view;
    private ArrayList<BaseFragment> mFragmentList;
    private EssayFavoritesFragment essayFavoritesFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        setTitleText("我的收藏");
    }

    @Override
    public void findViews() {
        super.findViews();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        vp_view = (MainViewPager) findViewById(R.id.activity_favorites_viewpager);
    }

    @Override
    public void bindData() {
        super.bindData();
        mFragmentList = new ArrayList<>(2);
        essayFavoritesFragment = new EssayFavoritesFragment();
        mFragmentList.add(new EssayFavoritesFragment());
        mFragmentList.add(new EssayFavoritesFragment());
        vp_view.setAdapter(new MainFragmentPageAdapter(getSupportFragmentManager(), mFragmentList));
        tabLayout.setupWithViewPager(vp_view);
        tabLayout.getTabAt(0).setText("文章");
        tabLayout.getTabAt(1).setText("视频");
    }

    @Override
    public void bindListener() {
        super.bindListener();
    }
}
