package com.jhs.taolibao.code.simtrade.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.adapter.TodayorHistoryPageAdapter;
import com.jhs.taolibao.code.simtrade.base.BaseActivity2;
import com.jhs.taolibao.code.simtrade.view.CategoryTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟交易
 * Created by kangxt on 2016/7/7.
 */
public class DealorDelegrationActivity extends BaseActivity2 {
    //分类的tab
    private CategoryTabStrip categoryTabStrip;
    private ViewPager viewPager;
    //分页适配器
    private TodayorHistoryPageAdapter adapter;
    private List<String> dataTypes;
    private List<Integer> dataImgs = new ArrayList<>();
    //标题tiele
    private TextView tvTitle;
    // 根据不同的状态加载相应的界面
    private ArrayList<String> titleList;
    private Boolean isToday,isDelegation;
    @Override
    public int getLayout() {
        return R.layout.activity_todaydeal;
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        initData();
        initView();
    }

    @Override
    public void OnViewClick(View v) {
    }

    /**
     * 初始化view
     */
    private void initView(){
        setTitle("查询");

        categoryTabStrip = (CategoryTabStrip)findViewById(R.id.category_strip);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        if (isToday){
            adapter = new TodayorHistoryPageAdapter(getSupportFragmentManager(),titleList,isToday);
            viewPager.setAdapter(adapter);
        }else{
            adapter = new TodayorHistoryPageAdapter(getSupportFragmentManager(),titleList,false);
            viewPager.setAdapter(adapter);
        }
        if (isDelegation){
            viewPager.setCurrentItem(0);
        }else{
            viewPager.setCurrentItem(1);
        }
        categoryTabStrip.setAverage(true);
        categoryTabStrip.setIndicatorFitParent(true);
        categoryTabStrip.setTitleWithImg(false);
        categoryTabStrip.setViewPager(viewPager);
    }

    /**
     * 初始化数据：类别
     */
    private void initData(){
        titleList = getIntent().getStringArrayListExtra("titleList");
        isToday = getIntent().getBooleanExtra("isToday", true);
        isDelegation = getIntent().getBooleanExtra("isDelegation",true);

    }
}
