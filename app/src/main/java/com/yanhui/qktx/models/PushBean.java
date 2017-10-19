package com.yanhui.qktx.models;

/**
 * Created by liupanpan on 2017/10/19.
 * 友盟推送实体类
 */

public class PushBean {
    /**
     * taskUrl : http://ld.qukantianxia.com/showArticle/169676
     * taskId : 169676
     */

    private String taskUrl;
    private int taskId;
    private int articleType;

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
