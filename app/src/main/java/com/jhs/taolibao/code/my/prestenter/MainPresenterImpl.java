package com.jhs.taolibao.code.my.prestenter;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.my.model.SoftUpdateModelImpl;
import com.jhs.taolibao.code.my.view.MainView;
import com.jhs.taolibao.entity.VersionInfo;

/**
 * Created by dds on 2016/7/21.
 *
 * @TODO
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private SoftUpdateModelImpl softUpdateModel;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        softUpdateModel = new SoftUpdateModelImpl();
    }

    @Override
    public void getVersionCode() {
        softUpdateModel.getVersionInfo(new SoftUpdateModelImpl.onGetVersionCodeListener() {
            @Override
            public void onSuccess(VersionInfo versionInfo) {
                if (versionInfo.getVerCode() > BaseApplication.getVersionCode()) {
                    mainView.needUpdate(versionInfo);
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                //ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
