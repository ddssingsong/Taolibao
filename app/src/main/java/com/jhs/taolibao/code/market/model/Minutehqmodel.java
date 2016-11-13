package com.jhs.taolibao.code.market.model;

/**
 * dds
 *
 * @TODO 分时图获取数据
 */
public interface Minutehqmodel {
    void GetMinutehqData(String StockID,MinutehqmodelImpl.onGetMinutehqDataListener listener);

}
