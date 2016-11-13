package com.jhs.taolibao.code.market.prestener;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.market.model.GeStockModelImpl;
import com.jhs.taolibao.code.market.view.GeStockView;
import com.jhs.taolibao.entity.GeStock;
import com.jhs.taolibao.entity.Level5;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.utils.ToastUtil;

import java.util.List;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public class GeStockPresenterImpl implements GeStockPresenter {

    private GeStockModelImpl geStockModel;
    private GeStockView geStockView;


    public GeStockPresenterImpl(GeStockView geStockView) {
        this.geStockView = geStockView;
        geStockModel = new GeStockModelImpl();
    }

    @Override
    public void GetStcokInfo(String stockId) {
        geStockModel.GetStcokInfo(stockId, new GeStockModelImpl.onGetStcokInfoListener() {
            @Override
            public void onSuccess(GeStock geStock, List<Level5> buylist, List<Level5> salelist) {
                geStockView.getStcokInfoSuccess(geStock, buylist, salelist);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                DialogUtil.closeProgressDialog();
               // ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });

    }

    @Override
    public void InsetOptional(String userid, String stockId) {
        geStockModel.InsetOptional(userid, stockId, new GeStockModelImpl.onInsetOptionalListener() {
            @Override
            public void onSuccess(int optioid) {
                geStockView.InsetOptionalSuccess(optioid);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }



    @Override
    public void DelOptional(String OptionalId,int position) {
        geStockModel.DelOptional(OptionalId, position ,new GeStockModelImpl.onDelOptionalListener() {
            @Override
            public void onSuccess(int position1) {
                geStockView.DelOptionalSuccess();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void isSelect(String userid, String stockId) {
        geStockModel.isSelect(userid, stockId, new GeStockModelImpl.onisSelectListener() {
            @Override
            public void onSuccess(boolean isSelect,String optionalId) {
                geStockView.isSelect(isSelect,optionalId);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
