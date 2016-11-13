package com.jhs.taolibao.code.market.prestener;

/**
 * Created by dds on 2016/7/8.
 *
 * @TODO 分时和K线图的连接
 */
public interface TuPresenter {
    void getMinutehqData(String stockId);
    void getKlineDate(String Type,String Stockid,String Start,String end);
}
