package com.jhs.taolibao.code.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.base.recyclerview.CommonAdapter;
import com.jhs.taolibao.base.recyclerview.OnItemClickListener;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.code.market.StockDetailActivity;
import com.jhs.taolibao.code.my.prestenter.GoldPresenterImpl;
import com.jhs.taolibao.code.my.view.GoldView;
import com.jhs.taolibao.entity.Jingu;
import com.jhs.taolibao.utils.StatusBarUtil;
import com.jhs.taolibao.view.TitleBar;

import java.util.List;

/**
 * 每日金股
 */
public class GoldActivity extends BaseActivity implements GoldView {
    private TitleBar titleBar;
    private RecyclerView recycle_jingu;
    private CommonAdapter adapter;
    private GoldPresenterImpl goldpresenter;
    private SwipeRefreshLayout swiperefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(GoldActivity.this);
        goldpresenter = new GoldPresenterImpl(this);
        setContentView(R.layout.activity_gold);

        iniView();
        initData();


    }


    private void iniView() {
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        titleBar = (TitleBar) findViewById(R.id.titlebar);
        recycle_jingu = (RecyclerView) findViewById(R.id.recycle_jingu);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void initData() {
        swiperefresh.setOnRefreshListener(listener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_jingu.setLayoutManager(linearLayoutManager);
        adapter = new CommonAdapter<Jingu.DataEntity>(this, R.layout.item_jingu) {
            @Override
            public void convert(ViewHolder holder, Jingu.DataEntity jingu) {
                holder.setText(R.id.tv_gold_name, jingu.getStockName());
                holder.setText(R.id.tv_gold_code, jingu.getStockCode());
            }
        };
        recycle_jingu.setAdapter(adapter);

        goldpresenter.getGoldList();
    }

    SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            goldpresenter.getGoldList();
        }
    };

    @Override
    public void getGoldListSuccess(List<Jingu.DataEntity> list) {
        adapter.addDataToAdapater(list, true);
        swiperefresh.setRefreshing(false);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Jingu.DataEntity stock = (Jingu.DataEntity) o;
                String stockid = stock.getStockCode();
                startActivity(new Intent(GoldActivity.this, StockDetailActivity.class).putExtra("stockid", stockid));
            }
        });
    }

    @Override
    public void getGoldListFail() {
        swiperefresh.setRefreshing(false);
    }
}
