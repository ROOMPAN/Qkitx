package com.yanhui.qktx.models;

/**
 * Created by xuyanjun on 15/10/24.
 * 用于data为String类型的接口
 * 如：
 *    {
 *    "code": 1,
 *    "data": "",
 *    "msg":"验证码已发送"
 *    }
 */
public class BaseMessageEntity extends BaseEntity {

    public String data;

}
