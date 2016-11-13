package com.jhs.taolibao.code.my.view;

import com.jhs.taolibao.entity.Jingu;

import java.util.List;

/**
 * Created by dds on 2016/7/27.
 *
 * @TODO
 */
public interface GoldView {
    void getGoldListSuccess(List<Jingu.DataEntity> list);
    void getGoldListFail();
}
