package com.yanhui.qktx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liupanpan on 2017/8/31.
 */

public class UserBean extends BaseObjectEntity<UserBean.MesBean> {


    /**
     * mes : {"userId":2,"name":"","sex":"","city":"","province":"","industry":"","job":"","openId":"","unionId":"g4xnwenl","mobile":"15538703220","pwd":"e10adc3949ba59abbe56e057f20f883e","headUrl":"","parentUserId":-99,"lastLoginTime":1504145515342,"ctime":1504145515342,"status":1,"level":0,"isNew":1,"isApprentice":0,"qcodeUrl":"","appQcodeUrl":"","lastQcodeTime":-99,"finishTaskTime":-99,"appInstalled":1,"point":null,"cid":"","appSystem":"","zfb":"","zfbname":"","czmobile":"","token":"bdebac0a08e881574654179874edf7c1","age":0,"points":0,"shortName":"","currentMoney":"0.0","strHiddenMobile":"155****3220","strLastLoginTime":"2017-08-31 10:11:55","strQcodeUrl":"http://images.xiaocao01.cn/","strAppQcodeUrl":"http://images.xiaocao01.cn/","strStatus":"激活","strCtime":"2017-08-31 10:11:55","strName":"100232","strHeadUrl":"http://7xld7z.com1.z0.glb.clouddn.com/img/noImg.png"}
     */

    @SerializedName("mes")
    private MesBean mesX;

    public MesBean getMesX() {
        return mesX;
    }

    public void setMesX(MesBean mesX) {
        this.mesX = mesX;
    }

    public static class MesBean {
        /**
         * userId : 2
         * name :
         * sex :
         * city :
         * province :
         * industry :
         * job :
         * openId :
         * unionId : g4xnwenl
         * mobile : 15538703220
         * pwd : e10adc3949ba59abbe56e057f20f883e
         * headUrl :
         * parentUserId : -99
         * lastLoginTime : 1504145515342
         * ctime : 1504145515342
         * status : 1
         * level : 0
         * isNew : 1
         * isApprentice : 0
         * qcodeUrl :
         * appQcodeUrl :
         * lastQcodeTime : -99
         * finishTaskTime : -99
         * appInstalled : 1
         * point : null
         * cid :
         * appSystem :
         * zfb :
         * zfbname :
         * czmobile :
         * token : bdebac0a08e881574654179874edf7c1
         * age : 0
         * points : 0
         * shortName :
         * currentMoney : 0.0
         * strHiddenMobile : 155****3220
         * strLastLoginTime : 2017-08-31 10:11:55
         * strQcodeUrl : http://images.xiaocao01.cn/
         * strAppQcodeUrl : http://images.xiaocao01.cn/
         * strStatus : 激活
         * strCtime : 2017-08-31 10:11:55
         * strName : 100232
         * strHeadUrl : http://7xld7z.com1.z0.glb.clouddn.com/img/noImg.png
         */

        private int userId;
        private String name;
        private String sex;
        private String city;
        private String province;
        private String industry;
        private String job;
        private String openId;
        private String unionId;
        private String mobile;
        private String pwd;
        private String headUrl;
        private int parentUserId;
        private long lastLoginTime;
        private long ctime;
        private int status;
        private int level;
        private int isNew;
        private int isApprentice;
        private String qcodeUrl;
        private String appQcodeUrl;
        private int lastQcodeTime;
        private int finishTaskTime;
        private int appInstalled;
        private Object point;
        private String cid;
        private String appSystem;
        private String zfb;
        private String zfbname;
        private String czmobile;
        private String token;
        private int age;
        private int points;
        private String shortName;
        private String currentMoney;
        private String strHiddenMobile;
        private String strLastLoginTime;
        private String strQcodeUrl;
        private String strAppQcodeUrl;
        private String strStatus;
        private String strCtime;
        private String strName;
        private String strHeadUrl;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getUnionId() {
            return unionId;
        }

        public void setUnionId(String unionId) {
            this.unionId = unionId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getParentUserId() {
            return parentUserId;
        }

        public void setParentUserId(int parentUserId) {
            this.parentUserId = parentUserId;
        }

        public long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public long getCtime() {
            return ctime;
        }

        public void setCtime(long ctime) {
            this.ctime = ctime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getIsNew() {
            return isNew;
        }

        public void setIsNew(int isNew) {
            this.isNew = isNew;
        }

        public int getIsApprentice() {
            return isApprentice;
        }

        public void setIsApprentice(int isApprentice) {
            this.isApprentice = isApprentice;
        }

        public String getQcodeUrl() {
            return qcodeUrl;
        }

        public void setQcodeUrl(String qcodeUrl) {
            this.qcodeUrl = qcodeUrl;
        }

        public String getAppQcodeUrl() {
            return appQcodeUrl;
        }

        public void setAppQcodeUrl(String appQcodeUrl) {
            this.appQcodeUrl = appQcodeUrl;
        }

        public int getLastQcodeTime() {
            return lastQcodeTime;
        }

        public void setLastQcodeTime(int lastQcodeTime) {
            this.lastQcodeTime = lastQcodeTime;
        }

        public int getFinishTaskTime() {
            return finishTaskTime;
        }

        public void setFinishTaskTime(int finishTaskTime) {
            this.finishTaskTime = finishTaskTime;
        }

        public int getAppInstalled() {
            return appInstalled;
        }

        public void setAppInstalled(int appInstalled) {
            this.appInstalled = appInstalled;
        }

        public Object getPoint() {
            return point;
        }

        public void setPoint(Object point) {
            this.point = point;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getAppSystem() {
            return appSystem;
        }

        public void setAppSystem(String appSystem) {
            this.appSystem = appSystem;
        }

        public String getZfb() {
            return zfb;
        }

        public void setZfb(String zfb) {
            this.zfb = zfb;
        }

        public String getZfbname() {
            return zfbname;
        }

        public void setZfbname(String zfbname) {
            this.zfbname = zfbname;
        }

        public String getCzmobile() {
            return czmobile;
        }

        public void setCzmobile(String czmobile) {
            this.czmobile = czmobile;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getCurrentMoney() {
            return currentMoney;
        }

        public void setCurrentMoney(String currentMoney) {
            this.currentMoney = currentMoney;
        }

        public String getStrHiddenMobile() {
            return strHiddenMobile;
        }

        public void setStrHiddenMobile(String strHiddenMobile) {
            this.strHiddenMobile = strHiddenMobile;
        }

        public String getStrLastLoginTime() {
            return strLastLoginTime;
        }

        public void setStrLastLoginTime(String strLastLoginTime) {
            this.strLastLoginTime = strLastLoginTime;
        }

        public String getStrQcodeUrl() {
            return strQcodeUrl;
        }

        public void setStrQcodeUrl(String strQcodeUrl) {
            this.strQcodeUrl = strQcodeUrl;
        }

        public String getStrAppQcodeUrl() {
            return strAppQcodeUrl;
        }

        public void setStrAppQcodeUrl(String strAppQcodeUrl) {
            this.strAppQcodeUrl = strAppQcodeUrl;
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

        public String getStrName() {
            return strName;
        }

        public void setStrName(String strName) {
            this.strName = strName;
        }

        public String getStrHeadUrl() {
            return strHeadUrl;
        }

        public void setStrHeadUrl(String strHeadUrl) {
            this.strHeadUrl = strHeadUrl;
        }

        @Override
        public String toString() {
            return "MesBean{" +
                    "userId=" + userId +
                    ", name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", city='" + city + '\'' +
                    ", province='" + province + '\'' +
                    ", industry='" + industry + '\'' +
                    ", job='" + job + '\'' +
                    ", openId='" + openId + '\'' +
                    ", unionId='" + unionId + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", pwd='" + pwd + '\'' +
                    ", headUrl='" + headUrl + '\'' +
                    ", parentUserId=" + parentUserId +
                    ", lastLoginTime=" + lastLoginTime +
                    ", ctime=" + ctime +
                    ", status=" + status +
                    ", level=" + level +
                    ", isNew=" + isNew +
                    ", isApprentice=" + isApprentice +
                    ", qcodeUrl='" + qcodeUrl + '\'' +
                    ", appQcodeUrl='" + appQcodeUrl + '\'' +
                    ", lastQcodeTime=" + lastQcodeTime +
                    ", finishTaskTime=" + finishTaskTime +
                    ", appInstalled=" + appInstalled +
                    ", point=" + point +
                    ", cid='" + cid + '\'' +
                    ", appSystem='" + appSystem + '\'' +
                    ", zfb='" + zfb + '\'' +
                    ", zfbname='" + zfbname + '\'' +
                    ", czmobile='" + czmobile + '\'' +
                    ", token='" + token + '\'' +
                    ", age=" + age +
                    ", points=" + points +
                    ", shortName='" + shortName + '\'' +
                    ", currentMoney='" + currentMoney + '\'' +
                    ", strHiddenMobile='" + strHiddenMobile + '\'' +
                    ", strLastLoginTime='" + strLastLoginTime + '\'' +
                    ", strQcodeUrl='" + strQcodeUrl + '\'' +
                    ", strAppQcodeUrl='" + strAppQcodeUrl + '\'' +
                    ", strStatus='" + strStatus + '\'' +
                    ", strCtime='" + strCtime + '\'' +
                    ", strName='" + strName + '\'' +
                    ", strHeadUrl='" + strHeadUrl + '\'' +
                    '}';
        }
    }
}