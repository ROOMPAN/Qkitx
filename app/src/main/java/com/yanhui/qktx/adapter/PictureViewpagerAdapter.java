package com.yanhui.qktx.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yanhui.qktx.R;

import java.util.List;

/**
 * Created by liupanpan on 2017/11/3.
 */

public class PictureViewpagerAdapter extends PagerAdapter {
    private List<String> image_url_list;
    private Activity activity;

    public PictureViewpagerAdapter(List<String> image_url_list, Activity activity) {
        this.image_url_list = image_url_list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if (image_url_list != null && image_url_list.size() != 0) {
            return image_url_list.size();
        }
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // 初始化显示的条目对象
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // return super.instantiateItem(container, position);
        View view = LayoutInflater.from(activity).inflate(R.layout.item_picture_image_view, container, false);
        ImageView imageView = view.findViewById(R.id.iv_picture_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        // 准备显示的数据，一个简单的TextView
//        TextView tv = new TextView(activity);
//        tv.setGravity(Gravity.CENTER);
//        tv.setTextSize(20);
//        tv.setBackgroundColor(activity.getResources().getColor(R.color.black));
//        tv.setText("我是天才" + position + "号");

        // 添加到ViewPager容器
        container.addView(view);

        // 返回填充的View对象
        return view;
    }

    // 销毁条目对象
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }


}
