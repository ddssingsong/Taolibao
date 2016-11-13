package com.jhs.taolibao.code.my.model;

/**
 * Created by dds on 2016/7/27.
 *
 * @TODO
 */
public interface GoldModel {
    void GetrecommendStock(String Time, GoldModelImpl.GetrecommendStockListener listener);
    void PayRemStockDate(String userID,GoldModelImpl.PayRemStockDateListener listener);
}
