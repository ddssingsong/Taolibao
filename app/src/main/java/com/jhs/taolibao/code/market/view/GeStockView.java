package com.jhs.taolibao.code.market.view;

import com.jhs.taolibao.entity.GeStock;
import com.jhs.taolibao.entity.Level5;

import java.util.List;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public interface GeStockView {
    void getStcokInfoSuccess(GeStock geStock, List<Level5> buylist, List<Level5> salelist);
    void InsetOptionalSuccess(int optionId);//添加自选
    void DelOptionalSuccess();//移除自选
    void isSelect(boolean isSelect,String optionalId);//判断是否自选

}
