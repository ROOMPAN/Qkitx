package com.yanhui.qktx.models;

import java.util.List;

/**
 * Created by liupanpan on 2017/9/13.
 */

public class PersonBean extends BaseEntity {

    /**
     * data : {"menu":{"message":{"memo":null,"name":"消息页面","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/message.html"},"protocol":{"memo":null,"name":"协议页面","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/protocol.html"},"rank":{"memo":null,"name":"排行榜页面","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/topb.html"},"help":{"memo":"趣看天下怎么玩？","name":"常见问题","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/help.html"},"income":{"memo":"查看收益、排行榜","name":"收入明细","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/srmx.html"},"withdraw":{"memo":"特价限时抢","name":"兑换提现","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/withdraw_list.html"},"about":{"memo":null,"name":"关于","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/about.html"},"inviteCode":{"memo":"再领取0.5元红包","name":"输入邀请码","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/bdyqm.html"},"comment":{"memo":null,"name":"我的评论","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/mycomment.html"},"inviteApprentice":{"memo":"只要朋友阅读您就有收益，一劳永逸","name":"推荐好友使用，每位奖励5元+3000金币","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/invite.html"},"activity":{"memo":null,"name":"任务系统","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/task.html"}},"data":{"point":110,"task":0,"money":3.18,"isRewardActive":0,"mess":0,"notice":"","info":0},"user":{"userId":178,"name":"看友178","sex":"","openId":"","unionId":"R9XBOK2j","headUrl":"http://images.qukantianxia.com/images/userNew/b94c03c6-b9ed-45d5-8202-5cdb6797f086.jpg","parentUserId":176,"lastLoginTime":1510234118499,"ctime":1510234118499,"status":1,"token":"3a623b34828bfa0a4e0d0686e21ffd74","age":"0","isFirstLogin":0,"hbAmount":0},"banner":[{"bannerId":4,"title":"11","imgUrl":"http://images.qukantianxia.com/c8792021-bb8e-4c7f-8b5d-ea810637d099.jpg","skipUrl":"http://statics.qukantianxia.com/html/h5/qukantianxia/hd.html","ctime":1509707667080,"status":1,"type":0}]}
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
         * menu : {"message":{"memo":null,"name":"消息页面","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/message.html"},"protocol":{"memo":null,"name":"协议页面","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/protocol.html"},"rank":{"memo":null,"name":"排行榜页面","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/topb.html"},"help":{"memo":"趣看天下怎么玩？","name":"常见问题","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/help.html"},"income":{"memo":"查看收益、排行榜","name":"收入明细","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/srmx.html"},"withdraw":{"memo":"特价限时抢","name":"兑换提现","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/withdraw_list.html"},"about":{"memo":null,"name":"关于","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/about.html"},"inviteCode":{"memo":"再领取0.5元红包","name":"输入邀请码","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/bdyqm.html"},"comment":{"memo":null,"name":"我的评论","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/mycomment.html"},"inviteApprentice":{"memo":"只要朋友阅读您就有收益，一劳永逸","name":"推荐好友使用，每位奖励5元+3000金币","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/invite.html"},"activity":{"memo":null,"name":"任务系统","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/task.html"}}
         * data : {"point":110,"task":0,"money":3.18,"isRewardActive":0,"mess":0,"notice":"","info":0}
         * user : {"userId":178,"name":"看友178","sex":"","openId":"","unionId":"R9XBOK2j","headUrl":"http://images.qukantianxia.com/images/userNew/b94c03c6-b9ed-45d5-8202-5cdb6797f086.jpg","parentUserId":176,"lastLoginTime":1510234118499,"ctime":1510234118499,"status":1,"token":"3a623b34828bfa0a4e0d0686e21ffd74","age":"0","isFirstLogin":0,"hbAmount":0}
         * banner : [{"bannerId":4,"title":"11","imgUrl":"http://images.qukantianxia.com/c8792021-bb8e-4c7f-8b5d-ea810637d099.jpg","skipUrl":"http://statics.qukantianxia.com/html/h5/qukantianxia/hd.html","ctime":1509707667080,"status":1,"type":0}]
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
             * message : {"memo":null,"name":"消息页面","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/message.html"}
             * protocol : {"memo":null,"name":"协议页面","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/protocol.html"}
             * rank : {"memo":null,"name":"排行榜页面","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/topb.html"}
             * help : {"memo":"趣看天下怎么玩？","name":"常见问题","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/help.html"}
             * income : {"memo":"查看收益、排行榜","name":"收入明细","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/srmx.html"}
             * withdraw : {"memo":"特价限时抢","name":"兑换提现","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/withdraw_list.html"}
             * about : {"memo":null,"name":"关于","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/about.html"}
             * inviteCode : {"memo":"再领取0.5元红包","name":"输入邀请码","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/bdyqm.html"}
             * comment : {"memo":null,"name":"我的评论","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/mycomment.html"}
             * inviteApprentice : {"memo":"只要朋友阅读您就有收益，一劳永逸","name":"推荐好友使用，每位奖励5元+3000金币","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/invite.html"}
             * activity : {"memo":null,"name":"任务系统","url":"http://statics.qukantianxia.com/html/h5/qukantianxia/task.html"}
             */

            private MessageBean message;
            private ProtocolBean protocol;
            private RankBean rank;
            private HelpBean help;
            private IncomeBean income;
            private WithdrawBean withdraw;
            private AboutBean about;
            private InviteCodeBean inviteCode;
            private CommentBean comment;
            private InviteApprenticeBean inviteApprentice;
            private ActivityBean activity;
            private LuckyBean lucky;
            private BindingBean binding;

            public BindingBean getBinding() {
                return binding;
            }

            public void setBinding(BindingBean binding) {
                this.binding = binding;
            }

            public LuckyBean getLucky() {
                return lucky;
            }

            public void setLucky(LuckyBean lucky) {
                this.lucky = lucky;
            }

            public MessageBean getMessage() {
                return message;
            }

            public void setMessage(MessageBean message) {
                this.message = message;
            }

            public ProtocolBean getProtocol() {
                return protocol;
            }

            public void setProtocol(ProtocolBean protocol) {
                this.protocol = protocol;
            }

            public RankBean getRank() {
                return rank;
            }

            public void setRank(RankBean rank) {
                this.rank = rank;
            }

            public HelpBean getHelp() {
                return help;
            }

            public void setHelp(HelpBean help) {
                this.help = help;
            }

            public IncomeBean getIncome() {
                return income;
            }

            public void setIncome(IncomeBean income) {
                this.income = income;
            }

            public WithdrawBean getWithdraw() {
                return withdraw;
            }

            public void setWithdraw(WithdrawBean withdraw) {
                this.withdraw = withdraw;
            }

            public AboutBean getAbout() {
                return about;
            }

            public void setAbout(AboutBean about) {
                this.about = about;
            }

            public InviteCodeBean getInviteCode() {
                return inviteCode;
            }

            public void setInviteCode(InviteCodeBean inviteCode) {
                this.inviteCode = inviteCode;
            }

            public CommentBean getComment() {
                return comment;
            }

            public void setComment(CommentBean comment) {
                this.comment = comment;
            }

            public InviteApprenticeBean getInviteApprentice() {
                return inviteApprentice;
            }

            public void setInviteApprentice(InviteApprenticeBean inviteApprentice) {
                this.inviteApprentice = inviteApprentice;
            }

            public ActivityBean getActivity() {
                return activity;
            }

            public void setActivity(ActivityBean activity) {
                this.activity = activity;
            }

            public static class MessageBean {
                /**
                 * memo : null
                 * name : 消息页面
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/message.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class ProtocolBean {
                /**
                 * memo : null
                 * name : 协议页面
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/protocol.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class LuckyBean {
                /**
                 * memo : null
                 * name : 新手任务
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/topb.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class BindingBean {
                /**
                 * memo : null
                 * name : 排行榜页面
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/topb.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class RankBean {
                /**
                 * memo : null
                 * name : 排行榜页面
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/topb.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class HelpBean {
                /**
                 * memo : 趣看天下怎么玩？
                 * name : 常见问题
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/help.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class IncomeBean {
                /**
                 * memo : 查看收益、排行榜
                 * name : 收入明细
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/srmx.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class WithdrawBean {
                /**
                 * memo : 特价限时抢
                 * name : 兑换提现
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/withdraw_list.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class AboutBean {
                /**
                 * memo : null
                 * name : 关于
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/about.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class InviteCodeBean {
                /**
                 * memo : 再领取0.5元红包
                 * name : 输入邀请码
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/bdyqm.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class CommentBean {
                /**
                 * memo : null
                 * name : 我的评论
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/mycomment.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class InviteApprenticeBean {
                /**
                 * memo : 只要朋友阅读您就有收益，一劳永逸
                 * name : 推荐好友使用，每位奖励5元+3000金币
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/invite.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class ActivityBean {
                /**
                 * memo : null
                 * name : 任务系统
                 * url : http://statics.qukantianxia.com/html/h5/qukantianxia/task.html
                 */

                private String memo;
                private String name;
                private String url;

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class DataBean {
            /**
             * point : 110
             * task : 0
             * money : 3.18
             * isRewardActive : 0
             * mess : 0
             * notice :
             * info : 0
             * lucky
             */

            private int point;
            private int task;
            private double money;
            private int isRewardActive;
            private int mess;
            private String notice;
            private int info;
            private int lucky;

            public int getLucky() {
                return lucky;
            }

            public void setLucky(int lucky) {
                this.lucky = lucky;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public int getTask() {
                return task;
            }

            public void setTask(int task) {
                this.task = task;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public int getIsRewardActive() {
                return isRewardActive;
            }

            public void setIsRewardActive(int isRewardActive) {
                this.isRewardActive = isRewardActive;
            }

            public int getMess() {
                return mess;
            }

            public void setMess(int mess) {
                this.mess = mess;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }

            public int getInfo() {
                return info;
            }

            public void setInfo(int info) {
                this.info = info;
            }
        }

        public static class UserBean {
            /**
             * userId : 178
             * name : 看友178
             * sex :
             * openId :
             * unionId : R9XBOK2j
             * headUrl : http://images.qukantianxia.com/images/userNew/b94c03c6-b9ed-45d5-8202-5cdb6797f086.jpg
             * parentUserId : 176
             * lastLoginTime : 1510234118499
             * ctime : 1510234118499
             * status : 1
             * token : 3a623b34828bfa0a4e0d0686e21ffd74
             * age : 0
             * isFirstLogin : 0
             * hbAmount : 0
             */

            private int userId;
            private String name;
            private String sex;
            private String openId;
            private String unionId;
            private String headUrl;
            private int parentUserId;
            private long lastLoginTime;
            private long ctime;
            private int status;
            private String token;
            private String age;
            private int isFirstLogin;
            private int hbAmount;

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

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public int getIsFirstLogin() {
                return isFirstLogin;
            }

            public void setIsFirstLogin(int isFirstLogin) {
                this.isFirstLogin = isFirstLogin;
            }

            public int getHbAmount() {
                return hbAmount;
            }

            public void setHbAmount(int hbAmount) {
                this.hbAmount = hbAmount;
            }
        }

        public static class BannerBean {
            /**
             * bannerId : 4
             * title : 11
             * imgUrl : http://images.qukantianxia.com/c8792021-bb8e-4c7f-8b5d-ea810637d099.jpg
             * skipUrl : http://statics.qukantianxia.com/html/h5/qukantianxia/hd.html
             * ctime : 1509707667080
             * status : 1
             * type : 0
             */

            private int bannerId;
            private String title;
            private String imgUrl;
            private String skipUrl;
            private long ctime;
            private int status;
            private int type;

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
