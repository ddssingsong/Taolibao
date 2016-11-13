package com.jhs.taolibao.code.market.widget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.market.prestener.CompanyPresenterImpl;
import com.jhs.taolibao.code.market.view.CompanyView;
import com.jhs.taolibao.entity.CompanyResponse;
import com.jhs.taolibao.utils.MathUtil;

/**
 * 个股行情里的简况信息
 *
 * @TODO
 */
public class Info1Fragment extends BaseFragment implements CompanyView {
    private TabLayout tab_stock_buttom;
    private RelativeLayout layout_jiankuang;//简况
    private TextView tv_jian1;//公司介绍
    private TextView tv_jian2;//上市日期
    private TextView tv_jian3;//所属行业
    private TextView tv_jian4;//发行价格
    private TextView tv_jian5;//发行数量
    private TextView tv_jian6;//所属地区
    private TextView tv_jian7;//主营业务
    private RelativeLayout layout_caiwu;//财务
    private TextView tv_lirun1;
    private TextView tv_lirun2;
    private TextView tv_lirun3;
    private TextView tv_lirun4;
    private TextView tv_fuzai1;
    private TextView tv_fuzai2;
    private TextView tv_fuzai3;
    private TextView tv_fuzai4;
    private TextView tv_fuzai5;
    private TextView tv_xianjin1;
    private TextView tv_xianjin2;
    private TextView tv_xianjin3;
    private CompanyPresenterImpl companyPresenter;
    private String StockCode;

    @Override
    protected boolean onBackPressed() {
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyPresenter = new CompanyPresenterImpl(this);
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_jiankuang, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        tab_stock_buttom = (TabLayout) rootView.findViewById(R.id.tab_stock_buttom);
        layout_jiankuang = (RelativeLayout) rootView.findViewById(R.id.layout_jiankuang);
        tv_jian1 = (TextView) rootView.findViewById(R.id.tv_jian1);
        tv_jian2 = (TextView) rootView.findViewById(R.id.tv_jian2);
        tv_jian3 = (TextView) rootView.findViewById(R.id.tv_jian3);
        tv_jian4 = (TextView) rootView.findViewById(R.id.tv_jian4);
        tv_jian5 = (TextView) rootView.findViewById(R.id.tv_jian5);
        tv_jian6 = (TextView) rootView.findViewById(R.id.tv_jian6);
        tv_jian7 = (TextView) rootView.findViewById(R.id.tv_jian7);
        tv_lirun1 = (TextView) rootView.findViewById(R.id.tv_lirun1);
        tv_lirun2 = (TextView) rootView.findViewById(R.id.tv_lirun2);
        tv_lirun3 = (TextView) rootView.findViewById(R.id.tv_lirun3);
        tv_lirun4 = (TextView) rootView.findViewById(R.id.tv_lirun4);
        tv_fuzai1 = (TextView) rootView.findViewById(R.id.tv_fuzai1);
        tv_fuzai2 = (TextView) rootView.findViewById(R.id.tv_fuzai2);
        tv_fuzai3 = (TextView) rootView.findViewById(R.id.tv_fuzai3);
        tv_fuzai4 = (TextView) rootView.findViewById(R.id.tv_fuzai4);
        tv_fuzai5 = (TextView) rootView.findViewById(R.id.tv_fuzai5);
        tv_xianjin1 = (TextView) rootView.findViewById(R.id.tv_xianjin1);
        tv_xianjin2 = (TextView) rootView.findViewById(R.id.tv_xianjin2);
        tv_xianjin3 = (TextView) rootView.findViewById(R.id.tv_xianjin3);
        layout_caiwu = (RelativeLayout) rootView.findViewById(R.id.layout_caiwu);


        tab_stock_buttom.addTab(tab_stock_buttom.newTab().setText(R.string.jiangkuang));
        tab_stock_buttom.addTab(tab_stock_buttom.newTab().setText(R.string.caiwu));
        tab_stock_buttom.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        layout_jiankuang.setVisibility(View.VISIBLE);
                        layout_caiwu.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        layout_jiankuang.setVisibility(View.INVISIBLE);
                        layout_caiwu.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onInitloadData() {

        companyPresenter.getCompanyInfo(StockCode);
    }


    //获取个股公司信息成功
    @Override
    public void getCompanySuccess(CompanyResponse companyResponse) {
        CompanyResponse.InfoEntity.CompanyEntity companyEntity = companyResponse.getInfo().getCompany();
        tv_jian1.setText(companyEntity.getChi_name());
        tv_jian2.setText(companyEntity.getList_date());
        tv_jian3.setText(companyEntity.getIndurstry());
        tv_jian4.setText(companyEntity.getIssue_price());
        tv_jian5.setText(companyEntity.getIssue_vol());
        tv_jian6.setText(companyEntity.getReg_addr());
        tv_jian7.setText(companyEntity.getMain_business());

        CompanyResponse.InfoEntity.IncomeEntity incomeEntity = companyResponse.getInfo().getIncome();

        String dou1 = MathUtil.get(Double.parseDouble(incomeEntity.getBasic_eps()));
        tv_lirun1.setText(dou1);
        String dou2 = MathUtil.get(Double.parseDouble(incomeEntity.getOperating_revenue()));
        tv_lirun2.setText(dou2);
        String dou3 = MathUtil.get(Double.parseDouble(incomeEntity.getOperating_profit()));
        tv_lirun3.setText(dou3);
        String dou4 = MathUtil.get(Double.parseDouble(incomeEntity.getNet_profit()));
        tv_lirun4.setText(dou4);

        CompanyResponse.InfoEntity.BalEntity balEntity = companyResponse.getInfo().getBal();
        String dou5 = MathUtil.get(Double.parseDouble(balEntity.getTotal_current_assets()));
        tv_fuzai1.setText(dou5);
        String dou6 = MathUtil.get(Double.parseDouble(balEntity.getTotal_assets()));
        tv_fuzai2.setText(dou6);
        String dou7 = MathUtil.get(Double.parseDouble(balEntity.getTotal_current_liability()));
        tv_fuzai3.setText(dou7);
        String dou8 = MathUtil.get(Double.parseDouble(balEntity.getTotal_liability()));
        tv_fuzai4.setText(dou8);
        String dou9 = MathUtil.get(Double.parseDouble(balEntity.getTotal_shareholder_equity()));
        tv_fuzai5.setText(dou9);


        CompanyResponse.InfoEntity.CashEntity cashEntity = companyResponse.getInfo().getCash();
        String dou10 = MathUtil.get(Double.parseDouble(cashEntity.getNet_operate_cash_flow()));
        tv_xianjin1.setText(dou10);
        String dou11 = MathUtil.get(Double.parseDouble(cashEntity.getNet_invest_cash_flow()));
        tv_xianjin2.setText(dou11);
        String dou12 = MathUtil.get(Double.parseDouble(cashEntity.getNet_finance_cash_flow()));
        tv_xianjin3.setText(dou12);


    }

    public void setStockCode(String stockCode1) {
        if (!stockCode1.equals("")) {
            String str = stockCode1.substring(2);
            String str2 = stockCode1.substring(0, 2);
            if (str2.equals("sh") || str2.equals("sz")) {
                this.StockCode = str + "." + str2;
            } else {
                if (stockCode1.substring(0, 1).equals("1")) {
                    this.StockCode = stockCode1 + ".SZ";
                } else {
                    this.StockCode = stockCode1 + ".SS";
                }
            }
        }
    }
}
