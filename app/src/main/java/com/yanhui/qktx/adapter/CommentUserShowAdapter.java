package com.yanhui.qktx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.yanhui.qktx.R;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.CommentBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.StringSapnbleUtils;
import com.yanhui.qktx.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/9/29.
 */

public class CommentUserShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CommentBean.DataBean.ListBean> mdata = new ArrayList<>();
    private List<CommentBean.DataBean> mdatahander = new ArrayList<>();
    private int HANDER = 102;//头部
    private int USERITEM = 103;// item
    private int USERTOUSER = 104;//用户对用户评论
    private PowerfulRecyclerView mRv_view;
    private int po;

    public CommentUserShowAdapter(Context context, PowerfulRecyclerView mRv_view) {
        this.context = context;
        this.mRv_view = mRv_view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        if (HANDER == viewType) {
            View viewhander = mInflater.inflate(R.layout.item_comment_user_show_hander, parent, false);
            holder = new HanderViewHolder(viewhander);
        } else if (USERITEM == viewType) {
            View view_item = LayoutInflater.from(context).inflate(R.layout.item_comment_show_user, parent, false);
            holder = new AddUserViewHolder(view_item);
        } else {
            View view_item = LayoutInflater.from(context).inflate(R.layout.item_comment_show_user_to_user, parent, false);
            holder = new AddUserToUserViewHolder(view_item);
        }
        return holder;
    }

    public void setData(List<CommentBean.DataBean.ListBean> mdata, List<CommentBean.DataBean> mdatahander) {
        this.mdata = mdata;
        this.mdatahander = mdatahander;
        notifyDataSetChanged();
    }

    public void AddAll(List<CommentBean.DataBean.ListBean> mdata, List<CommentBean.DataBean> mdatahander) {
        this.mdata.addAll(mdata);
        this.mdatahander.addAll(mdatahander);
        mRv_view.scrollToPosition(po);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HanderViewHolder) {
            //头部
            ((HanderViewHolder) holder).tvName.setText(mdatahander.get(0).getName());
            ImageLoad.into(context, mdatahander.get(0).getHeadUrl(), ((HanderViewHolder) holder).iv_user_photo);
            ((HanderViewHolder) holder).tv_show_time.setText(mdatahander.get(0).getStrCtime() + "");
            ((HanderViewHolder) holder).tv_praise_num.setText(mdatahander.get(0).getUps() + "");
            ((HanderViewHolder) holder).tv_comment_num.setText(mdatahander.get(0).getComments() + "");
            ((HanderViewHolder) holder).tv_comment_contex.setText(mdatahander.get(0).getContext());
            ((HanderViewHolder) holder).iv_praise_updas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HttpClient.getInstance().getAddups(mdatahander.get(0).getCommentId(), new NetworkSubscriber<BaseEntity>() {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                ToastUtils.showToast(data.mes);
                                ((HanderViewHolder) holder).tv_praise_num.setText(mdatahander.get(0).getUps() + 1 + "");
                                ((HanderViewHolder) holder).iv_praise_updas.setImageResource(R.drawable.icon_agree_selected);
                            }
                        }
                    });
                }
            });
        } else if (holder instanceof AddUserViewHolder) {
            // 用户评论
            ((AddUserViewHolder) holder).tv_add_user_context.setText(StringSapnbleUtils.getSpanString(mdata.get(position - 1).getName(), mdata.get(position - 1).getContext(), context));
        } else {
            // 用户对用户评论
            ((AddUserToUserViewHolder) holder).tv_add_user_name.setText(mdata.get(position - 1).getName());
            ((AddUserToUserViewHolder) holder).tv_add_to_user_name.setText(mdata.get(position - 1).getAnswerUserName());
            ((AddUserToUserViewHolder) holder).tv_add_context.setText(mdata.get(position - 1).getContext());

        }
        po = position;

    }

    @Override
    public int getItemCount() {
        if (mdata != null && mdata.size() != 0) {
            return mdata.size() + 1;
        } else {
            return 0;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HANDER;
        } else if (mdata.get(position - 1).getAnswerUserId() == mdatahander.get(0).getUserId()) {
            return USERITEM;
        } else {
            return USERTOUSER;
        }
    }

    public class AddUserToUserViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_add_context;
        public TextView tv_add_user_name;
        public TextView tv_add_to_user_name;


        public AddUserToUserViewHolder(View itemView) {
            super(itemView);
            tv_add_user_name = itemView.findViewById(R.id.tv_user_comment_name);
            tv_add_to_user_name = itemView.findViewById(R.id.tv_user_comment_other_name);
            tv_add_context = itemView.findViewById(R.id.tv_user_comment_context);

        }
    }

    public class AddUserViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_add_user_context;

        public AddUserViewHolder(View itemView) {
            super(itemView);
            tv_add_user_context = itemView.findViewById(R.id.tv_user_comment_context);

        }
    }

    class HanderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tv_show_time, tv_praise_num, tv_comment_num, tv_comment_contex;
        private ImageView iv_user_photo, iv_praise_updas, bt_comment;


        public HanderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_show_comment_name);
            iv_user_photo = itemView.findViewById(R.id.comment_show_user_logo);
            tv_show_time = itemView.findViewById(R.id.tv_show_province);
            tv_praise_num = itemView.findViewById(R.id.tv_show_praise_num);
            tv_comment_num = itemView.findViewById(R.id.tv_show_comment_num);
            tv_comment_contex = itemView.findViewById(R.id.item_show_comment_contex);
            iv_praise_updas = itemView.findViewById(R.id.img_show_praise_updas);
            bt_comment = itemView.findViewById(R.id.bt_show_comment);
        }

    }
}
