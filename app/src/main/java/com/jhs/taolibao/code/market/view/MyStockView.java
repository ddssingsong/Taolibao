package com.jhs.taolibao.code.market.view;

import com.jhs.taolibao.entity.Index;
import com.jhs.taolibao.entity.MyStock;

import java.util.List;

/**
 * Created by dds on 2016/7/5.
 *
 * @TODO
 */
public interface MyStockView {
    void getIndexViewSuccess(List<Index> indexList);


    void getOptionalByUserSuccess(List<MyStock> stockList);
    void getOptionalByUserNull();

    void deletOptionalSuccess(int position);
}
