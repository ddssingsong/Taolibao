package com.jhs.taolibao.code.market.prestener;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.market.model.CompanyModelImpl;
import com.jhs.taolibao.code.market.view.CompanyView;
import com.jhs.taolibao.entity.CompanyResponse;
import com.jhs.taolibao.utils.ToastUtil;

/**
 * Created by dds on 2016/7/13.
 *
 * @TODO
 */
public class CompanyPresenterImpl implements CompanyPresenter {
    private CompanyView companyView;
    private CompanyModelImpl companyModel;

    public CompanyPresenterImpl(CompanyView companyView) {
        this.companyView = companyView;
        companyModel = new CompanyModelImpl();
    }

    @Override
    public void getCompanyInfo(String stockCode) {
        companyModel.GetTockCompany(stockCode, new CompanyModelImpl.onGetTockCompanyListener() {
            @Override
            public void onSuccess(CompanyResponse response) {
                companyView.getCompanySuccess(response);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
