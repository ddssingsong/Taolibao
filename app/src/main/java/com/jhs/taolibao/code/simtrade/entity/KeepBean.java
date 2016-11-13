package com.jhs.taolibao.code.simtrade.entity;

import java.util.List;

/**
 * @author jiao on 2016/7/13 15:32
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:"持仓"bean
 */
public class KeepBean {
    private List<KeepItem> data;

    public List<KeepItem> getData() {
        return data;
    }

    public void setData(List<KeepItem> data) {
        this.data = data;
    }

    public class KeepItem {
        private double cost_price;
        private String money_type;
        private String income_balance;
        private String exchange_type;
        private String current_amount;
        private double last_price;
        private String stock_code;
        //加了SS或SZ的stock_code
        private String stock_code1;
        private String enable_amount;
        private String position_str;
        private String market_value;
        private String stock_account;
        private String stock_name;
        private String day_income_balance;


        public String getMoney_type() {
            return money_type;
        }

        public void setMoney_type(String money_type) {
            this.money_type = money_type;
        }

        public String getIncome_balance() {
            return income_balance;
        }

        public void setIncome_balance(String income_balance) {
            this.income_balance = income_balance;
        }

        public String getExchange_type() {
            return exchange_type;
        }

        public void setExchange_type(String exchange_type) {
            this.exchange_type = exchange_type;

        }

        public String getCurrent_amount() {
            return current_amount;
        }

        public void setCurrent_amount(String current_amount) {
            this.current_amount = current_amount;
        }

        public String getStock_code() {
            return stock_code;
        }

        public void setStock_code(String stock_code) {
            this.stock_code = stock_code;
        }

        public String getEnable_amount() {
            return enable_amount;
        }

        public void setEnable_amount(String enable_amount) {
            this.enable_amount = enable_amount;
        }

        public String getPosition_str() {
            return position_str;
        }

        public void setPosition_str(String position_str) {
            this.position_str = position_str;
        }

        public String getMarket_value() {
            return market_value;
        }

        public void setMarket_value(String market_value) {
            this.market_value = market_value;
        }

        public String getStock_account() {
            return stock_account;
        }

        public void setStock_account(String stock_account) {
            this.stock_account = stock_account;
        }

        public String getStock_code1() {
            return stock_code1;
        }

        public void setStock_code1(String stock_code1) {
            this.stock_code1 = stock_code1;
        }

        public String getStock_name() {
            return stock_name;
        }

        public void setStock_name(String stock_name) {
            this.stock_name = stock_name;
        }

        public String getDay_income_balance() {
            return day_income_balance;
        }

        public void setDay_income_balance(String day_income_balance) {
            this.day_income_balance = day_income_balance;
        }

        public double getCost_price() {
            return cost_price;
        }

        public void setCost_price(double cost_price) {
            this.cost_price = cost_price;
        }

        public double getLast_price() {
            return last_price;
        }

        public void setLast_price(double last_price) {
            this.last_price = last_price;
        }
    }
}
