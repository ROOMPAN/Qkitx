package com.yanhui.qktx.models;

/**
 * Created by xuyanjun on 15/10/24.
 * 所有接口通用数据
 */
public class BaseEntity {

    public int result;
    public String mes;
    public String field;

    public boolean isOKCode() {
        return result == 0;
    }

}
