package com.jhs.taolibao.entity;

/**
 * Created by dds on 2016/7/14.
 *
 * @TODO 大盘指数
 */
public class Street {


    /**
     * ID : 0
     * StreetValue : 2938.68
     * StreetSZValue : 10344.91
     * StreetCYValue : 2204.97
     * StreetZXValue : 6802.3
     * StreetValueDate : /Date(-62135596800000)/
     */

    private int ID;
    private double StreetValue;
    private double StreetSZValue;
    private double StreetCYValue;
    private double StreetZXValue;
    private String StreetValueDate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getStreetValue() {
        return StreetValue;
    }

    public void setStreetValue(double StreetValue) {
        this.StreetValue = StreetValue;
    }

    public double getStreetSZValue() {
        return StreetSZValue;
    }

    public void setStreetSZValue(double StreetSZValue) {
        this.StreetSZValue = StreetSZValue;
    }

    public double getStreetCYValue() {
        return StreetCYValue;
    }

    public void setStreetCYValue(double StreetCYValue) {
        this.StreetCYValue = StreetCYValue;
    }

    public double getStreetZXValue() {
        return StreetZXValue;
    }

    public void setStreetZXValue(double StreetZXValue) {
        this.StreetZXValue = StreetZXValue;
    }

    public String getStreetValueDate() {
        return StreetValueDate;
    }

    public void setStreetValueDate(String StreetValueDate) {
        this.StreetValueDate = StreetValueDate;
    }
}
