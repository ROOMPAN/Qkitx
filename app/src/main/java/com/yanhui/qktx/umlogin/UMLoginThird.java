package com.yanhui.qktx.umlogin;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.Config;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by liupanpan on 2017/8/29.
 */

public class UMLoginThird {
    private static final String TAG = "UMLoginThird.this";
    public Activity activity;

    public UMLoginThird(Activity activity) {
        this.activity = activity;
        Config.isJumptoAppStore = true;//如果对应的平台没有安装 跳转到下载频道
        UMShareAPI mShareAPI = UMShareAPI.get(activity);
        mShareAPI.doOauthVerify(activity, SHARE_MEDIA.WEIXIN, umAuthListener);
        mShareAPI.getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, umAuthListener);

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            //此处授权成功后 的回调  map集合为返回的个人信息
            String name = map.get("name");//用户姓名
            String openid = map.get("openid");
            String refreshtoken = map.get("refreshtoken");
            String expiration = map.get("expiration");//过期时间
            String unionid = map.get("unionid");//用户id
            String accesstoken = map.get("accessToken");//toaken
            String gender = map.get("gender");//性别
            String iconurl = map.get("iconurl");//头像链接
            String city = map.get("city");//用户所在城市
            String province = map.get("province");//省份

            Log.e(TAG, "name:" + name + "--" + "openid:" + openid + "--" + "refreshtoken:" + refreshtoken + "--" + "expiration:" + expiration + "--" + "unionid:" + unionid + "--" + "accesstoken:" + accesstoken + "--" + "gender:" + gender + "--" + "iconurl:" + iconurl + "--" + "city:" + city + "--" + "province:" + province + "--");
            //Log.e("thirdinfor", name + "--" + uid + "--" + accesstoken + "--" + gender + "--" + iconurl + "--" + city);

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Toast.makeText(activity, share_media + "登录失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(activity, share_media + "取消登录", Toast.LENGTH_SHORT).show();

        }
    };
}
