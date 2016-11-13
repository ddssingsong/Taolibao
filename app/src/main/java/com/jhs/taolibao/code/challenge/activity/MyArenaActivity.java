package com.jhs.taolibao.code.challenge.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.challenge.adapter.MyArenaPageAdapter;
import com.jhs.taolibao.code.simtrade.base.BaseActivity2;
import com.jhs.taolibao.code.simtrade.view.CategoryTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiao on 2016/7/14 10:47
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:我的擂台
 */
public class MyArenaActivity extends BaseActivity2 {

    //分类的tab
    private CategoryTabStrip categoryTabStrip;
    private ViewPager viewPager;

    private MyArenaPageAdapter adapter;
    private List<String>  dataList;
    @Override
    public int getLayout() {
        return R.layout.activity_myarena;
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        setTitle("我的擂台");
        initTitle();
        initView();
    }

    private void initTitle() {
        dataList = new ArrayList<>();
        dataList.add("在挑战");
        dataList.add("观战中");
        dataList.add("已完成");
    }

    private void initView() {
        categoryTabStrip = (CategoryTabStrip)findViewById(R.id.category_strip);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new MyArenaPageAdapter(getSupportFragmentManager(),dataList);
        viewPager.setAdapter(adapter);
        categoryTabStrip.setAverage(true);
        categoryTabStrip.setIndicatorFitParent(true);
        categoryTabStrip.setTitleWithImg(false);
        categoryTabStrip.setViewPager(viewPager);
    }

    @Override
    public void OnViewClick(View v) {

    }
}
