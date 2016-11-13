package com.jhs.taolibao.code.my.prestenter;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.my.model.GoldModelImpl;
import com.jhs.taolibao.code.my.view.MyView;
import com.jhs.taolibao.code.user.model.UserModelImpl;
import com.jhs.taolibao.entity.UserInfo;
import com.jhs.taolibao.utils.ToastUtil;

/**
 * Created by dds on 2016/7/27.
 *
 * @TODO
 */
public class MyPresenterimpl implements MyPresenter {

    private MyView myView;
    private UserModelImpl userModel;
    private GoldModelImpl goldModel;

    public MyPresenterimpl(MyView myView) {
        this.myView = myView;
        userModel = new UserModelImpl();
        goldModel = new GoldModelImpl();
    }

    @Override
    public void GetuserInfo(String userid) {
        userModel.GetuserInfo(userid, new UserModelImpl.onGetUserInfoListener() {
            @Override
            public void onSuccess(UserInfo userinfo) {
                myView.getUserBalanceSuccess(userinfo);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void PayRemStockDate(String userid) {
        goldModel.PayRemStockDate(userid, new GoldModelImpl.PayRemStockDateListener() {
            @Override
            public void onSuccess() {
                myView.payGoldSuccess();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
