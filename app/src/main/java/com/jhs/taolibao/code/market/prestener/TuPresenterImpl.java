package com.jhs.taolibao.code.market.prestener;

import com.jhs.taolibao.code.market.model.KlineModelImpl;
import com.jhs.taolibao.code.market.model.MinutehqmodelImpl;
import com.jhs.taolibao.code.market.view.KlineView;
import com.jhs.taolibao.code.market.view.MinuteHqView;
import com.jhs.taolibao.view.charts.DataParse;

/**
 * Created by dds on 2016/7/8.
 *
 * @TODO
 */
public class TuPresenterImpl implements TuPresenter {
    private MinuteHqView minuteHqView;
    private KlineView klineView;
    private MinutehqmodelImpl minutehqmodel;
    private KlineModelImpl klineModel;

    public TuPresenterImpl(KlineView klineView) {
        this.klineView = klineView;
        klineModel = new KlineModelImpl();
    }

    public TuPresenterImpl(MinuteHqView minuteHqView) {
        this.minuteHqView = minuteHqView;
        minutehqmodel = new MinutehqmodelImpl();
    }

    @Override
    public void getMinutehqData(String stockId) {
        minutehqmodel.GetMinutehqData(stockId, new MinutehqmodelImpl.onGetMinutehqDataListener() {
            @Override
            public void onSuccess(DataParse mData) {
                minuteHqView.getMinutehqDataSuccess(mData);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                minuteHqView.getMinutrhpDataFaild();
              //  ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void getKlineDate(String Type, String Stockid, String Start, String end) {
        klineModel.getKlineData(Type, Stockid, Start, end, new KlineModelImpl.ongetKlineDataListener() {
            @Override
            public void onSuccess(DataParse mData) {
                klineView.getKlineSuccess(mData);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                //ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
