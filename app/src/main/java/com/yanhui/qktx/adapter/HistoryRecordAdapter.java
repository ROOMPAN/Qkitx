package com.yanhui.qktx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.models.HistoryListBean;
import com.yanhui.qktx.network.ImageLoad;

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
 * Created by liupanpan on 2017/9/13.
 */

public class HistoryRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<HistoryListBean.DataBean> list_data;
    private AdapterView.OnItemClickListener mOnItemClickListener = null;

    public HistoryRecordAdapter(Context context, List<HistoryListBean.DataBean> list_data) {
        this.mContext = context;
        this.list_data = list_data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        View v = mInflater.inflate(R.layout.item_left_image_news, parent, false);
        holder = new HistoryViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HistoryViewHolder) {
            ((HistoryViewHolder) holder).tv_title.setText(list_data.get(position).getTTitle());
            ((HistoryViewHolder) holder).left_news_buttom_linner.setVisibility(View.GONE);
            if (list_data.get(position).getStrImages().size() != 0) {
                ImageLoad.into(mContext, list_data.get(position).getStrImages().get(0).getImage(), ((HistoryViewHolder) holder).iv_img);
            } else {
                ((HistoryViewHolder) holder).item_news_img_rela.setVisibility(View.GONE);
            }
            ((HistoryViewHolder) holder).item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(WEB_VIEW_LOAD_URL, list_data.get(position).getTaskUrl());
                    intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
                    intent.putExtra(TASKID, list_data.get(position).getTaskId());
                    intent.putExtra(ISCONN, list_data.get(position).getIsConn());
                    intent.putExtra(COMMENTS_NUM, list_data.get(position).getComments());
                    intent.putExtra(ARTICLETYPE, list_data.get(position).getArticleType());
                    intent.putExtra(SHARE_URL, list_data.get(position).getShareUrl());
                    intent.putExtra(SHARE_CONTEXT, list_data.get(position).getTDesc());
                    intent.putExtra(SHARE_IMG_URL, list_data.get(position).getTImage());
                    intent.putExtra(SHARE_TITLE, list_data.get(position).getTTitle());
                    mContext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (list_data != null) {
            return list_data.size();
        }
        return list_data.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView iv_img;
        LinearLayout item_layout, left_news_buttom_linner;
        RelativeLayout item_news_img_rela;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            item_layout = itemView.findViewById(R.id.item_left_layout);
            item_news_img_rela = itemView.findViewById(R.id.item_news_img_rela);
            left_news_buttom_linner = itemView.findViewById(R.id.left_news_buttom_linner);
        }

    }

}
