package com.yanhui.qktx.models;

import com.yanhui.qktx.utils.StringUtils;

/**
 * Created by xuyanjun on 15/10/24.
 * 所有接口通用数据
 */
public class BaseEntity {

    public String result;

    public boolean isOKCode() {
        if (Integer.parseInt(result) == 1 && !StringUtils.isEmpty(result)) {
            return true;
        } else {
            return false;
        }
    }

}
