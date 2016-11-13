package com.jhs.taolibao.code.market;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jhs.taolibao.R;
import com.jhs.taolibao.adapter.IndexAdapter;
import com.jhs.taolibao.adapter.MyStockAdapter;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.base.recyclerview.OnItemClickListener;
import com.jhs.taolibao.base.recyclerview.OnItemLongClickListener;
import com.jhs.taolibao.code.market.prestener.IndexPresenerImpl;
import com.jhs.taolibao.code.market.prestener.StockPresenterImpl;
import com.jhs.taolibao.code.market.view.MyStockView;
import com.jhs.taolibao.code.user.LoginAndRegitActivity;
import com.jhs.taolibao.entity.Index;
import com.jhs.taolibao.entity.MyStock;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.utils.ToastUtil;
import com.jhs.taolibao.view.TitleBar;

import java.util.List;

/**
 * Created by dds on 2016/6/13.
 *
 * @TODO 中间的
 */
public class MyStockFragment extends BaseFragment implements MyStockView, View.OnClickListener {

    private TitleBar titleBar;
    private RecyclerView recycle_index;//顶部指数
    private IndexAdapter adapter;
    private IndexPresenerImpl indexPresener;

    private RelativeLayout layout_no_stock;//自选股票列表
    private RecyclerView recycle_stock_list;
    private MyStockAdapter adapter1;
    private StockPresenterImpl stockPresenter;

    private int count;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            indexPresener.GetIndexView1();
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
        View view = inflater.inflate(R.layout.fragment_taoli, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        titleBar = (TitleBar) rootView.findViewById(R.id.titlebar);
        recycle_index = (RecyclerView) rootView.findViewById(R.id.recycle_index);
        layout_no_stock = (RelativeLayout) rootView.findViewById(R.id.layout_no_stock);
        recycle_stock_list = (RecyclerView) rootView.findViewById(R.id.recycle_stock_list);
        //跳转到搜索界面
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), SearchActivity.class), 1);
            }
        });
        layout_no_stock.setOnClickListener(this);

        //顶部指数初始化
        adapter = new IndexAdapter(getActivity(), R.layout.item_top_index);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycle_index.setLayoutManager(linearLayoutManager);
        recycle_index.setItemAnimator(new DefaultItemAnimator());
        recycle_index.setAdapter(adapter);
        //自选股票列表初始化
        adapter1 = new MyStockAdapter(getActivity(), R.layout.item_stock_rise_fall);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_stock_list.setLayoutManager(linearLayoutManager1);
        recycle_stock_list.setAdapter(adapter1);
        //获取顶部指数
        indexPresener.GetIndexView1();
    }

    @Override
    public void onInitloadData() {
        handler.postDelayed(runnable,6000); //开始刷新
    }


    @Override
    public void onResume() {
        super.onResume();
        //获取自选列表
        if (UserInfoSingleton.getUserInfo() != null) {
            stockPresenter.GetOptionalByUser(UserInfoSingleton.getUserId());
            recycle_stock_list.setVisibility(View.VISIBLE);
            layout_no_stock.setVisibility(View.GONE);
        }
    }

    @Override
    public void getIndexViewSuccess(List<Index> indexList) {
        adapter.addDataToAdapater(indexList, true);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Index index = (Index) o;
                String stockid = index.getIndexCode();
                startActivity(new Intent(getActivity(), StockDetailActivity.class).putExtra("stockid", stockid).putExtra("type","index"));
            }
        });
    }

    @Override
    public void getOptionalByUserSuccess(List<MyStock> stockList) {
        count = stockList.size();
        if (stockList.size() == 0) {
            layout_no_stock.setVisibility(View.VISIBLE);
            recycle_stock_list.setVisibility(View.INVISIBLE);
        } else {
            layout_no_stock.setVisibility(View.GONE);
            recycle_stock_list.setVisibility(View.VISIBLE);
        }
        adapter1.addDataToAdapater(stockList, true);

        DialogUtil.closeProgressDialog();
        adapter1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                MyStock index = (MyStock) o;
                String stockid = index.getStockCode();
                startActivity(new Intent(getActivity(), StockDetailActivity.class).putExtra("stockid", stockid));
            }
        });
        adapter1.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, final Object o, final int position) {

                DialogUtil.alertDialogTips(getActivity(), "请您确认", "您确定要移除这只股票吗？", new DialogUtil.ICommonCallBack() {
                    @Override
                    public void excute(Object object) {
                        MyStock index = (MyStock) o;
                        int optionalId = index.getId();
                        stockPresenter.DelOptional(Integer.toString(optionalId), position);
                    }
                }, null);
                return true;
            }
        });
    }

    @Override
    public void getOptionalByUserNull() {
        layout_no_stock.setVisibility(View.VISIBLE);
        recycle_stock_list.setVisibility(View.INVISIBLE);
    }

    @Override
    public void deletOptionalSuccess(int position) {
        ToastUtil.showToast(getActivity(), "移除成功");
        adapter1.deleteDataToAdapater(position);
        count--;
        if (count <= 0) {
            layout_no_stock.setVisibility(View.VISIBLE);
            recycle_stock_list.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_no_stock:
                //添加自选
                if (UserInfoSingleton.getUserInfo() != null) {
                    startActivityForResult(new Intent(getActivity(), SearchActivity.class), 1);
                } else {
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }
                break;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (UserInfoSingleton.getUserInfo() == null || count <= 0) {
                layout_no_stock.setVisibility(View.VISIBLE);
                recycle_stock_list.setVisibility(View.GONE);
            } else {
                onResume();
            }
        }
    }


}
