package com.yanhui.qktx.models;

import java.util.List;

/**
 * Created by liupanpan on 2017/9/20.
 */

public class CateNameBean extends BaseEntity {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cateId : 99
         * cateName : 推荐
         * gzhName : 推荐
         * gzhContent :
         * articleEnd :
         * gzhUrl :
         * sort : 0
         * isShow : 1
         * outerCateId : -99
         * cateAd :
         * originalId :
         * totalClick : 0
         * cateFans : 0
         * type : 0
         * strCateFans : 0(0%)
         * strIsShow : 显示
         */

        private int cateId;
        private String cateName;
        private String gzhName;
        private String gzhContent;
        private String articleEnd;
        private String gzhUrl;
        private int sort;
        private int isShow;
        private int outerCateId;
        private String cateAd;
        private String originalId;
        private int totalClick;
        private int cateFans;
        private int type;
        private String strCateFans;
        private String strIsShow;

        public int getCateId() {
            return cateId;
        }

        public void setCateId(int cateId) {
            this.cateId = cateId;
        }

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public String getGzhName() {
            return gzhName;
        }

        public void setGzhName(String gzhName) {
            this.gzhName = gzhName;
        }

        public String getGzhContent() {
            return gzhContent;
        }

        public void setGzhContent(String gzhContent) {
            this.gzhContent = gzhContent;
        }

        public String getArticleEnd() {
            return articleEnd;
        }

        public void setArticleEnd(String articleEnd) {
            this.articleEnd = articleEnd;
        }

        public String getGzhUrl() {
            return gzhUrl;
        }

        public void setGzhUrl(String gzhUrl) {
            this.gzhUrl = gzhUrl;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public int getOuterCateId() {
            return outerCateId;
        }

        public void setOuterCateId(int outerCateId) {
            this.outerCateId = outerCateId;
        }

        public String getCateAd() {
            return cateAd;
        }

        public void setCateAd(String cateAd) {
            this.cateAd = cateAd;
        }

        public String getOriginalId() {
            return originalId;
        }

        public void setOriginalId(String originalId) {
            this.originalId = originalId;
        }

        public int getTotalClick() {
            return totalClick;
        }

        public void setTotalClick(int totalClick) {
            this.totalClick = totalClick;
        }

        public int getCateFans() {
            return cateFans;
        }

        public void setCateFans(int cateFans) {
            this.cateFans = cateFans;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getStrCateFans() {
            return strCateFans;
        }

        public void setStrCateFans(String strCateFans) {
            this.strCateFans = strCateFans;
        }

        public String getStrIsShow() {
            return strIsShow;
        }

        public void setStrIsShow(String strIsShow) {
            this.strIsShow = strIsShow;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "cateId=" + cateId +
                    ", cateName='" + cateName + '\'' +
                    ", gzhName='" + gzhName + '\'' +
                    ", gzhContent='" + gzhContent + '\'' +
                    ", articleEnd='" + articleEnd + '\'' +
                    ", gzhUrl='" + gzhUrl + '\'' +
                    ", sort=" + sort +
                    ", isShow=" + isShow +
                    ", outerCateId=" + outerCateId +
                    ", cateAd='" + cateAd + '\'' +
                    ", originalId='" + originalId + '\'' +
                    ", totalClick=" + totalClick +
                    ", cateFans=" + cateFans +
                    ", type=" + type +
                    ", strCateFans='" + strCateFans + '\'' +
                    ", strIsShow='" + strIsShow + '\'' +
                    '}';
        }
    }
}
