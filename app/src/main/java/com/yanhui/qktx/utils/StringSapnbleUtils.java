package com.yanhui.qktx.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.yanhui.qktx.R;

/**
 * Created by liupanpan on 2017/9/24.
 */

public class StringSapnbleUtils {


    public static SpannableString getSpanString(String spanhans, String textCon, Context context) {
        if (!StringUtils.isEmpty(spanhans) && !StringUtils.isEmpty(textCon)) {
            SpannableString spannableString = new SpannableString(spanhans + ":" + textCon);
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.pop_bt)), 0, spanhans.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        } else {
            return null;
        }

    }


    public static SpannableString getSpannableString(String spanhans, String answearname, String textCon, Context context) {
        if (!StringUtils.isEmpty(spanhans) && !StringUtils.isEmpty(textCon) && !StringUtils.isEmpty(textCon)) {
            SpannableString ss = new SpannableString(spanhans + "回复" + answearname
                    + ":" + textCon);
            ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.pop_bt)), 0,
                    spanhans.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.pop_bt)), spanhans.length() + 2,
                    spanhans.length() + answearname.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ss;
        } else {
            return null;
        }
        //为回复的人昵称添加点击事件
//        ss.setSpan(new TextSpanClick(true), 0,
//                replyNickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        //为评论的人的添加点击事件
//        ss.setSpan(new TextSpanClick(false),replyNickName.length()+2,
//                replyNickName.length()+commentNickName.length()+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


    }

    public static SpannableStringBuilder getForegroundColorSpan(String key_word, String context) {
        if (!StringUtils.isEmpty(key_word) && !StringUtils.isEmpty(context)) {
            SpannableStringBuilder style = new SpannableStringBuilder(context);
            int fstart = context.indexOf(key_word);
            int fend = fstart + key_word.length();
            if (fstart != -1) {
                style.setSpan(new ForegroundColorSpan(Color.RED), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            } else {
                style.setSpan(new ForegroundColorSpan(Color.RED), 0, 0, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            return style;
        }
        return null;
    }
}
