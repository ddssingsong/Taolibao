package com.jhs.taolibao.code.challenge.model;

/**
 * @author jiao on 2016/7/18 14:40
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:用户信息
 */
public class UserInfo {
    private int code;
    private ArenaUserInfo data;
    private String Errinfo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArenaUserInfo getData() {
        return data;
    }

    public void setData(ArenaUserInfo data) {
        this.data = data;
    }

    public String getErrinfo() {
        return Errinfo;
    }

    public void setErrinfo(String errinfo) {
        Errinfo = errinfo;
    }
}
