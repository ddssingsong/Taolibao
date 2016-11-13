package com.jhs.taolibao.code.market.model;

/**
 * Created by dds on 2016/7/9.
 */
public interface FundModel {
    void getFundList(String StockID, String scn, String doa, String op, FundModelImpl.onGetFundAListListener listener);


    void getTaoliList(String StockID, String op, FundModelImpl.onGetFundAListListener listener);

    void getTaoliDetail(String op,String stockid,FundModelImpl.onGetTaoliDetailListener listener);
}
