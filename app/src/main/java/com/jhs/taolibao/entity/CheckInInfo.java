package com.jhs.taolibao.entity;

import com.google.gson.Gson;

/**
 * Created by dds on 2016/7/25.
 *
 * @TODO 签到实体类
 */
public class CheckInInfo {



    private int code;
    private UserSignEntity UserSign;
    private String info;
    private String info2;
    private String Errinfo;

    public static CheckInInfo objectFromData(String str) {

        return new Gson().fromJson(str, CheckInInfo.class);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserSignEntity getUserSign() {
        return UserSign;
    }

    public void setUserSign(UserSignEntity UserSign) {
        this.UserSign = UserSign;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getErrinfo() {
        return Errinfo;
    }

    public void setErrinfo(String Errinfo) {
        this.Errinfo = Errinfo;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public static class UserSignEntity {
        private int ID;
        private int UserID;
        private int State;
        private String SignDate;


        public static UserSignEntity objectFromData(String str) {

            return new Gson().fromJson(str, UserSignEntity.class);
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getSignDate() {
            return SignDate;
        }

        public void setSignDate(String SignDate) {
            this.SignDate = SignDate;
        }


    }
}
