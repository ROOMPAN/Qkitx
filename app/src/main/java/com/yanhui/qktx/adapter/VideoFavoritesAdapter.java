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

import com.jakewharton.rxbinding.view.RxView;
import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.HistoryListBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.ToastUtils;

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
 * Created by liupanpan on 2017/9/13.
 */

public class VideoFavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HistoryListBean.DataBean> listBean;

    public VideoFavoritesAdapter(Context context, List<HistoryListBean.DataBean> listBean) {
        this.context = context;
        this.listBean = listBean;
    }

    public void setData(List<HistoryListBean.DataBean> listBean) {
        this.listBean = listBean;
        notifyDataSetChanged();
    }

    public void addData(List<HistoryListBean.DataBean> listBean) {
        this.listBean.addAll(listBean);
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
            ((ViewHolder) holder).tv.setText(listBean.get(position).getTTitle());
            ((ViewHolder) holder).tv_favor_video_comment.setText(listBean.get(position).getCommentCount() + "评论");
            ImageLoad.into(context, listBean.get(position).getTImage(), ((ViewHolder) holder).iv_favor_video_img);
            ((ViewHolder) holder).tv_delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HttpClient.getInstance().getDeleteConnection(listBean.get(position).getTaskId(), new NetworkSubscriber<BaseEntity>() {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                listBean.remove(position);
                                notifyDataSetChanged();
                                ToastUtils.showToast(data.mes);
                            }
                        }
                    });
                }
            });
            RxView.clicks(((ViewHolder) holder).favor_video_item)
                    .throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(WEB_VIEW_LOAD_URL, listBean.get(position).getTaskUrl());
                    intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
                    intent.putExtra(TASKID, listBean.get(position).getTaskId());
                    intent.putExtra(ISCONN, listBean.get(position).getIsConn());
                    intent.putExtra(COMMENTS_NUM, listBean.get(position).getComments());
                    intent.putExtra(VIDEO_URL, listBean.get(position).getVideoUrl());
                    intent.putExtra(ARTICLETYPE, listBean.get(position).getArticleType());
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (listBean != null) {
            return listBean.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv, tv_favor_video_comment;
        ImageView iv_favor_video_img;
        TextView tv_delete_item;
        RelativeLayout favor_video_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
            tv_favor_video_comment = itemView.findViewById(R.id.tv_favor_video_comment);
            iv_favor_video_img = itemView.findViewById(R.id.iv_favor_video_img);
            tv_delete_item = itemView.findViewById(R.id.tv_favor_video_delete);
            favor_video_item = itemView.findViewById(R.id.favor_video_item);
        }
    }
}
