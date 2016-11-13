package com.jhs.taolibao.code.challenge.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.challenge.adapter.ShortRankingAdapter;
import com.jhs.taolibao.code.simtrade.base.BaseActivity2;
import com.jhs.taolibao.code.simtrade.view.CategoryTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiao、xujingbo on 2016/7/14 10:44
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:短线排行榜
 */
public class ShortRankingActivity extends BaseActivity2 {
    private CategoryTabStrip tab;
    private ViewPager mVp;
    private ShortRankingAdapter adapter;
    private List<String> types;
    private TextView title;
    private int currentSelection;

    @Override
    public int getLayout() {
        return R.layout.activity_short_ranking;
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        setTitle("短线排行榜");
        initTypes();
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        tab = (CategoryTabStrip) findViewById(R.id.short_category_strip);
        mVp = (ViewPager) findViewById(R.id.short_view_pager);

        adapter = new ShortRankingAdapter(getSupportFragmentManager(), types);
        tab.setAverage(true);
        tab.setIndicatorFitParent(true);
        tab.setTitleWithImg(false);

        mVp.setAdapter(adapter);
        tab.setViewPager(mVp);
    }

    /**
     * 配置标题
     */
    private void initTypes() {
        types = new ArrayList<String>();
        types.add("综合排名");
        types.add("累计收益");
    }

    @Override
    public void OnViewClick(View v) {

    }
}
