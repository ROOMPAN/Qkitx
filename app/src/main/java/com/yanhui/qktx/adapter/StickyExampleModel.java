package com.yanhui.qktx.adapter;

import com.yanhui.qktx.models.CommentBean;

import java.util.List;

public class StickyExampleModel {
    public String sticky;
    public String name;
    private List<CommentBean.DataBean> commentBeanList;

    public StickyExampleModel(String sticky, List<CommentBean.DataBean> commentBeanList) {
        this.sticky = sticky;
        this.commentBeanList = commentBeanList;
    }
}
