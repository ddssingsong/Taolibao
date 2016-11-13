package com.jhs.taolibao.code.market.view;

import com.jhs.taolibao.entity.Index;
import com.jhs.taolibao.entity.Stock;

import java.util.List;

/**
 * Created by dds on 2016/7/4.
 *
 * @TODO
 */
public interface MarketMainView {

    void getIndexViewSuccess(List<Index> indexList);
    void getRankingList(List<Stock> riseList, List<Stock> fallList);
    void getRankingFaild();
}
