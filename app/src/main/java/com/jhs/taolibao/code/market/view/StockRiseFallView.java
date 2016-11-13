package com.jhs.taolibao.code.market.view;

import com.jhs.taolibao.entity.Stock;

import java.util.List;

/**
 * Created by dds on 2016/7/5.
 *
 * @TODO
 */
public interface StockRiseFallView {
    void getRankListByTypeSuccess(List<Stock> stockList);
}
