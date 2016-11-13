package com.jhs.taolibao.code.my.prestenter;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.my.model.CheckInModelImpl;
import com.jhs.taolibao.code.my.view.CheckInView;
import com.jhs.taolibao.entity.CheckInInfo;
import com.jhs.taolibao.utils.ToastUtil;

/**
 * Created by dds on 2016/7/25.
 *
 * @TODO
 */
public class CheckInPresenterImpl implements CheckInPresenter {
    private CheckInView checkInView;
    private CheckInModelImpl checkInModel;

    public CheckInPresenterImpl(CheckInView checkInView) {
        this.checkInView = checkInView;
        checkInModel = new CheckInModelImpl();
    }

    @Override
    public void CheckIn(String userId) {
        checkInModel.CheckIn(userId, new CheckInModelImpl.onCheckInListener() {
            @Override
            public void onSuccess(CheckInInfo checkInInfo) {
                if (checkInInfo.getCode() == 0) {
                    checkInView.CheckinSuccess(checkInInfo);
                } else {
                    checkInView.onHavingCheckIn(checkInInfo);
                }

            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });

    }
}
