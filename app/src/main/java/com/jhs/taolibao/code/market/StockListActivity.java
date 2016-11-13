package com.jhs.taolibao.code.market;

import android.content.Intent;
import android.os.Bundle;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.market.widget.IndustryInfoFragment;
import com.jhs.taolibao.code.market.widget.IndustryListFragment;
import com.jhs.taolibao.code.market.widget.StockRiseFragment;
import com.jhs.taolibao.utils.StatusBarUtil;

/**
 * 行业涨幅和涨跌幅榜列表的管理
 */
public class StockListActivity extends BaseActivity implements BaseFragment.BaseFragmentCallback {
    private IndustryListFragment industryListFragment;
    private IndustryInfoFragment industryinfogragment;
    private StockRiseFragment stockRiseFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(StockListActivity.this);
        setContentView(R.layout.activity_stock_list);

        String type = getIntent().getStringExtra("type");
        String industry = getIntent().getStringExtra("industry");
        String industrycode=getIntent().getStringExtra("industrycode");
        industryListFragment = new IndustryListFragment();
        industryinfogragment = new IndustryInfoFragment();
        stockRiseFragment = new StockRiseFragment();
        //行业涨幅列表
        if (type.equals("industry1")) {
            replaceFragment(R.id.content_stocklist, industryListFragment);
        }
        //行业详情
        if (type.equals("industry2")) {
            industryinfogragment.setIndustry(industry,industrycode);
            replaceFragment(R.id.content_stocklist, industryinfogragment);

        }
        //涨幅榜
        if (type.equals("rise")) {

            stockRiseFragment.setType(1);
            replaceFragment(R.id.content_stocklist, stockRiseFragment);
        }
        //跌幅榜
        if (type.equals("fall")) {

            stockRiseFragment.setType(0);
            replaceFragment(R.id.content_stocklist, stockRiseFragment);
        }


    }


    @Override
    public void onCallback(String tag, int eventID, Object data) {
        if (tag.equals(industryListFragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                finish();
            }
            if (eventID == 2) {
                String industry = (String) data;
                Intent intent = new Intent(this, StockListActivity.class);
                intent.putExtra("type", "industry2");
                intent.putExtra("industry", industry);
                startActivity(intent);
            }
        }
        if (tag.equals(industryinfogragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                finish();
            }
        }
        if (tag.equals(stockRiseFragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                finish();
            }
        }
    }
}
