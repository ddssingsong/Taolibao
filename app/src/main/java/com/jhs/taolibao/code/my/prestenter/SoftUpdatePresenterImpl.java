package com.jhs.taolibao.code.my.prestenter;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.my.model.SoftUpdateModelImpl;
import com.jhs.taolibao.code.my.view.UpdateView;
import com.jhs.taolibao.entity.VersionInfo;
import com.jhs.taolibao.utils.ToastUtil;

/**
 * Created by dds on 2016/7/18.
 *
 * @TODO
 */
public class SoftUpdatePresenterImpl implements SoftUpdatePresenter {
    private UpdateView updateView;
    private SoftUpdateModelImpl softUpdateModel;

    public SoftUpdatePresenterImpl(UpdateView updateView) {
        this.updateView = updateView;
        softUpdateModel=new SoftUpdateModelImpl();
    }

    @Override
    public void getVersionCode() {
        softUpdateModel.getVersionInfo(new SoftUpdateModelImpl.onGetVersionCodeListener() {
            @Override
            public void onSuccess(VersionInfo versionInfo) {
                if (versionInfo.getVerCode() > BaseApplication.getVersionCode()) {
                    updateView.needUpdate(versionInfo);
                } else {
                    updateView.dontneedUpdate();
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

}
