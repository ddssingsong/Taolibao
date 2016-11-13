package com.jhs.taolibao.code.my.prestenter;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.my.model.BaobiModelImpl;
import com.jhs.taolibao.code.my.view.PaySelectView;
import com.jhs.taolibao.entity.Order;
import com.jhs.taolibao.utils.CalendarUtil;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.utils.ToastUtil;

/**
 * Created by dds on 2016/8/29.
 *
 * @TODO
 */
public class PaySelectPresenterImpl implements PaySelectPresenter {
    private PaySelectView paySelectView;
    private BaobiModelImpl baobiModel;


    public PaySelectPresenterImpl(PaySelectView paySelectView) {
        this.paySelectView = paySelectView;
        baobiModel = new BaobiModelImpl();
    }


    @Override
    public void GetOrderWaterTn(String userid, final int amount) {
        baobiModel.Pay(userid, amount, 2, 0, "充值", new BaobiModelImpl.onPayListener() {
            @Override
            public void onSuccess(final Order order) {
                baobiModel.getOrderWaterTn("juhesheng" + Integer.toString(order.getID()), CalendarUtil.getCurrentDate1(), amount, new BaobiModelImpl.onGetOrderWaterTnListener() {
                    @Override
                    public void onSuccess(String tn) {
                        paySelectView.getTnSuccess(tn, order.getID());
                    }

                    @Override
                    public void onFailure(String msg, Exception e) {
                        DialogUtil.closeProgressDialog();
                        ToastUtil.showToast(BaseApplication.getApplication(), msg);
                    }
                });
            }

            @Override
            public void onFailure(String msg, Exception e) {
                DialogUtil.closeProgressDialog();
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void notifyAddBaobi(String id) {
        baobiModel.notifyAddBaobi(id, new BaobiModelImpl.onotifyAddBaobiListener() {
            @Override
            public void onSuccess() {
                paySelectView.notifyAddBaobisuccess();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
