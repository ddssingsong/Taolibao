package com.jhs.taolibao.code.market.prestener;

import com.jhs.taolibao.code.market.model.IndexModelImpl;
import com.jhs.taolibao.code.market.view.MarketMainView;
import com.jhs.taolibao.code.market.view.MyStockView;
import com.jhs.taolibao.entity.Index;

import java.util.List;

/**
 * Created by dds on 2016/7/4.
 *
 * @TODO
 */
public class IndexPresenerImpl implements IndexPresener {
    private MarketMainView marketMainView;
    private MyStockView myStockView;
    private IndexModelImpl indexModel;

    public IndexPresenerImpl(MarketMainView marketMainView) {
        this.marketMainView = marketMainView;
        indexModel = new IndexModelImpl();
    }
    public IndexPresenerImpl(MyStockView myStockView) {
        this.myStockView = myStockView;
        indexModel = new IndexModelImpl();
    }

    @Override
    public void GetIndexView() {
        indexModel.GetIndexView(new IndexModelImpl.onGetIndexViewListener() {
            @Override
            public void onSuccess(List<Index> indexlist) {
                marketMainView.getIndexViewSuccess(indexlist);
            }

            @Override
            public void onFailure(String msg, Exception e) {
               // ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void GetIndexView1() {
        indexModel.GetIndexView(new IndexModelImpl.onGetIndexViewListener() {
            @Override
            public void onSuccess(List<Index> indexlist) {
                myStockView.getIndexViewSuccess(indexlist);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                //ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
