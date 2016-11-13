package com.jhs.taolibao.code.challenge.model;

import java.util.List;

/**
 * @author jiao on 2016/7/18 12:24
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:擂台信息
 */
public class ChallengeInfo {
    private int code;
    private ArenaDetailView data;
    private String Errinfo;

    public class ArenaDetailView {
        private ArenaUserInfo Champion;
        private List<ArenaUserInfo> Challenger;
        private String Notes;

        public ArenaUserInfo getChampion() {
            return Champion;
        }

        public void setChampion(ArenaUserInfo champion) {
            Champion = champion;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String notes) {
            Notes = notes;
        }

        public List<ArenaUserInfo> getChallenger() {
            return Challenger;
        }

        public void setChallenger(List<ArenaUserInfo> challenger) {
            Challenger = challenger;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArenaDetailView getData() {
        return data;
    }

    public void setData(ArenaDetailView data) {
        this.data = data;
    }

    public String getErrinfo() {
        return Errinfo;
    }

    public void setErrinfo(String errinfo) {
        Errinfo = errinfo;
    }
}
