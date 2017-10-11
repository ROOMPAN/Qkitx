package com.yanhui.qktx.network;

import com.yanhui.qktx.business.BusinessManager;
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.CateNameBean;
import com.yanhui.qktx.models.CommentBean;
import com.yanhui.qktx.models.HistoryListBean;
import com.yanhui.qktx.models.IsConnBean;
import com.yanhui.qktx.models.PersonBean;
import com.yanhui.qktx.models.PhotoBean;
import com.yanhui.qktx.models.UserBean;
import com.yanhui.qktx.models.VirtualBean;
import com.yanhui.qktx.utils.JsonFormat;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liupanpan on 2017/6/28.
 * 请求接口请求参数
 */

public class HttpClient {
    //    http://offlintab.firefoxchina.cn/data/master-ii
//    http://sp.kaola.com/api/category/
//    http://192.168.1.177:8080/hhz-app/
//    http://101.37.164.3:8081/
//    http://192.168.1.195:8080/hhz-admin/admin/uploadImg.do
//    http://192.168.1.195:8080/hhz-admin/admin/uploadImg.json
    private static final String DOMAIN = "http://app.qukantianxia.com";
    //    private static final String DOMAIN = "http://192.168.10.97:8080/hhz-app/";
    //    private static final String DOMAIN = "http://192.168.1.195:8080/hhz-admin/";
    private static HttpClient sInstance;
    private Retrofit mRetrofit;
    private ApiManagerService mApi;

    private HttpClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request signRequest = AddCommOnParamter.signRequest(AddCommOnParamter.addCommon(request));
                long t1 = System.nanoTime();
                System.out.println(String.format("Sending request %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));
                Response response = chain.proceed(signRequest == null ? request : signRequest);
                long t2 = System.nanoTime();

                ResponseBody responseBody = response.peekBody(1024 * 1024);
                System.out.println("intercept: " + String.format("接收响应: [%s] %n返回json:[%s] %.1fms%n%s",
                        response.request().url(),
                        JsonFormat.format(responseBody.string()),
                        (t2 - t1) / 1e6d,
                        response.headers()));

                return response;
            }//设置超时
        }).connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS)
                //错误重连
                .retryOnConnectionFailure(true)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new RxThreadCallAdapater(Schedulers.io(), AndroidSchedulers.mainThread()))
                .client(client)
                .build();

        mApi = mRetrofit.create(ApiManagerService.class);
    }


    public synchronized static HttpClient getInstance() {
        if (sInstance == null) {
            sInstance = new HttpClient();
        }
        return sInstance;
    }

    public void getVirLi(String V330, String categoryId, NetworkSubscriber subscriber) {
        Observable<VirtualBean> observable = mApi.getVirtualLi(V330, categoryId);
        observable.subscribe(subscriber);
    }

    public void getdefaultdials(NetworkSubscriber subscriber) {
        Observable<VirtualBean> observable = mApi.getdefaultdials();
        observable.subscribe(subscriber);
    }

    /**
     * 短信验证码
     *
     * @param mobile
     * @param subscriber
     */
    public void getMsgCode(String mobile, NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getMsgCode(mobile);
        observable.subscribe(subscriber);
    }

    /**
     * 注册接口
     *
     * @param mobile
     * @param pwd
     * @param msgcode
     * @param subscriber
     */
    public void getRegister(String mobile, String pwd, String msgcode, NetworkSubscriber subscriber) {
        Observable<UserBean> observable = mApi.getRegister(mobile, pwd, msgcode);
        observable.subscribe(subscriber);
    }

    /**
     * 登录
     *
     * @param mobile
     * @param pwd
     * @param subscriber
     */
    public void getLogin(String mobile, String pwd, NetworkSubscriber subscriber) {
        Observable<UserBean> observable = mApi.getLogin(mobile, pwd);
        observable.subscribe(subscriber);
    }

    /**
     * 退出登录
     *
     * @param subscriber
     */

    public void getLogOut(NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getLogOut();
        observable.subscribe(subscriber);
    }

    /**
     * 修改密码
     *
     * @param mobile
     * @param pwd
     * @param code
     * @param subscriber
     */

    public void getForgetPwd(String mobile, String pwd, String code, NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getForgetPwd(mobile, pwd, code);
        observable.subscribe(subscriber);
    }

    /**
     * 短信验证码效验
     *
     * @param mobile
     * @param code
     * @param subscriber
     */
    public void getvalidateCode(String mobile, String code, NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getvalidateCode(mobile, code);
        observable.subscribe(subscriber);
    }

    /**
     * 绑定微信
     *
     * @param openId
     * @param unionId
     * @param headUrl
     * @param nickname
     * @param sex
     * @param city
     * @param province
     * @param subscriber
     */
    public void getbindwx(String openId, String unionId, String headUrl, String nickname, String sex, String city, String province, NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getbindingwx(openId, unionId, headUrl, nickname, sex, city, province);
        observable.subscribe(subscriber);
    }

    /**
     * 用户资料修改
     *
     * @param name
     * @param headUrl
     * @param ege
     * @param subscriber
     */
    public void getUpdateInfo(String name, String headUrl, String ege, NetworkSubscriber subscriber) {
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), new File(headUrl));
        Observable<BaseEntity> observable = mApi.getUpdateInfo(name, headUrl, ege);
        observable.subscribe(subscriber);
    }

    /**
     * 上传头像
     *
     * @param imagePath
     * @param subscriber
     */
    public void getUpdateHead(String imagePath, NetworkSubscriber subscriber) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), new File(imagePath));
        Observable<PhotoBean> observable = mApi.getUpdateHead(requestBody, BusinessManager.getInstance().getUserToken(), Long.valueOf(AddCommOnParamter.timestamp), AddCommOnParamter.sign);
        observable.subscribe(subscriber);
    }

    /**
     * 个人中心
     *
     * @param subscriber
     */
    public void getPoint(NetworkSubscriber subscriber) {
        Observable<PersonBean> observable = mApi.getPoint();
        observable.subscribe(subscriber);

    }

    /**
     * 获取视频标题
     *
     * @param subscriber
     */

    public void getVedioCate(NetworkSubscriber subscriber) {
        Observable<CateNameBean> observable = mApi.getVedioCate();
        observable.subscribe(subscriber);
    }

    /**
     * 获取文章标题
     *
     * @param subscriber
     */
    public void getCate(NetworkSubscriber subscriber) {
        Observable<CateNameBean> observable = mApi.getCate();
        observable.subscribe(subscriber);
    }

    /**
     * 上传已选择标题
     *
     * @param carlist
     * @param subscriber
     */

    public void getUpdataUserCate(String carlist, NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getUpdataUserCate(carlist);
        observable.subscribe(subscriber);
    }

    /**
     * 获取首页文章列表
     *
     * @param refreshType 0上拉 ,1 下拉
     * @param catid
     * @param pageNo
     * @param pageSize
     * @param subscriber
     */
    public void getFindPage(int refreshType, String catid, String articleType, int pageNo, int pageSize, NetworkSubscriber subscriber) {
        Observable<ArticleListBean> observable = mApi.getFindPage(refreshType, catid, articleType, pageNo, pageSize);
        observable.subscribe(subscriber);
    }


    /**
     * 收藏文章/视频
     *
     * @param taskId     文章或者视频 id;
     * @param type       1  文章 2 视频
     * @param subscriber
     */
    public void getAddConnection(int taskId, int type, NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getAddConnection(taskId, type);
        observable.subscribe(subscriber);
    }

    /**
     * 文章是否点赞
     *
     * @param taskId
     * @param subscriber
     */
    public void getArticleIsConn(int taskId, NetworkSubscriber subscriber) {
        Observable<IsConnBean> observable = mApi.getArticleIsConn(taskId);
        observable.subscribe(subscriber);

    }

    /**
     * 取消收藏 ,删除收藏
     *
     * @param taskId
     * @param subscriber
     */
    public void getDeleteConnection(int taskId, NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getDeleteConnection(taskId);
        observable.subscribe(subscriber);
    }

    /**
     * 视频收藏列表
     *
     * @param subscriber
     */
    public void getConnVedio(int pagerNo, int pagersize, NetworkSubscriber subscriber) {
        Observable<HistoryListBean> observable = mApi.getConnVedio(pagerNo, pagersize);
        observable.subscribe(subscriber);
    }

    /**
     * 文章收藏列表
     *
     * @param subscriber
     */
    public void getConnArticle(int pagerNo, int pagersize, NetworkSubscriber subscriber) {
        Observable<HistoryListBean> observable = mApi.getConnArticle(pagerNo, pagersize);
        observable.subscribe(subscriber);
    }

    /**
     * 历史列表记录
     *
     * @param subscriber
     */
    public void getReadRecord(String clearLastTime, NetworkSubscriber subscriber) {
        Observable<HistoryListBean> observable = mApi.getReadRecord(clearLastTime);
        observable.subscribe(subscriber);
    }

    /**
     * 删除历史记录
     *
     * @param subscriber
     */
    public void getDeleteHisto(NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getDeleteHist();
        observable.subscribe(subscriber);
    }

    /**
     * 提交文章评论
     *
     * @param taskid
     * @param context
     * @param subscriber
     */
    public void getAddComment(int taskid, String context, NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getAddComment(taskid, context);
        observable.subscribe(subscriber);
    }

    public void getAddUserComment(int taskid, int answerUserId, String context, int answerCommentid, NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getAddUserComment(taskid, answerUserId, context, answerCommentid);
        observable.subscribe(subscriber);
    }

    /**
     * 热点评论列表
     *
     * @param taskid
     * @param subscriber
     */
    public void getHotComments(int taskid, NetworkSubscriber subscriber) {
        Observable<CommentBean> observable = mApi.getHotComments(taskid);
        observable.subscribe(subscriber);
    }

    /**
     * 最新评论列表
     *
     * @param taskid
     * @param subscriber
     */
    public void getNewComments(int taskid, NetworkSubscriber subscriber) {
        Observable<CommentBean> observable = mApi.getNewComments(taskid);
        observable.subscribe(subscriber);
    }

    /**
     * 展开全部评论列表
     *
     * @param taskid
     * @param pagerNo    翻页
     * @param pagersize  一页几条
     * @param subscriber
     */
    public void getShowAllComments(int taskid, int commentId, int pagerNo, int pagersize, NetworkSubscriber subscriber) {
        Observable<CommentBean> observable = mApi.getShowAllComments(taskid, commentId, pagerNo, pagersize);
        observable.subscribe(subscriber);
    }

    /**
     * 点赞
     *
     * @param commentId
     * @param subscriber
     */

    public void getAddups(int commentId, NetworkSubscriber subscriber) {
        Observable<BaseEntity> observable = mApi.getAddups(commentId);
        observable.subscribe(subscriber);
    }

    /**
     * 搜索获取文章
     *
     * @param seachType     1,文章,2 视频
     * @param searchContext
     * @param pageNo
     * @param pageSize
     * @param subscriber
     */
    public void getSearchTask(int seachType, String searchContext, int pageNo, int pageSize, NetworkSubscriber subscriber) {
        Observable<ArticleListBean> observable = mApi.getsearchTasks(seachType, searchContext, pageNo, pageSize);
        observable.subscribe(subscriber);
    }
}

