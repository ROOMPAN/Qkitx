package com.yanhui.qktx.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.yanhui.qktx.R;

/**
 * Created by liupanpan on 2017/9/24.
 */

public class StringSapnbleUtils {
    private static SpannableString spannableString;

    public static SpannableString getSpanString(String spanhans, String textCon, Context context) {
        if (!StringUtils.isEmpty(spanhans) && !StringUtils.isEmpty(textCon)) {
            spannableString = new SpannableString(spanhans + ":" + textCon);
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.pop_bt)), 0, spanhans.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        } else {
            return null;
        }

    }
}
