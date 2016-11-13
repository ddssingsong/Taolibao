package com.jhs.taolibao.code.market.prestener;

/**
 * Created by dds on 2016/7/5.
 *
 * @TODO
 */
public interface StockPresenter {
    void GetRankingList();

    void GetStockByIndustry(int page, int size, String order, String sortby, String inustry);

    void GetRankListByType(int type);

    void GetOptionalByUser(String userid);//获取自选

    void InsetOptional(String userId, String stockCode);//添加自选

    void DelOptional(String optinalID,int position);//移除自选


}
