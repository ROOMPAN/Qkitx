package com.yanhui.qktx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanhui.qktx.R;

/**
 * Created by liupanpan on 2017/9/13.
 */

public class HistoryRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    public HistoryRecordAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        View v = mInflater.inflate(R.layout.item_left_image_news, parent, false);
        holder = new OneViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class OneViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public OneViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
        }
    }
}
