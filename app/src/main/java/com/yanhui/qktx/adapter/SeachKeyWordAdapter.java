package com.yanhui.qktx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhui.qktx.R;

import java.util.List;

/**
 * Created by liupanpan on 2017/9/21.
 */

public class SeachKeyWordAdapter extends BaseAdapter {
    private List<String> key_word_list;
    private Context context;

    public SeachKeyWordAdapter(List<String> key_word_list, Context context) {
        this.key_word_list = key_word_list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (key_word_list != null) {
            return key_word_list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        keyviewHolder keyviewHolder;
        if (view == null) {
            keyviewHolder = new keyviewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_seach_key_word_add, viewGroup, false);
            keyviewHolder.tv_title = view.findViewById(R.id.tv_key_word_title);
            keyviewHolder.iv_close = view.findViewById(R.id.img_key_close);
            view.setTag(keyviewHolder);
        } else {
            keyviewHolder = (SeachKeyWordAdapter.keyviewHolder) view.getTag();
        }
        keyviewHolder.tv_title.setText(key_word_list.get(position));
        keyviewHolder.iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key_word_list.remove(position);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    public class keyviewHolder {
        public TextView tv_title;
        public ImageView iv_close;
    }
}

