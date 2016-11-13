package com.jhs.taolibao.code.simtrade.entity;

/**
 * Created by KANGXIANGTAO on 2016/7/11.
 */
public class StockOfCancel {

    // 股票名字和代码
    private String stokeName;
    private String stokeCode;

    // 委托价格和数量
    private double price;
    private double DeleNum;

    // 状态和时间
    private String status;
    private String time;

    // 类型和成交数量
    private String style;
    private int DealNum;


    public String getStokeName() {
        return stokeName;
    }

    public void setStokeName(String stokeName) {
        this.stokeName = stokeName;
    }

    public String getStokeCode() {
        return stokeCode;
    }

    public void setStokeCode(String stokeCode) {
        this.stokeCode = stokeCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDeleNum() {
        return DeleNum;
    }

    public void setDeleNum(double deleNum) {
        DeleNum = deleNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getDealNum() {
        return DealNum;
    }

    public void setDealNum(int dealNum) {
        DealNum = dealNum;
    }
}
