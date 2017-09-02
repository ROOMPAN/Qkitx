package com.yanhui.qktx.network;


import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.BaseMessageEntity;
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
}
