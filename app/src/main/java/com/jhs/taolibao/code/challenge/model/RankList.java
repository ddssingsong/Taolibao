package com.jhs.taolibao.code.challenge.model;

/**
 * @author jiao on 2016/7/18 14:33
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:短线排行
 */
public class RankList {
    private int code;
    private ArenaRankingList data;
    private String Errinfo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArenaRankingList getData() {
        return data;
    }

    public void setData(ArenaRankingList data) {
        this.data = data;
    }

    public String getErrinfo() {
        return Errinfo;
    }

    public void setErrinfo(String errinfo) {
        Errinfo = errinfo;
    }
}
