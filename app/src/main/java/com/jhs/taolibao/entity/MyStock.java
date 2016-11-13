package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public class MyStock implements Serializable {

    private int id;
    private int userid;
    private String StockCode;
    private String CreateDate;
    private String Price;
    private String Ratio;
    private String stockName;


    public void SetJSONObject(JSONObject json) throws JSONException {
        if (!json.isNull("ID")) {
            try {
                this.setId(json.getInt("ID"));
            } catch (Exception e) {
            }
        }
        if (!json.isNull("UserID")) {
            try {
                this.setUserid(json.getInt("UserID"));
            } catch (Exception e) {
            }
        }

        if (!json.isNull("StockCode")) {
            this.setStockCode(json.getString("StockCode"));
        }
        if (!json.isNull("CreateDate")) {
            this.setCreateDate(json.getString("CreateDate"));
        }
        if (!json.isNull("Price")) {
            this.setPrice(json.getString("Price"));
        }
        if (!json.isNull("Ratio")) {
            this.setRatio(json.getString("Ratio"));


        }
        if (!json.isNull("stockName")) {
            this.setStockName(json.getString("stockName"));
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getStockCode() {
        return StockCode;
    }

    public void setStockCode(String stockCode) {
        StockCode = stockCode;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRatio() {
        return Ratio;
    }

    public void setRatio(String ratio) {
        Ratio = ratio;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
