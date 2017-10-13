package com.yanhui.qktx.models;

/**
 * Created by liupanpan on 2017/9/29.
 */

public class IsConnBean extends BaseEntity {

    /**
     * data : {"comments":3,"isConn":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * comments : 3
         * isConn : 0
         */

        private int comments;
        private int isConn;

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public int getIsConn() {
            return isConn;
        }

        public void setIsConn(int isConn) {
            this.isConn = isConn;
        }
    }
}