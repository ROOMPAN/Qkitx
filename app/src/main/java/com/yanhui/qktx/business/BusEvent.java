package com.yanhui.qktx.business;

import android.os.Bundle;

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

    public int what;
    public int arg1;
    public int arg2;
    public Object obj;
    public Bundle bundle;
    public String SendMessage;

}
