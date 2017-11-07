package com.yanhui.qktx.models;

import java.util.List;

/**
 * Created by liupanpan on 2017/9/27.
 */

public class CommentBean extends BaseEntity {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * commentId : 14
         * answerCommentId : 0
         * comments : 2
         * list : [{"commentId":0,"answerCommentId":14,"comments":0,"list":[],"context":"wo qu ","taskId":168772,"userId":22,"answerUserId":0,"answerUserName":"NeverFear","ctime":0,"ups":0,"status":0,"strCtime":""},{"commentId":0,"answerCommentId":14,"comments":0,"list":[],"context":"nimei ","taskId":168772,"userId":24,"answerUserId":0,"answerUserName":"爷贼坏了","ctime":0,"ups":0,"status":0,"strCtime":""}]
         * context : null
         * taskId : 0
         * userId : 20
         * answerUserId : 0
         * answerUserName : null
         * ctime : 1504147711703
         * ups : 0
         * status : 0
         * strCtime : 2017-08-31 10:48:31
         * commentIds : null
         * headUrl : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJrxNAnfibiaU3wQUOjQAoQJ9UScV3cRZXLxicEXUzk3YgsicH71hwXGHAtn5ibmZ25f93JBS6bsEySI9A/0
         * name : ROOM先生
         */

        private int commentId;
        private int answerCommentId;
        private int comments;
        private String context;
        private int taskId;
        private int userId;
        private int answerUserId;
        private String answerUserName;
        private long ctime;
        private int ups;
        private int status;
        private String strCtime;
        private String commentIds;
        private String headUrl;
        private String name;
        private int isUp;
        private String address;
        private List<ListBean> list;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIsUp() {
            return isUp;
        }

        public void setIsUp(int isUp) {
            this.isUp = isUp;
        }

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

        public Object getAnswerUserName() {
            return answerUserName;
        }

        public void setAnswerUserName(String answerUserName) {
            this.answerUserName = answerUserName;
        }

        public long getCtime() {
            return ctime;
        }

        public void setCtime(long ctime) {
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

        public Object getCommentIds() {
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
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
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "commentId=" + commentId +
                    ", answerCommentId=" + answerCommentId +
                    ", comments=" + comments +
                    ", context=" + context +
                    ", taskId=" + taskId +
                    ", userId=" + userId +
                    ", answerUserId=" + answerUserId +
                    ", answerUserName=" + answerUserName +
                    ", ctime=" + ctime +
                    ", ups=" + ups +
                    ", status=" + status +
                    ", strCtime='" + strCtime + '\'' +
                    ", commentIds=" + commentIds +
                    ", headUrl='" + headUrl + '\'' +
                    ", name='" + name + '\'' +
                    ", list=" + list +
                    '}';
        }
    }
}
