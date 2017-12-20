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

import com.androidquery.AQuery;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.sogou.feedads.AdView;
import com.yanhui.qktx.R;
import com.yanhui.qktx.activity.WebViewActivity;
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * 腾讯广点通广告
     */
    private static final int CENTER_SINGLE_PIC_NEWS = 100;
    /**
     * 搜狗广告
     */
    private static final int CENTER_SOU_GOU_AD_NEWS = 101;
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
    private AQuery aq;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap;
    private Map viewHolderMap = new HashMap<>();


    public NewsAdapter(Context mContext, String mChannelCode, PowerfulRecyclerView mRecyclerView, BGARefreshLayout mRefreshLayout, HashMap<NativeExpressADView, Integer> mAdViewPositionMap) {
        this.mContext = mContext;
        this.mChannelCode = mChannelCode;
        this.mRefreshLayout = mRefreshLayout;
        this.mRecyclerView = mRecyclerView;
        this.mAdViewPositionMap = mAdViewPositionMap;
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
        } else if (CENTER_SOU_GOU_AD_NEWS == viewType) {
            View v = mInflater.inflate(R.layout.item_sougou_ad_three_news, parent, false);
            holder = new SouGouAdviewHolder(v);
        }
        return holder;

    }

    // 把返回的NativeExpressADView添加到数据集里面去
    public void addADViewToPosition(int position, NativeExpressADView adView) {
        if (position >= 0 && position < mData.size() && adView != null) {
            mData.add(position, adView);
        }
    }

    // 移除NativeExpressADView的时候是一条一条移除的
    public void removeADView(int position, NativeExpressADView adView) {
        mData.remove(position);
        notifyItemRemoved(position); // position为adView在当前列表中的位置
        notifyItemRangeChanged(0, mData.size() - 1);

    }

    //搜狗广告
    public Map getViewHolderMap() {
        if (viewHolderMap != null) {
            return viewHolderMap;
        } else {
            return null;
        }
    }

    //搜狗广告 添加到数据链
    public void addSouGouADViewToPosition(int position, AdView adView) {
        if (position >= 0 && position < mData.size() && adView != null && adView.adReady) {
            mData.add(position, adView);
        }
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
            ((NesViewHolder) holder).tv_author.setVisibility(View.VISIBLE);
            ((NesViewHolder) holder).tv_author.setText(((ArticleListBean.DataBean) mData.get(position)).getTuName());
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
            ((RightImgViewHolder) holder).tv_author.setVisibility(View.VISIBLE);
            ((RightImgViewHolder) holder).tv_author.setText(((ArticleListBean.DataBean) mData.get(position)).getTuName());
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
            ((ThreeViewHolder) holder).tv_author.setVisibility(View.VISIBLE);
            ((ThreeViewHolder) holder).tv_author.setText(((ArticleListBean.DataBean) mData.get(position)).getTuName());
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

        } else if (holder instanceof TenCentViewHolder) {
            //腾讯广告
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
        } else if (holder instanceof SouGouAdviewHolder) {
            if (aq == null) {
                aq = new AQuery(mContext);
            }
            final AdView adView = (AdView) mData.get(position);
            aq.id(((SouGouAdviewHolder) holder).tv1).text(adView.getTitle());
            ImageLoad.into(mContext, adView.getImglist()[0], ((SouGouAdviewHolder) holder).iv_img1);
            ImageLoad.into(mContext, adView.getImglist()[1], ((SouGouAdviewHolder) holder).iv_img2);
            ImageLoad.into(mContext, adView.getImglist()[2], ((SouGouAdviewHolder) holder).iv_img3);
            RxView.clicks(((SouGouAdviewHolder) holder).item_three_pic_layout)
                    .throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    adView.onAdClick(((SouGouAdviewHolder) holder).item_three_pic_layout);
                }
            });
            viewHolderMap.put(position, holder);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof ArticleListBean.DataBean) {
            if (((ArticleListBean.DataBean) mData.get(position)).getStrImages().size() == 3) {
                return THREE_PICS_NEWS;
            } else if (((ArticleListBean.DataBean) mData.get(position)).getStrImages().size() == 1) {
                return RIGHT_PIC_VIDEO_NEWS;
            } else if (((ArticleListBean.DataBean) mData.get(position)).getStrImages().size() == 2) {
                return RIGHT_PIC_VIDEO_NEWS;
            } else {
                return TEXT_NEWS;
            }
        } else if (mData.get(position) instanceof NativeExpressADView) {
            //该条数据是腾讯广告位
            return CENTER_SINGLE_PIC_NEWS;
        } else if (mData.get(position) instanceof AdView) {
            //搜狗广告 view
            return CENTER_SOU_GOU_AD_NEWS;
        } else {
            return -1;
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
        TextView tv, tv_time_year, tv_news_comment_num, tv_author;
        ImageView iv_img, iv_news_delete_item;
        LinearLayout item_news_null_pic_linner;
        LinearLayout last_news_resh_linner;

        public NesViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
            tv_author = itemView.findViewById(R.id.tv_author);
            iv_img = itemView.findViewById(R.id.iv_img);
            iv_news_delete_item = itemView.findViewById(R.id.iv_news_delete_item);
            item_news_null_pic_linner = itemView.findViewById(R.id.item_news_null_pic_linner);
            last_news_resh_linner = itemView.findViewById(R.id.item_news_last_resh_linner);
            tv_time_year = itemView.findViewById(R.id.tv_time_year);
            tv_news_comment_num = itemView.findViewById(R.id.tv_news_comment_num);
        }
    }

    class ThreeViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv_time_year, tv_news_comment_num, tv_author;
        LinearLayout item_three_pic_layout;
        ImageView iv_img1, iv_img2, iv_img3, iv_news_delete_item;
        LinearLayout last_news_resh_linner;

        public ThreeViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_title);
            tv_author = itemView.findViewById(R.id.tv_author);
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
        TextView tv1, tv_time_year, tv_news_comment_num, tv_author;
        ImageView iv_img, iv_news_delete_item;
        LinearLayout item_right_pic_linner;
        LinearLayout last_news_resh_linner;

        public RightImgViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_title);
            tv_author = itemView.findViewById(R.id.tv_author);
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
     * 搜狗广告位置
     */
    class SouGouAdviewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        LinearLayout item_three_pic_layout;
        ImageView iv_img1, iv_img2, iv_img3;

        public SouGouAdviewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_title);
            iv_img1 = itemView.findViewById(R.id.iv_img1);
            iv_img2 = itemView.findViewById(R.id.iv_img2);
            iv_img3 = itemView.findViewById(R.id.iv_img3);
            item_three_pic_layout = itemView.findViewById(R.id.item_three_pic_layout);
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



