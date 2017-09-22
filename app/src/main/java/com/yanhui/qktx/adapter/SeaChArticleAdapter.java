package com.yanhui.qktx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanhui.qktx.R;

/**
 * Created by liupanpan on 2017/9/21.
 * 文章搜索适配器
 */

public class SeaChArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    public SeaChArticleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        View v = mInflater.inflate(R.layout.item_fragment_video, parent, false);
        holder = new SeaChArticleAdapter.ArticleHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ArticleHolder extends RecyclerView.ViewHolder {

        public ArticleHolder(View itemView) {
            super(itemView);
        }
    }
}
