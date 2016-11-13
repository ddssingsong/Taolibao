package com.jhs.taolibao.code.simtrade.entity;

import java.util.List;

/**
 * @author jiao on 2016/7/22 12:29
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:个人收益率
 */
public class PersonalReturnRate {

    /**
     * profit : 0.00
     * end_date : 20160722
     * profit_rank : 1
     * profit_period : 1
     * profit_rate : 0.00000000
     * begin_date : 20160722
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String profit;
        private String end_date;
        private String profit_rank;
        private String profit_period;
        private String profit_rate;
        private String begin_date;

        public String getProfit() {
            return profit;
        }

        public void setProfit(String profit) {
            this.profit = profit;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getProfit_rank() {
            return profit_rank;
        }

        public void setProfit_rank(String profit_rank) {
            this.profit_rank = profit_rank;
        }

        public String getProfit_period() {
            return profit_period;
        }

        public void setProfit_period(String profit_period) {
            this.profit_period = profit_period;
        }

        public String getProfit_rate() {
            return profit_rate;
        }

        public void setProfit_rate(String profit_rate) {
            this.profit_rate = profit_rate;
        }

        public String getBegin_date() {
            return begin_date;
        }

        public void setBegin_date(String begin_date) {
            this.begin_date = begin_date;
        }
    }
}
