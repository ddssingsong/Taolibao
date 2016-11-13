package com.jhs.taolibao.entity;

import com.jhs.taolibao.utils.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dds on 2016/6/1.
 */
public class UserInfo implements Serializable {
    private int id;
    private String sid;
    private String mobile;
    private String password;
    private String icon; // 用户头像
    private String alias;// 用户昵称
    private String SexStr;//性别
    private String birthday;//生日
    private String Signature;//个人签名
    private String city;
    private int sex;

    private int UserTotalPoint;//用户宝币数
    private String iconName; // 经过处理的环信用户名
    private String ExporationDate;// 到期时间
    private double userBalance;//用户充值宝币
    private int freezePoint;//用户冻结资金

    private double AccountSum;//
    private double gain; //总盈亏
    private double tradeSum;//交易总额

    private boolean payuser;

    private String HsAccount;
    private String HsPwd;


    public void SetJSONObject(JSONObject json) throws JSONException {
        if (!json.isNull("ID")) {
            try {
                this.setId(json.getInt("ID"));
            } catch (Exception e) {
            }
        }
        if (!json.isNull("SID")) {
            this.setSid(json.getString("SID"));
        }
        if (!json.isNull("Mobile")) {
            this.setMobile(json.getString("Mobile"));
        }
        if (!json.isNull("Password")) {
            this.setPassword(json.getString("Password"));
        }
        if (!json.isNull("Icon")) {
            this.setIcon(json.getString("Icon"));
        }
        if (!json.isNull("Alias")) {
            this.setAlias(json.getString("Alias"));
        }

        if (!json.isNull("SexStr")) {

            this.setSexStr(json.getString("SexStr"));
        }

        if (!json.isNull("Birthday")) {
            this.setBirthday(json.getString("Birthday"));
        }

        if (!json.isNull("Signature")) {
            this.setSign(json.getString("Signature"));
        }

        if (!json.isNull("City")) {
            this.setCity(json.getString("City"));
        }
        if (!json.isNull("Sex")) {
            try {
                this.setSex(json.getInt("Sex"));
            } catch (Exception e) {
            }
        }
        if (!json.isNull("IconName")) {
            this.setIconName(json.getString("IconName"));
        }
        if (!json.isNull("ExporationDate")) {
            this.setExporationDate(DateUtil.timeStamp2Date(json.getString("ExporationDate"), "yyyy-MM-dd"));
        }

        if (!json.isNull("UserBalance")) {
            try {
                this.setUserBalance(json.getDouble("UserBalance"));
            } catch (Exception e) {
                this.setUserBalance(0);
            }
        }

        if (!json.isNull("FreezePoint")) {
            try {
                this.setFreezePoint(json.getInt("FreezePoint"));
            } catch (Exception e) {
                this.setFreezePoint(0);
            }
        }
        if (!json.isNull("UserTotalPoint")) {
            try {
                this.setUserTotalPoint(json.getInt("UserTotalPoint"));
            } catch (Exception e) {
                this.setUserTotalPoint(0);
            }
        }

        if (!json.isNull("AccountSum")) {
            try {
                this.setAccountSum(json.getDouble("AccountSum"));
            } catch (Exception e) {
                this.setAccountSum(0);
            }
        }
        if (!json.isNull("Gain")) {
            try {
                this.setGain(json.getDouble("Gain"));
            } catch (Exception e) {
                this.setGain(0);
            }
        }
        if (!json.isNull("TradeSum")) {
            try {
                this.setTradeSum(json.getDouble("TradeSum"));
            } catch (Exception e) {
                this.setTradeSum(0);
            }
        }
        if (!json.isNull("HsAccount")) {
            this.setHsAccount(json.getString("HsAccount"));
        }

        if (!json.isNull("HsPwd")) {
            this.setHsPwd(json.getString("HsPwd"));
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public double getTradeSum() {
        return tradeSum;
    }

    public void setTradeSum(double tradeSum) {
        this.tradeSum = tradeSum;
    }

    public String getExporationDate() {
        return ExporationDate;
    }

    public void setExporationDate(String exporationDate) {
        this.ExporationDate = exporationDate;
    }

    public double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    public int getFreezePoint() {
        return freezePoint;
    }

    public void setFreezePoint(int freezePoint) {
        this.freezePoint = freezePoint;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSign() {
        return Signature;
    }

    public void setSign(String sign) {
        this.Signature = sign;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public double getAccountSum() {
        return AccountSum;
    }

    public void setAccountSum(double accountSum) {
        AccountSum = accountSum;
    }

    public String getSexStr() {
        return SexStr;
    }

    public void setSexStr(String sexStr) {
        SexStr = sexStr;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getUserTotalPoint() {
        return UserTotalPoint;
    }

    public void setUserTotalPoint(int userTotalPoint) {
        UserTotalPoint = userTotalPoint;
    }

    public boolean isPayuser() {
        return payuser;
    }

    public void setPayuser(boolean payuser) {
        this.payuser = payuser;
    }

    public String getHsAccount() {
        return HsAccount;
    }

    public void setHsAccount(String hsAccount) {
        HsAccount = hsAccount;
    }

    public String getHsPwd() {
        return HsPwd;
    }

    public void setHsPwd(String hsPwd) {
        HsPwd = hsPwd;
    }
}
