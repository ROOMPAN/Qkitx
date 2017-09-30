package com.yanhui.qktx.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.yanhui.qktx.R;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.CommentBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.SharedPreferencesMgr;
import com.yanhui.qktx.utils.StringSapnbleUtils;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/9/29.
 */

public class CommentUserShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Activity context;
    private List<CommentBean.DataBean.ListBean> mdata = new ArrayList<>();
    private List<CommentBean.DataBean> mdatahander = new ArrayList<>();
    private int HANDER = 102;//头部
    private int USERITEM = 103;// item
    private int USERTOUSER = 104;//用户对用户评论
    private PowerfulRecyclerView mRv_view;
    private LinearLayout rela_send_mess;
    private EditText et_message;
    private Button bt_send;
    private int po;
    private int taskId;
    private int answerUserId = 0, answercommentid = 0;
    private int isups;

    public CommentUserShowAdapter(Activity context, PowerfulRecyclerView mRv_view, EditText et_message, LinearLayout rela_send_mess, Button bt_send) {
        this.context = context;
        this.mRv_view = mRv_view;
        this.rela_send_mess = rela_send_mess;
        this.bt_send = bt_send;
        this.et_message = et_message;
        bt_send.setOnClickListener(this);
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
        mRv_view.scrollToPosition(po);//回到当前的位置,用于上拉加载,回到底部.
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
            if (mdatahander.get(position).getIsUp() == 1) {
                isups = 1;
                ((HanderViewHolder) holder).iv_praise_updas.setImageResource(R.drawable.icon_agree_selected);
            } else {
                isups = 0;
                ((HanderViewHolder) holder).iv_praise_updas.setImageResource(R.drawable.icon_agree_normal);
            }
            ((HanderViewHolder) holder).iv_praise_updas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isups != 1) {
                        HttpClient.getInstance().getAddups(mdatahander.get(0).getCommentId(), new NetworkSubscriber<BaseEntity>() {
                            @Override
                            public void onNext(BaseEntity data) {
                                super.onNext(data);
                                if (data.isOKResult()) {
                                    ToastUtils.showToast(data.mes);
                                    isups = 1;
                                    ((HanderViewHolder) holder).tv_praise_num.setText(mdatahander.get(0).getUps() + 1 + "");
                                    ((HanderViewHolder) holder).iv_praise_updas.setImageResource(R.drawable.icon_agree_selected);
                                }
                            }
                        });
                    } else {
                        ToastUtils.showToast("你已经点过赞了...");
                    }
                }
            });
            ((HanderViewHolder) holder).bt_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //按钮评论;
                    if (mdatahander.get(0).getUserId() != SharedPreferencesMgr.getInt("userid", 0)) {
                        rela_send_mess.setVisibility(View.VISIBLE);
                        showSoftInputFromWindow(context, et_message, true);
                        et_message.setHint("@" + mdatahander.get(0).getName());
                        et_message.setHintTextColor(context.getResources().getColor(R.color.status_color_grey));
                        answerUserId = mdatahander.get(0).getUserId();//被回复者 id
                        answercommentid = mdatahander.get(0).getCommentId();// 当前评论 id
                        taskId = mdatahander.get(0).getTaskId();
                    } else {
                        ToastUtils.showToast("你不能回复自己");
                    }
                }
            });
        } else if (holder instanceof AddUserViewHolder) {
            // 用户评论
            ((AddUserViewHolder) holder).tv_add_user_context.setText(StringSapnbleUtils.getSpanString(mdata.get(position - 1).getName(), mdata.get(position - 1).getContext(), context));
            ((AddUserViewHolder) holder).item_user_comment_linner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //用户评论
                    if (mdata.get(position - 1).getUserId() != SharedPreferencesMgr.getInt("userid", 0)) {
                        Log.e("userid", "" + mdata.get(position - 1).getUserId() + "----" + SharedPreferencesMgr.getInt("userid", 0));
                        rela_send_mess.setVisibility(View.VISIBLE);
                        et_message.setHint("@" + mdata.get(position - 1).getName());
                        et_message.setHintTextColor(context.getResources().getColor(R.color.status_color_grey));
                        answerUserId = mdata.get(position - 1).getUserId();//被回复者 id
                        answercommentid = mdata.get(position - 1).getCommentId();// 当前评论 id
                        taskId = mdata.get(position - 1).getTaskId();
                        showSoftInputFromWindow(context, et_message, true);
                        ToastUtils.showToast(mdata.get(position - 1).getUserId() + "" + mdata.get(position - 1).getName() + "" + mdata.get(position - 1).getCommentId());
                    } else {
                        ToastUtils.showToast("你不能回复自己");
                    }
                }
            });
        } else {
            // 用户对用户评论
            ((AddUserToUserViewHolder) holder).tv_add_user_name.setText(mdata.get(position - 1).getName());
            ((AddUserToUserViewHolder) holder).tv_add_to_user_name.setText(mdata.get(position - 1).getAnswerUserName());
            ((AddUserToUserViewHolder) holder).tv_add_context.setText(mdata.get(position - 1).getContext());
            ((AddUserToUserViewHolder) holder).item_show_user_to_user_comment_linner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //对某人回复:
                    if (mdata.get(position - 1).getUserId() != SharedPreferencesMgr.getInt("userid", 0)) {
                        Log.e("userid", "" + mdata.get(position - 1).getUserId() + "----" + SharedPreferencesMgr.getInt("userid", 0));
                        rela_send_mess.setVisibility(View.VISIBLE);
                        et_message.setHint("@" + mdata.get(position - 1).getName());
                        answerUserId = mdata.get(position - 1).getUserId();//被回复者 id
                        answercommentid = mdata.get(position - 1).getCommentId();// 当前评论 id
                        taskId = mdata.get(position - 1).getTaskId();
                        et_message.setHintTextColor(context.getResources().getColor(R.color.status_color_grey));
                        showSoftInputFromWindow(context, et_message, true);
                        ToastUtils.showToast(mdata.get(position - 1).getUserId() + "" + mdata.get(position - 1).getName() + "" + mdata.get(position - 1).getCommentId());
                    } else {
                        ToastUtils.showToast("你不能评论自己");
                    }
                }
            });

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

    public void setCommentForActivity() {
        if (mdatahander.get(0).getUserId() != SharedPreferencesMgr.getInt("userid", 0)) {
            rela_send_mess.setVisibility(View.VISIBLE);
            showSoftInputFromWindow(context, et_message, true);
            et_message.setHint("@" + mdatahander.get(0).getName());
            et_message.setHintTextColor(context.getResources().getColor(R.color.status_color_grey));
            answerUserId = mdatahander.get(0).getUserId();//被回复者 id
            answercommentid = mdatahander.get(0).getCommentId();// 当前评论 id
            taskId = mdatahander.get(0).getTaskId();
        } else {
            ToastUtils.showToast("你不能回复自己...");
        }
    }

    @Override
    public void onClick(View view) {
        //评论发送按钮
        if (!StringUtils.isEmpty(et_message.getText().toString()) && answerUserId != 0) {
            HttpClient.getInstance().getAddUserComment(taskId, answerUserId, et_message.getText().toString(), answercommentid, new NetworkSubscriber<BaseEntity>() {
                @Override
                public void onNext(BaseEntity data) {
                    super.onNext(data);
                    if (data.isOKResult()) {
                        ToastUtils.showToast(data.mes);
                        et_message.setText("");
                        showSoftInputFromWindow(context, et_message, false);
                    }
                }
            });
        } else if (!StringUtils.isEmpty(et_message.getText().toString()) && answerUserId == 0) {
            HttpClient.getInstance().getAddComment(taskId, et_message.getText().toString(), new NetworkSubscriber<BaseEntity>() {
                @Override
                public void onNext(BaseEntity data) {
                    super.onNext(data);
                    if (data.isOKResult()) {
                        ToastUtils.showToast(data.mes);
                        et_message.setText("");
                        showSoftInputFromWindow(context, et_message, false);
                    }
                }
            });
            ToastUtils.showToast("评论内容不能为空!!!");
        }
    }

    public class AddUserToUserViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_add_context;
        public TextView tv_add_user_name;
        public TextView tv_add_to_user_name;
        public LinearLayout item_show_user_to_user_comment_linner;


        public AddUserToUserViewHolder(View itemView) {
            super(itemView);
            tv_add_user_name = itemView.findViewById(R.id.tv_user_comment_name);
            tv_add_to_user_name = itemView.findViewById(R.id.tv_user_comment_other_name);
            tv_add_context = itemView.findViewById(R.id.tv_user_comment_context);
            item_show_user_to_user_comment_linner = itemView.findViewById(R.id.item_show_user_to_user_comment_linner);

        }
    }

    public class AddUserViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_add_user_context;
        private LinearLayout item_user_comment_linner;

        public AddUserViewHolder(View itemView) {
            super(itemView);
            tv_add_user_context = itemView.findViewById(R.id.tv_user_comment_context);
            item_user_comment_linner = itemView.findViewById(R.id.item_user_comment_linner);

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

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText, boolean open) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (open) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            //关闭软键盘
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }

    }
}
