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
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.HistoryListBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.ToastUtils;

import java.util.List;

import static com.yanhui.qktx.constants.Constant.ARTICLETYPE;
import static com.yanhui.qktx.constants.Constant.COMMENTS_NUM;
import static com.yanhui.qktx.constants.Constant.ISCONN;
import static com.yanhui.qktx.constants.Constant.SHOW_BUTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.TASKID;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/9/13.
 */

public class EssayFavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HistoryListBean.DataBean> listBean;
    // 纯文字布局(文章、广告)
    private static final int TEXT_NEWS = 1;
    //左侧小图
    private static final int LEFT_PIC_VIDEO_NEWS = 2;
    //三张图片布局(文章、广告
    private static final int THREE_PICS_NEWS = 3;


    public EssayFavoritesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HistoryListBean.DataBean> listBean) {
        this.listBean = listBean;
        notifyDataSetChanged();
    }

    public void setDataAll(List<HistoryListBean.DataBean> listBean) {
        this.listBean.addAll(listBean);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        if (THREE_PICS_NEWS == viewType) {
            View v = mInflater.inflate(R.layout.item_three_pics_news, parent, false);
            holder = new ThreePicViewHolder(v);
        } else if (LEFT_PIC_VIDEO_NEWS == viewType) {
            View v = mInflater.inflate(R.layout.item_left_image_news, parent, false);
            holder = new LeftoPicViewHolder(v);
        } else if (TEXT_NEWS == viewType) {
            View v = mInflater.inflate(R.layout.item_favoites_null_pic, parent, false);
            holder = new NullPicViewHolder(v);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NullPicViewHolder) {
            ((NullPicViewHolder) holder).tv_title.setText(listBean.get(position).getTTitle());
//            ((NullPicViewHolder) holder).tv_author.setText("[新华社]");
            ((NullPicViewHolder) holder).tv_comment.setVisibility(View.VISIBLE);
            ((NullPicViewHolder) holder).tv_comment.setText(listBean.get(position).getComments() + "评论");
//            ((NullPicViewHolder) holder).tv_time.setText("2017-9-11");
            ((NullPicViewHolder) holder).item_null_pice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    starWebActivity(listBean, position, "");
                }
            });
            ((NullPicViewHolder) holder).tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RemoveItem(listBean, position);
                }
            });
        } else if (holder instanceof LeftoPicViewHolder) {
            ((LeftoPicViewHolder) holder).tv_title.setText(listBean.get(position).getTTitle());
            ((LeftoPicViewHolder) holder).tv_author.setText("[热点]");
            ((LeftoPicViewHolder) holder).tv_comment.setVisibility(View.VISIBLE);
            ((LeftoPicViewHolder) holder).tv_comment.setText(listBean.get(position).getComments() + "评论");
            ((LeftoPicViewHolder) holder).tv_time.setText("2017-8-9");
            ImageLoad.into(context, listBean.get(position).getStrImages().get(0).getImage(), ((LeftoPicViewHolder) holder).iv_img);
            ((LeftoPicViewHolder) holder).item_left_pic_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    starWebActivity(listBean, position, listBean.get(position).getStrImages().get(0).getImage());
                }
            });
            ((LeftoPicViewHolder) holder).tv_delete.setVisibility(View.VISIBLE);
            ((LeftoPicViewHolder) holder).tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RemoveItem(listBean, position);
                }
            });
        } else if (holder instanceof ThreePicViewHolder) {
            ((ThreePicViewHolder) holder).tv_title.setText(listBean.get(position).getTTitle());
            ((ThreePicViewHolder) holder).tv_comment.setVisibility(View.VISIBLE);
            ((ThreePicViewHolder) holder).tv_author.setText("[推荐]");
            ((ThreePicViewHolder) holder).tv_comment.setText(listBean.get(position).getComments() + "评论");
            ((ThreePicViewHolder) holder).tv_time.setText("2017-9-1");
            ImageLoad.into(context, listBean.get(position).getStrImages().get(0).getImage(), ((ThreePicViewHolder) holder).iv_img1);
            ImageLoad.into(context, listBean.get(position).getStrImages().get(1).getImage(), ((ThreePicViewHolder) holder).iv_img2);
            ImageLoad.into(context, listBean.get(position).getStrImages().get(2).getImage(), ((ThreePicViewHolder) holder).iv_img3);
            ((ThreePicViewHolder) holder).item_three_pic_lyout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    starWebActivity(listBean, position, listBean.get(position).getStrImages().get(0).getImage());
                }
            });
            ((ThreePicViewHolder) holder).tv_delete.setVisibility(View.VISIBLE);
            ((ThreePicViewHolder) holder).tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RemoveItem(listBean, position);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (listBean.get(position).getStrImages().size() == 1) {
            return LEFT_PIC_VIDEO_NEWS;
        } else if (listBean.get(position).getStrImages().size() == 3) {
            return THREE_PICS_NEWS;
        } else {
            return TEXT_NEWS;
        }
    }

    @Override
    public int getItemCount() {
        if (listBean != null) {
            return listBean.size();
        } else {
            return 0;
        }
    }


    //没有图片
    class NullPicViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_author, tv_time, tv_comment, tv_delete; //标题,位置类型,文章日期,评论数, 删除
        LinearLayout item_null_pice;

        public NullPicViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            item_null_pice = itemView.findViewById(R.id.favoites_null_pic_item);
            tv_author = itemView.findViewById(R.id.tv_favor_null_pic_type);
            tv_time = itemView.findViewById(R.id.tv_favor_null_pic_time);
            tv_comment = itemView.findViewById(R.id.tv_favor_null_pic_comment);
            tv_delete = itemView.findViewById(R.id.tv_favor_null_pic_delete);
        }
    }

    //左边一张图片
    class LeftoPicViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_author, tv_time, tv_comment, tv_delete; //标题,位置类型,文章日期,评论数 删除
        ImageView iv_img;
        LinearLayout item_left_pic_layout;

        public LeftoPicViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_comment = itemView.findViewById(R.id.tv_news_comment_num);
            tv_time = itemView.findViewById(R.id.tv_time_year);
            tv_delete = itemView.findViewById(R.id.tv_favor_delete);
            item_left_pic_layout = itemView.findViewById(R.id.item_left_layout);
        }
    }

    //三张图片
    class ThreePicViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_author, tv_time, tv_comment, tv_delete; //标题,位置类型,文章日期,评论数 ,删除
        ImageView iv_img1, iv_img2, iv_img3;
        LinearLayout item_three_pic_lyout;

        public ThreePicViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_img1 = itemView.findViewById(R.id.iv_img1);
            iv_img2 = itemView.findViewById(R.id.iv_img2);
            iv_img3 = itemView.findViewById(R.id.iv_img3);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_comment = itemView.findViewById(R.id.tv_news_comment_num);
            tv_time = itemView.findViewById(R.id.tv_time_year);
            tv_delete = itemView.findViewById(R.id.tv_favor_delete);
            item_three_pic_lyout = itemView.findViewById(R.id.item_three_pic_layout);
        }
    }

    //跳转到 webview详情页
    public void starWebActivity(List<HistoryListBean.DataBean> listBean, int position, String shareimgurl) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WEB_VIEW_LOAD_URL, listBean.get(position).getTaskUrl());
        intent.putExtra(SHOW_WEB_VIEW_BUTTOM, SHOW_BUTOM);
        intent.putExtra(TASKID, listBean.get(position).getTaskId());
        intent.putExtra(ISCONN, listBean.get(position).getIsConn());
        intent.putExtra(COMMENTS_NUM, listBean.get(position).getComments());
        intent.putExtra(ARTICLETYPE, listBean.get(position).getArticleType());
        context.startActivity(intent);
    }


    //删除 item
    public void RemoveItem(List<HistoryListBean.DataBean> listBean, int position) {
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
}
