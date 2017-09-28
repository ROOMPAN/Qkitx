package com.yanhui.qktx.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.yanhui.qktx.R;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.CommentBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.StringSapnbleUtils;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;

import java.util.List;


public class CommentExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    private Activity context;
    private List<StickyExampleModel> stickyExampleModels;
    private List<CommentBean.DataBean> dataBeanList;
    private LinearLayout rela_send_mess;
    private EditText et_message;
    private Button bt_send;
    private int taskId;
    private int answerUserId = 0, answercommentid = 0;
    private BGARefreshLayout mRefreshLayout;


    public CommentExampleAdapter(Activity context, List<StickyExampleModel> recyclerViewModels, EditText et_message, LinearLayout rela_send_mess, Button bt_send, BGARefreshLayout mRefreshLayout) {
        this.context = context;
        this.stickyExampleModels = recyclerViewModels;
        this.rela_send_mess = rela_send_mess;
        this.et_message = et_message;
        this.bt_send = bt_send;
        this.mRefreshLayout = mRefreshLayout;
        bt_send.setOnClickListener(this);
    }

    public void addAll(List<StickyExampleModel> recyclerViewModels) {
        this.stickyExampleModels = recyclerViewModels;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_list_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RecyclerViewHolder) {
            AddViewHolder addViewHolder;
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
            StickyExampleModel stickyExampleModel = stickyExampleModels.get(position);
            recyclerViewHolder.item_comment_user_add_linner.removeAllViews();
            dataBeanList = stickyExampleModel.commentBeanList;
            ((RecyclerViewHolder) viewHolder).tvName.setText(dataBeanList.get(position).getName());
            ImageLoad.into(context, dataBeanList.get(position).getHeadUrl(), ((RecyclerViewHolder) viewHolder).iv_user_photo);
            ((RecyclerViewHolder) viewHolder).tv_show_time.setText(dataBeanList.get(position).getStrCtime() + "");
            ((RecyclerViewHolder) viewHolder).tv_praise_num.setText(dataBeanList.get(position).getUps() + "");
            ((RecyclerViewHolder) viewHolder).tv_comment_contex.setText(dataBeanList.get(position).getContext());
            //((RecyclerViewHolder) viewHolder).tv_comment_num.setText(dataBeanList.get(position).);
            //评论打开键盘
            ((RecyclerViewHolder) viewHolder).bt_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rela_send_mess.setVisibility(View.VISIBLE);
                    showSoftInputFromWindow(context, et_message, true);
                    taskId = dataBeanList.get(position).getTaskId();
                }
            });
            ((RecyclerViewHolder) viewHolder).iv_praise_updas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HttpClient.getInstance().getAddups(dataBeanList.get(position).getCommentId(), new NetworkSubscriber<BaseEntity>() {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                ToastUtils.showToast(data.mes);
                            }
                        }
                    });
                }
            });
            if (dataBeanList.get(position).getList().size() != 0) {
                recyclerViewHolder.item_comment_add_linner_bg.setVisibility(View.VISIBLE);
                for (int i = 0; i < dataBeanList.get(position).getList().size(); i++) {
                    if (dataBeanList.get(position).getList().get(i).getAnswerUserId() == dataBeanList.get(position).getUserId()) {
                        View add_user_comment_view = LayoutInflater.from(context).inflate(R.layout.item_user_comment, null);
                        addViewHolder = new AddViewHolder(add_user_comment_view);
                        addViewHolder.tv_add_context.setText(StringSapnbleUtils.getSpanString(dataBeanList.get(position).getList().get(i).getName(), dataBeanList.get(position).getList().get(i).getContext(), context));
                        recyclerViewHolder.item_comment_user_add_linner.addView(add_user_comment_view);
                        int finalI = i;
                        add_user_comment_view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rela_send_mess.setVisibility(View.VISIBLE);
                                et_message.setHint("@" + dataBeanList.get(position).getList().get(finalI).getName());
                                et_message.setHintTextColor(context.getResources().getColor(R.color.status_color_grey));
                                answerUserId = dataBeanList.get(position).getList().get(finalI).getUserId();//被回复者 id
                                answercommentid = dataBeanList.get(position).getCommentId();// 当前评论 id
                                taskId = dataBeanList.get(position).getTaskId();
                                showSoftInputFromWindow(context, et_message, true);
                                ToastUtils.showToast(dataBeanList.get(position).getList().get(finalI).getUserId() + "" + dataBeanList.get(position).getList().get(finalI).getName() + "" + dataBeanList.get(position).getList().get(finalI).getAnswerCommentId());
                            }
                        });
                    } else {
                        View add_user_to_user_comment_view = LayoutInflater.from(context).inflate(R.layout.item_user_to_user_comment, null);
                        addViewHolder = new AddViewHolder(add_user_to_user_comment_view);
                        addViewHolder.tv_add_context.setText(dataBeanList.get(position).getList().get(i).getContext());
                        addViewHolder.tv_add_user_name.setText(dataBeanList.get(position).getList().get(i).getName());
                        addViewHolder.tv_add_to_user_name.setText(dataBeanList.get(position).getList().get(i).getAnswerUserName());
                        recyclerViewHolder.item_comment_user_add_linner.addView(add_user_to_user_comment_view);
                        int finalI = i;
                        add_user_to_user_comment_view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rela_send_mess.setVisibility(View.VISIBLE);
                                et_message.setHint("@" + dataBeanList.get(position).getList().get(finalI).getName());
                                answerUserId = dataBeanList.get(position).getList().get(finalI).getUserId();//被回复者 id
                                answercommentid = dataBeanList.get(position).getCommentId();// 当前评论 id
                                taskId = dataBeanList.get(position).getTaskId();
                                et_message.setHintTextColor(context.getResources().getColor(R.color.status_color_grey));
                                showSoftInputFromWindow(context, et_message, true);
                                ToastUtils.showToast(dataBeanList.get(position).getList().get(finalI).getUserId() + "" + dataBeanList.get(position).getList().get(finalI).getName());
                            }
                        });
                    }
                }
                View add_add_view_show_all_comment = LayoutInflater.from(context).inflate(R.layout.item_comment_add_view_show_all_comment, null);
                recyclerViewHolder.item_comment_user_add_linner.addView(add_add_view_show_all_comment);
                add_add_view_show_all_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            } else {
                recyclerViewHolder.item_comment_add_linner_bg.setVisibility(View.GONE);
            }
            if (position == 0) {
                recyclerViewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
                recyclerViewHolder.tvStickyHeader.setBackground(context.getResources().getDrawable(R.drawable.shape_comment_handle_red_bg));
                recyclerViewHolder.tvStickyHeader.setText(stickyExampleModel.sticky);
                recyclerViewHolder.itemView.setTag(FIRST_STICKY_VIEW);
            } else {
                //判断当前数据 中的 sticky的值 和上一条的sticky的值是否相同
                if (!TextUtils.equals(stickyExampleModel.sticky, stickyExampleModels.get(position - 1).sticky)) {
                    recyclerViewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
                    recyclerViewHolder.tvStickyHeader.setBackground(context.getResources().getDrawable(R.drawable.shape_comment_handle));
                    recyclerViewHolder.tvStickyHeader.setText(stickyExampleModel.sticky);
                    recyclerViewHolder.itemView.setTag(HAS_STICKY_VIEW);
                } else {
                    recyclerViewHolder.tvStickyHeader.setVisibility(View.GONE);
                    recyclerViewHolder.itemView.setTag(NONE_STICKY_VIEW);
                }
            }
            recyclerViewHolder.itemView.setContentDescription(stickyExampleModel.sticky);
        }
    }

    @Override
    public int getItemCount() {
        return stickyExampleModels == null ? 0 : stickyExampleModels.size();
    }

    @Override
    public void onClick(View view) {
        if (!StringUtils.isEmpty(et_message.getText().toString()) && answerUserId != 0) {
            HttpClient.getInstance().getAddUserComment(taskId, answerUserId, et_message.getText().toString(), answercommentid, new NetworkSubscriber<BaseEntity>() {
                @Override
                public void onNext(BaseEntity data) {
                    super.onNext(data);
                    if (data.isOKResult()) {
                        ToastUtils.showToast(data.mes);
                        et_message.setText("");
                        mRefreshLayout.beginRefreshing();
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
                        mRefreshLayout.beginRefreshing();
                        showSoftInputFromWindow(context, et_message, false);
                    }
                }
            });
            ToastUtils.showToast("评论内容不能为空!!!");
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStickyHeader;
        public RelativeLayout rlContentWrapper;
        public LinearLayout item_comment_user_add_linner, item_comment_add_linner_bg;
        public TextView tvName, tv_show_time, tv_praise_num, tv_comment_num, tv_comment_contex;
        private ImageView iv_user_photo, iv_praise_updas, bt_comment;
        public TextView tvGender;
        public TextView tvProfession;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvStickyHeader = itemView.findViewById(R.id.tv_sticky_header_view);
            item_comment_user_add_linner = itemView.findViewById(R.id.item_comment_user_add_linner);
            item_comment_add_linner_bg = itemView.findViewById(R.id.item_comment_add_linner_bg);
            tvName = itemView.findViewById(R.id.item_comment_name);
            iv_user_photo = itemView.findViewById(R.id.comment_list_user_logo);
            tv_show_time = itemView.findViewById(R.id.tv_province);
            tv_praise_num = itemView.findViewById(R.id.tv_praise_num);
            tv_comment_num = itemView.findViewById(R.id.tv_comment_num);
            tv_comment_contex = itemView.findViewById(R.id.item_comment_contex);
            iv_praise_updas = itemView.findViewById(R.id.img_praise_updas);
            bt_comment = itemView.findViewById(R.id.bt_comment);
        }
    }

    public class AddViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_add_context;
        public TextView tv_add_user_name;
        public TextView tv_add_to_user_name;


        public AddViewHolder(View itemView) {
            super(itemView);
            tv_add_user_name = itemView.findViewById(R.id.tv_user_comment_name);
            tv_add_to_user_name = itemView.findViewById(R.id.tv_user_comment_other_name);
            tv_add_context = itemView.findViewById(R.id.tv_user_comment_context);

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
