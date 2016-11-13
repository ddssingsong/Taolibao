package com.jhs.taolibao.code.market.model;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public interface SearchStockModel {
    void GetBinefStock(String keyword, SearchStockModelImpl.onGetBinefStockListener listener);
}
