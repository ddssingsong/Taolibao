package com.jhs.taolibao.entity;

import com.google.gson.Gson;

/**
 * Created by dds on 2016/7/21.
 *
 * @TODO 订单信息
 */
public class Order {


    private int ID;
    private int UserID;
    private int Amount;
    private String PayTime;
    private int PayType;
    private int BankID;
    private String CreateTime;
    private String Notes;
    private Object PayTypeStr;
    private Object Bank;

    public static Order objectFromData(String str) {

        return new Gson().fromJson(str, Order.class);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String PayTime) {
        this.PayTime = PayTime;
    }

    public int getPayType() {
        return PayType;
    }

    public void setPayType(int PayType) {
        this.PayType = PayType;
    }

    public int getBankID() {
        return BankID;
    }

    public void setBankID(int BankID) {
        this.BankID = BankID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }

    public Object getPayTypeStr() {
        return PayTypeStr;
    }

    public void setPayTypeStr(Object PayTypeStr) {
        this.PayTypeStr = PayTypeStr;
    }

    public Object getBank() {
        return Bank;
    }

    public void setBank(Object Bank) {
        this.Bank = Bank;
    }
}
