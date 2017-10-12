package com.yanhui.qktx.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.models.PersonBean;
import com.yanhui.qktx.network.ImageLoad;

import java.util.List;

import static com.yanhui.qktx.constants.Constant.GONE_BUTTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/8/25.
 */

public class TestNomalAdapter extends StaticPagerAdapter {
    private Context context;
    private List<PersonBean.DataBeanX.BannerBean> bnnerlist;

    public TestNomalAdapter(Context context, List<PersonBean.DataBeanX.BannerBean> bnnerlist) {
        this.context = context;
        this.bnnerlist = bnnerlist;
    }

    private int[] imgs = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
    };

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView imgview = new ImageView(container.getContext());
//        view.setImageResource(imgs[position]);
        ImageLoad.into(context, bnnerlist.get(position).getImgUrl(), imgview);
        imgview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bnnerlist.get(position).getSkipUrl() != null) {
                    context.startActivity(new Intent(context, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, bnnerlist.get(position).getSkipUrl()).putExtra(SHOW_WEB_VIEW_BUTTOM, GONE_BUTTOM));
                }
            }
        });
        imgview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return imgview;
    }

    @Override
    public int getCount() {
        if (bnnerlist != null) {

            return bnnerlist.size();
        }
        return 0;
    }
}
