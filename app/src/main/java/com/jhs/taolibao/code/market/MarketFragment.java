package com.jhs.taolibao.code.market;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jhs.taolibao.R;
import com.jhs.taolibao.adapter.IndexAdapter;
import com.jhs.taolibao.adapter.StockRiseAdapter;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.base.BaseFragmentPagerAdapter;
import com.jhs.taolibao.base.recyclerview.OnItemClickListener;
import com.jhs.taolibao.code.market.prestener.IndexPresenerImpl;
import com.jhs.taolibao.code.market.prestener.StockPresenterImpl;
import com.jhs.taolibao.code.market.view.MarketMainView;
import com.jhs.taolibao.code.market.widget.IndustryPagerFragment;
import com.jhs.taolibao.entity.Index;
import com.jhs.taolibao.entity.Stock;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.view.XCircleIndicator;

import java.util.List;

/**
 * Created by dds on 2016/6/13.
 *
 * @TODO 行情界面
 */
public class MarketFragment extends BaseFragment implements MarketMainView, View.OnClickListener {
    private RecyclerView recycle_index;//顶部指数
    private IndexAdapter adapter;

    private ViewPager viewpager_industry;//中间滑动行业领涨股
    private BaseFragmentPagerAdapter pageradapter;

    private RecyclerView recycle_stock_rise;//底部涨跌幅榜
    private StockRiseAdapter adapter1;
    private RecyclerView recycle_stock_fall;
    private StockRiseAdapter adapter2;

    private IndexPresenerImpl indexPresener;
    private StockPresenterImpl stockPresenter;

    private RelativeLayout layout_industry_hot;//更多行业涨幅
    private RelativeLayout layout_stock_rise;//涨幅榜
    private RelativeLayout layout_stock_fall;//跌幅榜

    private XCircleIndicator indicator; //圆点指示器

    private SwipeRefreshLayout swiperefresh;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            indexPresener.GetIndexView();
            stockPresenter.GetRankingList();
            handler.postDelayed(this, 6000);//6秒刷新一次
        }

    };

    @Override
    protected boolean onBackPressed() {
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indexPresener = new IndexPresenerImpl(this);
        stockPresenter = new StockPresenterImpl(this);
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        recycle_index = (RecyclerView) rootView.findViewById(R.id.recycle_index);
        viewpager_industry = (ViewPager) rootView.findViewById(R.id.viewpager_industry);
        layout_industry_hot = (RelativeLayout) rootView.findViewById(R.id.layout_industry_hot);
        layout_stock_rise = (RelativeLayout) rootView.findViewById(R.id.layout_stock_rise);
        layout_stock_fall = (RelativeLayout) rootView.findViewById(R.id.layout_stock_fall);
        recycle_stock_rise = (RecyclerView) rootView.findViewById(R.id.recycle_stock_rise);
        recycle_stock_fall = (RecyclerView) rootView.findViewById(R.id.recycle_stock_fall);
        indicator = (XCircleIndicator) rootView.findViewById(R.id.indicator);
        swiperefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        layout_industry_hot.setOnClickListener(this);
        layout_stock_rise.setOnClickListener(this);
        layout_stock_fall.setOnClickListener(this);
        //添加圆点指示器
        indicator.initData(3, 0);
        indicator.setCurrentPage(0);
        viewpager_industry.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //解决滑动冲突
        viewpager_industry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        swiperefresh.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swiperefresh.setEnabled(true);
                        break;
                }
                return false;
            }
        });


        //顶部指数初始化
        adapter = new IndexAdapter(getActivity(), R.layout.item_top_index);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycle_index.setLayoutManager(linearLayoutManager);
        recycle_index.setAdapter(adapter);

        //涨幅榜
        adapter1 = new StockRiseAdapter(getActivity(), R.layout.item_stock_rise_fall);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_stock_rise.setLayoutManager(linearLayoutManager1);
        recycle_stock_rise.setAdapter(adapter1);

        //跌幅榜
        adapter2 = new StockRiseAdapter(getActivity(), R.layout.item_stock_rise_fall);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_stock_fall.setLayoutManager(linearLayoutManager2);
        recycle_stock_fall.setAdapter(adapter2);

        //中间行业涨幅
        pageradapter = new BaseFragmentPagerAdapter(getFragmentManager());
        viewpager_industry.setAdapter(pageradapter);
        //设置下拉刷新
        swiperefresh.setOnRefreshListener(listener);
    }

    SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            initData();
        }
    };

    @Override
    public void onInitloadData() {
        handler.postDelayed(runnable, 6000); //开始刷新
        pageradapter.addFragmentToAdapter(new IndustryPagerFragment().newInstance(1), false);
        pageradapter.addFragmentToAdapter(new IndustryPagerFragment().newInstance(2), false);
        pageradapter.addFragmentToAdapter(new IndustryPagerFragment().newInstance(3), false);
        initData();
    }

    private void initData() {
        DialogUtil.showProgress(getActivity(), "正在加载中");
        //获取数据
        indexPresener.GetIndexView();
        stockPresenter.GetRankingList();
    }


    @Override
    public void getIndexViewSuccess(List<Index> indexList) {
        adapter.addDataToAdapater(indexList, true);
        swiperefresh.setRefreshing(false);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Index index = (Index) o;
                String str = index.getIndexCode().substring(0, 2);
                String stockid = null;
                if (str.equals("sh")) {
                    stockid = index.getIndexCode().substring(2)+".SS";
                } else {
                    stockid = index.getIndexCode().substring(2)+".SZ";
                }
                startActivity(new Intent(getActivity(), StockDetailActivity.class).putExtra("stockid", stockid).putExtra("type", "index"));
            }
        });
    }

    @Override
    public void getRankingList(List<Stock> riseList, List<Stock> fallList) {
        adapter1.addDataToAdapater(riseList, true);
        adapter2.addDataToAdapater(fallList, true);
        swiperefresh.setRefreshing(false);


        adapter1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Stock index = (Stock) o;
                String stockid = index.getStockCode();
                startActivity(new Intent(getActivity(), StockDetailActivity.class).putExtra("stockid", stockid));
            }
        });

        adapter2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Stock index = (Stock) o;
                String stockid = index.getStockCode();
                startActivity(new Intent(getActivity(), StockDetailActivity.class).putExtra("stockid", stockid));
            }
        });
        DialogUtil.closeProgressDialog();

    }

    @Override
    public void getRankingFaild() {
        swiperefresh.setRefreshing(false);
        DialogUtil.closeProgressDialog();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.layout_industry_hot:
                //更多行业涨幅
                startActivity(new Intent(getActivity(), StockListActivity.class).putExtra("type", "industry1"));
                break;
            case R.id.layout_stock_rise:
                //涨幅榜
                startActivity(new Intent(getActivity(), StockListActivity.class).putExtra("type", "rise"));
                break;
            case R.id.layout_stock_fall:
                //跌幅榜
                startActivity(new Intent(getActivity(), StockListActivity.class).putExtra("type", "fall"));
                break;

        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
