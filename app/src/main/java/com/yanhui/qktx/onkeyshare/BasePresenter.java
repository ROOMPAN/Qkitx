package com.yanhui.qktx.onkeyshare;

import android.os.Handler;
import android.os.Message;

public class BasePresenter {
    protected Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dealWith(msg);
        }
    };

    protected void dealWith(Message msg) {

    }
}
