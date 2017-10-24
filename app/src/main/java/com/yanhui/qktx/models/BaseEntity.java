package com.yanhui.qktx.models;

import com.yanhui.qktx.utils.StringUtils;

/**
 * Created by xuyanjun on 15/10/24.
 * 所有接口通用数据
 */
public class BaseEntity {
    public int code;

    public String mes;
    public String msg;

    public String result;

    public boolean isOKCode() {
        if (code == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOKResult() {
        if (Integer.parseInt(result) == 1 && !StringUtils.isEmpty(result)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNotResult() {
        if (Integer.parseInt(result) == 10000 && !StringUtils.isEmpty(result)) {
            return true;
        } else {
            return false;
        }
    }

}
