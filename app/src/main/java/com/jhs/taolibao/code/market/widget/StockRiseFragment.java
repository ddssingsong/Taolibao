package com.jhs.taolibao.code.market.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.base.recyclerview.CommonAdapter;
import com.jhs.taolibao.base.recyclerview.OnItemClickListener;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.code.market.StockDetailActivity;
import com.jhs.taolibao.code.market.prestener.StockPresenterImpl;
import com.jhs.taolibao.code.market.view.StockRiseFallView;
import com.jhs.taolibao.entity.Stock;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.view.TitleBar;

import java.util.List;

/**
 * Created by dds on 2016/7/5.
 *
 * @TODO
 */
public class StockRiseFragment extends BaseFragment implements StockRiseFallView {
    private TitleBar titleBar;
    private RecyclerView recycle_stock_list;
    private CommonAdapter adapter;

    private StockPresenterImpl stockPresenter;

    private int type = 1;
    private SwipeRefreshLayout swiperefresh;

    @Override
    protected boolean onBackPressed() {
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockPresenter = new StockPresenterImpl(this);
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_industry_info, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        swiperefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        recycle_stock_list = (RecyclerView) rootView.findViewById(R.id.recycle_stock_list);
        titleBar = (TitleBar) rootView.findViewById(R.id.titlebar);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackToActivity(1, null);
            }
        });
        swiperefresh.setOnRefreshListener(listener);
    }

    SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            stockPresenter.GetRankListByType(type);
        }
    };

    @Override
    public void onInitloadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_stock_list.setLayoutManager(linearLayoutManager);
        adapter = new CommonAdapter<Stock>(getActivity(), R.layout.item_stock_rise_fall) {
            @Override
            public void convert(ViewHolder holder, Stock stock) {
                String stockname = stock.getStockName().replace("&nbsp;", "");
                holder.setText(R.id.tv_stock_name, stockname);
                String stockcode = stock.getStockCode().substring(2);
                holder.setText(R.id.tv_stock_code, stockcode);
                holder.setText(R.id.tv_stock_dealprice, stock.getDealPrice());
                double ratio = Double.parseDouble(stock.getChangeRate());

                if (ratio >= 0) {
                    holder.setTextColorRes(R.id.tv_stock_ratio, R.color.Red);
                    holder.setText(R.id.tv_stock_ratio, "+" + String.format("%.2f%%", ratio));
                } else {
                    holder.setTextColorRes(R.id.tv_stock_ratio, R.color.Olive);
                    holder.setText(R.id.tv_stock_ratio, String.format("%.2f%%", ratio));
                }
            }
        };
        recycle_stock_list.setAdapter(adapter);
        DialogUtil.showProgress(getActivity(), "正在加载中");
        if (type == 1) {
            titleBar.setTitle("涨幅榜");
        } else {
            titleBar.setTitle("跌幅榜");
        }

        stockPresenter.GetRankListByType(type);
    }

    @Override
    public void getRankListByTypeSuccess(List<Stock> stockList) {
        adapter.addDataToAdapater(stockList);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Stock index = (Stock) o;
                String stockid = index.getStockCode();
                startActivity(new Intent(getActivity(), StockDetailActivity.class).putExtra("stockid", stockid));
            }
        });
        DialogUtil.closeProgressDialog();
        swiperefresh.setRefreshing(false);
    }


    public void setType(int type) {
        this.type = type;
    }
}
