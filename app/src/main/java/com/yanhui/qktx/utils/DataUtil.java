package com.yanhui.qktx.utils;

import com.yanhui.qktx.adapter.StickyExampleModel;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    public static final int MODEL_COUNT = 20;

    public static List<StickyExampleModel> getData() {
        List<StickyExampleModel> stickyExampleModels = new ArrayList<>();

        for (int index = 0; index < MODEL_COUNT; index++) {
            if (index < 5) {
                stickyExampleModels.add(new StickyExampleModel("最热评论", "name" + index, "gender" + index, "profession" + index));
            } else {
                stickyExampleModels.add(new StickyExampleModel("最新评论", "name" + index, "gender" + index, "profession" + index));
            }
        }

        return stickyExampleModels;
    }
}
