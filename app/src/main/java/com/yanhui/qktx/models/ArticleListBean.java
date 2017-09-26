package com.yanhui.qktx.models;

import java.util.List;

/**
 * Created by liupanpan on 2017/9/15.
 */

public class ArticleListBean extends BaseEntity {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * taskId : 169650
         * passId : 0
         * tTitle : 大马一华商收存超限量石油气桶 被罚款10万令吉
         * tDesc : 大马一华商收存超限量石油气桶 被罚款10万令吉
         * tImage : http://images.xiaocao01.cn/e0868ea2-68fe-4eb5-a68b-81d7b4357a57.jpg
         * tUrl : http://www.weikandian.com/n?timestamp=1505811760&signature=AprknogbLlXMvW9dP81D7wga8hKo8rnD36Ej2a7KRJwNyYOQx5&access=WIFI
         * weight : 1
         * urlType : 0
         * totalClickCount : 1000000000
         * currentClickCount : 0
         * clickPoints : 80
         * maxPoints : 2000
         * tType : 1,session,timeline
         * status : 1
         * ctime : 1506036003904
         * memo :
         * tCate : 84
         * tKey : weikandian1561351
         * tReadCount : 0
         * tReadLimit : 0
         * tTime : 0
         * pageUrl : 1506036003904.html
         * tAuthImg :
         * tAuthName :
         * targetType : 1
         * articleType : 2
         * lastModifyTime : 1506036003904
         * isHot : 1
         * isRalation : 0
         * hotSort : 1506036003904
         * cateSort : 1506036003904
         * forApp : 0
         * isAd : 0
         * isTecentStyle : 0
         * customerAdvertId : -99
         * strImages : [{"image":"http://images.xiaocao01.cn/e0868ea2-68fe-4eb5-a68b-81d7b4357a57.jpg"}]
         * shortTitle : 大马一华商收存超限量石油气桶 ...
         * strStatus : 开启
         * strCtime : 2017-09-22 07:20:03
         * smallImage : http://images.xiaocao01.cn/e0868ea2-68fe-4eb5-a68b-81d7b4357a57.jpg?imageMogr2/thumbnail/200x160!
         * strArticleTime :
         * redirectUrl : http://7xp7e5.com1.z0.glb.clouddn.com/outer.html
         * strTReadCount : 100000+
         * clickMoney : 0.08
         * surplusMoney : -1604378.624
         * strHotSort : 已推荐
         * isHightPrice : 0
         * ucSmallImage : http://images.xiaocao01.cn/e0868ea2-68fe-4eb5-a68b-81d7b4357a57.jpg?imageMogr2/thumbnail/200x200!
         * strUrlType : 暂无
         * strShortCtime : 2017-09-22
         * qnPageUrl : http://ld.qukantianxia.com/showArticle/169650
         * taskServerUrl : http://ld.qukantianxia.com/showArticle/169650
         * shareUrl :
         */

        private int taskId;
        private int passId;
        private String tTitle;
        private String tDesc;
        private String tImage;
        private String tUrl;
        private int weight;
        private int urlType;
        private int totalClickCount;
        private int currentClickCount;
        private int clickPoints;
        private int maxPoints;
        private String tType;
        private int status;
        private long ctime;
        private String memo;
        private int tCate;
        private String tKey;
        private int tReadCount;
        private int tReadLimit;
        private int tTime;
        private String pageUrl;
        private String tAuthImg;
        private String tAuthName;
        private int targetType;
        private int articleType;
        private long lastModifyTime;
        private int isHot;
        private int isRalation;
        private long hotSort;
        private long cateSort;
        private int forApp;
        private int isAd;
        private int isTecentStyle;
        private int customerAdvertId;
        private String shortTitle;
        private String strStatus;
        private String strCtime;
        private String smallImage;
        private String strArticleTime;
        private String redirectUrl;
        private String strTReadCount;
        private String clickMoney;
        private double surplusMoney;
        private String strHotSort;
        private int isHightPrice;
        private String ucSmallImage;
        private String strUrlType;
        private String strShortCtime;
        private String qnPageUrl;
        private String taskServerUrl;
        private String shareUrl;
        private List<StrImagesBean> strImages;
        private Long showTime;
        private String videoUrl;
        private int videoSecond;
        private String taskUrl;
        private int isConn;
        private int comments;

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public int getVideoSecond() {
            return videoSecond;
        }

        public void setVideoSecond(int videoSecond) {
            this.videoSecond = videoSecond;
        }

        public String getTaskUrl() {
            return taskUrl;
        }

        public void setTaskUrl(String taskUrl) {
            this.taskUrl = taskUrl;
        }

        public int getIsConn() {
            return isConn;
        }

        public void setIsConn(int isConn) {
            this.isConn = isConn;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public Long getShowTime() {
            return showTime;
        }

        public void setShowTime(Long showTime) {
            this.showTime = showTime;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getPassId() {
            return passId;
        }

        public void setPassId(int passId) {
            this.passId = passId;
        }

        public String getTTitle() {
            return tTitle;
        }

        public void setTTitle(String tTitle) {
            this.tTitle = tTitle;
        }

        public String getTDesc() {
            return tDesc;
        }

        public void setTDesc(String tDesc) {
            this.tDesc = tDesc;
        }

        public String getTImage() {
            return tImage;
        }

        public void setTImage(String tImage) {
            this.tImage = tImage;
        }

        public String getTUrl() {
            return tUrl;
        }

        public void setTUrl(String tUrl) {
            this.tUrl = tUrl;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getUrlType() {
            return urlType;
        }

        public void setUrlType(int urlType) {
            this.urlType = urlType;
        }

        public int getTotalClickCount() {
            return totalClickCount;
        }

        public void setTotalClickCount(int totalClickCount) {
            this.totalClickCount = totalClickCount;
        }

        public int getCurrentClickCount() {
            return currentClickCount;
        }

        public void setCurrentClickCount(int currentClickCount) {
            this.currentClickCount = currentClickCount;
        }

        public int getClickPoints() {
            return clickPoints;
        }

        public void setClickPoints(int clickPoints) {
            this.clickPoints = clickPoints;
        }

        public int getMaxPoints() {
            return maxPoints;
        }

        public void setMaxPoints(int maxPoints) {
            this.maxPoints = maxPoints;
        }

        public String getTType() {
            return tType;
        }

        public void setTType(String tType) {
            this.tType = tType;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCtime() {
            return ctime;
        }

        public void setCtime(long ctime) {
            this.ctime = ctime;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public int getTCate() {
            return tCate;
        }

        public void setTCate(int tCate) {
            this.tCate = tCate;
        }

        public String getTKey() {
            return tKey;
        }

        public void setTKey(String tKey) {
            this.tKey = tKey;
        }

        public int getTReadCount() {
            return tReadCount;
        }

        public void setTReadCount(int tReadCount) {
            this.tReadCount = tReadCount;
        }

        public int getTReadLimit() {
            return tReadLimit;
        }

        public void setTReadLimit(int tReadLimit) {
            this.tReadLimit = tReadLimit;
        }

        public int getTTime() {
            return tTime;
        }

        public void setTTime(int tTime) {
            this.tTime = tTime;
        }

        public String getPageUrl() {
            return pageUrl;
        }

        public void setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
        }

        public String getTAuthImg() {
            return tAuthImg;
        }

        public void setTAuthImg(String tAuthImg) {
            this.tAuthImg = tAuthImg;
        }

        public String getTAuthName() {
            return tAuthName;
        }

        public void setTAuthName(String tAuthName) {
            this.tAuthName = tAuthName;
        }

        public int getTargetType() {
            return targetType;
        }

        public void setTargetType(int targetType) {
            this.targetType = targetType;
        }

        public int getArticleType() {
            return articleType;
        }

        public void setArticleType(int articleType) {
            this.articleType = articleType;
        }

        public long getLastModifyTime() {
            return lastModifyTime;
        }

        public void setLastModifyTime(long lastModifyTime) {
            this.lastModifyTime = lastModifyTime;
        }

        public int getIsHot() {
            return isHot;
        }

        public void setIsHot(int isHot) {
            this.isHot = isHot;
        }

        public int getIsRalation() {
            return isRalation;
        }

        public void setIsRalation(int isRalation) {
            this.isRalation = isRalation;
        }

        public long getHotSort() {
            return hotSort;
        }

        public void setHotSort(long hotSort) {
            this.hotSort = hotSort;
        }

        public long getCateSort() {
            return cateSort;
        }

        public void setCateSort(long cateSort) {
            this.cateSort = cateSort;
        }

        public int getForApp() {
            return forApp;
        }

        public void setForApp(int forApp) {
            this.forApp = forApp;
        }

        public int getIsAd() {
            return isAd;
        }

        public void setIsAd(int isAd) {
            this.isAd = isAd;
        }

        public int getIsTecentStyle() {
            return isTecentStyle;
        }

        public void setIsTecentStyle(int isTecentStyle) {
            this.isTecentStyle = isTecentStyle;
        }

        public int getCustomerAdvertId() {
            return customerAdvertId;
        }

        public void setCustomerAdvertId(int customerAdvertId) {
            this.customerAdvertId = customerAdvertId;
        }

        public String getShortTitle() {
            return shortTitle;
        }

        public void setShortTitle(String shortTitle) {
            this.shortTitle = shortTitle;
        }

        public String getStrStatus() {
            return strStatus;
        }

        public void setStrStatus(String strStatus) {
            this.strStatus = strStatus;
        }

        public String getStrCtime() {
            return strCtime;
        }

        public void setStrCtime(String strCtime) {
            this.strCtime = strCtime;
        }

        public String getSmallImage() {
            return smallImage;
        }

        public void setSmallImage(String smallImage) {
            this.smallImage = smallImage;
        }

        public String getStrArticleTime() {
            return strArticleTime;
        }

        public void setStrArticleTime(String strArticleTime) {
            this.strArticleTime = strArticleTime;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public String getStrTReadCount() {
            return strTReadCount;
        }

        public void setStrTReadCount(String strTReadCount) {
            this.strTReadCount = strTReadCount;
        }

        public String getClickMoney() {
            return clickMoney;
        }

        public void setClickMoney(String clickMoney) {
            this.clickMoney = clickMoney;
        }

        public double getSurplusMoney() {
            return surplusMoney;
        }

        public void setSurplusMoney(double surplusMoney) {
            this.surplusMoney = surplusMoney;
        }

        public String getStrHotSort() {
            return strHotSort;
        }

        public void setStrHotSort(String strHotSort) {
            this.strHotSort = strHotSort;
        }

        public int getIsHightPrice() {
            return isHightPrice;
        }

        public void setIsHightPrice(int isHightPrice) {
            this.isHightPrice = isHightPrice;
        }

        public String getUcSmallImage() {
            return ucSmallImage;
        }

        public void setUcSmallImage(String ucSmallImage) {
            this.ucSmallImage = ucSmallImage;
        }

        public String getStrUrlType() {
            return strUrlType;
        }

        public void setStrUrlType(String strUrlType) {
            this.strUrlType = strUrlType;
        }

        public String getStrShortCtime() {
            return strShortCtime;
        }

        public void setStrShortCtime(String strShortCtime) {
            this.strShortCtime = strShortCtime;
        }

        public String getQnPageUrl() {
            return qnPageUrl;
        }

        public void setQnPageUrl(String qnPageUrl) {
            this.qnPageUrl = qnPageUrl;
        }

        public String getTaskServerUrl() {
            return taskServerUrl;
        }

        public void setTaskServerUrl(String taskServerUrl) {
            this.taskServerUrl = taskServerUrl;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public List<StrImagesBean> getStrImages() {
            return strImages;
        }

        public void setStrImages(List<StrImagesBean> strImages) {
            this.strImages = strImages;
        }

        public static class StrImagesBean {
            /**
             * image : http://images.xiaocao01.cn/e0868ea2-68fe-4eb5-a68b-81d7b4357a57.jpg
             */

            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "taskId=" + taskId +
                    ", passId=" + passId +
                    ", tTitle='" + tTitle + '\'' +
                    ", tDesc='" + tDesc + '\'' +
                    ", tImage='" + tImage + '\'' +
                    ", tUrl='" + tUrl + '\'' +
                    ", weight=" + weight +
                    ", urlType=" + urlType +
                    ", totalClickCount=" + totalClickCount +
                    ", currentClickCount=" + currentClickCount +
                    ", clickPoints=" + clickPoints +
                    ", maxPoints=" + maxPoints +
                    ", tType='" + tType + '\'' +
                    ", status=" + status +
                    ", ctime=" + ctime +
                    ", memo='" + memo + '\'' +
                    ", tCate=" + tCate +
                    ", tKey='" + tKey + '\'' +
                    ", tReadCount=" + tReadCount +
                    ", tReadLimit=" + tReadLimit +
                    ", tTime=" + tTime +
                    ", pageUrl='" + pageUrl + '\'' +
                    ", tAuthImg='" + tAuthImg + '\'' +
                    ", tAuthName='" + tAuthName + '\'' +
                    ", targetType=" + targetType +
                    ", articleType=" + articleType +
                    ", lastModifyTime=" + lastModifyTime +
                    ", isHot=" + isHot +
                    ", isRalation=" + isRalation +
                    ", hotSort=" + hotSort +
                    ", cateSort=" + cateSort +
                    ", forApp=" + forApp +
                    ", isAd=" + isAd +
                    ", isTecentStyle=" + isTecentStyle +
                    ", customerAdvertId=" + customerAdvertId +
                    ", shortTitle='" + shortTitle + '\'' +
                    ", strStatus='" + strStatus + '\'' +
                    ", strCtime='" + strCtime + '\'' +
                    ", smallImage='" + smallImage + '\'' +
                    ", strArticleTime='" + strArticleTime + '\'' +
                    ", redirectUrl='" + redirectUrl + '\'' +
                    ", strTReadCount='" + strTReadCount + '\'' +
                    ", clickMoney='" + clickMoney + '\'' +
                    ", surplusMoney=" + surplusMoney +
                    ", strHotSort='" + strHotSort + '\'' +
                    ", isHightPrice=" + isHightPrice +
                    ", ucSmallImage='" + ucSmallImage + '\'' +
                    ", strUrlType='" + strUrlType + '\'' +
                    ", strShortCtime='" + strShortCtime + '\'' +
                    ", qnPageUrl='" + qnPageUrl + '\'' +
                    ", taskServerUrl='" + taskServerUrl + '\'' +
                    ", shareUrl='" + shareUrl + '\'' +
                    ", strImages=" + strImages +
                    '}';
        }
    }
}
