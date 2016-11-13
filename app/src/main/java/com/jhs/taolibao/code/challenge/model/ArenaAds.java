package com.jhs.taolibao.code.challenge.model;

import java.util.List;

/**
 * 挑战乱播广告
 * Created by xujingbo on 2016/7/28.
 */
public class ArenaAds {

    /**
     * code : 0
     * Slogan : ["擂主123正接受456个人的挑战","擂主456正接受567个人的挑战","擂主789正接受1230个人的挑战"]
     * Errinfo :
     */

    private int code;
    private String Errinfo;
    private List<String> Slogan;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrinfo() {
        return Errinfo;
    }

    public void setErrinfo(String Errinfo) {
        this.Errinfo = Errinfo;
    }

    public List<String> getSlogan() {
        return Slogan;
    }

    public void setSlogan(List<String> Slogan) {
        this.Slogan = Slogan;
    }
}
