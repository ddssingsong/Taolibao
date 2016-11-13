package com.jhs.taolibao.code.my.prestenter;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.my.model.BaobiModelImpl;
import com.jhs.taolibao.code.my.view.BaobiView;
import com.jhs.taolibao.code.user.model.UserModelImpl;
import com.jhs.taolibao.entity.Order;
import com.jhs.taolibao.entity.UserInfo;
import com.jhs.taolibao.utils.CalendarUtil;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.utils.ToastUtil;

/**
 * Created by dds on 2016/7/15.
 *
 * @TODO
 */
public class BaobiPresenterImpl implements BaobiPresenter {

    private BaobiView baobiView;
    private UserModelImpl userModel;
    private BaobiModelImpl baobiModel;

    public BaobiPresenterImpl(BaobiView baobiView) {
        this.baobiView = baobiView;
        userModel = new UserModelImpl();
        baobiModel = new BaobiModelImpl();
    }

    @Override
    public void GetuserInfo(String userid) {
        userModel.GetuserInfo(userid, new UserModelImpl.onGetUserInfoListener() {
            @Override
            public void onSuccess(UserInfo userinfo) {
                int totalpoint = userinfo.getUserTotalPoint();
                baobiView.getUserBalanceSuccess(totalpoint);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });

    }

    @Override
    public void GetOrderWaterTn(String userid, final int amount) {
        baobiModel.Pay(userid, amount, 2, 0, "充值", new BaobiModelImpl.onPayListener() {
            @Override
            public void onSuccess(final Order order) {
                baobiModel.getOrderWaterTn("juhesheng"+Integer.toString(order.getID()), CalendarUtil.getCurrentDate1(), amount, new BaobiModelImpl.onGetOrderWaterTnListener() {
                    @Override
                    public void onSuccess(String tn) {

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

            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }


}
