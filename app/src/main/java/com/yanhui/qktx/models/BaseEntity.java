package com.yanhui.qktx.models;

/**
 * Created by xuyanjun on 15/10/24.
 * 所有接口通用数据
 */
public class BaseEntity {

    public int code;
    public String msg;
    public String field;

    public boolean isOKCode() {
        return code == 0;
    }

}
