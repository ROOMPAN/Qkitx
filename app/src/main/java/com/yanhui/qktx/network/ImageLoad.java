package com.yanhui.qktx.network;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yanhui.qktx.R;

/**
 * 图片请求封装
 * Created by liupanpan on 2017/6/28.
 */

public class ImageLoad {
    public static void into(Context context, String imageurl, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .override(300, 200)
                .centerCrop()
                .placeholder(R.drawable.img_news_default_big)//占位图片
//                .error(R.mipmap.ic_launcher)//出错图片
                .priority(Priority.HIGH) //优先级 最高 high 最低 low
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存
        Glide.with(context)
                .asBitmap()
                .apply(options)
                .load(imageurl)
                .into(imageView);

    }

    public static void intoNullPlace(Context context, String imageurl, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.color.)//占位图片
//                .error(R.mipmap.ic_launcher)//出错图片
                .priority(Priority.LOW) //优先级 最高 high 最低 low
                .diskCacheStrategy(DiskCacheStrategy.NONE);//缓存
        Glide.with(context)
                .asBitmap()
                .apply(options)
                .load(imageurl)
                .into(imageView);

    }
}
