package com.yanhui.qktx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.HistoryListBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.ToastUtils;

import static com.yanhui.qktx.constants.Constant.ARTICLETYPE;
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

public class VideoFavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private HistoryListBean listBean;

    public VideoFavoritesAdapter(Context context, HistoryListBean listBean) {
        this.context = context;
        this.listBean = listBean;
    }

    public void setData(HistoryListBean listBean) {
        this.listBean = listBean;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        View v = mInflater.inflate(R.layout.item_essay_favoite_video, parent, false);
        holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).tv.setText(listBean.getData().get(position).getTTitle());
            ImageLoad.into(context, listBean.getData().get(position).getTImage(), ((ViewHolder) holder).iv_favor_video_img);
            ((ViewHolder) holder).tv_delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HttpClient.getInstance().getDeleteConnection(listBean.getData().get(position).getTaskId(), new NetworkSubscriber<BaseEntity>() {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                listBean.getData().remove(position);
                                notifyDataSetChanged();
                                ToastUtils.showToast(data.mes);
                            }
                        }
                    });
                }
            });
            ((ViewHolder) holder).favor_video_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(WEB_VIEW_LOAD_URL, listBean.getData().get(position).getTaskUrl());
                    intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
                    intent.putExtra(TASKID, listBean.getData().get(position).getTaskId());
                    intent.putExtra(ISCONN, listBean.getData().get(position).getIsConn());
                    intent.putExtra(ARTICLETYPE, listBean.getData().get(position).getArticleType());
                    intent.putExtra(SHARE_URL, listBean.getData().get(position).getShareUrl());
                    intent.putExtra(SHARE_CONTEXT, listBean.getData().get(position).getTDesc());
                    intent.putExtra(SHARE_IMG_URL, listBean.getData().get(position).getTImage());
                    intent.putExtra(SHARE_TITLE, listBean.getData().get(position).getTTitle());
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (listBean != null) {
            return listBean.getData().size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv_favor_video_img;
        TextView tv_delete_item;
        RelativeLayout favor_video_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
            iv_favor_video_img = itemView.findViewById(R.id.iv_favor_video_img);
            tv_delete_item = itemView.findViewById(R.id.tv_favor_video_delete);
            favor_video_item = itemView.findViewById(R.id.favor_video_item);
        }
    }
}
