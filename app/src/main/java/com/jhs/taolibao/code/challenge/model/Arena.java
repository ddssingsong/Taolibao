package com.jhs.taolibao.code.challenge.model;

import com.jhs.taolibao.code.simtrade.utils.DateUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author jiao on 2016/7/20 11:34
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:"擂台"首页的擂台
 */
public class Arena{
    private int code;
    private List<Challenger> data;
    private String Errinfo;

    public class Challenger implements Serializable{
        private int ID;
        private String StartDate;
        private String EndDate;
        private String StartDateStr;
        private String EndDateStr;
        private int State;
        private String GuardUser;
        private String WinnerUser;
        private String CreateDate;
        private ArenaUserInfo Champion;
        private String Users;
        private String PeepUser;
        private int ArenaType;
        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String startDate) {
            StartDate = startDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String endDate) {
            EndDate = endDate;
        }

        public int getState() {
            return State;
        }

        public void setState(int state) {
            State = state;
        }

        public String getGuardUser() {
            return GuardUser;
        }

        public void setGuardUser(String guardUser) {
            GuardUser = guardUser;
        }

        public String getWinnerUser() {
            return WinnerUser;
        }

        public void setWinnerUser(String winnerUser) {
            WinnerUser = winnerUser;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String createDate) {
            CreateDate = createDate;
        }

        public ArenaUserInfo getChampion() {
            return Champion;
        }

        public void setChampion(ArenaUserInfo champion) {
            Champion = champion;
        }

        public String getUsers() {
            return Users;
        }

        public void setUsers(String users) {
            Users = users;
        }

        public String getPeepUser() {
            return PeepUser;
        }

        public void setPeepUser(String peepUser) {
            PeepUser = peepUser;
        }

        public long getStartDateStr() {
            return DateUtils.getTime(StartDateStr);
        }

        public void setStartDateStr(String startDateStr) {
            StartDateStr = startDateStr;
        }

        public long getEndDateStr() {
            return DateUtils.getTime(EndDateStr);
        }

        public void setEndDateStr(String endDateStr) {
            EndDateStr = endDateStr;
        }

        public int getArenaType() {
            return ArenaType;
        }

        public void setArenaType(int arenaType) {
            ArenaType = arenaType;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Challenger> getData() {
        return data;
    }

    public void setData(List<Challenger> data) {
        this.data = data;
    }

    public String getErrinfo() {
        return Errinfo;
    }

    public void setErrinfo(String errinfo) {
        Errinfo = errinfo;
    }
}
