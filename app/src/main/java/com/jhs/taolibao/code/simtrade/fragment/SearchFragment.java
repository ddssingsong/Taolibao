package com.jhs.taolibao.code.simtrade.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.activity.DealorDelegrationActivity;
import com.jhs.taolibao.code.simtrade.base.BaseFragment2;

import java.util.ArrayList;

/**
 * 查询界面
 * Created by kangxt on 2016/7/7.
 */
public class SearchFragment extends BaseFragment2 {

    private RelativeLayout todayDelegration, todayDeal, historyDelegration, historyDeal;
    private ArrayList<String> todayTitleList,historyTitleList;

    private static final String TAG = "SearchFragment";
    private SharedPreferences spUserInfo;
    private String s1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, null);
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {

        
        initView();
        initData();
    }

    private void initData() {
        todayTitleList = new ArrayList<>();
        todayTitleList.add("今日委托");
        todayTitleList.add("今日成交");
        historyTitleList = new ArrayList<>();
        historyTitleList.add("历史委托");
        historyTitleList.add("历史成交");
    }

    private void initView() {
        setTitle("查询1");
        todayDeal = (RelativeLayout) getView().findViewById(R.id.ll_today_deal);
        todayDelegration = (RelativeLayout) getView().findViewById(R.id.ll_today_delegation);
        historyDeal = (RelativeLayout) getView().findViewById(R.id.ll_history_deal);
        historyDelegration = (RelativeLayout) getView().findViewById(R.id.ll_history_delegation);
        setViewClick(R.id.ll_today_deal);
        setViewClick(R.id.ll_today_delegation);
        setViewClick(R.id.ll_history_deal);
        setViewClick(R.id.ll_history_delegation);
    }

    @Override
    public void OnViewClick(View v) {
        switch (v.getId()) {
            case R.id.ll_today_deal:
                Intent intent = new Intent(getContext(),DealorDelegrationActivity.class);;
                intent.putStringArrayListExtra("titleList",todayTitleList);
                intent.putExtra("isToday", true);
                intent.putExtra("isDelegation",false);
                startActivity(intent);
                break;
            case R.id.ll_today_delegation:
                intent = new Intent(getContext(),DealorDelegrationActivity.class);
                intent.putStringArrayListExtra("titleList",todayTitleList);
                intent.putExtra("isToday", true);
                intent.putExtra("isDelegation",true);
                startActivity(intent);
                break;
            case R.id.ll_history_deal:

                intent = new Intent(getContext(),DealorDelegrationActivity.class);
                intent.putStringArrayListExtra("titleList",historyTitleList);
                intent.putExtra("isToday", false);
                intent.putExtra("isDelegation",false);
                startActivity(intent);
                break;
            case R.id.ll_history_delegation:

                intent = new Intent(getContext(),DealorDelegrationActivity.class);
                intent.putStringArrayListExtra("titleList",historyTitleList);
                intent.putExtra("isToday", false);
                intent.putExtra("isDelegation",true);
                startActivity(intent);
                break;
        }

    }

}
