package com.jhs.taolibao.code.market.prestener;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.market.model.FundModelImpl;
import com.jhs.taolibao.code.market.view.FundAView;
import com.jhs.taolibao.code.market.view.FundBView;
import com.jhs.taolibao.code.market.view.FundDetailView;
import com.jhs.taolibao.code.market.view.FundMView;
import com.jhs.taolibao.code.market.view.FundTaoliView;
import com.jhs.taolibao.entity.Fund;
import com.jhs.taolibao.utils.ToastUtil;

import java.util.List;

/**
 * Created by dds on 2016/7/9.
 */
public class FundPresenterImpl implements FundPresenter {
    private FundAView fundAView;
    private FundBView fundBView;
    private FundMView fundMView;
    private FundTaoliView fundTaoli;
    private FundModelImpl fundModel;
    private FundDetailView fundDetailView;

    public FundPresenterImpl(FundDetailView fundDetailView) {
        this.fundDetailView = fundDetailView;
        fundModel = new FundModelImpl();
    }

    public FundPresenterImpl(FundAView fundAView) {
        this.fundAView = fundAView;
        fundModel = new FundModelImpl();
    }

    public FundPresenterImpl(FundBView fundBView) {
        this.fundBView = fundBView;
        fundModel = new FundModelImpl();
    }

    public FundPresenterImpl(FundMView fundMView) {
        this.fundMView = fundMView;
        fundModel = new FundModelImpl();
    }

    public FundPresenterImpl(FundTaoliView fundTaoli) {
        this.fundTaoli = fundTaoli;
        fundModel = new FundModelImpl();
    }

    @Override
    public void getFundAList(String StockID, String scn, String doa, boolean isfirst) {
        String op = "fja";
        if (isfirst) {
            fundAView.showProgress();
        }
        fundModel.getFundList(StockID, scn, doa, op, new FundModelImpl.onGetFundAListListener() {
            @Override
            public void onSuccess(List<Fund> list) {
                fundAView.getFundListSuccess(list);
                fundAView.hideProgress();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                fundAView.hideProgress();
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void getFundBList(String StockID, String scn, String doa, boolean isfirst) {
        String op = "fjb";
        if (isfirst) {
            fundBView.showProgress();
        }
        fundModel.getFundList(StockID, scn, doa, op, new FundModelImpl.onGetFundAListListener() {
            @Override
            public void onSuccess(List<Fund> list) {
                fundBView.getFundListSuccess(list);
                fundBView.hideProgress();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
                fundBView.hideProgress();
            }
        });
    }

    @Override
    public void getFundMList(String StockID, String scn, String doa, boolean isfirst) {
        String op = "fjm";
        if (isfirst) {
            fundMView.showProgress();
        }
        fundModel.getFundList(StockID, scn, doa, op, new FundModelImpl.onGetFundAListListener() {
            @Override
            public void onSuccess(List<Fund> list) {
                fundMView.getFundListSuccess(list);
                fundMView.hideProgress();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
                fundMView.hideProgress();
            }
        });
    }

    @Override
    public void getFundTaoli(String StockID, boolean isfirst) {
        String op = "tlb";
        if (isfirst) {
            fundTaoli.showProgress();
        }
        fundModel.getTaoliList(StockID, op, new FundModelImpl.onGetFundAListListener() {
            @Override
            public void onSuccess(List<Fund> list) {
                fundTaoli.getFundListSuccess(list);
                fundTaoli.hideProgress();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
                fundTaoli.hideProgress();
            }
        });
    }

    @Override
    public void getFundDetail(String StockID) {
        String op = "fjamsb";
        fundModel.getTaoliDetail(op, StockID, new FundModelImpl.onGetTaoliDetailListener() {
            @Override
            public void onSuccess(Fund fund) {
                fundDetailView.getFundDetailSuccess(fund);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
