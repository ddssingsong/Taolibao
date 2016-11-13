package com.jhs.taolibao.code.market.model;

/**
 * Created by dds on 2016/7/19.
 *
 * @TODO
 */
public interface KlineModel {
    void getKlineData(String Type,String Stockid,String Start,String end,KlineModelImpl.ongetKlineDataListener listener);
}
