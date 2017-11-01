package com.yanhui.qktx.models;

/**
 * Created by liupanpan on 2017/10/31.
 */

public class ConfigBean extends BaseEntity {

    /**
     * data : {"address":"上海市","APP_VERSION":"1.1.1.1","info":1}
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
         * address : 上海市
         * APP_VERSION : 1.1.1.1
         * info : 1
         */
        private String address;
        private String APP_VERSION;
        private int info;
        private String invite_code;

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAPP_VERSION() {
            return APP_VERSION;
        }

        public void setAPP_VERSION(String APP_VERSION) {
            this.APP_VERSION = APP_VERSION;
        }

        public int getInfo() {
            return info;
        }

        public void setInfo(int info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "address='" + address + '\'' +
                    ", APP_VERSION='" + APP_VERSION + '\'' +
                    ", info=" + info +
                    ", invite_code='" + invite_code + '\'' +
                    '}';
        }
    }

}
