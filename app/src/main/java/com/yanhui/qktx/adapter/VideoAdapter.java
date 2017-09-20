package com.yanhui.qktx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.models.News;

import java.util.ArrayList;

/**
 * Created by liupanpan on 2017/9/8.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    private String mCateId;
    private RecyclerView mRecyclerView;
    private ArrayList<News> mData = new ArrayList<>();


    public VideoAdapter(Context mContext, String mCateId, RecyclerView mRecyclerView, ArrayList<News> data) {
        this.mContext = mContext;
        this.mCateId = mCateId;
        this.mRecyclerView = mRecyclerView;
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        View v = mInflater.inflate(R.layout.item_fragment_video, parent, false);
        holder = new TwoViewHolder(v);
        return holder;

    }

    public void setData(ArrayList<News> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<News> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OneViewHolder) {
            ((OneViewHolder) holder).tv.setText(mData.get(position).getTitle());
        } else {
            ((TwoViewHolder) holder).tv1.setText(mData.get(position).getTitle());
        }

    }

    @Override
    public int getItemCount() {
        if (mData != null && mData.size() != 0) {
            return mData.size();
        }
        return 0;
    }

    class OneViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public OneViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
        }
    }

    class TwoViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;

        public TwoViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_title);
        }
    }
}

