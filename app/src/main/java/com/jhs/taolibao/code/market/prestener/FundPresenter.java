package com.jhs.taolibao.code.market.prestener;

/**
 * Created by dds on 2016/7/9.
 */
public interface FundPresenter {
    void getFundAList(String StockID, String scn, String doa,boolean isfirst);
    void getFundBList(String StockID, String scn, String doa,boolean isfirst);
    void getFundMList(String StockID, String scn, String doa,boolean isfirst);
    void getFundTaoli(String StockID,boolean isfirst);

    void getFundDetail(String StockID);

}
