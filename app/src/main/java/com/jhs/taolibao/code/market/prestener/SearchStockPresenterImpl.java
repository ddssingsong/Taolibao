package com.jhs.taolibao.code.market.prestener;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.market.model.GeStockModelImpl;
import com.jhs.taolibao.code.market.model.SearchStockModelImpl;
import com.jhs.taolibao.code.market.view.SearchView;
import com.jhs.taolibao.entity.SearchStock;
import com.jhs.taolibao.utils.ToastUtil;

import java.util.List;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public class SearchStockPresenterImpl implements SearchStockPresenter {
    private SearchView searchView;
    private SearchStockModelImpl searchStockModel;
    private GeStockModelImpl geStockModel;

    public SearchStockPresenterImpl(SearchView searchView) {
        this.searchView = searchView;
        searchStockModel = new SearchStockModelImpl();
        geStockModel=new GeStockModelImpl();
    }

    @Override
    public void GetBinefStock(String keyword) {
        searchStockModel.GetBinefStock(keyword, new SearchStockModelImpl.onGetBinefStockListener() {
            @Override
            public void onSuccess(List<SearchStock> stockList) {
                searchView.GetBinefStockSuccess(stockList);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void InsetOptional(String userId, String stockCode) {
        geStockModel.InsetOptional(userId, stockCode, new GeStockModelImpl.onInsetOptionalListener() {
            @Override
            public void onSuccess(int optionId) {
                searchView.InsetOptionalSuccess();
            }

            @Override
            public void onFailure(String msg, Exception e) {

                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
