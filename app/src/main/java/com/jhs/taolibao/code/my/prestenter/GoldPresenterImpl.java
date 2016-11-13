package com.jhs.taolibao.code.my.prestenter;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.my.model.GoldModelImpl;
import com.jhs.taolibao.code.my.view.GoldView;
import com.jhs.taolibao.entity.Jingu;
import com.jhs.taolibao.utils.CalendarUtil;
import com.jhs.taolibao.utils.ToastUtil;

import java.util.List;

/**
 * Created by dds on 2016/7/27.
 *
 * @TODO
 */
public class GoldPresenterImpl implements GoldPresenter {

    private GoldView goldView;
    private GoldModelImpl goldModel;

    public GoldPresenterImpl(GoldView goldView) {
        this.goldView = goldView;
        goldModel = new GoldModelImpl();
    }

    @Override
    public void getGoldList() {
        String time = CalendarUtil.getCurrentDate("yyyy-MM-dd");
        goldModel.GetrecommendStock(time, new GoldModelImpl.GetrecommendStockListener() {
            @Override
            public void onSuccess(Jingu jingu) {
                List<Jingu.DataEntity> list = jingu.getData();
                goldView.getGoldListSuccess(list);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
                goldView.getGoldListFail();
            }
        });

    }
}
