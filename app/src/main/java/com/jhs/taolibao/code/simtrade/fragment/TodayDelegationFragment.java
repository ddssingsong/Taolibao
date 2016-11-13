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
import com.jhs.taolibao.code.simtrade.adapter.TodayDeleAdapter;
import com.jhs.taolibao.code.simtrade.base.BaseFragment2;
import com.jhs.taolibao.code.simtrade.entity.Stock;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.code.simtrade.utils.HscloudUtils;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshBase;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 今日委托界面
 * Created by KANGXIANGTAO on 2016/7/7.
 */
public class TodayDelegationFragment extends BaseFragment2 {

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private LinearLayout ll ;

    private List<Stock> datasList = new ArrayList<>();
    private static final String TAG = "TodayDelegationFragment";
    private String entrust_status;

    //    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            listView.setAdapter(new TodayDeleAdapter(getContext(), datasList));
//        }
//    };
    private String entrust_bs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todaydelegation, null);
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        showProgressDialog();
        initView();
        initData();
        setListener();

    }

    private void setListener() {
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

    /**
     * 访问网络数据
     */
    private void initData() {
        try {
            SimtradeCenter.getInstance().getEntrustqry(new HscloudUtils.onGetListListener() {
                @Override
                public void onSuccess(String response) {
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
                    String type = json.getString("exchange_type");
                    String stock_code = json.getString("stock_code");
                    // 根据type类型判断是哪个交易所
                    if (type.endsWith("1")) {
                        stock_code += ".SS";
                    } else {
                        stock_code += ".SZ";
                    }
                    String entrust_time = json.getString("entrust_time");
                    StringBuffer sb = new StringBuffer(entrust_time);
                    // 处理时间字符串 返回来的是200501装换成20：05：01
                    entrust_time = sb.insert(2, ":").insert(5, ":").toString();

                    String entrust_date = json.getString("entrust_date"); // 委托时间,历史委托显示的年月日
                    StringBuffer sb1 = new StringBuffer(entrust_date);
                    // 处理时间字符串 返回来的是200501装换成20：05：01
                    entrust_date = sb1.insert(4,"-").insert(7,"-").toString();

                    String entrust_price = json.getString("entrust_price");
                    String entrust_amount = json.getString("entrust_amount");
                    entrust_amount = entrust_amount.substring(0, entrust_amount.length() - 3);
                    entrust_status = json.getString("entrust_status");
                    dealStatus();
                    entrust_bs = json.getString("entrust_bs");
                    dealEnrbs();
                    String business_amount = json.getString("business_amount");
                    Stock stock = new Stock();
                    stock.setName(stock_name);
                    stock.setCode(stock_code);
                    stock.setEntrustTime(entrust_time);
                    stock.setEntrustPrice(entrust_price);
                    stock.setEntrustAmount(entrust_amount);
                    stock.setBusinessAmount(business_amount);
                    stock.setEntrustBs(entrust_bs);
                    stock.setEntrustStatus(entrust_status);
                    stock.setEntrustDate(entrust_date);
                    datasList.add(stock);
                }
            }
            // 如果数据为空 那么加载另一种布局
            if (datasList.size() != 0){
                listView.setAdapter(new TodayDeleAdapter(getContext(), datasList));
            }else{
                ll.removeAllViews();
                LinearLayout layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_data_null, null);
                ll.addView(layout);
            }
            disMissDialog();
//            Log.e(TAG, "onSuccess: " + response);
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

    /**
     * 根据返回来的数字判定成交状态
     */
    private void dealStatus() {
        String temp[] = {"未报","待报","已报","已报待撤","部成待撤","部撤","已撤","部成","已成","废单"};
        int position = Integer.valueOf(entrust_status);
        entrust_status = temp[position];
//        switch (entrust_status) {
//            case "0":
//                entrust_status = "未报";
//                break;
//            case "1":
//                entrust_status = "待报";
//                break;
//            case "2":
//                entrust_status = "已报";
//                break;
//            case "3":
//                entrust_status = "已报待撤";
//                break;
//            case "4":
//                entrust_status = "部成待撤";
//                break;
//            case "5":
//                entrust_status = "部撤";
//                break;
//            case "6":
//                entrust_status = "已撤";
//                break;
//            case "7":
//                entrust_status = "部成";
//                break;
//            case "8":
//                entrust_status = "已成";
//                break;
//            case "9":
//                entrust_status = "废单";
//                break;
//        }
    }

    private void initView() {
        ll = (LinearLayout) getView().findViewById(R.id.ll);
        pullToRefreshListView = (PullToRefreshListView) getView().findViewById(R.id.ptrl);
        listView = pullToRefreshListView.getRefreshableView();
        listView.setCacheColorHint(0);
//        listView.setSelector(R.color.transparent);

    }

    @Override
    public void OnViewClick(View v) {

    }
}
