package com.jhs.taolibao.code.market.prestener;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public interface SearchStockPresenter {
    void GetBinefStock(String keyword);
    void InsetOptional(String userId, String stockCode);//添加自选
}
