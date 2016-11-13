package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 分时图实体
 *
 * @TODO
 */
public class Minutehq implements Serializable {
    private String TimeID;//时间序号
    private String HQZJCJ;//成交价格
    private String AveragePrice;//成交均价
    private String HBCJSL;//成交数量
    private String Turnover;//成交金额
    private String Min;//分钟
    private String Buy;//买的数量
    private String Sell;//卖的数量
    private String netvalue;//基金净值
    private String YesterdayIncome;//昨日收盘


    public void SetJSONObject(JSONObject json) throws JSONException {


        if (!json.isNull("TimeID")) {
            this.setTimeID(json.getString("TimeID"));

        }
        if (!json.isNull("HQZJCJ")) {
            this.setHQZJCJ(json.getString("HQZJCJ"));
        }
        if (!json.isNull("AveragePrice")) {
            this.setAveragePrice(json.getString("AveragePrice"));
        }
        if (!json.isNull("HBCJSL")) {
            this.setHBCJSL(json.getString("HBCJSL"));


        }
        if (!json.isNull("Turnover")) {
            this.setTurnover(json.getString("Turnover"));
        }
        if (!json.isNull("Min")) {
            this.setMin(json.getString("Min"));
        }
        if (!json.isNull("Buy")) {
            this.setBuy(json.getString("Buy"));
        }
        if (!json.isNull("Sell")) {
            this.setSell(json.getString("Sell"));
        }
        if (!json.isNull("netvalue")) {
            this.setNetvalue(json.getString("netvalue"));
        }
        if (!json.isNull("YesterdayIncome")) {
            this.setYesterdayIncome(json.getString("YesterdayIncome"));
        }
    }


    public String getTimeID() {
        return TimeID;
    }

    public void setTimeID(String timeID) {
        TimeID = timeID;
    }

    public String getHQZJCJ() {
        return HQZJCJ;
    }

    public void setHQZJCJ(String HQZJCJ) {
        this.HQZJCJ = HQZJCJ;
    }

    public String getAveragePrice() {
        return AveragePrice;
    }

    public void setAveragePrice(String averagePrice) {
        AveragePrice = averagePrice;
    }

    public String getHBCJSL() {
        return HBCJSL;
    }

    public void setHBCJSL(String HBCJSL) {
        this.HBCJSL = HBCJSL;
    }

    public String getTurnover() {
        return Turnover;
    }

    public void setTurnover(String turnover) {
        Turnover = turnover;
    }

    public String getMin() {
        return Min;
    }

    public void setMin(String min) {
        Min = min;
    }

    public String getBuy() {
        return Buy;
    }

    public void setBuy(String buy) {
        Buy = buy;
    }

    public String getSell() {
        return Sell;
    }

    public void setSell(String sell) {
        Sell = sell;
    }

    public String getNetvalue() {
        return netvalue;
    }

    public void setNetvalue(String netvalue) {
        this.netvalue = netvalue;
    }

    public String getYesterdayIncome() {
        return YesterdayIncome;
    }

    public void setYesterdayIncome(String yesterdayIncome) {
        YesterdayIncome = yesterdayIncome;
    }

}
