package com.jhs.taolibao.code.challenge.model;

/**
 * @author jiao on 2016/7/19 18:24
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:用户开户信息
 */
public class UserOpenAccountInfo {
    private int code;
    private HsInfo data;
    private String Errinfo;

    public class HsInfo {
        private int ID;
        private int UserID;
        private String HsAcouunt;
        private String HsPwd;
        private int Yield;
        private int Guard;
        private int Win;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int userID) {
            UserID = userID;
        }

        public String getHsAcouunt() {
            return HsAcouunt;
        }

        public void setHsAcouunt(String hsAcouunt) {
            HsAcouunt = hsAcouunt;
        }

        public String getHsPwd() {
            return HsPwd;
        }

        public void setHsPwd(String hsPwd) {
            HsPwd = hsPwd;
        }

        public int getYield() {
            return Yield;
        }

        public void setYield(int yield) {
            Yield = yield;
        }

        public int getGuard() {
            return Guard;
        }

        public void setGuard(int guard) {
            Guard = guard;
        }

        public int getWin() {
            return Win;
        }

        public void setWin(int win) {
            Win = win;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HsInfo getData() {
        return data;
    }

    public void setData(HsInfo data) {
        this.data = data;
    }

    public String getErrinfo() {
        return Errinfo;
    }

    public void setErrinfo(String errinfo) {
        Errinfo = errinfo;
    }
}


































