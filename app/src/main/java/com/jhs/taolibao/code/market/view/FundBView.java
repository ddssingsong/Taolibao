package com.jhs.taolibao.code.market.view;

import com.jhs.taolibao.entity.Fund;

import java.util.List;

/**
 * Created by dds on 2016/7/11.
 *
 * @TODO
 */
public interface FundBView {
    void getFundListSuccess(List<Fund> list);
    void showProgress();
    void hideProgress();
}
