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
import com.jakewharton.rxbinding.view.RxView;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.network.ImageLoad;

import java.util.ArrayList;
import java.util.HashMap;
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
 * Created by liupanpan on 2017/9/8.
 * 视频列表适配器
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private String mCateId;
    private RecyclerView mRecyclerView;
    private List<Object> mData = new ArrayList<>();
    private BGARefreshLayout mRefreshLayout;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap;
    /**
     * 视频数据
     */
    private static final int VIDEO_LIST_TEXT = 0;

    /**
     * 广告位
     */
    private static final int CENTER_SINGLE_PIC_NEWS = 1;


    public VideoAdapter(Context mContext, String mCateId, RecyclerView mRecyclerView, BGARefreshLayout mRefreshLayout, HashMap<NativeExpressADView, Integer> mAdViewPositionMap) {
        this.mContext = mContext;
        this.mCateId = mCateId;
        this.mRecyclerView = mRecyclerView;
        this.mRefreshLayout = mRefreshLayout;
        this.mAdViewPositionMap = mAdViewPositionMap;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        if (viewType == VIDEO_LIST_TEXT) {
            View v = mInflater.inflate(R.layout.item_fragment_video, parent, false);
            holder = new OneViewHolder(v);
        } else if (viewType == CENTER_SINGLE_PIC_NEWS) {
            View v = mInflater.inflate(R.layout.item_express_ad, parent, false);
            holder = new TenCentViewHolder(v);
        }
        return holder;

    }

    // 把返回的NativeExpressADView添加到数据集里面去
    public void addADViewToPosition(int position, NativeExpressADView adView) {
        if (position >= 0 && position < mData.size() && adView != null) {
            mData.add(position, adView);
            notifyDataSetChanged();
        }
    }

    // 移除NativeExpressADView的时候是一条一条移除的
    public void removeADView(int position, NativeExpressADView adView) {
        mData.remove(position);
        notifyItemRemoved(position); // position为adView在当前列表中的位置
        notifyItemRangeChanged(0, mData.size() - 1);
        notifyDataSetChanged();

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
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof NativeExpressADView) {
            //该条数据是广告位
            return CENTER_SINGLE_PIC_NEWS;
        } else if (mData.get(position) instanceof ArticleListBean.DataBean) {
            return VIDEO_LIST_TEXT;
        } else {
            return -1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OneViewHolder) {
            ((OneViewHolder) holder).tv.setText(((ArticleListBean.DataBean) mData.get(position)).getTTitle());
            ((OneViewHolder) holder).tv_author.setVisibility(View.VISIBLE);
            ((OneViewHolder) holder).tv_author.setText(((ArticleListBean.DataBean) mData.get(position)).getTuName());
            ImageLoad.into(mContext, ((ArticleListBean.DataBean) mData.get(position)).getTImage(), ((OneViewHolder) holder).iv_img);
            ((OneViewHolder) holder).video_list_button_comment_linner.setVisibility(View.VISIBLE);
            ((OneViewHolder) holder).tv_video_comment_num.setText(((ArticleListBean.DataBean) mData.get(position)).getCommentCount() + "");
            if (position < 10 && ((ArticleListBean.DataBean) mData.get(position)).getisFinally() == 1) {
                ((OneViewHolder) holder).item_video_last_resh_linner.setVisibility(View.VISIBLE);
                ((OneViewHolder) holder).view_last_resh.setVisibility(View.VISIBLE);
            } else {
                ((OneViewHolder) holder).item_video_last_resh_linner.setVisibility(View.GONE);
                ((OneViewHolder) holder).view_last_resh.setVisibility(View.GONE);
            }
            ((OneViewHolder) holder).item_video_last_resh_linner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecyclerView.scrollToPosition(0);
                    mRefreshLayout.beginRefreshing();
                }
            });
            if (((ArticleListBean.DataBean) mData.get(position)).getIsRead() == 1) {//获取当前的数据是都是已读状态
                ((OneViewHolder) holder).tv.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
            } else {
                ((OneViewHolder) holder).tv.setTextColor(mContext.getResources().getColor(R.color.common_text_color));
            }
            RxView.clicks(((OneViewHolder) holder).iv_img)
                    .throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    //跳转到 webview 页面
                    ((ArticleListBean.DataBean) mData.get(position)).setIsRead(1);//设置已读状态
                    ((OneViewHolder) holder).tv.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(WEB_VIEW_LOAD_URL, ((ArticleListBean.DataBean) mData.get(position)).getTaskUrl());
                    intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
                    intent.putExtra(TASKID, ((ArticleListBean.DataBean) mData.get(position)).getTaskId());
                    intent.putExtra(COMMENTS_NUM, ((ArticleListBean.DataBean) mData.get(position)).getComments());
                    intent.putExtra(ISCONN, ((ArticleListBean.DataBean) mData.get(position)).getIsConn());
                    intent.putExtra(VIDEO_URL, ((ArticleListBean.DataBean) mData.get(position)).getVideoUrl());
                    intent.putExtra(ARTICLETYPE, ((ArticleListBean.DataBean) mData.get(position)).getArticleType());
                    mContext.startActivity(intent);
                }
            });

        } else if (holder instanceof TenCentViewHolder) {
            final NativeExpressADView adView = (NativeExpressADView) mData.get(position);
            mAdViewPositionMap.put(adView, position); // 广告在列表中的位置是可以被更新的
            if (((TenCentViewHolder) holder).container.getChildCount() > 0
                    && ((TenCentViewHolder) holder).container.getChildAt(0) == adView) {
                return;
            }

            if (((TenCentViewHolder) holder).container.getChildCount() > 0) {
                ((TenCentViewHolder) holder).container.removeAllViews();
            }

            if (adView.getParent() != null) {
                ((ViewGroup) adView.getParent()).removeView(adView);
            }

            ((TenCentViewHolder) holder).container.addView(adView);
            adView.render(); // 调用render方法后sdk才会开始展示广告
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
        TextView tv, tv_bottom_comment_max, tv_time, tv_video_comment_num, tv_author;
        ImageView iv_img, iv_comment, iv_share_more;
        View view_last_resh;
        LinearLayout item_video_last_resh_linner, video_list_button_comment_linner;

        public OneViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
            tv_author = itemView.findViewById(R.id.tv_author);
            iv_img = itemView.findViewById(R.id.iv_img);
            iv_share_more = itemView.findViewById(R.id.iv_share_more);
            iv_comment = itemView.findViewById(R.id.iv_comment);
            tv_time = itemView.findViewById(R.id.tv_time_year);
            item_video_last_resh_linner = itemView.findViewById(R.id.item_video_last_resh_linner);
            tv_bottom_comment_max = itemView.findViewById(R.id.tv_bottom_comment_max);
            video_list_button_comment_linner = itemView.findViewById(R.id.video_list_button_comment_linner);
            tv_video_comment_num = itemView.findViewById(R.id.video_list_tv_comment_number);
            view_last_resh = itemView.findViewById(R.id.last_resh_view);
        }
    }

    /**
     * 腾讯广告位
     */
    class TenCentViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ViewGroup container;

        public TenCentViewHolder(View itemView) {
            super(itemView);
            container = (ViewGroup) itemView.findViewById(R.id.express_ad_container);
        }
    }

}

