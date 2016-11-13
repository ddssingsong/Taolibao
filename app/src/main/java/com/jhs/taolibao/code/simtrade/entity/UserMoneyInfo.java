package com.jhs.taolibao.code.simtrade.entity;

import java.util.List;

/**
 * @author jiao on 2016/7/22 11:56
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:用户资金信息
 */
public class UserMoneyInfo {
    /**
     * frozen_balance : 0
     * asset_balance : 4980029.20
     * unfrozen_balance : 0
     * enable_balance : 2397861.40
     * fetch_balance : 2397861.40
     * money_type : 0
     * current_balance : 2397861.40
     * fund_balance : 2397861.40
     * market_value : 2582167.80
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String frozen_balance;
        private String asset_balance;
        private String unfrozen_balance;
        private String enable_balance;
        private String fetch_balance;
        private String money_type;
        private String current_balance;
        private String fund_balance;
        private String market_value;

        public String getFrozen_balance() {
            return frozen_balance;
        }

        public void setFrozen_balance(String frozen_balance) {
            this.frozen_balance = frozen_balance;
        }

        public String getAsset_balance() {
            return asset_balance;
        }

        public void setAsset_balance(String asset_balance) {
            this.asset_balance = asset_balance;
        }

        public String getUnfrozen_balance() {
            return unfrozen_balance;
        }

        public void setUnfrozen_balance(String unfrozen_balance) {
            this.unfrozen_balance = unfrozen_balance;
        }

        public String getEnable_balance() {
            return enable_balance;
        }

        public void setEnable_balance(String enable_balance) {
            this.enable_balance = enable_balance;
        }

        public String getFetch_balance() {
            return fetch_balance;
        }

        public void setFetch_balance(String fetch_balance) {
            this.fetch_balance = fetch_balance;
        }

        public String getMoney_type() {
            return money_type;
        }

        public void setMoney_type(String money_type) {
            this.money_type = money_type;
        }

        public String getCurrent_balance() {
            return current_balance;
        }

        public void setCurrent_balance(String current_balance) {
            this.current_balance = current_balance;
        }

        public String getFund_balance() {
            return fund_balance;
        }

        public void setFund_balance(String fund_balance) {
            this.fund_balance = fund_balance;
        }

        public String getMarket_value() {
            return market_value;
        }

        public void setMarket_value(String market_value) {
            this.market_value = market_value;
        }
    }


}
