package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public class GeStock implements Serializable {
    private String StockName;
    private String StockCode;
    private String stockPrice;
    private String StockRatio;
    private String StockRatioValue;
    private String IndustryName;
    private String TodayOpen;
    private String YesterdayIncome;
    private String TopTurnover;
    private String LowTurnover;
    private String TurnoverNum;
    private String TurnoverValue;
    private String turnoverRate;
    private String PeRatio;
    private String Invol;
    private String OuterDisc;


    public void SetJSONObject(JSONObject json) throws JSONException {
        if (!json.isNull("StockName")) {
            this.setStockName(json.getString("StockName"));
        }

        if (!json.isNull("StockCode")) {
            this.setStockCode(json.getString("StockCode"));
        }
        if (!json.isNull("stockPrice")) {
            this.setStockPrice(json.getString("stockPrice"));
        }

        if (!json.isNull("StockRatio")) {
            this.setStockRatio(json.getString("StockRatio"));
        }
        if (!json.isNull("StockRatioValue")) {
            this.setStockRatioValue(json.getString("StockRatioValue"));
        }

        if (!json.isNull("IndustryName")) {
            this.setIndustryName(json.getString("IndustryName"));
        }
        if (!json.isNull("TodayOpen")) {
            this.setTodayOpen(json.getString("TodayOpen"));
        }

        if (!json.isNull("YesterdayIncome")) {
            this.setYesterdayIncome(json.getString("YesterdayIncome"));
        }
        if (!json.isNull("TopTurnover")) {
            this.setTopTurnover(json.getString("TopTurnover"));
        }

        if (!json.isNull("LowTurnover")) {
            this.setLowTurnover(json.getString("LowTurnover"));
        }
        if (!json.isNull("TurnoverNum")) {
            this.setTurnoverNum(json.getString("TurnoverNum"));
        }

        if (!json.isNull("TurnoverValue")) {
            this.setTurnoverValue(json.getString("TurnoverValue"));
        }


        if (!json.isNull("turnoverRate")) {
            this.setTurnoverRate(json.getString("turnoverRate"));
        }

        if (!json.isNull("PeRatio")) {
            this.setPeRatio(json.getString("PeRatio"));
        }


        if (!json.isNull("Invol")) {
            this.setInvol(json.getString("Invol"));
        }
        if (!json.isNull("OuterDisc")) {
            this.setOuterDisc(json.getString("OuterDisc"));
        }

    }

    public String getStockName() {
        return StockName;
    }


    public void setStockName(String stockName) {
        StockName = stockName;
    }

    public String getStockCode() {
        return StockCode;
    }

    public void setStockCode(String stockCode) {
        StockCode = stockCode;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getStockRatio() {
        return StockRatio;
    }

    public void setStockRatio(String stockRatio) {
        StockRatio = stockRatio;
    }

    public String getStockRatioValue() {
        return StockRatioValue;
    }

    public void setStockRatioValue(String stockRatioValue) {
        StockRatioValue = stockRatioValue;
    }

    public String getIndustryName() {
        return IndustryName;
    }

    public void setIndustryName(String industryName) {
        IndustryName = industryName;
    }

    public String getTodayOpen() {
        return TodayOpen;
    }

    public void setTodayOpen(String todayOpen) {
        TodayOpen = todayOpen;
    }

    public String getYesterdayIncome() {
        return YesterdayIncome;
    }

    public void setYesterdayIncome(String yesterdayIncome) {
        YesterdayIncome = yesterdayIncome;
    }

    public String getTopTurnover() {
        return TopTurnover;
    }

    public void setTopTurnover(String topTurnover) {
        TopTurnover = topTurnover;
    }

    public String getLowTurnover() {
        return LowTurnover;
    }

    public void setLowTurnover(String lowTurnover) {
        LowTurnover = lowTurnover;
    }

    public String getTurnoverNum() {
        return TurnoverNum;
    }

    public void setTurnoverNum(String turnoverNum) {
        TurnoverNum = turnoverNum;
    }

    public String getTurnoverValue() {
        return TurnoverValue;
    }

    public void setTurnoverValue(String turnoverValue) {
        TurnoverValue = turnoverValue;
    }

    public String getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(String turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public String getPeRatio() {
        return PeRatio;
    }

    public void setPeRatio(String peRatio) {
        PeRatio = peRatio;
    }


    public String getInvol() {
        return Invol;
    }

    public void setInvol(String invol) {
        Invol = invol;
    }

    public String getOuterDisc() {
        return OuterDisc;
    }

    public void setOuterDisc(String outerDisc) {
        OuterDisc = outerDisc;
    }
}
