package com.jhs.taolibao.code.market.prestener;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public interface GeStockPresenter {
    void GetStcokInfo(String stockId);
    void InsetOptional(String userid,String stockId);//添加自选
    void DelOptional(String OptionalId,int position);//移除自选
    void isSelect(String userid,String stockId);//判断是否自选
}
