package com.yanhui.qktx.business;

import com.yanhui.qktx.utils.SharedPreferencesMgr;
import com.yanhui.qktx.utils.StringUtils;

/**
 * Created by liupanpan on 2017/9/6.
 */

public class BusinessManager {
    private static BusinessManager mInstance;

    private String Token;//用户的 token用于所有接口调试

    private boolean isLogin; //用户是否登录

    public synchronized static BusinessManager getInstance() {
        if (mInstance == null) {
            mInstance = new BusinessManager();
        }
        return mInstance;
    }

    public String getUserToken() {
        Token = SharedPreferencesMgr.getString("token", "");
        if (!StringUtils.isEmpty(Token)) {
            return Token;
        } else {
            return null;
        }
    }

    public void login() {
        SharedPreferencesMgr.setBoolean("is_login", true);
        isLogin = true;
    }

    public void logout() {
        SharedPreferencesMgr.setBoolean("is_login", false);
        isLogin = false;
    }

    public boolean isLogin() {
        return SharedPreferencesMgr.getBoolean("is_login", false);
    }
}
