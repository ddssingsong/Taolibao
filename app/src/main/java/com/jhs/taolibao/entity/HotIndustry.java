package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dds on 2016/7/4.
 *
 * @TODO
 */
public class HotIndustry implements Serializable {
    private String IndustryCode;
    private String IndustryName;
    private String IndustryRatio;
    private String StockCode;
    private String StockName;
    private String StockPrice;
    private String StockRatio;

    public void SetJSONObject(JSONObject json) throws JSONException {
        if (!json.isNull("IndustryCode")) {
            this.setIndustryCode(json.getString("IndustryCode"));
        }
        if (!json.isNull("IndustryName")) {
            this.setIndustryName(json.getString("IndustryName"));
        }

        if (!json.isNull("IndustryRatio")) {
            this.setIndustryRatio(json.getString("IndustryRatio"));
        }
        if (!json.isNull("StockCode")) {
            this.setStockCode(json.getString("StockCode"));
        }
        if (!json.isNull("StockName")) {
            this.setStockName(json.getString("StockName"));
        }
        if (!json.isNull("StockPrice")) {
            this.setStockPrice(json.getString("StockPrice"));
        }
        if (!json.isNull("StockRatio")) {
            this.setStockRatio(json.getString("StockRatio"));
        }

    }


    public String getIndustryName() {
        return IndustryName;
    }

    public void setIndustryName(String industryName) {
        IndustryName = industryName;
    }

    public String getIndustryRatio() {
        return IndustryRatio;
    }

    public void setIndustryRatio(String industryRatio) {
        IndustryRatio = industryRatio;
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

    public String getIndustryCode() {
        return IndustryCode;
    }

    public void setIndustryCode(String industryCode) {
        IndustryCode = industryCode;
    }
}
