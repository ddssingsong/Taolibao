package com.jhs.taolibao.code.challenge.model;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * @author jiao on 2016/7/19 16:33
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:擂台用户信息
 */
public class ArenaUserInfo implements Serializable {
    //昵称
    private String Alias;
    //头像
    private String IconImg;
    //签名
    private String Signature;
    //本期收益率
    private String CurrentYield;
    //收益率区间
    private String YeildRegion;
    //年化收益率
    private String YearsYield;
    //累计收益率
    private String CumulativeReturns;
    //胜利次数
    private int Win;
    //失败次数
    private int Lose;
    //守擂次数
    private int guard;
    //用户id
    private int UserID;

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public String getIconImg() {
        return IconImg;
    }

    public void setIconImg(String iconImg) {
        IconImg = iconImg;
    }

    public String getCurrentYield() {
        if (null == CurrentYield){
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(Float.valueOf(CurrentYield) * 100) + "%";
    }

    public void setCurrentYield(String currentYield) {
        CurrentYield = currentYield;
    }

    public String getYearsYield() {
        if (null == YearsYield){
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(Float.valueOf(YearsYield) * 100) + "%";
    }

    public void setYearsYield(String yearsYield) {
        YearsYield = yearsYield;
    }

    public String getCumulativeReturns() {
        if (null == CumulativeReturns){
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(Float.valueOf(CumulativeReturns) * 100) + "%";
    }

    public void setCumulativeReturns(String cumulativeReturns) {
        CumulativeReturns = cumulativeReturns;
    }

    public int getWin() {
        return Win;
    }

    public void setWin(int win) {
        Win = win;
    }

    public int getLose() {
        return Lose;
    }

    public void setLose(int lose) {
        Lose = lose;
    }

    public int getGuard() {
        return guard;
    }

    public void setGuard(int guard) {
        this.guard = guard;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getYeildRegion() {
        return YeildRegion+"天";
    }

    public void setYeildRegion(String yeildRegion) {
        YeildRegion = yeildRegion;
    }
}
