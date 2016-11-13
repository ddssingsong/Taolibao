package com.jhs.taolibao.code.market.prestener;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.market.model.GeStockModelImpl;
import com.jhs.taolibao.code.market.model.StockModelImpl;
import com.jhs.taolibao.code.market.view.IndustryInfoView;
import com.jhs.taolibao.code.market.view.MarketMainView;
import com.jhs.taolibao.code.market.view.MyStockView;
import com.jhs.taolibao.code.market.view.StockRiseFallView;
import com.jhs.taolibao.entity.MyStock;
import com.jhs.taolibao.entity.Stock;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.utils.ToastUtil;

import java.util.List;

/**
 * Created by dds on 2016/7/5.
 *
 * @TODO
 */
public class StockPresenterImpl implements StockPresenter {
    private MarketMainView mainView;
    private IndustryInfoView industryInfoView;
    private StockRiseFallView stockRiseFallView;
    private MyStockView myStockView;
    private StockModelImpl stockModel;

    private GeStockModelImpl geStockModel;

    public StockPresenterImpl(MarketMainView mainView) {
        this.mainView = mainView;
        stockModel = new StockModelImpl();

    }

    public StockPresenterImpl(IndustryInfoView industryInfoView) {
        this.industryInfoView = industryInfoView;
        stockModel = new StockModelImpl();

    }

    public StockPresenterImpl(StockRiseFallView stockRiseFallView) {
        this.stockRiseFallView = stockRiseFallView;
        stockModel = new StockModelImpl();

    }

    public StockPresenterImpl(MyStockView myStockView) {
        this.myStockView = myStockView;
        stockModel = new StockModelImpl();
        geStockModel = new GeStockModelImpl();

    }

    //获取涨跌幅排行榜
    @Override
    public void GetRankingList() {
        stockModel.GetRankingList(new StockModelImpl.onGetRankingListListener() {
            @Override
            public void onSuccess(List<Stock> riseList, List<Stock> fallList) {
                mainView.getRankingList(riseList, fallList);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mainView.getRankingFaild();

            }
        });

    }

    //根据行业获取股票列表
    @Override
    public void GetStockByIndustry(int page, int size, String order, String sortby, String inustry) {
        stockModel.GetStockByIndustry(page, size, order, sortby, inustry, new StockModelImpl.onGetStockByIndustryListener() {
            @Override
            public void onSuccess(List<Stock> stockList) {
                industryInfoView.GetStockByIndustrysuccess(stockList);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                // ToastUtil.showToast(BaseApplication.getApplication(), msg);
                DialogUtil.closeProgressDialog();
            }
        });
    }

    //获取更多涨跌幅
    @Override
    public void GetRankListByType(int type) {
        stockModel.GetRankListByType(type, new StockModelImpl.onGetRankListByTypeListener() {
            @Override
            public void onSuccess(List<Stock> stockList) {
                stockRiseFallView.getRankListByTypeSuccess(stockList);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                DialogUtil.closeProgressDialog();
               // ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }


    //通过用户id获取自选股票
    @Override
    public void GetOptionalByUser(String userid) {
        stockModel.GetOptionalByUser(userid, new StockModelImpl.onGetOptionalByUserListener() {
            @Override
            public void onSuccess(List<MyStock> stockList) {
                myStockView.getOptionalByUserSuccess(stockList);

            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (msg.equals("null")) {
                    myStockView.getOptionalByUserNull();
                } else {
                    ToastUtil.showToast(BaseApplication.getApplication(), msg);
                }

            }
        });
    }

    @Override
    public void InsetOptional(String userId, String stockCode) {

    }

    @Override
    public void DelOptional(String optinalID, int position) {
        geStockModel.DelOptional(optinalID, position, new GeStockModelImpl.onDelOptionalListener() {
            @Override
            public void onSuccess(int postion1) {
                myStockView.deletOptionalSuccess(postion1);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
