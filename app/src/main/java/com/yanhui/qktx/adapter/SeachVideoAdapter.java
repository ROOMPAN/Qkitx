package com.yanhui.qktx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

import static com.yanhui.qktx.constants.Constant.ARTICLETYPE;
import static com.yanhui.qktx.constants.Constant.COMMENTS_NUM;
import static com.yanhui.qktx.constants.Constant.ISCONN;
import static com.yanhui.qktx.constants.Constant.SHOW_BUTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.TASKID;
import static com.yanhui.qktx.constants.Constant.VIDEO_URL;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/9/22.
 * 视频搜索适配器
 */

public class SeachVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ArticleListBean.DataBean> mData = new ArrayList<>();

    public SeachVideoAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        View v = mInflater.inflate(R.layout.item_fragment_video, parent, false);
        holder = new VideoViewHolder(v);
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
        if (holder instanceof VideoViewHolder) {
            ((VideoViewHolder) holder).tv_title.setText(mData.get(position).getTTitle());
            ImageLoad.into(mContext, mData.get(position).getTImage(), ((VideoViewHolder) holder).iv_img);
            ((VideoViewHolder) holder).iv_comment.setVisibility(View.VISIBLE);
            ((VideoViewHolder) holder).tv_comment_num.setText("2");
            ((VideoViewHolder) holder).tv_time_year.setText(TimeUtils.getShortTime(mData.get(position).getLastModifyTime() * 1000));
            RxView.clicks(((VideoViewHolder) holder).iv_img)
                    .throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(WEB_VIEW_LOAD_URL, mData.get(position).getTaskUrl());
                    intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
                    intent.putExtra(TASKID, mData.get(position).getTaskId());
                    intent.putExtra(ISCONN, mData.get(position).getIsConn());
                    intent.putExtra(COMMENTS_NUM, mData.get(position).getComments());
                    intent.putExtra(VIDEO_URL, mData.get(position).getVideoUrl());
                    intent.putExtra(ARTICLETYPE, mData.get(position).getArticleType());
                    mContext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        // 标题 来源 时间日期 评论数 视频时长
        TextView tv_title, tv_time_year, tv_comment_num;
        ImageView iv_img, iv_comment;

        public VideoViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_time_year = itemView.findViewById(R.id.tv_time_year);
            iv_comment = itemView.findViewById(R.id.iv_comment);
            tv_comment_num = itemView.findViewById(R.id.tv_bottom_comment_max);
        }
    }
}
