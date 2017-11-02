package com.yanhui.qktx.business;

import android.os.Bundle;

import java.math.BigDecimal;

/**
 * Created by liupanpan on 17/9/12.
 * evenbus封装类
 */
public class BusEvent {

    public BusEvent() {
    }

    public BusEvent(int what) {
        this.what = what;
    }

    public BusEvent(int what, int arg1, int arg2, Object obj, Bundle bundle, String SendMessage) {
        this.what = what;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.obj = obj;
        this.bundle = bundle;
        this.SendMessage = SendMessage;
    }

    public BusEvent(int what, int arg1) {
        this.what = what;
        this.arg1 = arg1;
    }

    public BusEvent(int what, BigDecimal HbMoney) {
        this.what = what;
        this.HbMoney = HbMoney;
    }

    public BusEvent(int what, String title, String coumnt_json) {
        this.what = what;
        this.title = title;
        this.coumnt_json = coumnt_json;
    }

    public int what;
    public int arg1;
    public int arg2;
    public String title;
    public String coumnt_json;
    public Object obj;
    public Bundle bundle;
    public String SendMessage;
    public BigDecimal HbMoney;//红包金额

}
