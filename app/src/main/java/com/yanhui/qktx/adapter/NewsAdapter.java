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

import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

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
    private String mChannelCode;
    private RecyclerView mRecyclerView;
    private List<Object> mData = new ArrayList<>();
    private int resh_data_size;


    public NewsAdapter(Context mContext, String mChannelCode, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mChannelCode = mChannelCode;
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
            if (position == 9) {
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
            ((NesViewHolder) holder).tv_time_year.setText(TimeUtils.getShortTime(((ArticleListBean.DataBean) mData.get(position)).getLastModifyTime()));
            ((NesViewHolder) holder).item_news_null_pic_linner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //没有图片的 item 传递到 web 页面  需要默认给一个分享图片链接
                    ((ArticleListBean.DataBean) mData.get(position)).setIsRead(1);//设置已读状态
                    ((NesViewHolder) holder).tv.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
                    starWebActivity(mData, position);
                }
            });
        } else if (holder instanceof RightImgViewHolder) {
            if (position == 9) {
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
            ((RightImgViewHolder) holder).item_right_pic_linner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ArticleListBean.DataBean) mData.get(position)).setIsRead(1);//设置已读状态
                    ((RightImgViewHolder) holder).tv1.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
                    starWebActivity(mData, position);
                }
            });
            ((RightImgViewHolder) holder).tv_time_year.setText(TimeUtils.getShortTime(((ArticleListBean.DataBean) mData.get(position)).getLastModifyTime()));
            ImageLoad.into(mContext, ((ArticleListBean.DataBean) mData.get(position)).getStrImages().get(0).getImage(), ((RightImgViewHolder) holder).iv_img);
        } else {
            if (position == 9) {
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
            ((ThreeViewHolder) holder).tv_time_year.setText(TimeUtils.getShortTime(((ArticleListBean.DataBean) mData.get(position)).getLastModifyTime()));
            ((ThreeViewHolder) holder).item_three_pic_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ArticleListBean.DataBean) mData.get(position)).setIsRead(1);//设置已读状态
                    ((ThreeViewHolder) holder).tv1.setTextColor(mContext.getResources().getColor(R.color.light_font_color));
                    starWebActivity(mData, position);
                }
            });
            ImageLoad.into(mContext, ((ArticleListBean.DataBean) mData.get(position)).getStrImages().get(0).getImage(), ((ThreeViewHolder) holder).iv_img1);
            ImageLoad.into(mContext, ((ArticleListBean.DataBean) mData.get(position)).getStrImages().get(1).getImage(), ((ThreeViewHolder) holder).iv_img2);
            ImageLoad.into(mContext, ((ArticleListBean.DataBean) mData.get(position)).getStrImages().get(2).getImage(), ((ThreeViewHolder) holder).iv_img3);

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (((ArticleListBean.DataBean) mData.get(position)).getStrImages().size() == 3) {
            return THREE_PICS_NEWS;
        } else if (((ArticleListBean.DataBean) mData.get(position)).getStrImages().size() == 1) {
            return RIGHT_PIC_VIDEO_NEWS;
        } else {
            return TEXT_NEWS;
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
        TextView tv, tv_time_year;
        ImageView iv_img;
        LinearLayout item_news_null_pic_linner;
        LinearLayout last_news_resh_linner;

        public NesViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            item_news_null_pic_linner = itemView.findViewById(R.id.item_news_null_pic_linner);
            last_news_resh_linner = itemView.findViewById(R.id.item_news_last_resh_linner);
            tv_time_year = itemView.findViewById(R.id.tv_time_year);
        }
    }

    class ThreeViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv_time_year;
        LinearLayout item_three_pic_layout;
        ImageView iv_img1, iv_img2, iv_img3;
        LinearLayout last_news_resh_linner;

        public ThreeViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_title);
            tv_time_year = itemView.findViewById(R.id.tv_time_year);
            item_three_pic_layout = itemView.findViewById(R.id.item_three_pic_layout);
            last_news_resh_linner = itemView.findViewById(R.id.item_news_last_resh_linner);
            iv_img1 = itemView.findViewById(R.id.iv_img1);
            iv_img2 = itemView.findViewById(R.id.iv_img2);
            iv_img3 = itemView.findViewById(R.id.iv_img3);
        }
    }

    class RightImgViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv_time_year;
        ImageView iv_img;
        LinearLayout item_right_pic_linner;
        LinearLayout last_news_resh_linner;

        public RightImgViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_title);
            tv_time_year = itemView.findViewById(R.id.tv_time_year);
            item_right_pic_linner = itemView.findViewById(R.id.item_right_pic_linner);
            last_news_resh_linner = itemView.findViewById(R.id.item_news_last_resh_linner);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }

    /**
     * @param listBean    数据集合
     * @param position    第几条
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
}



