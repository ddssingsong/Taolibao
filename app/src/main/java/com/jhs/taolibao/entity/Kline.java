package com.jhs.taolibao.entity;

import java.io.Serializable;

/**
 * k线图实体
 *
 * @TODO
 */
public class Kline implements Serializable{

    private String Time;//时间
    private String Opening;//开盘
    private String Closing;//收盘
    private String HighestPrice;//最高成交
    private String Lowest;//最低成交
    private String RatioValue;//涨跌额
    private String Ratio;//涨跌幅
    private String Quantity;//成交量
    private String MA5;
    private String MA10;
    private String MA20;
    private String MA60;
    private String CJSLMA5;
    private String CJSLMA10;
    private String EMA12;
    private String EMA26;
    private String DIF;
    private String DEA;
    private String RSI6;
    private String RSI12;
    private String RSI24;
    private String K;
    private String D;
    private String J;
    private String Turnover;
    private String stockCode;


    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getOpening() {
        return Opening;
    }

    public void setOpening(String opening) {
        Opening = opening;
    }

    public String getClosing() {
        return Closing;
    }

    public void setClosing(String closing) {
        Closing = closing;
    }

    public String getHighestPrice() {
        return HighestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        HighestPrice = highestPrice;
    }

    public String getLowest() {
        return Lowest;
    }

    public void setLowest(String lowest) {
        Lowest = lowest;
    }

    public String getRatioValue() {
        return RatioValue;
    }

    public void setRatioValue(String ratioValue) {
        RatioValue = ratioValue;
    }

    public String getRatio() {
        return Ratio;
    }

    public void setRatio(String ratio) {
        Ratio = ratio;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getMA5() {
        return MA5;
    }

    public void setMA5(String MA5) {
        this.MA5 = MA5;
    }

    public String getMA10() {
        return MA10;
    }

    public void setMA10(String MA10) {
        this.MA10 = MA10;
    }

    public String getMA20() {
        return MA20;
    }

    public void setMA20(String MA20) {
        this.MA20 = MA20;
    }

    public String getMA60() {
        return MA60;
    }

    public void setMA60(String MA60) {
        this.MA60 = MA60;
    }

    public String getCJSLMA5() {
        return CJSLMA5;
    }

    public void setCJSLMA5(String CJSLMA5) {
        this.CJSLMA5 = CJSLMA5;
    }

    public String getCJSLMA10() {
        return CJSLMA10;
    }

    public void setCJSLMA10(String CJSLMA10) {
        this.CJSLMA10 = CJSLMA10;
    }

    public String getEMA12() {
        return EMA12;
    }

    public void setEMA12(String EMA12) {
        this.EMA12 = EMA12;
    }

    public String getEMA26() {
        return EMA26;
    }

    public void setEMA26(String EMA26) {
        this.EMA26 = EMA26;
    }

    public String getDIF() {
        return DIF;
    }

    public void setDIF(String DIF) {
        this.DIF = DIF;
    }

    public String getDEA() {
        return DEA;
    }

    public void setDEA(String DEA) {
        this.DEA = DEA;
    }

    public String getRSI6() {
        return RSI6;
    }

    public void setRSI6(String RSI6) {
        this.RSI6 = RSI6;
    }

    public String getRSI12() {
        return RSI12;
    }

    public void setRSI12(String RSI12) {
        this.RSI12 = RSI12;
    }

    public String getRSI24() {
        return RSI24;
    }

    public void setRSI24(String RSI24) {
        this.RSI24 = RSI24;
    }

    public String getK() {
        return K;
    }

    public void setK(String k) {
        K = k;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getJ() {
        return J;
    }

    public void setJ(String j) {
        J = j;
    }

    public String getTurnover() {
        return Turnover;
    }

    public void setTurnover(String turnover) {
        Turnover = turnover;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }
}
