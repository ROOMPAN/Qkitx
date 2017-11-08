package com.yanhui.qktx.network;

import android.text.TextUtils;
import android.util.Log;

import com.yanhui.qktx.BuildConfig;
import com.yanhui.qktx.business.BusinessManager;
import com.yanhui.qktx.utils.MD5Util;
import com.yanhui.qktx.utils.MobileUtils;
import com.yanhui.qktx.utils.SharedPreferencesMgr;
import com.yanhui.qktx.utils.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * Created by liupanpan on 2017/6/28.
 */

public class AddCommOnParamter {
    private static final String SECRET_KEY = "af72faABI7709ddsa223";
    private static String deviceId;
    public static String timestamp;
    private static String token;
    public static String sign;

    public static Request addCommon(Request request) throws IOException {
        String method = request.method();
        deviceId = MobileUtils.getIMEI();
        token = BusinessManager.getInstance().getUserToken();
        if (StringUtils.isEmpty(token)) {
            token = MobileUtils.getIMEI();
        }
        timestamp = String.valueOf(System.currentTimeMillis());
        String os = "1";
        String pushToken = SharedPreferencesMgr.getString("pushtoken", "1231dadsd");
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        Request commonRequest = null;
        if (method.equalsIgnoreCase("GET")) {
            commonRequest =
                    request.newBuilder().url(
                            request.url().newBuilder()
                                    //链接中添加固定的请求参数
//                                    .addQueryParameter("userToken", userToken)
                                    .addQueryParameter("os", os)
                                    .addQueryParameter("timestamp", timestamp)
                                    .addQueryParameter("token", token)
                                    .addQueryParameter("versionCode", versionCode + "")
                                    .addQueryParameter("versionName", versionName + "")
                                    .addQueryParameter("pushToken", pushToken)
                                    .addQueryParameter("deviceId", deviceId)
                                    .build())
                            .build();
        } else if (method.equalsIgnoreCase("POST")) {
            //文件上传不做签名处理
            MediaType contentType = request.body().contentType();
            if (contentType != null && contentType.toString().contains("multipart")) {
                return request;
            } else {
                Buffer buffer = new Buffer();
                request.newBuilder().build().body().writeTo(buffer);
                String queryStr = buffer.readUtf8();
                queryStr += (TextUtils.isEmpty(queryStr) ? "" : "&") + "token=" + token + "&os=" + os + "&timestamp=" + timestamp
                        + "&pushToken=" + pushToken + "&versionCode=" + versionCode + "&versionName=" + versionName + "deviceId" + deviceId;
                commonRequest = request.newBuilder()
                        .url(request.url().newBuilder()
                                .addQueryParameter("versionCode", versionCode + "")
                                .addQueryParameter("versionName", versionName)
                                .addQueryParameter("pushtoken", pushToken)
                                .addQueryParameter("os", os)
                                .build())
                        .post(RequestBody.create(contentType, queryStr)).build();
            }
        }
        return commonRequest;
    }

    /**
     * Request参数签名
     *
     * @param request
     * @return
     */
    public static Request signRequest(Request request) throws IOException {
        Request signRequest = null;
        String needSignStr;

        Map m = new HashMap();
        m.put("token", token);
        m.put("timestamp", timestamp);
        m.put("appKey", SECRET_KEY);

        SortedMap<String, String> sort = new TreeMap<String, String>(m);
        try {
            sign = MD5Util.createSign1(sort);
            Log.e("sign", "" + sign);
            Log.e("token", "" + token);
            Log.e("timestamp", "" + timestamp);
            Log.e("appKey", "" + SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 参数签名
        if (request.method().equalsIgnoreCase("GET")) {
            signRequest = request.newBuilder().url(request.url().newBuilder()
                    //在请求头 添加签名参数
                    .addQueryParameter("sign", sign)
                    .build())
                    .build();
        } else if (request.method().equalsIgnoreCase("POST")) {
            Buffer buffer = new Buffer();
            request.newBuilder().build().body().writeTo(buffer);
            MediaType contentType = request.body().contentType();
            // 文件上传 不做签名处理
            if (contentType != null && contentType.toString().contains("multipart")) {
                return request;
//                return request.newBuilder().post(RequestBody.create(MediaType.parse(contentType.toString().replace("multipart/form-data;", "image/png;")), buffer.readByteArray())).build();
            }
            String queryStr = URLDecoder.decode(buffer.readUtf8(), "utf-8");
            if (!TextUtils.isEmpty(queryStr)) {
                String[] queryArray = queryStr.split("&");
                Arrays.sort(queryArray);
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < queryArray.length; i++) {
                    builder.append(queryArray[i] + (i == queryArray.length - 1 ? "" : "&"));
                }
                needSignStr = builder.toString() + SECRET_KEY;
            } else {
                needSignStr = SECRET_KEY;
            }
            // String sign = StringUtils.MD5(needSignStr);
            signRequest = request.newBuilder().post(RequestBody.create(request.body().contentType(), queryStr + "&sign=" + sign)).build();
        }
        return signRequest;
    }
}
