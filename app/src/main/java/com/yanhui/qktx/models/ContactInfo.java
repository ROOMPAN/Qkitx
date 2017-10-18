package com.yanhui.qktx.models;

/**
 * Created by liupanpan on 2017/10/17.
 */

public class ContactInfo {
    public String mobile;
    public String name;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
