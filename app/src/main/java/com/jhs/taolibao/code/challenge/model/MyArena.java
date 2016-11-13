package com.jhs.taolibao.code.challenge.model;

import android.text.TextUtils;

import java.util.List;

/**
 * @author jiao on 2016/7/18 14:37
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:我的擂台
 */
public class MyArena {
    private int code;
    private List<ArenaDetailView> data;
    private String Errinfo;

    public class ArenaDetailView {
        private ArenaUserInfo Champion;
        private ArenaUserInfo Challenger;
        private String Notes;
        private int ArenaID;
        private boolean GuardWin;
        private String StartDtae;
        public ArenaUserInfo getChampion() {
            return Champion;
        }

        public void setChampion(ArenaUserInfo champion) {
            Champion = champion;
        }

        public ArenaUserInfo getChallenger() {
            return Challenger;
        }

        public void setChallenger(ArenaUserInfo challenger) {
            Challenger = challenger;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String notes) {
            Notes = notes;
        }

        public int getArenaID() {
            return ArenaID;
        }

        public void setArenaID(int arenaID) {
            ArenaID = arenaID;
        }

        public boolean isGuardWin() {
            return GuardWin;
        }

        public void setGuardWin(boolean guardWin) {
            GuardWin = guardWin;
        }

        public String getStartDtae() {
            if (null != StartDtae && !StartDtae.isEmpty()) {
                return TextUtils.substring(StartDtae, 6,StartDtae.length()-2);
            }
            return null;
        }

        public void setStartDtae(String startDtae) {
            StartDtae = startDtae;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ArenaDetailView> getData() {
        return data;
    }

    public void setData(List<ArenaDetailView> data) {
        this.data = data;
    }

    public String getErrinfo() {
        return Errinfo;
    }

    public void setErrinfo(String errinfo) {
        Errinfo = errinfo;
    }
}
