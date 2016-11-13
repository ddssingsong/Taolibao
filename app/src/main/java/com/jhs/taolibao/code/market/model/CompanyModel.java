package com.jhs.taolibao.code.market.model;

/**
 * Created by dds on 2016/7/13.
 *
 * @TODO
 */
public interface CompanyModel {
    void GetTockCompany(String StockCode,CompanyModelImpl.onGetTockCompanyListener listener);
}
