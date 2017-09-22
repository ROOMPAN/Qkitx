package com.yanhui.qktx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhui.qktx.R;

/**
 * Created by liupanpan on 2017/9/22.
 * 视频搜索适配器
 */

public class SeachVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    public SeachVideoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        View v = mInflater.inflate(R.layout.item_fragment_video, parent, false);
        holder = new SeachVideoAdapter.VideoViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoViewHolder) {
            // do setText();
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        // 标题 来源 时间日期 评论数 视频时长
        TextView tv_title, tv_author, tv_time_year, tv_comment_num, tv_bottom_right;
        ImageView iv_img;

        public VideoViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_bottom_right = itemView.findViewById(R.id.tv_bottom_right);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_time_year = itemView.findViewById(R.id.tv_time_year);
            tv_comment_num = itemView.findViewById(R.id.tv_comment_num);
        }
    }
}
