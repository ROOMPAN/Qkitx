package com.yanhui.qktx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.network.ImageLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/9/7.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 纯文字布局(文章、广告)
     */
    private static final int TEXT_NEWS = 0;
    /**
     * 居中大图布局(1.单图文章；2.单图广告；3.视频，中间显示播放图标，右侧显示时长)
     */
    private static final int CENTER_SINGLE_PIC_NEWS = 1;
    /**
     * 右侧小图布局(1.小图新闻；2.视频类型，右下角显示视频时长)
     */
    private static final int RIGHT_PIC_VIDEO_NEWS = 2;
    /**
     * 三张图片布局(文章、广告)
     */
    private static final int THREE_PICS_NEWS = 3;

    /**
     * 视频列表类型
     */
    private static final int VIDEO_LIST_NEWS = 4;
    private Context mContext;
    private String mChannelCode;
    private RecyclerView mRecyclerView;
    private List<ArticleListBean.DataBean> mData = new ArrayList<>();


    public NewsAdapter(Context mContext, String mChannelCode, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mChannelCode = mChannelCode;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        if (THREE_PICS_NEWS == viewType) {
            View v = mInflater.inflate(R.layout.item_three_pics_news, parent, false);
            holder = new ThreeViewHolder(v);
        } else if (TEXT_NEWS == viewType) {
            View v = mInflater.inflate(R.layout.item_text_news, parent, false);
            holder = new NesViewHolder(v);
        } else if (RIGHT_PIC_VIDEO_NEWS == viewType) {
            View v = mInflater.inflate(R.layout.item_pic_video_news, parent, false);
            holder = new RightImgViewHolder(v);
        }
        return holder;

    }

    public void setData(List<ArticleListBean.DataBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<ArticleListBean.DataBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NesViewHolder) {
            ((NesViewHolder) holder).tv.setText(mData.get(position).getTTitle());
        } else if (holder instanceof RightImgViewHolder) {
            ((RightImgViewHolder) holder).tv1.setText(mData.get(position).getTTitle());
            ImageLoad.into(mContext, mData.get(position).getStrImages().get(0).getImage(), ((RightImgViewHolder) holder).iv_img);
        } else {
            ((ThreeViewHolder) holder).tv1.setText(mData.get(position).getTTitle());
            ImageLoad.into(mContext, mData.get(position).getStrImages().get(0).getImage(), ((ThreeViewHolder) holder).iv_img1);
            ImageLoad.into(mContext, mData.get(position).getStrImages().get(1).getImage(), ((ThreeViewHolder) holder).iv_img2);
            ImageLoad.into(mContext, mData.get(position).getStrImages().get(2).getImage(), ((ThreeViewHolder) holder).iv_img3);

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getStrImages().size() == 3) {
            return THREE_PICS_NEWS;
        } else if (mData.get(position).getStrImages().size() == 1) {
            return RIGHT_PIC_VIDEO_NEWS;
        } else {
            return TEXT_NEWS;
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null && mData.size() != 0) {
            return mData.size();
        }
        return 0;
    }

    class NesViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv_img;

        public NesViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }

    class ThreeViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        ImageView iv_img1, iv_img2, iv_img3;

        public ThreeViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_title);

            iv_img1 = itemView.findViewById(R.id.iv_img1);
            iv_img2 = itemView.findViewById(R.id.iv_img2);
            iv_img3 = itemView.findViewById(R.id.iv_img3);
        }
    }

    class RightImgViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        ImageView iv_img;

        public RightImgViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }
}



