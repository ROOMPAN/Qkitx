package com.yanhui.qktx.models;

import java.util.List;

/**
 * Created by liupanpan on 2017/9/13.
 */

public class PersonBean extends BaseEntity {


    /**
     * data : {"menu":{"message":"http://statics.qukantianxia.com/html/h5/qukantianxia/message.html","rank":"http://statics.qukantianxia.com/html/h5/qukantianxia/topb.html","help":"http://statics.qukantianxia.com/html/h5/qukantianxia/help.html","income":"http://statics.qukantianxia.com/html/h5/qukantianxia/srmx.html","inviteApprentice ":"http://statics.qukantianxia.com/html/h5/qukantianxia/invite.html","inviteCode":"http://statics.qukantianxia.com/html/h5/qukantianxia/bdyqm.html","comment":"http://statics.qukantianxia.com/html/h5/qukantianxia/mycomment.html","activity":"http://statics.qukantianxia.com/html/h5/qukantianxia/task.html"},"data":{"point":2500,"money":0,"isRewardActive":0,"notice":""},"user":{"userId":20,"name":"吃吃吃","sex":"","city":"","province":"","industry":"","job":"","openId":"osZzdvyB1ioYx8C5Qr67s4Wo1MQE","unionId":"cuhaHtfP","mobile":"15538703220","pwd":"e10adc3949ba59abbe56e057f20f883e","headUrl":"http://images.qukantianxia.com/images/userNew/2abe8d1c-fb6f-47c2-b123-7baaa60a84f7.jpg","parentUserId":-99,"lastLoginTime":1505901350191,"ctime":1505210687340,"status":1,"level":0,"isNew":1,"isApprentice":0,"qcodeUrl":"","appQcodeUrl":"","lastQcodeTime":-99,"finishTaskTime":-99,"appInstalled":1,"point":null,"cid":"","appSystem":"","zfb":"","zfbname":"","czmobile":"","token":"9068137014509e3c15057f1e77af12f6","yesPoint":0,"yesMoney":0,"age":78,"money":0,"points":0,"shortName":"吃吃吃","strHiddenMobile":"155****3220","currentMoney":"0.0","strCtime":"2017-09-12 18:04:47","strLastLoginTime":"2017-09-20 17:55:50","strHeadUrl":"http://images.qukantianxia.com/images/userNew/2abe8d1c-fb6f-47c2-b123-7baaa60a84f7.jpg","strName":"吃吃吃","strQcodeUrl":"http://images.qukantianxia.com/","strAppQcodeUrl":"http://images.qukantianxia.com/","strStatus":"激活"},"banner":[{"bannerId":4,"title":"11","imgUrl":"http://images.qukantianxia.com/0cb9204e-87b1-458b-9a92-efc18c18b56e.png","skipUrl":"http://www.baidu.com","ctime":1507790522045,"status":1},{"bannerId":6,"title":"222","imgUrl":"http://images.qukantianxia.com/d189c0d2-a77e-4cf0-bc76-52aaa681f17b.png","skipUrl":"http://m.youku.com","ctime":1507790547873,"status":1}]}
     */

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * menu : {"message":"http://statics.qukantianxia.com/html/h5/qukantianxia/message.html","rank":"http://statics.qukantianxia.com/html/h5/qukantianxia/topb.html","help":"http://statics.qukantianxia.com/html/h5/qukantianxia/help.html","income":"http://statics.qukantianxia.com/html/h5/qukantianxia/srmx.html","inviteApprentice ":"http://statics.qukantianxia.com/html/h5/qukantianxia/invite.html","inviteCode":"http://statics.qukantianxia.com/html/h5/qukantianxia/bdyqm.html","comment":"http://statics.qukantianxia.com/html/h5/qukantianxia/mycomment.html","activity":"http://statics.qukantianxia.com/html/h5/qukantianxia/task.html"}
         * data : {"point":2500,"money":0,"isRewardActive":0,"notice":""}
         * user : {"userId":20,"name":"吃吃吃","sex":"","city":"","province":"","industry":"","job":"","openId":"osZzdvyB1ioYx8C5Qr67s4Wo1MQE","unionId":"cuhaHtfP","mobile":"15538703220","pwd":"e10adc3949ba59abbe56e057f20f883e","headUrl":"http://images.qukantianxia.com/images/userNew/2abe8d1c-fb6f-47c2-b123-7baaa60a84f7.jpg","parentUserId":-99,"lastLoginTime":1505901350191,"ctime":1505210687340,"status":1,"level":0,"isNew":1,"isApprentice":0,"qcodeUrl":"","appQcodeUrl":"","lastQcodeTime":-99,"finishTaskTime":-99,"appInstalled":1,"point":null,"cid":"","appSystem":"","zfb":"","zfbname":"","czmobile":"","token":"9068137014509e3c15057f1e77af12f6","yesPoint":0,"yesMoney":0,"age":78,"money":0,"points":0,"shortName":"吃吃吃","strHiddenMobile":"155****3220","currentMoney":"0.0","strCtime":"2017-09-12 18:04:47","strLastLoginTime":"2017-09-20 17:55:50","strHeadUrl":"http://images.qukantianxia.com/images/userNew/2abe8d1c-fb6f-47c2-b123-7baaa60a84f7.jpg","strName":"吃吃吃","strQcodeUrl":"http://images.qukantianxia.com/","strAppQcodeUrl":"http://images.qukantianxia.com/","strStatus":"激活"}
         * banner : [{"bannerId":4,"title":"11","imgUrl":"http://images.qukantianxia.com/0cb9204e-87b1-458b-9a92-efc18c18b56e.png","skipUrl":"http://www.baidu.com","ctime":1507790522045,"status":1},{"bannerId":6,"title":"222","imgUrl":"http://images.qukantianxia.com/d189c0d2-a77e-4cf0-bc76-52aaa681f17b.png","skipUrl":"http://m.youku.com","ctime":1507790547873,"status":1}]
         */

        private MenuBean menu;
        private DataBean data;
        private UserBean user;
        private List<BannerBean> banner;

        public MenuBean getMenu() {
            return menu;
        }

        public void setMenu(MenuBean menu) {
            this.menu = menu;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public static class MenuBean {
            /**
             * message : http://statics.qukantianxia.com/html/h5/qukantianxia/message.html
             * rank : http://statics.qukantianxia.com/html/h5/qukantianxia/topb.html
             * help : http://statics.qukantianxia.com/html/h5/qukantianxia/help.html
             * income : http://statics.qukantianxia.com/html/h5/qukantianxia/srmx.html
             * inviteApprentice  : http://statics.qukantianxia.com/html/h5/qukantianxia/invite.html
             * inviteCode : http://statics.qukantianxia.com/html/h5/qukantianxia/bdyqm.html
             * comment : http://statics.qukantianxia.com/html/h5/qukantianxia/mycomment.html
             * activity : http://statics.qukantianxia.com/html/h5/qukantianxia/task.html
             */

            private String message;
            private String rank;
            private String help;
            private String income;
            private String inviteApprentice;
            private String inviteCode;
            private String comment;
            private String activity;
            private String withdraw;
            private String protocol;
            private String about;

            public String getProtocol() {
                return protocol;
            }

            public void setProtocol(String protocol) {
                this.protocol = protocol;
            }

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getWithdraw() {
                return withdraw;
            }

            public void setWithdraw(String withdraw) {
                this.withdraw = withdraw;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getHelp() {
                return help;
            }

            public void setHelp(String help) {
                this.help = help;
            }

            public String getIncome() {
                return income;
            }

            public void setIncome(String income) {
                this.income = income;
            }

            public String getInviteApprentice() {
                return inviteApprentice;
            }

            public void setInviteApprentice(String inviteApprentice) {
                this.inviteApprentice = inviteApprentice;
            }

            public String getInviteCode() {
                return inviteCode;
            }

            public void setInviteCode(String inviteCode) {
                this.inviteCode = inviteCode;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getActivity() {
                return activity;
            }

            public void setActivity(String activity) {
                this.activity = activity;
            }
        }

        public static class DataBean {
            /**
             * point : 2500
             * money : 0
             * isRewardActive : 0
             * notice :
             */

            private int point;
            private float money;
            private int isRewardActive;
            private String notice;
            private int mess;
            private int info;
            private int task;

            public int getMess() {
                return mess;
            }

            public void setMess(int mess) {
                this.mess = mess;
            }

            public int getInfo() {
                return info;
            }

            public void setInfo(int info) {
                this.info = info;
            }

            public int getTask() {
                return task;
            }

            public void setTask(int task) {
                this.task = task;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public float getMoney() {
                return money;
            }

            public void setMoney(float money) {
                this.money = money;
            }

            public int getIsRewardActive() {
                return isRewardActive;
            }

            public void setIsRewardActive(int isRewardActive) {
                this.isRewardActive = isRewardActive;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class UserBean {
            /**
             * userId : 20
             * name : 吃吃吃
             * sex :
             * city :
             * province :
             * industry :
             * job :
             * openId : osZzdvyB1ioYx8C5Qr67s4Wo1MQE
             * unionId : cuhaHtfP
             * mobile : 15538703220
             * pwd : e10adc3949ba59abbe56e057f20f883e
             * headUrl : http://images.qukantianxia.com/images/userNew/2abe8d1c-fb6f-47c2-b123-7baaa60a84f7.jpg
             * parentUserId : -99
             * lastLoginTime : 1505901350191
             * ctime : 1505210687340
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
             * token : 9068137014509e3c15057f1e77af12f6
             * yesPoint : 0
             * yesMoney : 0
             * age : 78
             * money : 0
             * points : 0
             * shortName : 吃吃吃
             * strHiddenMobile : 155****3220
             * currentMoney : 0.0
             * strCtime : 2017-09-12 18:04:47
             * strLastLoginTime : 2017-09-20 17:55:50
             * strHeadUrl : http://images.qukantianxia.com/images/userNew/2abe8d1c-fb6f-47c2-b123-7baaa60a84f7.jpg
             * strName : 吃吃吃
             * strQcodeUrl : http://images.qukantianxia.com/
             * strAppQcodeUrl : http://images.qukantianxia.com/
             * strStatus : 激活
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
            private int yesPoint;
            private int yesMoney;
            private int age;
            private int money;
            private int points;
            private String shortName;
            private String strHiddenMobile;
            private String currentMoney;
            private String strCtime;
            private String strLastLoginTime;
            private String strHeadUrl;
            private String strName;
            private String strQcodeUrl;
            private String strAppQcodeUrl;
            private String strStatus;

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

            public int getYesPoint() {
                return yesPoint;
            }

            public void setYesPoint(int yesPoint) {
                this.yesPoint = yesPoint;
            }

            public int getYesMoney() {
                return yesMoney;
            }

            public void setYesMoney(int yesMoney) {
                this.yesMoney = yesMoney;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
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

            public String getStrHiddenMobile() {
                return strHiddenMobile;
            }

            public void setStrHiddenMobile(String strHiddenMobile) {
                this.strHiddenMobile = strHiddenMobile;
            }

            public String getCurrentMoney() {
                return currentMoney;
            }

            public void setCurrentMoney(String currentMoney) {
                this.currentMoney = currentMoney;
            }

            public String getStrCtime() {
                return strCtime;
            }

            public void setStrCtime(String strCtime) {
                this.strCtime = strCtime;
            }

            public String getStrLastLoginTime() {
                return strLastLoginTime;
            }

            public void setStrLastLoginTime(String strLastLoginTime) {
                this.strLastLoginTime = strLastLoginTime;
            }

            public String getStrHeadUrl() {
                return strHeadUrl;
            }

            public void setStrHeadUrl(String strHeadUrl) {
                this.strHeadUrl = strHeadUrl;
            }

            public String getStrName() {
                return strName;
            }

            public void setStrName(String strName) {
                this.strName = strName;
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
        }

        public static class BannerBean {
            /**
             * bannerId : 4
             * title : 11
             * imgUrl : http://images.qukantianxia.com/0cb9204e-87b1-458b-9a92-efc18c18b56e.png
             * skipUrl : http://www.baidu.com
             * ctime : 1507790522045
             * status : 1
             */

            private int bannerId;
            private String title;
            private String imgUrl;
            private String skipUrl;
            private long ctime;
            private int status;

            public int getBannerId() {
                return bannerId;
            }

            public void setBannerId(int bannerId) {
                this.bannerId = bannerId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getSkipUrl() {
                return skipUrl;
            }

            public void setSkipUrl(String skipUrl) {
                this.skipUrl = skipUrl;
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
        }
    }
}
