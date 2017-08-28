package com.yanhui.qktx.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by liupanpan on 2017/8/23.
 */

public class GsonToJsonUtil {
    private static Gson gson = new Gson();

    public static String toJson(Object ob) {
        return gson.toJson(ob);
    }

    public static  <T> T  fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
}
