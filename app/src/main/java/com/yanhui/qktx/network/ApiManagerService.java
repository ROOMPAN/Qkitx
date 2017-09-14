package com.yanhui.qktx.network;


import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.BaseMessageEntity;
import com.yanhui.qktx.models.PersonBean;
import com.yanhui.qktx.models.UserBean;
import com.yanhui.qktx.models.VirtualBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("user/getCode.json")
    Observable<BaseEntity> getMsgCode(@Query("mobile") String mobile);

    @FormUrlEncoded
    @POST("user/registerUser.json")
    Observable<UserBean> getRegister(@Field("mobile") String mobile, @Field("pwd") String pwd, @Field("code") String code);

    @FormUrlEncoded
    @POST("user/mobileLogin.json")
    Observable<UserBean> getLogin(@Field("mobile") String mobile, @Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("user/forgetPwd.json")
    Observable<BaseEntity> getForgetPwd(@Field("mobile") String mobile, @Field("pwd") String pwd, @Field("code") String code);

    @GET("user/validateCode.json")
    Observable<BaseEntity> getvalidateCode(@Query("mobile") String mobile, @Query("code") String code);

    @FormUrlEncoded
    @POST("user/binding.json")
    Observable<BaseEntity> getbindingwx(@Field("openId") String openId, @Field("unionId") String unionId, @Field("headUrl") String headUrl, @Field("nickname") String nickname, @Field("sex") String sex, @Field("city") String city, @Field("province") String province);

    @FormUrlEncoded
    @POST("user/updateUserInfo.json")
    Observable<BaseEntity> getUpdateInfo(@Field("name") String name, @Field("headUrl") String headUrl, @Field("age") String age);

    @GET("user/point.json")
    Observable<PersonBean> getPoint();

    @GET("task/getVedioCate.json")
    Observable<BaseEntity> getVedioCate();

    @GET("connect/getConnVedio.json")
    Observable<BaseEntity> getConnVedio();

    @GET("connect/getConnArticle.json")
    Observable<BaseEntity> getConnArticle();

    @GET("task/getReadRecord.json")
    Observable<BaseEntity> getReadRecord();

    @GET("user/loginOut.json")
    Observable<BaseEntity> getLogOut();

}
