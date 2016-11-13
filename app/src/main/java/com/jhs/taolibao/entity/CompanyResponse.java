package com.jhs.taolibao.entity;

/**
 * Created by dds on 2016/7/13.
 *
 * @TODO
 */
public class CompanyResponse {

    private int code;
    private InfoEntity Info;
    private String ErrInfo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoEntity getInfo() {
        return Info;
    }

    public void setInfo(InfoEntity Info) {
        this.Info = Info;
    }

    public String getErrInfo() {
        return ErrInfo;
    }

    public void setErrInfo(String ErrInfo) {
        this.ErrInfo = ErrInfo;
    }

    public static class InfoEntity {


        private CompanyEntity Company;
        private IncomeEntity Income;
        private BalEntity Bal;
        private CashEntity Cash;

        public CompanyEntity getCompany() {
            return Company;
        }

        public void setCompany(CompanyEntity Company) {
            this.Company = Company;
        }

        public IncomeEntity getIncome() {
            return Income;
        }

        public void setIncome(IncomeEntity Income) {
            this.Income = Income;
        }

        public BalEntity getBal() {
            return Bal;
        }

        public void setBal(BalEntity Bal) {
            this.Bal = Bal;
        }

        public CashEntity getCash() {
            return Cash;
        }

        public void setCash(CashEntity Cash) {
            this.Cash = Cash;
        }

        public static class CompanyEntity {
            private String chi_name;
            private String list_date;
            private String indurstry;
            private String issue_price;
            private String issue_vol;
            private String reg_addr;
            private String main_business;
            private String state;

            public String getChi_name() {
                return chi_name;
            }

            public void setChi_name(String chi_name) {
                this.chi_name = chi_name;
            }

            public String getList_date() {
                return list_date;
            }

            public void setList_date(String list_date) {
                this.list_date = list_date;
            }

            public String getIndurstry() {
                return indurstry;
            }

            public void setIndurstry(String indurstry) {
                this.indurstry = indurstry;
            }

            public String getIssue_price() {
                return issue_price;
            }

            public void setIssue_price(String issue_price) {
                this.issue_price = issue_price;
            }

            public String getIssue_vol() {
                return issue_vol;
            }

            public void setIssue_vol(String issue_vol) {
                this.issue_vol = issue_vol;
            }

            public String getReg_addr() {
                return reg_addr;
            }

            public void setReg_addr(String reg_addr) {
                this.reg_addr = reg_addr;
            }

            public String getMain_business() {
                return main_business;
            }

            public void setMain_business(String main_business) {
                this.main_business = main_business;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }

        public static class IncomeEntity {
            private String basic_eps;
            private String operating_revenue;
            private String operating_profit;
            private String net_profit;

            public String getBasic_eps() {
                return basic_eps;
            }

            public void setBasic_eps(String basic_eps) {
                this.basic_eps = basic_eps;
            }

            public String getOperating_revenue() {
                return operating_revenue;
            }

            public void setOperating_revenue(String operating_revenue) {
                this.operating_revenue = operating_revenue;
            }

            public String getOperating_profit() {
                return operating_profit;
            }

            public void setOperating_profit(String operating_profit) {
                this.operating_profit = operating_profit;
            }

            public String getNet_profit() {
                return net_profit;
            }

            public void setNet_profit(String net_profit) {
                this.net_profit = net_profit;
            }
        }

        public static class BalEntity {
            private String total_current_assets;
            private String total_assets;
            private String total_current_liability;
            private String total_liability;
            private String total_shareholder_equity;

            public String getTotal_current_assets() {
                return total_current_assets;
            }

            public void setTotal_current_assets(String total_current_assets) {
                this.total_current_assets = total_current_assets;
            }

            public String getTotal_assets() {
                return total_assets;
            }

            public void setTotal_assets(String total_assets) {
                this.total_assets = total_assets;
            }

            public String getTotal_current_liability() {
                return total_current_liability;
            }

            public void setTotal_current_liability(String total_current_liability) {
                this.total_current_liability = total_current_liability;
            }

            public String getTotal_liability() {
                return total_liability;
            }

            public void setTotal_liability(String total_liability) {
                this.total_liability = total_liability;
            }

            public String getTotal_shareholder_equity() {
                return total_shareholder_equity;
            }

            public void setTotal_shareholder_equity(String total_shareholder_equity) {
                this.total_shareholder_equity = total_shareholder_equity;
            }
        }

        public static class CashEntity {
            private String net_operate_cash_flow;
            private String net_invest_cash_flow;
            private String net_finance_cash_flow;

            public String getNet_operate_cash_flow() {
                return net_operate_cash_flow;
            }

            public void setNet_operate_cash_flow(String net_operate_cash_flow) {
                this.net_operate_cash_flow = net_operate_cash_flow;
            }

            public String getNet_invest_cash_flow() {
                return net_invest_cash_flow;
            }

            public void setNet_invest_cash_flow(String net_invest_cash_flow) {
                this.net_invest_cash_flow = net_invest_cash_flow;
            }

            public String getNet_finance_cash_flow() {
                return net_finance_cash_flow;
            }

            public void setNet_finance_cash_flow(String net_finance_cash_flow) {
                this.net_finance_cash_flow = net_finance_cash_flow;
            }
        }
    }
}
