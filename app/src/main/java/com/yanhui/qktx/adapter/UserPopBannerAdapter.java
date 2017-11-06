package com.yanhui.qktx.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.yanhui.qktx.R;
import com.yanhui.qktx.models.PersonBean;
import com.yanhui.qktx.utils.ToastUtils;

import java.util.List;

/**
 * Created by liupanpan on 2017/11/2.
 * 个人中心弹出框 pop 适配器
 */

public class UserPopBannerAdapter extends StaticPagerAdapter {
    private Context context;
    private List<PersonBean.DataBeanX.BannerBean> bnnerlist;

    public UserPopBannerAdapter(Context context) {
        this.context = context;
    }

    private int[] imgs = {
            R.drawable.img_login_redpackage,
            R.drawable.img_login_redpackage,
            R.drawable.img_login_redpackage,
            R.drawable.img_login_redpackage,
    };

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView imgview = new ImageView(container.getContext());
        imgview.setImageResource(imgs[position]);
//        ImageLoad.intoNullPlace(context, bnnerlist.get(position).getImgUrl(), imgview);
//        imgview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(position + "");
            }
        });
        imgview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return imgview;
    }

    @Override
    public int getCount() {
        if (imgs != null) {

            return imgs.length;
        }
        return 0;
    }
}

