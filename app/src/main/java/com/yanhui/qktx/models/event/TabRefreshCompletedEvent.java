package com.yanhui.qktx.models.event;

/**
 * @author ChayChan
 * @description: 点击页签刷新完成的事件，包括加载成功和加载失败
 * @date 2017/6/23  15:03
 */

public class TabRefreshCompletedEvent {

    public int what = 0;

    public TabRefreshCompletedEvent(int what) {
        this.what = what;
    }
}
