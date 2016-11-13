package com.jhs.taolibao.code.simtrade.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.SimtradeCenter;
import com.jhs.taolibao.code.simtrade.adapter.CancelAdapter;
import com.jhs.taolibao.code.simtrade.base.BaseFragment2;
import com.jhs.taolibao.code.simtrade.entity.Stock;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.code.simtrade.utils.HscloudUtils;
import com.jhs.taolibao.code.simtrade.view.EntrustAlertDialog;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshBase;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 撤单界面
 * Created by xujingbo on 2016/7/7.
 */
public class CancelFragment extends BaseFragment2 {

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private LinearLayout ll;

    private List<Stock> datasList = new ArrayList<>();
    // 委托状态
    private String entrust_status;
    private static final String TAG = "CancelFragment";
    private String entrust_bs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cancel, null);
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        showProgressDialog();
        initData();
        initView();
        setListener();
    }

    private void setListener() {
        pullToRefreshListView.setScrollLoadEnabled(true);
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

    private void initView() {
        ll = (LinearLayout) getView().findViewById(R.id.ll);
        pullToRefreshListView = (PullToRefreshListView) getView().findViewById(R.id.ptrl);
        listView = pullToRefreshListView.getRefreshableView();
        listView.setCacheColorHint(0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 撤单
                try {
                    showAffrim(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAffrim(final int position) {
        new EntrustAlertDialog(getContext()).builder()
                .setTitle("确认撤单")
                .setMsgContent1("买卖方向", datasList.get(position).getEntrustBs())
                .setMsgContent2("委托价格", datasList.get(position).getEntrustPrice())
                .setMsgContent3("委托数量", datasList.get(position).getEntrustAmount())
                .setMsgContent4("成交数量", datasList.get(position).getBusinessAmount())
                .hideContent(5)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelOrder(position);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing
            }
        }).show();
    }

    private void cancelOrder(int position) {

        try {
            SimtradeCenter.getInstance().withdrawFlag(datasList.get(position).getEntrustNo(), new HscloudUtils.onGetListListener() {
                @Override
                public void onSuccess(String response) {
                    Log.e(TAG, "cancel onSuccess: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("data")) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject json = data.getJSONObject(i);
                                String entrustNo = json.getString("entrust_no");
                                if (entrustNo != null) {
                                    showToast(getResources().getString(R.string.cancel_order));
                                    initData();
                                }
                            }
                        }else{

                            if (jsonObject.has("error_code")){

                                int errorCode = jsonObject.getInt("error_code");
                                if (errorCode == 10220){
                                    showToast(jsonObject.getString("error_info"));
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String msg, Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {

        //先查询今日委托
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
                    String entrust_no = json.getString("entrust_no"); // 委托编号，撤单要传递此参数
                    String withdraw_flag = json.getString("withdraw_flag");
                    if (withdraw_flag.equals("0")) {
                        continue;
                    }
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
                    String entrust_price = json.getString("entrust_price"); // 委托价格
                    String entrust_amount = json.getString("entrust_amount"); // 委托数量
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
                    stock.setEntrustStatus(entrust_status);
                    stock.setEntrustBs(entrust_bs);
                    stock.setEntrustNo(entrust_no);
                    datasList.add(stock);

                }
            }
            if (datasList.size() != 0){
                listView.setAdapter(new CancelAdapter(getContext(), datasList));
            }else{
                ll.removeAllViews();
                LinearLayout layout = (LinearLayout)LayoutInflater.from(getContext()).inflate(R.layout.layout_data_null,null);
                ll.addView(layout);
            }
            disMissDialog();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据返回来的数字判定成交状态
     */
    private void dealStatus() {
        switch (entrust_status) {
            case "0":
                entrust_status = "未报";
                break;
            case "1":
                entrust_status = "待报";
                break;
            case "2":
                entrust_status = "已报";
                break;
            case "3":
                entrust_status = "已报待撤";
                break;
            case "4":
                entrust_status = "部成待撤";
                break;
            case "6":
                entrust_status = "已撤";
                break;
            case "7":
                entrust_status = "部成";
                break;
            case "8":
                entrust_status = "已成";
                break;
            case "9":
                entrust_status = "废单";
                break;
        }
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
