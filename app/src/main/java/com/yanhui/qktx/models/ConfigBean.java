package com.yanhui.qktx.models;

/**
 * Created by liupanpan on 2017/10/31.
 */

public class ConfigBean extends BaseEntity {


    /**
     * data : {"IOS_VERSION_CODE":1,"address":"上海市","invite_code":"http://statics.qukantianxia.com/html/h5/qukantianxia/yqm.html","AD_VERSION_CODE":1,"AD_IS_UPDATE":0,"IOS_IS_VERIFY":1,"AD_UPDATE_CONTENT":"解决了一些问题","AD_UPDATE_URL":"-","IOS_UPDATE_URL":"-"}
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
         * IOS_VERSION_CODE : 1
         * address : 上海市
         * invite_code : http://statics.qukantianxia.com/html/h5/qukantianxia/yqm.html
         * AD_VERSION_CODE : 1
         * AD_IS_UPDATE : 0
         * IOS_IS_VERIFY : 1
         * AD_UPDATE_CONTENT : 解决了一些问题
         * AD_UPDATE_URL : -
         * IOS_UPDATE_URL : -
         */

        private int IOS_VERSION_CODE;
        private String address;
        private String invite_code;
        private int AD_VERSION_CODE;
        private int AD_IS_UPDATE;
        private int IOS_IS_VERIFY;
        private String AD_UPDATE_CONTENT;
        private String AD_UPDATE_URL;
        private String IOS_UPDATE_URL;

        public int getIOS_VERSION_CODE() {
            return IOS_VERSION_CODE;
        }

        public void setIOS_VERSION_CODE(int IOS_VERSION_CODE) {
            this.IOS_VERSION_CODE = IOS_VERSION_CODE;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public int getAD_VERSION_CODE() {
            return AD_VERSION_CODE;
        }

        public void setAD_VERSION_CODE(int AD_VERSION_CODE) {
            this.AD_VERSION_CODE = AD_VERSION_CODE;
        }

        public int getAD_IS_UPDATE() {
            return AD_IS_UPDATE;
        }

        public void setAD_IS_UPDATE(int AD_IS_UPDATE) {
            this.AD_IS_UPDATE = AD_IS_UPDATE;
        }

        public int getIOS_IS_VERIFY() {
            return IOS_IS_VERIFY;
        }

        public void setIOS_IS_VERIFY(int IOS_IS_VERIFY) {
            this.IOS_IS_VERIFY = IOS_IS_VERIFY;
        }

        public String getAD_UPDATE_CONTENT() {
            return AD_UPDATE_CONTENT;
        }

        public void setAD_UPDATE_CONTENT(String AD_UPDATE_CONTENT) {
            this.AD_UPDATE_CONTENT = AD_UPDATE_CONTENT;
        }

        public String getAD_UPDATE_URL() {
            return AD_UPDATE_URL;
        }

        public void setAD_UPDATE_URL(String AD_UPDATE_URL) {
            this.AD_UPDATE_URL = AD_UPDATE_URL;
        }

        public String getIOS_UPDATE_URL() {
            return IOS_UPDATE_URL;
        }

        public void setIOS_UPDATE_URL(String IOS_UPDATE_URL) {
            this.IOS_UPDATE_URL = IOS_UPDATE_URL;
        }
    }
}