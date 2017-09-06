package com.yanhui.qktx.models;

/**
 * Created by xuyanjun on 15/10/24.
 * 所有接口通用数据
 */
public class BaseEntity {
    public int code;
    public String msg;

    //public String result;

    public boolean isOKCode() {
        if (code == 1) {
            return true;
        } else {
            return false;
        }
    }

//    public boolean isOKCode() {
//        if (Integer.parseInt(result) == 1 && !StringUtils.isEmpty(result)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

}
