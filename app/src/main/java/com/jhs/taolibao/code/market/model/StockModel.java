package com.jhs.taolibao.code.market.model;

/**
 * Created by dds on 2016/7/5.
 *
 * @TODO
 */
public interface StockModel {
    void GetRankingList(StockModelImpl.onGetRankingListListener listener);

    void GetStockByIndustry(int page, int size, String order, String sortby, String inustry, StockModelImpl.onGetStockByIndustryListener listener);

    void GetRankListByType(int type, StockModelImpl.onGetRankListByTypeListener listener);

    void GetOptionalByUser(String userid,StockModelImpl.onGetOptionalByUserListener listener);

}
