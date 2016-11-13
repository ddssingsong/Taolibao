package com.jhs.taolibao.code.my.prestenter;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.my.model.BaobiModelImpl;
import com.jhs.taolibao.code.my.view.ExchangeView;
import com.jhs.taolibao.code.user.model.UserModelImpl;
import com.jhs.taolibao.entity.UserInfo;
import com.jhs.taolibao.utils.ToastUtil;

/**
 * Created by dds on 2016/7/20.
 *
 * @TODO
 */
public class ExchangePresenterImpl implements ExchangePresenter {

    private ExchangeView exchangeView;
    private UserModelImpl userModel;
    private BaobiModelImpl baobiModel;

    public ExchangePresenterImpl(ExchangeView exchangeView) {
        this.exchangeView = exchangeView;
        userModel = new UserModelImpl();
        baobiModel = new BaobiModelImpl();
    }

    @Override
    public void GetuserInfo(String userid) {
        userModel.GetuserInfo(userid, new UserModelImpl.onGetUserInfoListener() {
            @Override
            public void onSuccess(UserInfo userinfo) {
                int totalpoint = userinfo.getUserTotalPoint();
                exchangeView.getUserBalanceSuccess(totalpoint);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });

    }

    @Override
    public void ExchangeJingdong(String userid, int amount) {
        baobiModel.exchangeJingdong(userid, 1, amount, 0, new BaobiModelImpl.onExchangeJingdongListener() {
            @Override
            public void onSuccess() {
                exchangeView.changeSuccess();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                exchangeView.changeFaided();
            }
        });
    }
}
