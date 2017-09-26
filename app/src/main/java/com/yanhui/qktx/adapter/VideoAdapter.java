package com.yanhui.qktx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.network.ImageLoad;

import java.util.ArrayList;
import java.util.List;

import static com.yanhui.qktx.constants.Constant.ARTICLETYPE;
import static com.yanhui.qktx.constants.Constant.SHOW_BUTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.TASKID;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/9/8.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private String mCateId;
    private RecyclerView mRecyclerView;
    private List<ArticleListBean.DataBean> mData = new ArrayList<>();


    public VideoAdapter(Context mContext, String mCateId, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mCateId = mCateId;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        View v = mInflater.inflate(R.layout.item_fragment_video, parent, false);
        holder = new OneViewHolder(v);
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
        if (holder instanceof OneViewHolder) {
            ((OneViewHolder) holder).tv.setText(mData.get(position).getTTitle());
            ImageLoad.into(mContext, mData.get(position).getTImage(), ((OneViewHolder) holder).iv_img);
            ((OneViewHolder) holder).tv_time.setText("");
            ((OneViewHolder) holder).iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(WEB_VIEW_LOAD_URL, mData.get(position).getTUrl());
                    intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
                    intent.putExtra(TASKID, mData.get(position).getTaskId());
                    intent.putExtra(ARTICLETYPE, mData.get(position).getArticleType());
                    mContext.startActivity(intent);
                }
            });
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
        TextView tv, tv_bottom_comment_max, tv_time;
        ImageView iv_img, iv_comment, iv_share_more;

        public OneViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            iv_share_more = itemView.findViewById(R.id.iv_share_more);
            iv_comment = itemView.findViewById(R.id.iv_comment);
            tv_time = itemView.findViewById(R.id.tv_time_year);
            tv_bottom_comment_max = itemView.findViewById(R.id.tv_bottom_comment_max);
        }
    }

}

