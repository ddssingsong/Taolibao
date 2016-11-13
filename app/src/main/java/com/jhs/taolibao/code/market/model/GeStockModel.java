package com.jhs.taolibao.code.market.model;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public interface GeStockModel {
    void GetStcokInfo(String stockId, GeStockModelImpl.onGetStcokInfoListener listener);
    void InsetOptional(String userid,String stockId,GeStockModelImpl.onInsetOptionalListener listener);//添加自选
    void DelOptional(String OptionalId,int position,GeStockModelImpl.onDelOptionalListener listener);//移除自选
    void isSelect(String userid,String stockId,GeStockModelImpl.onisSelectListener listener);//判断是否自选
}
