package com.yanhui.qktx.network;


import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.BaseMessageEntity;
import com.yanhui.qktx.models.CateNameBean;
import com.yanhui.qktx.models.CommentBean;
import com.yanhui.qktx.models.HistoryListBean;
import com.yanhui.qktx.models.IsConnBean;
import com.yanhui.qktx.models.PersonBean;
import com.yanhui.qktx.models.PhotoBean;
import com.yanhui.qktx.models.UserBean;
import com.yanhui.qktx.models.VirtualBean;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 接口定义api
 */

public interface ApiManagerService {

    /**
     * @param mobile
     * @param type   验证码类型(1注册2找回密码3绑定手机号)
     * @return
     */
    @GET("/common/get_code")
    Observable<BaseMessageEntity> getCode(@Query("mobile") String mobile, @Query("type") int type);

    @GET("frontCategory?")
    Observable<VirtualBean> getVirtualLi(@Query("V330") String V330, @Query("categoryId") String categoryId);

    @GET("defaultdials-0.json")
    Observable<VirtualBean> getdefaultdials();

    @GET("user/getCode.do")
    Observable<BaseEntity> getMsgCode(@Query("mobile") String mobile);

    @FormUrlEncoded
    @POST("user/registerUser.do")
    Observable<UserBean> getRegister(@Field("mobile") String mobile, @Field("pwd") String pwd, @Field("code") String code);

    @FormUrlEncoded
    @POST("user/mobileLogin.do")
    Observable<UserBean> getLogin(@Field("mobile") String mobile, @Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("user/forgetPwd.do")
    Observable<BaseEntity> getForgetPwd(@Field("mobile") String mobile, @Field("pwd") String pwd, @Field("code") String code);

    @GET("user/validateCode.do")
    Observable<BaseEntity> getvalidateCode(@Query("mobile") String mobile, @Query("code") String code);

    @FormUrlEncoded
    @POST("user/binding.json")
    Observable<BaseEntity> getbindingwx(@Field("openId") String openId, @Field("unionId") String unionId, @Field("headUrl") String headUrl, @Field("nickname") String nickname, @Field("sex") String sex, @Field("city") String city, @Field("province") String province);

    @FormUrlEncoded
    @POST("user/updateUserInfo.json")
//    @Part("headUrl\";filename=\"image.jpg") RequestBody photo
    Observable<BaseEntity> getUpdateInfo(@Field("name") String name, @Field("headUrl") String headUrl, @Field("age") String age);

    @Multipart
    @POST("user/updateUserHead.json")
    Observable<PhotoBean> getUpdateHead(@Part("img\"; filename=\"image.jpg") RequestBody photo, @Part("token") String userToken, @Part("timestamp") Long timestamp, @Part("sign") String sign);

    @GET("user/point.json")
    Observable<PersonBean> getPoint();

    @GET("task/getVedioCate.do")
    Observable<CateNameBean> getVedioCate();

    @GET("task/getCate.do")
    Observable<CateNameBean> getCate();

    @FormUrlEncoded
    @POST("task/updateUserCate.json")
    Observable<BaseEntity> getUpdataUserCate(@Field("cateIds") String cateIdlist);

    @GET("task/getConnVedio.json")
    Observable<HistoryListBean> getConnVedio(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    @GET("task/getConnArticle.json")
    Observable<HistoryListBean> getConnArticle(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    @GET("task/getReadRecord.json")
    Observable<HistoryListBean> getReadRecord(@Query("clearLastTime") String clearLastTime);

    @GET("task/deleteReadHistory.json")
    Observable<BaseEntity> getDeleteHist();

    @GET("user/loginOut.json")
    Observable<BaseEntity> getLogOut();

    @GET("task/findPageTasks.do")
    Observable<ArticleListBean> getFindPage(@Query("refreshType") int refreshType, @Query("tCate") String tCate, @Query("articleType") String articleType, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("task/addConnection.json")
    Observable<BaseEntity> getAddConnection(@Field("taskId") int taskId, @Field("type") int type);

    @FormUrlEncoded
    @POST("task/deleteConnection.json")
    Observable<BaseEntity> getDeleteConnection(@Field("taskId") int taskId);

    @GET("task/getArticleInfo.do")
    Observable<IsConnBean> getArticleInfo(@Query("taskId") int taskId);

    @FormUrlEncoded
    @POST("comment/addComment.json")
    Observable<BaseEntity> getAddComment(@Field("taskId") int taskId, @Field("context") String context);

    @FormUrlEncoded
    @POST("comment/addUserComment.json")
    Observable<BaseEntity> getAddUserComment(@Field("taskId") int taskId, @Field("answerUserId") int answerUserId, @Field("context") String context, @Field("answerCommentId") int answerCommentId);

    @GET("comment/getHotComments.do")
    Observable<CommentBean> getHotComments(@Query("taskId") int taskId);

    @GET("comment/getNewComments.do")
    Observable<CommentBean> getNewComments(@Query("taskId") int taskId);

    @GET("comment/getCommentsByCommentId.do")
    Observable<CommentBean> getShowAllComments(@Query("taskId") int taskId, @Query("commentId") int commentId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("comment/addUps.json")
    Observable<BaseEntity> getAddups(@Field("commentId") int commentId);

    @GET("task/searchTasks.json")
    Observable<ArticleListBean> getsearchTasks(@Query("type") int searchtype, @Query("title") String searchContext, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

}
