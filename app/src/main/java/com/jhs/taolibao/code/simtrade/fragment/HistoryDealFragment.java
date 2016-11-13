package com.jhs.taolibao.code.simtrade.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.SimtradeCenter;
import com.jhs.taolibao.code.simtrade.adapter.HistoryDealAdapter;
import com.jhs.taolibao.code.simtrade.base.BaseFragment2;
import com.jhs.taolibao.code.simtrade.entity.Stock;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.code.simtrade.utils.HscloudUtils;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshBase;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by KANGXIANGTAO on 2016/7/7.
 */
public class HistoryDealFragment extends BaseFragment2 {

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private LinearLayout ll;
    private List<Stock> datasList = new ArrayList<>();
    private String entrust_bs;

    private static final String TAG = "HistoryDealFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historydeal, null);
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        showProgressDialog();
        initData();
        initView();
        setListener();
    }

    private void setListener() {
//        pullToRefreshListView.setScrollLoadEnabled(false);
//        pullToRefreshListView.setPullLoadEnabled(false);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
                String s = DateUtils.getTimestampString(new Date(System.currentTimeMillis()));
                pullToRefreshListView.setLastUpdatedLabel(s);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                showToast("上拉加载");
//                pullToRefreshListView.onPullUpRefreshComplete();
            }
        });
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMyy");
        final String dateStr = format.format(DateUtils.getCurrentTime());
        calendar.add(Calendar.DATE, -30);
        String dateStr1 = format.format(calendar.getTime());
        try {
            SimtradeCenter.getInstance().getBusinessHistoryqry(dateStr1, dateStr, new HscloudUtils.onGetListListener() {
                @Override
                public void onSuccess(String response) {
                    disMissDialog();
                    datasList.clear();
                    dealJson(response);
                    Log.e(TAG, "onSuccess: " + response);
                    pullToRefreshListView.onPullDownRefreshComplete();
                }

                @Override
                public void onFailure(String msg, Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        ll = (LinearLayout) getView().findViewById(R.id.ll);
        pullToRefreshListView = (PullToRefreshListView) getView().findViewById(R.id.ptrl);
        listView = pullToRefreshListView.getRefreshableView();
        listView.setCacheColorHint(0);
    }

    /**
     * 解析json
     *
     * @param response
     */
    private void dealJson(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.has("data")) {
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject json = data.getJSONObject(i);
                    String stock_name = json.getString("stock_name");
                    String stock_code = json.getString("stock_code");
                    String type = json.getString("exchange_type");
                    if (type.endsWith("1")) {
                        stock_code += ".SS";
                    } else {
                        stock_code += ".SZ";
                    }
                    String business_time = json.getString("business_time"); // 成交时间
                    StringBuffer sb = new StringBuffer(business_time);
                    // 处理时间字符串 返回来的是200501装换成20：05：01
                    business_time = sb.insert(2, ":").insert(5, ":").toString();
                    String init_date = json.getString("init_date");
                    StringBuffer sb1 = new StringBuffer(init_date);
                    init_date = sb1.insert(4, "-").insert(7, "-").toString();
                    String business_price = json.getString("business_price");

                    String business_balance = json.getString("business_balance");
                    entrust_bs = json.getString("entrust_bs");
                    dealEnrbs();
                    String occur_amount = json.getString("occur_amount"); // 成交数量
                    occur_amount = occur_amount.substring(0, occur_amount.length() - 3);
                    Stock stock = new Stock();
                    stock.setName(stock_name);
                    stock.setCode(stock_code);
                    stock.setBusinessTime(business_time);
                    stock.setBusinessPrice(business_price);
                    stock.setBusinessAmount(occur_amount);
                    stock.setEntrustBs(entrust_bs);
                    stock.setBusinessBalance(business_balance);
                    stock.setInitDate(init_date);
                    datasList.add(stock);
                }
            }
            listView.setEmptyView(getView().findViewById(R.id.ll_data_null));
            if (datasList.size() != 0 ){
                listView.setAdapter(new HistoryDealAdapter(getContext(), datasList));
            }else{
                ll.removeAllViews();
                LinearLayout layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_data_null, null);
                ll.addView(layout);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //handler.sendEmptyMessage(0);
    }

    /**
     * 根据返回来的数字判定成交方向
     */
    private void dealEnrbs() {
        switch (entrust_bs) {
            case "1":
                entrust_bs = "买入";
                break;
            case "2":
                entrust_bs = "卖出";
                break;
        }
    }

    @Override
    public void OnViewClick(View v) {

    }
}
