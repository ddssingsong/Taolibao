package com.jhs.taolibao.code.market;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.market.prestener.GeStockPresenterImpl;
import com.jhs.taolibao.code.market.view.GeStockView;
import com.jhs.taolibao.code.market.widget.Info1Fragment;
import com.jhs.taolibao.code.market.widget.KlineFragment;
import com.jhs.taolibao.code.market.widget.TimeFragment;
import com.jhs.taolibao.entity.GeStock;
import com.jhs.taolibao.entity.Level5;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.utils.MathUtil;
import com.jhs.taolibao.utils.StatusBarUtil;
import com.jhs.taolibao.utils.ToastUtil;

import java.util.List;

public class StockDetailActivity extends BaseActivity implements GeStockView, View.OnClickListener {
    private RelativeLayout left_layout;//返回键
    private TextView title;//标题
    private TextView title_stock_code;//标题下面的代码
    private Button right_image;//右边的添加自选
    private boolean isSelectflag;

    private TextView tv_stock_price;
    private TextView tv_stock_ratio_value;
    private TextView tv_stock_ratio_ratio;
    private TextView tv_stock_industry;

    private TextView today_open;
    private TextView yesterday_income;
    private TextView turnover_num;
    private TextView turnover_value_income;
    private TextView top_turnover;
    private TextView low_turnover_income;
    private TextView turnover_rate;
    private TextView pe_ratio;

    private TextView turnover_rate_label;
    private TextView pe_ratio_label;


    private TabLayout tab_layout_tu;
    private KlineFragment klineFragment;
    private TimeFragment timeFragment;


    private GeStockPresenterImpl geStockPresenter;
    private String StockId;//用户获取个股信息
    private String optionalId;//用于删除自选


    private BaseFragment currentFragment;
    private Info1Fragment info1Fragment;

    private BaseFragment currentFragment1;
    private SwipeRefreshLayout swiperefresh;

    private RelativeLayout content_stock_buttom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(StockDetailActivity.this);
        geStockPresenter = new GeStockPresenterImpl(this);
        setContentView(R.layout.activity_stock_detail);
        klineFragment = new KlineFragment();
        timeFragment = new TimeFragment();
        info1Fragment = new Info1Fragment();
        intView();
        initData();
        initInfo();
        initTu();
    }


    private void intView() {
        content_stock_buttom = (RelativeLayout) findViewById(R.id.content_stock_buttom);
        left_layout = (RelativeLayout) findViewById(R.id.left_layout);
        title = (TextView) findViewById(R.id.title);
        title_stock_code = (TextView) findViewById(R.id.title_stock_code);
        right_image = (Button) findViewById(R.id.right_image);
        tv_stock_price = (TextView) findViewById(R.id.tv_stock_price);
        tv_stock_ratio_value = (TextView) findViewById(R.id.tv_stock_ratio_value);
        tv_stock_ratio_ratio = (TextView) findViewById(R.id.tv_stock_ratio_ratio);
        tv_stock_industry = (TextView) findViewById(R.id.tv_stock_industry);
        today_open = (TextView) findViewById(R.id.today_open);
        yesterday_income = (TextView) findViewById(R.id.yesterday_income);
        turnover_num = (TextView) findViewById(R.id.turnover_num);
        turnover_value_income = (TextView) findViewById(R.id.turnover_value_income);
        top_turnover = (TextView) findViewById(R.id.top_turnover);
        low_turnover_income = (TextView) findViewById(R.id.low_turnover_income);
        turnover_rate = (TextView) findViewById(R.id.turnover_rate);
        pe_ratio = (TextView) findViewById(R.id.pe_ratio);
        turnover_rate_label= (TextView) findViewById(R.id.turnover_rate_label);
        pe_ratio_label= (TextView) findViewById(R.id.pe_ratio_label);
        tab_layout_tu = (TabLayout) findViewById(R.id.tab_layout_tu);
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        left_layout.setOnClickListener(this);
        right_image.setOnClickListener(this);
        swiperefresh.setOnRefreshListener(listener);

    }

    SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            geStockPresenter.GetStcokInfo(StockId);
        }
    };

    //获取网络数据
    private void initData() {
        //获取个股详情
        DialogUtil.showProgress(this, "正在加载中");
        StockId = getIntent().getStringExtra("stockid");
        geStockPresenter.GetStcokInfo(StockId);

        String type = getIntent().getStringExtra("type");
        if (type != null) {
            if ("index".equals(type)) {
                content_stock_buttom.setVisibility(View.GONE);
                timeFragment.IsIndex();
                klineFragment.IsIndex();
            }
        }


        //获取本支股票是否自选
        if (UserInfoSingleton.getUserInfo() != null) {
            geStockPresenter.isSelect(UserInfoSingleton.getUserId(), StockId);
        }


    }

    //获取个股是否被选回调
    @Override
    public void isSelect(boolean isSelect, String optionalId) {
        if (isSelect) {
            right_image.setText("移除自选");
            isSelectflag = true;
            this.optionalId = optionalId;
        } else {
            right_image.setText("+加自选");
            isSelectflag = false;
        }


    }

    //获取个股信息成功
    @Override
    public void getStcokInfoSuccess(GeStock geStock, List<Level5> buylist, List<Level5> salelist) {
        if (geStock != null) {
            setData(geStock);
        }
        DialogUtil.closeProgressDialog();
        timeFragment.setBuyAndSale(buylist, salelist);
        swiperefresh.setRefreshing(false);

    }

    //添加自选成功
    @Override
    public void InsetOptionalSuccess(int optionId) {
        ToastUtil.showToast(this, "添加成功");
        right_image.setText("移除自选");
        isSelectflag = true;
        right_image.setEnabled(true);
        this.optionalId = Integer.toString(optionId);
    }


    //移除自选成功
    @Override
    public void DelOptionalSuccess() {
        ToastUtil.showToast(this, "移除自选成功");
        right_image.setText("+加自选");
        isSelectflag = false;
        right_image.setEnabled(true);
    }


    //适配个股数据
    private void setData(GeStock geStock) {
        String stockname = geStock.getStockName().replace("&nbsp;", "");
        title.setText(stockname);
        title_stock_code.setText(geStock.getStockCode());

        tv_stock_ratio_value.setText(geStock.getStockRatioValue());
        double stockratio = Double.parseDouble(geStock.getStockRatio());

        if (stockratio >= 0) {
            tv_stock_price.setTextColor(getResources().getColor(R.color.Red));
            tv_stock_ratio_value.setTextColor(getResources().getColor(R.color.Red));
            tv_stock_ratio_ratio.setTextColor(getResources().getColor(R.color.Red));
            tv_stock_ratio_ratio.setText("+" + String.format("%.2f%%", stockratio));
            tv_stock_price.setText("+" + geStock.getStockPrice());
        } else {
            tv_stock_price.setTextColor(getResources().getColor(R.color.Olive));
            tv_stock_ratio_value.setTextColor(getResources().getColor(R.color.Olive));
            tv_stock_ratio_ratio.setTextColor(getResources().getColor(R.color.Olive));
            tv_stock_ratio_ratio.setText(String.format("%.2f%%", stockratio));
            tv_stock_price.setText(geStock.getStockPrice());
        }

        tv_stock_industry.setText(geStock.getIndustryName());
        today_open.setText(geStock.getTodayOpen());
        yesterday_income.setText(geStock.getTodayOpen());
        turnover_num.setText(geStock.getTurnoverNum());
        double TurnoverValue = Double.parseDouble(geStock.getTurnoverValue());
        turnover_value_income.setText(MathUtil.get(TurnoverValue));
        top_turnover.setText(geStock.getTopTurnover());
        low_turnover_income.setText(geStock.getLowTurnover());


        turnover_rate.setText(geStock.getInvol());
        pe_ratio.setText(geStock.getOuterDisc());
        if (geStock.getStockName().contains("上证") || geStock.getStockName().contains("深证") || geStock.getStockName().contains("中小板") || geStock.getStockName().contains("创业板")) {
            turnover_rate.setText("");
            pe_ratio.setText("");
            turnover_rate_label.setVisibility(View.INVISIBLE);
            pe_ratio_label.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.left_layout:
                finish();
                break;
            case R.id.right_image:
                //添加自选
                if (UserInfoSingleton.getUserInfo() != null) {
                    if (isSelectflag) {
                        geStockPresenter.DelOptional(optionalId, 0);
                        right_image.setEnabled(false);
                    } else {
                        geStockPresenter.InsetOptional(UserInfoSingleton.getUserId(), StockId);
                        right_image.setEnabled(false);
                    }
                }
                break;
        }
    }

    //分时和日K的初始化
    private void initTu() {
        //分时和日k的选择
        tab_layout_tu.addTab(tab_layout_tu.newTab().setText(R.string.fenshi));
        tab_layout_tu.addTab(tab_layout_tu.newTab().setText(R.string.riK));
        activityshowFragment1(R.id.content_tu, timeFragment);
        timeFragment.setStockid(StockId);
        klineFragment.setStockid(StockId);
        tab_layout_tu.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    activityshowFragment1(R.id.content_tu, timeFragment);
                }
                if (position == 1) {
                    activityshowFragment1(R.id.content_tu, klineFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //底部资讯类数据
    private void initInfo() {
        activityshowFragment(R.id.content_stock_buttom, info1Fragment);
        info1Fragment.setStockCode(StockId);
    }

    public void activityshowFragment(int contentID, BaseFragment toFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (currentFragment != null) {
            currentFragment.onPause();
            ft.hide(currentFragment);
        }
        String tag = toFragment.getClass().getSimpleName();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment == null) {
            ft.add(contentID, toFragment, tag);
        } else {
            toFragment.onResume();
            ft.show(toFragment);
        }
        ft.commit();
        currentFragment = toFragment;
    }


    public void activityshowFragment1(int contentID, BaseFragment toFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (currentFragment1 != null) {
            currentFragment1.onPause();
            ft.hide(currentFragment1);
        }
        String tag = toFragment.getClass().getSimpleName();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment == null) {
            ft.add(contentID, toFragment, tag);
        } else {
            toFragment.onResume();
            ft.show(toFragment);
        }
        ft.commit();
        currentFragment1 = toFragment;
    }
}
