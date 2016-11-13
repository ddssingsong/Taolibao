package com.jhs.taolibao.code.simtrade.entity;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xujingbo on 2016/7/8.
 */
public class Stock {


    public enum Type {
        SELL,
        BUY;
    }

    //股票代码
    private String code;
    private String codeWithoutMic;
    //股票名字
    private String name;
    //剩余数目
    private double count;
    //选择的数量
    private double selCount;
    //现价
    private double curPrice;
    //原价
    private double oriPrice;
    //价格变化百分比
    private float pricePercent;
    private Type type;
    //最高价
    private String highPx;
    //最低价
    private String lowPx;
    //涨停价
    private String upPx;
    //跌停价
    private String downPx;
    //买档位
    private List<Grp> bidGrp;
    //卖档位
    private List<Grp> offerGrp;

    // 委托时间
    private String entrustTime;
    // 委托日期
    private String entrustDate;
    // 委托价格
    private String entrustPrice;
    // 委托数量
    private String entrustAmount;
    // 委托状态
    private String entrustStatus;
    // 委托方向 / 买卖方向
    private String entrustBs;
    // 成交数量
    private String businessAmount;
    // 委托编号
    private String entrustNo;

    // 交易日期
    private String initDate;



    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public String getBusinessBalance() {
        return businessBalance;
    }

    public void setBusinessBalance(String businessBalance) {
        this.businessBalance = businessBalance;
    }

    public String getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(String businessPrice) {
        this.businessPrice = businessPrice;
    }

    // 成交时间
    private String businessTime;
    // 成交金额
    private String businessBalance;
    // 成交价格
    private String businessPrice;



    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public void setCodeWithoutMic(String codeWithoutMic){
        this.codeWithoutMic = codeWithoutMic;
    }
    public String getCodeWithoutMic(){
        return codeWithoutMic;
    }
    public void setCurPrice(double curPrice){

        this.curPrice = curPrice;
    }

    public double getCurPrice() {
        return curPrice;
    }

    public void setOriPrice(double oriPrice) {
        this.oriPrice = oriPrice;
    }

    public double getOriPrice() {
        return oriPrice;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getCount() {
        return count;
    }

    public void setSelCount(double selCount) {
        this.selCount = selCount;
    }

    public double getSelCount() {
        return selCount;
    }

    public void setPricePercent(float pricePercent) {
        this.pricePercent = pricePercent;
    }

    public float getPricePercent() {
        return pricePercent;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setHighPx(String highPx) {
        this.highPx = highPx;
    }

    public String getHighPx() {
        return highPx;
    }

    public void setLowPx(String lowPx) {
        this.lowPx = lowPx;
    }

    public String getLowPx() {
        return lowPx;
    }

    public void setUpPx(String upPx) {
        this.upPx = upPx;
    }

    public String getUpPx() {
        return upPx;
    }

    public void setDownPx(String downPx) {
        this.downPx = downPx;
    }

    public String getDownPx() {
        return downPx;
    }

    public void setBidGrp(String strBidGrp) {
        String[] big = TextUtils.split(strBidGrp, ",");
        List<Grp> bidGrp = new ArrayList<>();
        for (int i = 0; i < (big.length - 1); i++) {
            if (i % 3 == 0) {

                Grp grp = new Grp(big[i], String.valueOf(Long.parseLong(big[i + 1]) / 100));
                bidGrp.add(grp);
            }
        }
        this.bidGrp = bidGrp;
    }

    public List<Grp> getBidGrp() {
        return bidGrp;
    }

    public void setOfferGrp(String strOfferGrp) {
        //this.offerGrp = offerGrp;
        String[] offer = TextUtils.split(strOfferGrp, ",");
        List<Grp> offerGrp = new ArrayList<>();
        for (int i = offer.length - 2; i >= 0; i--) {
            if (i % 3 == 0) {
                Grp grp = new Grp(offer[i], String.valueOf(Long.parseLong(offer[i + 1]) / 100));
                offerGrp.add(grp);
            }
        }
        this.offerGrp = offerGrp;
    }

    public List<Grp> getOfferGrp() {
        return offerGrp;
    }
    public String getEntrustTime() {
        return entrustTime;
    }

    public void setEntrustTime(String entrustTime) {
        this.entrustTime = entrustTime;
    }

    public String getEntrustDate() {
        return entrustDate;
    }

    public void setEntrustDate(String entrustDate) {
        this.entrustDate = entrustDate;
    }

    public String getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(String entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public String getEntrustAmount() {
        return entrustAmount;
    }

    public void setEntrustAmount(String entrustAmount) {
        this.entrustAmount = entrustAmount;
    }

    public String getEntrustStatus() {
        return entrustStatus;
    }

    public void setEntrustStatus(String entrustStatus) {
        this.entrustStatus = entrustStatus;
    }

    public String getEntrustBs() {
        return entrustBs;
    }

    public void setEntrustBs(String entrustBs) {
        this.entrustBs = entrustBs;
    }

    public String getBusinessAmount() {
        return businessAmount;
    }

    public void setBusinessAmount(String businessAmount) {
        this.businessAmount = businessAmount;
    }

    public class Grp {
        String price;
        String count;

        public Grp(String price, String count) {
            this.price = price;
            this.count = count;
        }

        public void setPrice(String strPrice) {
            price = strPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setCount(String strCount) {
            count = strCount;
        }

        public String getCount() {
            return count;
        }
    }

    public String getEntrustNo() {
        return entrustNo;
    }

    public void setEntrustNo(String entrustNo) {
        this.entrustNo = entrustNo;
    }
    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }
}
