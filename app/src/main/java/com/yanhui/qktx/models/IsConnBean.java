package com.yanhui.qktx.models;

/**
 * Created by liupanpan on 2017/9/29.
 */

public class IsConnBean extends BaseEntity {

    /**
     * data : {"status":0,"strCtime":"","commentIds":""}
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
         * status : 0
         * strCtime :
         * commentIds :
         */

        private int isConn;

        public int getIsConn() {
            return isConn;
        }

        public void setIsConn(int isConn) {
            this.isConn = isConn;
        }

    }
}