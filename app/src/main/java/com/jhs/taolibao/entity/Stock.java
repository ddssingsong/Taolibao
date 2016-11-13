package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dds on 2016/7/1.
 *
 * @TODO
 */
public class Stock implements Serializable{
    private String StockCode;
    private String StockName;
    private String DealPrice;
    private String ClosingPrice;
    private String ChangeRate;

    private String StockPrice;
    private String StockRatio;

    public void SetJSONObject(JSONObject json) throws JSONException {

        if (!json.isNull("StockCode")) {
            this.setStockCode(json.getString("StockCode"));
        }

        if (!json.isNull("StockName")) {
            this.setStockName(json.getString("StockName"));
        }
        if (!json.isNull("DealPrice")) {
            this.setDealPrice(json.getString("DealPrice"));
        }
        if (!json.isNull("ClosingPrice")) {
            this.setClosingPrice(json.getString("ClosingPrice"));
        }
        if (!json.isNull("ChangeRate")) {
            this.setChangeRate(json.getString("ChangeRate"));
        }


        if (!json.isNull("StockPrice")) {
            this.setStockPrice(json.getString("StockPrice"));
        }
        if (!json.isNull("StockRatio")) {
            this.setStockRatio(json.getString("StockRatio"));
        }


    }

    public String getStockCode() {
        return StockCode;
    }

    public void setStockCode(String stockCode) {
        StockCode = stockCode;
    }

    public String getStockName() {
        return StockName;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }

    public String getDealPrice() {
        return DealPrice;
    }

    public void setDealPrice(String dealPrice) {
        DealPrice = dealPrice;
    }

    public String getClosingPrice() {
        return ClosingPrice;
    }

    public void setClosingPrice(String closingPrice) {
        ClosingPrice = closingPrice;
    }

    public String getChangeRate() {
        return ChangeRate;
    }

    public void setChangeRate(String changeRate) {
        ChangeRate = changeRate;
    }

    public String getStockPrice() {
        return StockPrice;
    }

    public void setStockPrice(String stockPrice) {
        StockPrice = stockPrice;
    }

    public String getStockRatio() {
        return StockRatio;
    }

    public void setStockRatio(String stockRatio) {
        StockRatio = stockRatio;
    }
}
