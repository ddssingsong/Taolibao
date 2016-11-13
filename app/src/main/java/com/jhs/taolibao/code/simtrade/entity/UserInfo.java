package com.jhs.taolibao.code.simtrade.entity;

/**
 * Created by xujingbo on 2016/7/29.
 */
public class UserInfo {

    /**
     * code : 0
     * user : {"ID":2,"Mobile":"18333608516","Password":"","CreateDate":"/Date(1461218294013)/","AccountSum":270420.515457511,"Gain":-4726.32391494513,"TradeSum":729579.484542489,"Locked":false,"ExporationDate":"/Date(1472378460887)/","UserBalance":0,"UserTotalPoint":454055,"Icon":"/UploadIocn/1184.jpg","Alias":"哈哈哈还","IconName":"JHS_1184","FreezePoint":51100,"Sex":1,"Birthday":"/Date(1451577600000)/","Signature":"我是涡轮巴菲特","City":"太原市","SID":null,"SexStr":"男","BirthdayStr":"2016-01-01","HsAccount":"70006981\t","HsPwd":"111111","Yeild":0,"Gruad":0,"Win":0}
     * payUser : true
     */

    private int code;

    private UserBean user;
    private boolean payUser;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public boolean isPayUser() {
        return payUser;
    }

    public void setPayUser(boolean payUser) {
        this.payUser = payUser;
    }

    public static class UserBean {
        private int ID;
        private String Mobile;
        private String Password;
        private String CreateDate;
        private double AccountSum;
        private double Gain;
        private double TradeSum;
        private boolean Locked;
        private String ExporationDate;
        private double UserBalance;
        private int UserTotalPoint;
        private String Icon;
        private String Alias;
        private String IconName;
        private int FreezePoint;
        private int Sex;
        private String Birthday;
        private String Signature;
        private String City;
        private Object SID;
        private String SexStr;
        private String BirthdayStr;
        private String HsAccount;
        private String HsPwd;
        private double Yeild;
        private int Gruad;
        private int Win;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public double getAccountSum() {
            return AccountSum;
        }

        public void setAccountSum(double AccountSum) {
            this.AccountSum = AccountSum;
        }

        public double getGain() {
            return Gain;
        }

        public void setGain(double Gain) {
            this.Gain = Gain;
        }

        public double getTradeSum() {
            return TradeSum;
        }

        public void setTradeSum(double TradeSum) {
            this.TradeSum = TradeSum;
        }

        public boolean isLocked() {
            return Locked;
        }

        public void setLocked(boolean Locked) {
            this.Locked = Locked;
        }

        public String getExporationDate() {
            return ExporationDate;
        }

        public void setExporationDate(String ExporationDate) {
            this.ExporationDate = ExporationDate;
        }

        public double getUserBalance() {
            return UserBalance;
        }

        public void setUserBalance(double UserBalance) {
            this.UserBalance = UserBalance;
        }

        public int getUserTotalPoint() {
            return UserTotalPoint;
        }

        public void setUserTotalPoint(int UserTotalPoint) {
            this.UserTotalPoint = UserTotalPoint;
        }

        public String getIcon() {
            return Icon;
        }

        public void setIcon(String Icon) {
            this.Icon = Icon;
        }

        public String getAlias() {
            return Alias;
        }

        public void setAlias(String Alias) {
            this.Alias = Alias;
        }

        public String getIconName() {
            return IconName;
        }

        public void setIconName(String IconName) {
            this.IconName = IconName;
        }

        public int getFreezePoint() {
            return FreezePoint;
        }

        public void setFreezePoint(int FreezePoint) {
            this.FreezePoint = FreezePoint;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }

        public String getSignature() {
            return Signature;
        }

        public void setSignature(String Signature) {
            this.Signature = Signature;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public Object getSID() {
            return SID;
        }

        public void setSID(Object SID) {
            this.SID = SID;
        }

        public String getSexStr() {
            return SexStr;
        }

        public void setSexStr(String SexStr) {
            this.SexStr = SexStr;
        }

        public String getBirthdayStr() {
            return BirthdayStr;
        }

        public void setBirthdayStr(String BirthdayStr) {
            this.BirthdayStr = BirthdayStr;
        }

        public String getHsAccount() {
            return HsAccount;
        }

        public void setHsAccount(String HsAccount) {
            this.HsAccount = HsAccount;
        }

        public String getHsPwd() {
            return HsPwd;
        }

        public void setHsPwd(String HsPwd) {
            this.HsPwd = HsPwd;
        }

        public double getYeild() {
            return Yeild;
        }

        public void setYeild(double Yeild) {
            this.Yeild = Yeild;
        }

        public int getGruad() {
            return Gruad;
        }

        public void setGruad(int Gruad) {
            this.Gruad = Gruad;
        }

        public int getWin() {
            return Win;
        }

        public void setWin(int Win) {
            this.Win = Win;
        }
    }
}
