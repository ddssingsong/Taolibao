package com.jhs.taolibao.code.simtrade.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.adapter.SimtradePagerAdapter;
import com.jhs.taolibao.code.simtrade.base.BaseActivity2;
import com.jhs.taolibao.code.simtrade.view.CategoryTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟交易
 * Created by xujingbo on 2016/7/7.
 */
public class SimtradeActivity extends BaseActivity2 {
    //分类的tab
    private CategoryTabStrip categoryTabStrip;
    private ViewPager viewPager;
    //分页适配器
    private SimtradePagerAdapter adapter;
    private List<String> dataTypes;
    private List<Integer> dataImgs = new ArrayList<>();
    //标题tiele
    private TextView tvTitle;
    private  int currentSelection = 0;
    @Override
    public int getLayout() {
        return R.layout.activity_simtrade;
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        currentSelection = getIntent().getIntExtra("currentIndex",0);
        initData();
        initView();
    }

    @Override
    public void OnViewClick(View v) {

    }
//    public static void setSelection(int selection){
//        currentSelection = selection;
//    }
    /**
     * 初始化view
     */
    private void initView(){
        tvTitle = (TextView)findViewById(R.id.title_name_text);

        categoryTabStrip = (CategoryTabStrip)findViewById(R.id.category_strip);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new SimtradePagerAdapter(getSupportFragmentManager(),dataTypes,dataImgs);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentSelection);
        categoryTabStrip.setAverage(true);
        categoryTabStrip.setTitleWithImg(true);
        categoryTabStrip.setCurrentTvCateroty(tvTitle);
        categoryTabStrip.setViewPager(viewPager);
    }

    /**
     * 初始化数据：类别
     */
    private void initData(){
        dataTypes = new ArrayList<>();
        dataTypes.add(getResources().getString(R.string.simtrade_buy));
        dataTypes.add(getResources().getString(R.string.simtrade_sell));
        dataTypes.add(getResources().getString(R.string.simtrade_cancel));
        dataTypes.add(getResources().getString(R.string.simtrade_keep));
        dataTypes.add(getResources().getString(R.string.simtrade_search));

        dataImgs.add(R.drawable.category_tab_bug_selector);
        dataImgs.add(R.drawable.category_tab_sell_selector);
        dataImgs.add(R.drawable.category_tab_cancel_selector);
        dataImgs.add(R.drawable.category_tab_keep_selector);
        dataImgs.add(R.drawable.category_tab_search_selector);
    }
}
