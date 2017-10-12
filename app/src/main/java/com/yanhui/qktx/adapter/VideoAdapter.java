package com.yanhui.qktx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.network.ImageLoad;

import java.util.ArrayList;
import java.util.List;

import static com.yanhui.qktx.constants.Constant.ARTICLETYPE;
import static com.yanhui.qktx.constants.Constant.COMMENTS_NUM;
import static com.yanhui.qktx.constants.Constant.ISCONN;
import static com.yanhui.qktx.constants.Constant.SHARE_CONTEXT;
import static com.yanhui.qktx.constants.Constant.SHARE_IMG_URL;
import static com.yanhui.qktx.constants.Constant.SHARE_TITLE;
import static com.yanhui.qktx.constants.Constant.SHARE_URL;
import static com.yanhui.qktx.constants.Constant.SHOW_BUTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.TASKID;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/9/8.
 * 视频列表适配器
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private String mCateId;
    private RecyclerView mRecyclerView;
    private List<Object> mData = new ArrayList<>();
    private BGARefreshLayout mRefreshLayout;


    public VideoAdapter(Context mContext, String mCateId, RecyclerView mRecyclerView, BGARefreshLayout mRefreshLayout) {
        this.mContext = mContext;
        this.mCateId = mCateId;
        this.mRecyclerView = mRecyclerView;
        this.mRefreshLayout = mRefreshLayout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        View v = mInflater.inflate(R.layout.item_fragment_video, parent, false);
        holder = new OneViewHolder(v);
        return holder;

    }

    public void setData(List data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(List data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OneViewHolder) {
            ((OneViewHolder) holder).tv.setText(((ArticleListBean.DataBean) mData.get(position)).getTTitle());
            ImageLoad.into(mContext, ((ArticleListBean.DataBean) mData.get(position)).getTImage(), ((OneViewHolder) holder).iv_img);
            ((OneViewHolder) holder).tv_time.setText("");
            if (position == 8) {
                ((OneViewHolder) holder).item_video_last_resh_linner.setVisibility(View.VISIBLE);
            } else {
                ((OneViewHolder) holder).item_video_last_resh_linner.setVisibility(View.GONE);
            }
            ((OneViewHolder) holder).item_video_last_resh_linner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRefreshLayout.beginRefreshing();
                }
            });
            if (((ArticleListBean.DataBean) mData.get(position)).getIsRead() == 1) {//获取当前的数据是都是已读状态
                ((OneViewHolder) holder).tv.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
            } else {
                ((OneViewHolder) holder).tv.setTextColor(mContext.getResources().getColor(R.color.common_text_color));
            }
            ((OneViewHolder) holder).iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳转到 webview 页面
                    ((ArticleListBean.DataBean) mData.get(position)).setIsRead(1);//设置已读状态
                    ((OneViewHolder) holder).tv.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(WEB_VIEW_LOAD_URL, ((ArticleListBean.DataBean) mData.get(position)).getTaskUrl());
                    intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
                    intent.putExtra(TASKID, ((ArticleListBean.DataBean) mData.get(position)).getTaskId());
                    intent.putExtra(COMMENTS_NUM, ((ArticleListBean.DataBean) mData.get(position)).getComments());
                    intent.putExtra(ISCONN, ((ArticleListBean.DataBean) mData.get(position)).getIsConn());
                    intent.putExtra(ARTICLETYPE, ((ArticleListBean.DataBean) mData.get(position)).getArticleType());
                    intent.putExtra(SHARE_URL, ((ArticleListBean.DataBean) mData.get(position)).getShareUrl());
                    intent.putExtra(SHARE_CONTEXT, ((ArticleListBean.DataBean) mData.get(position)).getTDesc());
                    intent.putExtra(SHARE_IMG_URL, ((ArticleListBean.DataBean) mData.get(position)).getTImage());
                    intent.putExtra(SHARE_TITLE, ((ArticleListBean.DataBean) mData.get(position)).getTTitle());
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
        LinearLayout item_video_last_resh_linner;

        public OneViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            iv_share_more = itemView.findViewById(R.id.iv_share_more);
            iv_comment = itemView.findViewById(R.id.iv_comment);
            tv_time = itemView.findViewById(R.id.tv_time_year);
            item_video_last_resh_linner = itemView.findViewById(R.id.item_video_last_resh_linner);
            tv_bottom_comment_max = itemView.findViewById(R.id.tv_bottom_comment_max);
        }
    }

}

