package com.jhs.taolibao.code.market.view;

import com.jhs.taolibao.entity.SearchStock;

import java.util.List;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public interface SearchView {
    void GetBinefStockSuccess(List<SearchStock> list);
    void InsetOptionalSuccess();
}
