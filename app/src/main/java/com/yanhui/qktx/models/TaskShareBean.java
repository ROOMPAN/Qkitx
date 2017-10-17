package com.yanhui.qktx.models;

/**
 * Created by liupanpan on 2017/10/17.
 */

public class TaskShareBean extends BaseEntity {

    /**
     * data : {"shareUrl":"http://tz.qukantianxia.com/showArticle/169636/8c68898a60c64a5cb6edba3947fe1277","shareTitle":"日本战后做的第1件事，7万少女...","shareDesc":"日本战后做的第1件事，7万少女的血泪史，东京成美军大兵的天堂","shareImg":"http://images.xiaocao01.cn/42bebb72-dfa6-4d6d-b3e5-a1506c937e34.jpg?imageMogr2/thumbnail/200x160!"}
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
         * shareUrl : http://tz.qukantianxia.com/showArticle/169636/8c68898a60c64a5cb6edba3947fe1277
         * shareTitle : 日本战后做的第1件事，7万少女...
         * shareDesc : 日本战后做的第1件事，7万少女的血泪史，东京成美军大兵的天堂
         * shareImg : http://images.xiaocao01.cn/42bebb72-dfa6-4d6d-b3e5-a1506c937e34.jpg?imageMogr2/thumbnail/200x160!
         */

        private String shareUrl;
        private String shareTitle;
        private String shareDesc;
        private String shareImg;

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public String getShareDesc() {
            return shareDesc;
        }

        public void setShareDesc(String shareDesc) {
            this.shareDesc = shareDesc;
        }

        public String getShareImg() {
            return shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }
    }
}
