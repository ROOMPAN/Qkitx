package com.yanhui.qktx.models;

import java.util.List;

/**
 * Created by liupanpan on 2017/10/14.
 */

public class ListBean extends CommentBean.DataBean.ListBean {
    /**
     * commentId : 0
     * answerCommentId : 14
     * comments : 0
     * list : []
     * context : wo qu
     * taskId : 168772
     * userId : 22
     * answerUserId : 0
     * answerUserName : NeverFear
     * ctime : 0
     * ups : 0
     * status : 0
     * strCtime :
     * commentIds:""
     * headUrl":""
     * name":"NeverFear"
     */

    private int commentId;
    private int answerCommentId;
    private int comments;
    private String context;
    private int taskId;
    private int userId;
    private int answerUserId;
    private String answerUserName;
    private int ctime;
    private int ups;
    private int status;
    private String strCtime;
    private List<?> list;
    private String commentIds;
    private String headUrl;
    private String name;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getAnswerCommentId() {
        return answerCommentId;
    }

    public void setAnswerCommentId(int answerCommentId) {
        this.answerCommentId = answerCommentId;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnswerUserId() {
        return answerUserId;
    }

    public void setAnswerUserId(int answerUserId) {
        this.answerUserId = answerUserId;
    }

    public String getAnswerUserName() {
        return answerUserName;
    }

    public void setAnswerUserName(String answerUserName) {
        this.answerUserName = answerUserName;
    }

    public int getCtime() {
        return ctime;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStrCtime() {
        return strCtime;
    }

    public void setStrCtime(String strCtime) {
        this.strCtime = strCtime;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public String getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(String commentIds) {
        this.commentIds = commentIds;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
