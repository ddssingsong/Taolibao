package com.jhs.taolibao.code.simtrade.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.SimtradeCenter;
import com.jhs.taolibao.code.simtrade.adapter.KeepAdapter2;
import com.jhs.taolibao.code.simtrade.base.BaseFragment2;
import com.jhs.taolibao.code.simtrade.entity.KeepBean;
import com.jhs.taolibao.code.simtrade.entity.PersonalReturnRate;
import com.jhs.taolibao.code.simtrade.entity.UserMoneyInfo;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.code.simtrade.utils.HscloudUtils;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshBase;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshListView;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

/**
 * 持仓界面
 * Created by jiao on 2016/7/7.
 */
public class KeepFragment extends BaseFragment2 {
    public static final String KEEP = "KEEPINFO";
    private PullToRefreshListView pull;
    private ListView mLv;
    //总资产
    private TextView allMoney;
    //可用资产
    private TextView enableMoney;
    //总收益率
    private TextView allReturnRate;
    //当日收益率
    private TextView dayReturnRate;
    //是否显示progress
    private boolean isShowProgress = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_keep, null);
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        initView();
        initHead();
        setListView();
        setListener();

    }

    /**
     * 配置listview
     */
    private void setListView() {


    }

    /**
     * 配置ListView的头文件
     */
    private void initHead() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_keep_head, mLv, false);
        allMoney = (TextView) view.findViewById(R.id.keep_allMoney);
        allReturnRate = (TextView) view.findViewById(R.id.keep_all_returnRate);
        dayReturnRate = (TextView) view.findViewById(R.id.keep_day_returnRate);
        enableMoney = (TextView) view.findViewById(R.id.keep_enablemoney);
        mLv.addHeaderView(view);

    }

    @Override
    public void onResume() {
        super.onResume();
        isShowProgress = true;
        getData();
        getUserIncomeInfo();
        getUserMoneyInfo();
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        pull.setScrollLoadEnabled(false);
        pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isShowProgress = false;
                getData();
                getUserIncomeInfo();
                getUserMoneyInfo();
                String s = DateUtils.getTimestampString(new Date(System.currentTimeMillis()));
                pull.setLastUpdatedLabel(s);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pull.onPullUpRefreshComplete();
            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        pull = (PullToRefreshListView) getView().findViewById(R.id.keep_lv);
        mLv = pull.getRefreshableView();

    }

    @Override
    public void OnViewClick(View v) {

    }

    /**
     * 获取数据
     */
    private void getData() {
        if (isShowProgress) {
            showProgressDialog();
        }
        try {
            SimtradeCenter.getInstance().getKeepInfo(SimtradeCenter.getInstance().getUserId(), null,null,
                    new HscloudUtils.onGetBeanListener() {
                        @Override
                        public void onSuccess(KeepBean response) {
                            disMissDialog();
                            KeepAdapter2 adapter = new KeepAdapter2();
                            mLv.setEmptyView(getView().findViewById(R.id.ll_data_null));
                            adapter.setData(response.getData());
                            mLv.setAdapter(adapter);

                            pull.onPullDownRefreshComplete();
                            //防止在某些异常情况下取不到数据
                            if (response.getData() != null) {
//                                countAllIncome(response.getData());
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

    /**
     * 计算总收益
     * 收益率 = (最新价 - 成本价) / 成本价
     */
    private void countAllIncome(List<KeepBean.KeepItem> mList) {
        //最新价
        float lastPrice = 0;
        //成本价
        float costPrice = 0;
        for (KeepBean.KeepItem keepItem : mList) {
//            lastPrice += Float.valueOf(keepItem.getLast_price());
//            costPrice += Float.valueOf(keepItem.getCost_price());
        }
        //收益率
        float returnRate = (lastPrice - costPrice) / costPrice;
        allReturnRate.setText(String.valueOf(returnRate));
    }

    /**
     * 获取当日收益率
     */
    public void getUserIncomeInfo() {
        try {
            SimtradeCenter.getInstance().getUserIncomeInfo(new HscloudUtils.onGetListListener() {
                @Override
                public void onSuccess(String response) {
                    Log.d("KeepActivitys", response);
                    PersonalReturnRate deserialize = JsonUtils.deserialize(response, PersonalReturnRate.class);

                    if (deserialize.getData() != null && deserialize.getData().size() > 0 && !deserialize.getData().get(0).getProfit_rate().equals("")) {
                        if (Double.valueOf(deserialize.getData().get(0).getProfit_rate()) < 0) {
                            dayReturnRate.setTextColor(getResources().getColor(R.color.Olive));
                        }
                        NumberFormat fmt = NumberFormat.getPercentInstance();
                        fmt.setMaximumFractionDigits(2);
                        String format = fmt.format(Double.valueOf(deserialize.getData().get(0).getProfit_rate()));
                        dayReturnRate.setText(format);
//                        dayReturnRate.setText(String.valueOf(deserialize.getData().get(0).getProfit_rate()));
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

    /**
     * 获取总资产和可用资产
     */
    public void getUserMoneyInfo() {
        try {
            SimtradeCenter.getInstance().getUserMoneyInfo(new HscloudUtils.onGetListListener() {
                @Override
                public void onSuccess(String response) {
                    Log.d("KeepActivityss", response);
                    UserMoneyInfo deserialize = JsonUtils.deserialize(response, UserMoneyInfo.class);
                    //防止在某些异常情况下取不到数据
                    if (deserialize.getData() != null && !deserialize.getData().get(0).getAsset_balance().equals("")) {
                        allMoney.setText(String.valueOf(deserialize.getData().get(0).getAsset_balance()));
                        enableMoney.setText(String.valueOf(deserialize.getData().get(0).getEnable_balance()));
                        //总收益率
                        if (((Double.valueOf(deserialize.getData().get(0).getAsset_balance()) - 1000000) / 1000000) < 0) {
                            allReturnRate.setTextColor(getResources().getColor(R.color.Olive));
                        }
                        NumberFormat fmt = NumberFormat.getPercentInstance();
                        fmt.setMaximumFractionDigits(2);
                        String format = fmt.format((Double.valueOf(deserialize.getData().get(0).getAsset_balance()) - 1000000) / 1000000);
                        allReturnRate.setText(format);
//                        allReturnRate.setText(String.valueOf((Long.valueOf(deserialize.getData().get(0).getAsset_balance()) - 1000000) / 1000000));
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


}
