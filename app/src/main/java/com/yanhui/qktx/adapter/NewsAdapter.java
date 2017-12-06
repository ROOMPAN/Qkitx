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

import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
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
import static com.yanhui.qktx.constants.Constant.ISCONN;
import static com.yanhui.qktx.constants.Constant.SHOW_BUTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.TASKID;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/9/7.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 纯文字布局(文章、广告)
     */
    private static final int TEXT_NEWS = 0;
    /**
     * 广告
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
     * 空数据
     */
    private static final int LIST_NULL = 4;
    private Context mContext;
    private String mChannelCode;
    private PowerfulRecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    private List<Object> mData = new ArrayList<>();
    private int resh_data_size;


    public NewsAdapter(Context mContext, String mChannelCode, PowerfulRecyclerView mRecyclerView, BGARefreshLayout mRefreshLayout) {
        this.mContext = mContext;
        this.mChannelCode = mChannelCode;
        this.mRefreshLayout = mRefreshLayout;
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
        } else if (CENTER_SINGLE_PIC_NEWS == viewType) {
            View v = mInflater.inflate(R.layout.item_express_ad, parent, false);
            holder = new TenCentViewHolder(v);
        }
        return holder;

    }

    public void setData(List data) {
        resh_data_size = data.size();
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(List data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NesViewHolder) {
            if (position < 10 && ((ArticleListBean.DataBean) mData.get(position)).getisFinally() == 1) {
                ((NesViewHolder) holder).last_news_resh_linner.setVisibility(View.VISIBLE);
            } else {
                ((NesViewHolder) holder).last_news_resh_linner.setVisibility(View.GONE);
            }
            if (((ArticleListBean.DataBean) mData.get(position)).getIsRead() == 1) {//获取当前的数据是都是已读状态
                ((NesViewHolder) holder).tv.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
            } else {
                ((NesViewHolder) holder).tv.setTextColor(mContext.getResources().getColor(R.color.common_text_color));
            }
            ((NesViewHolder) holder).tv.setText(((ArticleListBean.DataBean) mData.get(position)).getTTitle());
            ((NesViewHolder) holder).tv_time_year.setVisibility(View.VISIBLE);
            ((NesViewHolder) holder).iv_news_delete_item.setVisibility(View.VISIBLE);
            ((NesViewHolder) holder).tv_time_year.setText(TimeUtils.getShortTime(((ArticleListBean.DataBean) mData.get(position)).getShowTime()));
            ((NesViewHolder) holder).tv_news_comment_num.setVisibility(View.VISIBLE);
            ((NesViewHolder) holder).tv_news_comment_num.setText(((ArticleListBean.DataBean) mData.get(position)).getCommentCount() + "评论");
            RxView.clicks(((NesViewHolder) holder).item_news_null_pic_linner)
                    .throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    ((ArticleListBean.DataBean) mData.get(position)).setIsRead(1);//设置已读状态
                    ((NesViewHolder) holder).tv.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
                    starWebActivity(mData, position);
                }
            });
            ((NesViewHolder) holder).last_news_resh_linner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecyclerView.scrollToPosition(0);
                    mRefreshLayout.beginRefreshing();
                }
            });
            ((NesViewHolder) holder).iv_news_delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ToastUtils.showToast(position + "");
                    setDeleteItem(position);
                }
            });
        } else if (holder instanceof RightImgViewHolder) {
            if (position < 10 && ((ArticleListBean.DataBean) mData.get(position)).getisFinally() == 1) {
                ((RightImgViewHolder) holder).last_news_resh_linner.setVisibility(View.VISIBLE);
            } else {
                ((RightImgViewHolder) holder).last_news_resh_linner.setVisibility(View.GONE);
            }
            if (((ArticleListBean.DataBean) mData.get(position)).getIsRead() == 1) {//获取当前的数据是都是已读状态
                ((RightImgViewHolder) holder).tv1.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
            } else {
                ((RightImgViewHolder) holder).tv1.setTextColor(mContext.getResources().getColor(R.color.common_text_color));
            }
            ((RightImgViewHolder) holder).tv1.setText(((ArticleListBean.DataBean) mData.get(position)).getTTitle());
            ((RightImgViewHolder) holder).iv_news_delete_item.setVisibility(View.VISIBLE);
            ((RightImgViewHolder) holder).tv_news_comment_num.setVisibility(View.VISIBLE);
            ((RightImgViewHolder) holder).tv_news_comment_num.setText(((ArticleListBean.DataBean) mData.get(position)).getCommentCount() + "评论");
            RxView.clicks(((RightImgViewHolder) holder).item_right_pic_linner)
                    .throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    ((ArticleListBean.DataBean) mData.get(position)).setIsRead(1);//设置已读状态
                    ((RightImgViewHolder) holder).tv1.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
                    starWebActivity(mData, position);
                }
            });
            ((RightImgViewHolder) holder).last_news_resh_linner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecyclerView.scrollToPosition(0);
                    mRefreshLayout.beginRefreshing();
                }
            });
            ((RightImgViewHolder) holder).iv_news_delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ToastUtils.showToast(position + "");
                    setDeleteItem(position);
                }
            });
            ((RightImgViewHolder) holder).tv_time_year.setVisibility(View.VISIBLE);
            ((RightImgViewHolder) holder).tv_time_year.setText(TimeUtils.getShortTime(((ArticleListBean.DataBean) mData.get(position)).getShowTime()));
            ImageLoad.into(mContext, ((ArticleListBean.DataBean) mData.get(position)).getStrImages().get(0).getImage(), ((RightImgViewHolder) holder).iv_img);
        } else if (holder instanceof ThreeViewHolder) {
            if (position < 10 && ((ArticleListBean.DataBean) mData.get(position)).getisFinally() == 1) {
                ((ThreeViewHolder) holder).last_news_resh_linner.setVisibility(View.VISIBLE);
            } else {
                ((ThreeViewHolder) holder).last_news_resh_linner.setVisibility(View.GONE);
            }
            if (((ArticleListBean.DataBean) mData.get(position)).getIsRead() == 1) {//获取当前的数据是都是已读状态
                ((ThreeViewHolder) holder).tv1.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
            } else {
                ((ThreeViewHolder) holder).tv1.setTextColor(mContext.getResources().getColor(R.color.common_text_color));
            }
            ((ThreeViewHolder) holder).tv1.setText(((ArticleListBean.DataBean) mData.get(position)).getTTitle());
            ((ThreeViewHolder) holder).iv_news_delete_item.setVisibility(View.VISIBLE);
            ((ThreeViewHolder) holder).tv_time_year.setVisibility(View.VISIBLE);
            ((ThreeViewHolder) holder).tv_time_year.setText(TimeUtils.getShortTime(((ArticleListBean.DataBean) mData.get(position)).getShowTime()));
            ((ThreeViewHolder) holder).tv_news_comment_num.setVisibility(View.VISIBLE);
            ((ThreeViewHolder) holder).tv_news_comment_num.setText(((ArticleListBean.DataBean) mData.get(position)).getCommentCount() + "评论");
            RxView.clicks(((ThreeViewHolder) holder).item_three_pic_layout)
                    .throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    ((ArticleListBean.DataBean) mData.get(position)).setIsRead(1);//设置已读状态
                    ((ThreeViewHolder) holder).tv1.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
                    starWebActivity(mData, position);
                }
            });
            ((ThreeViewHolder) holder).last_news_resh_linner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecyclerView.scrollToPosition(0);
                    mRefreshLayout.beginRefreshing();
                }
            });
            ((ThreeViewHolder) holder).iv_news_delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ToastUtils.showToast(position + "");
                    setDeleteItem(position);
                }
            });
            ImageLoad.into(mContext, ((ArticleListBean.DataBean) mData.get(position)).getStrImages().get(0).getImage(), ((ThreeViewHolder) holder).iv_img1);
            ImageLoad.into(mContext, ((ArticleListBean.DataBean) mData.get(position)).getStrImages().get(1).getImage(), ((ThreeViewHolder) holder).iv_img2);
            ImageLoad.into(mContext, ((ArticleListBean.DataBean) mData.get(position)).getStrImages().get(2).getImage(), ((ThreeViewHolder) holder).iv_img3);

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (!((ArticleListBean.DataBean) mData.get(position)).getType().equals("ad")) {
            if (((ArticleListBean.DataBean) mData.get(position)).getStrImages().size() == 3) {
                return THREE_PICS_NEWS;
            } else if (((ArticleListBean.DataBean) mData.get(position)).getStrImages().size() == 1) {
                return RIGHT_PIC_VIDEO_NEWS;
            } else if (((ArticleListBean.DataBean) mData.get(position)).getStrImages().size() == 2) {
                return RIGHT_PIC_VIDEO_NEWS;
            } else {
                return TEXT_NEWS;
            }
        } else {
            //该条数据是广告位
            return CENTER_SINGLE_PIC_NEWS;
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
        TextView tv, tv_time_year, tv_news_comment_num;
        ImageView iv_img, iv_news_delete_item;
        LinearLayout item_news_null_pic_linner;
        LinearLayout last_news_resh_linner;

        public NesViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            iv_news_delete_item = itemView.findViewById(R.id.iv_news_delete_item);
            item_news_null_pic_linner = itemView.findViewById(R.id.item_news_null_pic_linner);
            last_news_resh_linner = itemView.findViewById(R.id.item_news_last_resh_linner);
            tv_time_year = itemView.findViewById(R.id.tv_time_year);
            tv_news_comment_num = itemView.findViewById(R.id.tv_news_comment_num);
        }
    }

    class ThreeViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv_time_year, tv_news_comment_num;
        LinearLayout item_three_pic_layout;
        ImageView iv_img1, iv_img2, iv_img3, iv_news_delete_item;
        LinearLayout last_news_resh_linner;

        public ThreeViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_title);
            tv_time_year = itemView.findViewById(R.id.tv_time_year);
            iv_news_delete_item = itemView.findViewById(R.id.iv_news_delete_item);
            item_three_pic_layout = itemView.findViewById(R.id.item_three_pic_layout);
            last_news_resh_linner = itemView.findViewById(R.id.item_news_last_resh_linner);
            tv_news_comment_num = itemView.findViewById(R.id.tv_news_comment_num);
            iv_img1 = itemView.findViewById(R.id.iv_img1);
            iv_img2 = itemView.findViewById(R.id.iv_img2);
            iv_img3 = itemView.findViewById(R.id.iv_img3);
        }
    }

    class RightImgViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv_time_year, tv_news_comment_num;
        ImageView iv_img, iv_news_delete_item;
        LinearLayout item_right_pic_linner;
        LinearLayout last_news_resh_linner;

        public RightImgViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_title);
            tv_time_year = itemView.findViewById(R.id.tv_time_year);
            iv_news_delete_item = itemView.findViewById(R.id.iv_news_delete_item);
            item_right_pic_linner = itemView.findViewById(R.id.item_right_pic_linner);
            last_news_resh_linner = itemView.findViewById(R.id.item_news_last_resh_linner);
            tv_news_comment_num = itemView.findViewById(R.id.tv_news_comment_num);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }

    /**
     * 腾讯广告位置
     */
    class TenCentViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ViewGroup container;

        public TenCentViewHolder(View itemView) {
            super(itemView);
            container = (ViewGroup) itemView.findViewById(R.id.express_ad_container);
        }
    }

    /**
     * @param listBean 数据集合
     * @param position 第几条
     */
    //跳转到 webview详情页
    public void starWebActivity(List<Object> listBean, int position) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(WEB_VIEW_LOAD_URL, ((ArticleListBean.DataBean) listBean.get(position)).getTaskUrl());
        intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
        intent.putExtra(TASKID, ((ArticleListBean.DataBean) listBean.get(position)).getTaskId());
        intent.putExtra(ARTICLETYPE, ((ArticleListBean.DataBean) listBean.get(position)).getArticleType());
        intent.putExtra(ISCONN, ((ArticleListBean.DataBean) listBean.get(position)).getIsConn());
        mContext.startActivity(intent);
    }

    /**
     * 点击删除不想看的数据 刷新页面
     *
     * @param item_position
     */
    public void setDeleteItem(int item_position) {
        mData.remove(item_position);
        notifyDataSetChanged();
    }
}



