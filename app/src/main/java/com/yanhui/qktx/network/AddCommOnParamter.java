package com.yanhui.qktx.network;

import android.support.compat.BuildConfig;
import android.text.TextUtils;
import android.util.Log;

import com.yanhui.qktx.utils.MobileUtils;
import com.yanhui.qktx.utils.Sha1Util;

import java.io.IOException;
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
    private static String timestamp;
    private static String token;

    public static Request addCommon(Request request) throws IOException {
        String method = request.method();
        //String userToken = BusinessManager.getInstance().getUserToken();
        token = MobileUtils.getIMEI();
        timestamp = String.valueOf(System.currentTimeMillis());
        String os = "1";
        String pushToken = "1231dadsd";
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
//                                    .addQueryParameter("pushToken", pushToken)
                                    .build())
                            .build();
        } else if (method.equalsIgnoreCase("POST")) {
            MediaType contentType = request.body().contentType();
            if (contentType != null && contentType.toString().contains("multipart")) {
                return request;
            } else {
                Buffer buffer = new Buffer();
                request.newBuilder().build().body().writeTo(buffer);
                String queryStr = buffer.readUtf8();
                queryStr += (TextUtils.isEmpty(queryStr) ? "" : "&") + "token=" + token + "&os=" + os + "&timestamp=" + timestamp
                        + "&pushToken=" + pushToken + "&versionCode=" + versionCode + "&versionName=" + versionName;
                commonRequest = request.newBuilder()
                        .url(request.url().newBuilder()
                                .addQueryParameter("versionCode", versionCode + "")
                                .addQueryParameter("versionName", versionName)
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
        String sign = null;

        Map m = new HashMap();
        m.put("token", token);
        m.put("timestamp", timestamp);
        m.put("appKey", SECRET_KEY);

        SortedMap<String, String> sort = new TreeMap<String, String>(m);
        try {
            sign = Sha1Util.createSHA1Sign(sort);
            Log.e("sign", "" + sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 参数签名
        if (request.method().equalsIgnoreCase("GET")) {
            signRequest = request.newBuilder().url(request.url()
                    .newBuilder()
                    //在请求头 添加签名参数
                    .addQueryParameter("sign", sign)
                    .build())
                    .build();
        } else if (request.method().equalsIgnoreCase("POST")) {
            Buffer buffer = new Buffer();
            request.newBuilder().build().body().writeTo(buffer);
            String queryStr = buffer.readUtf8();
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
