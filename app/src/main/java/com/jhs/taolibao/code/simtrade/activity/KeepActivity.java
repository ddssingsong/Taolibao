package com.jhs.taolibao.code.simtrade.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshBase;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshListView;
import com.jhs.taolibao.code.challenge.ChallengeCenter;
import com.jhs.taolibao.code.simtrade.SimtradeCenter;
import com.jhs.taolibao.code.simtrade.adapter.KeepAdapter2;
import com.jhs.taolibao.code.simtrade.base.BaseActivity2;
import com.jhs.taolibao.code.simtrade.entity.KeepBean;
import com.jhs.taolibao.code.simtrade.entity.PersonalReturnRate;
import com.jhs.taolibao.code.simtrade.entity.UserMoneyInfo;
import com.jhs.taolibao.code.simtrade.fragment.DayKFragment;
import com.jhs.taolibao.code.simtrade.fragment.MinuteHourFragment;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.code.simtrade.utils.HscloudUtils;
import com.jhs.taolibao.utils.JsonUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jiao on 2016/7/7 12:37
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:持仓
 */
public class KeepActivity extends BaseActivity2 {
    private static final String TAG = "KeepActivity";
    public static final String KEEP = "KEEPINFO";

    //分时布局
    private FrameLayout minuteHour;
    //分时指示器
    private RelativeLayout minuteHourRe;
    private TextView minuteHourTv;
    private TextView minuteHourSign;

    //K线布局
    private FrameLayout dayK;
    //K线指示器
    private RelativeLayout dayKRe;
    private TextView dayKTv;
    private TextView dayKSign;

    //中间部分的菜单栏--买入,卖出,撤单,持仓,查询
    private LinearLayout mTabs[] = new LinearLayout[5];
    private ImageView mIv[] = new ImageView[5];
    private TextView mTv[] = new TextView[5];
    private int index;
    private int currentIndex;

    private PullToRefreshListView pull;
    private ListView mLv;

    private List<KeepBean.KeepItem> mList = new ArrayList<>();

    //总资产
    private TextView allMoney;
    //可用资产
    private TextView enableMoney;
    //总收益率
    private TextView allReturnRate;
    //当日收益率
    private TextView dayReturnRate;
    private boolean isShowProgress = true;

    @Override
    public int getLayout() {
        return R.layout.activity_keep;
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        setTitle("持仓");
        //此方法改为在启动页已登录情况下调用
        //SimtradeCenter.getInstance().init("70006987", "111111");
        //获取公有令牌
        if (SimtradeCenter.getInstance().isPublicTokenInvalid()) {
            Log.d(TAG, "OnActCreate: publicToken未获取或publicToken无效，重新获取令牌");
            //token未获取或token无效，重新获取令牌
            SimtradeCenter.getInstance().getPublicAccessToken();
        }
        initView();
        setListener();
        beginTransaction();

        getUserIncomeInfo();
        getUserMoneyInfo();
        getData();
    }

    /**
     * 加载Fragment
     */
    private void beginTransaction() {
        MinuteHourFragment minuteHourFragment = new MinuteHourFragment();
        DayKFragment dayKFragment = new DayKFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(R.id.minute_hours, minuteHourFragment);
        beginTransaction.add(R.id.dayK, dayKFragment);
        beginTransaction.commit();

    }


    /**
     * 设置点击事件
     */
    private void setListener() {
        setViewClick(R.id.minute_hours_re);
        setViewClick(R.id.dayK_re);
        setViewClick(R.id.buy);
        setViewClick(R.id.sell);
        setViewClick(R.id.cancel);
        setViewClick(R.id.keep);
        setViewClick(R.id.selector);

        pull.setScrollLoadEnabled(false);
        pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isShowProgress = false;
                getUserIncomeInfo();
                getUserMoneyInfo();
                getData();
                String s = DateUtils.getTimestampString(new Date(System.currentTimeMillis()));
                pull.setLastUpdatedLabel(s);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        pull = (PullToRefreshListView) findViewById(R.id.noscrolllistview);
        mLv = pull.getRefreshableView();
//        minuteHour = (FrameLayout) findViewById(R.id.minute_hours);
//        minuteHourRe = (RelativeLayout) findViewById(R.id.minute_hours_re);
//        minuteHourSign = (TextView) findViewById(R.id.minute_hours_sign);
//        minuteHourTv = (TextView) findViewById(R.id.minute_hours_tv);
//        dayK = (FrameLayout) findViewById(R.id.dayK);
//        dayKRe = (RelativeLayout) findViewById(R.id.dayK_re);
//        dayKSign = (TextView) findViewById(R.id.dayK_sign);
//        dayKTv = (TextView) findViewById(R.id.dayK_tv);
        initHead();

    }

    /**
     * 初始化头文件
     */
    private void initHead() {
        View head1 = LayoutInflater.from(this).inflate(R.layout.keep_head1, mLv, false);

        View head2 = LayoutInflater.from(this).inflate(R.layout.keep_head2, mLv, false);
        mIv[0] = (ImageView) head2.findViewById(R.id.buy_iv);
        mIv[1] = (ImageView) head2.findViewById(R.id.sell_iv);
        mIv[2] = (ImageView) head2.findViewById(R.id.cancel_iv);
        mIv[3] = (ImageView) head2.findViewById(R.id.keep_iv);
        mIv[4] = (ImageView) head2.findViewById(R.id.selector_iv);
        mTv[0] = (TextView) head2.findViewById(R.id.buy_tv);
        mTv[1] = (TextView) head2.findViewById(R.id.sell_tv);
        mTv[2] = (TextView) head2.findViewById(R.id.cancel_tv);
        mTv[3] = (TextView) head2.findViewById(R.id.keep_tv);
        mTv[4] = (TextView) head2.findViewById(R.id.selector_tv);

        View head3 = LayoutInflater.from(this).inflate(R.layout.keep_head3, mLv, false);
        allMoney = (TextView) head3.findViewById(R.id.keep_allMoney);
        allReturnRate = (TextView) head3.findViewById(R.id.keep_all_returnRate);
        dayReturnRate = (TextView) head3.findViewById(R.id.keep_day_returnRate);
        enableMoney = (TextView) head3.findViewById(R.id.keep_enablemoney);

        mLv.addHeaderView(head3);
        mLv.addHeaderView(head2);
        mLv.addHeaderView(head1);
    }


    @Override
    public void OnViewClick(View v) {
        switch (v.getId()) {
            //点击"分时"
            case R.id.minute_hours_re:
                minuteHourSign.setVisibility(View.VISIBLE);
                dayKSign.setVisibility(View.GONE);

                minuteHour.setVisibility(View.VISIBLE);
                dayK.setVisibility(View.GONE);

                break;
            //点击"日K"
            case R.id.dayK_re:
                minuteHourSign.setVisibility(View.GONE);
                dayKSign.setVisibility(View.VISIBLE);

                minuteHour.setVisibility(View.GONE);
                dayK.setVisibility(View.VISIBLE);
                break;

            //中间菜单的点击事件
            //买入
            case R.id.buy:
                index = 0;
                setBtn();
                break;
            //卖出
            case R.id.sell:
                index = 1;
                setBtn();
                break;
            //撤单
            case R.id.cancel:
                index = 2;
                setBtn();
                break;
            //持仓
            case R.id.keep:
                index = 3;
                setBtn();
                break;
            //查询
            case R.id.selector:
                index = 4;
                setBtn();
                break;
        }
    }

    /**
     * 配置中间导航栏
     */
    private void setBtn() {
        mIv[currentIndex].setSelected(false);
        mTv[currentIndex].setSelected(false);

        mIv[index].setSelected(true);
        mTv[index].setSelected(true);

        currentIndex = index;

        startActivity(new Intent(this, SimtradeActivity.class).putExtra("currentIndex", index));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTv[currentIndex].setSelected(false);
        mIv[currentIndex].setSelected(false);

        //选中"持仓"
        mTv[3].setSelected(true);
        mIv[3].setSelected(true);

        currentIndex = 3;

    }

    /**
     * 获取持仓信息
     *
     * @return
     */
    public void getData() {
        if (isShowProgress) {
            showProgressDialog();
        }
//        try {
//            SimtradeCenter.getInstance().getKeepInfo(SimtradeCenter.getInstance().getUserId(),
//                    null,
//                    null,
//                    new HscloudUtils.onGetBeanListener() {
//                        @Override
//                        public void onSuccess(KeepBean response) {
//
//                            disMissDialog();
//                            mList = response.getData();
//                          //  mLv.setEmptyView(findViewById(R.id.ll_data_null));
//                            KeepAdapter2 adapter = new KeepAdapter2();
//                            adapter.setData(mList);
//                            mLv.setAdapter(adapter);
//                            pull.onPullDownRefreshComplete();
//
//                            //计算总收益率
//                            if (mList != null) {
////                                countAllIncome();
//                            }
//
//                        }
//
//                        @Override
//                        public void onFailure(String msg, Exception e) {
//                            disMissDialog();
//                            showToast(msg);
//                            pull.onPullDownRefreshComplete();
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        ChallengeCenter.getInstance().getKeepInfo(SimtradeCenter.getInstance().getUserId(), new HscloudUtils.onGetBeanListener() {
            @Override
            public void onSuccess(KeepBean response) {
                disMissDialog();
                mList = response.getData();
                //  mLv.setEmptyView(findViewById(R.id.ll_data_null));
                KeepAdapter2 adapter = new KeepAdapter2();
                adapter.setData(mList);
                mLv.setAdapter(adapter);
                pull.onPullDownRefreshComplete();

                //计算总收益率
                if (mList != null) {
//                countAllIncome();
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                disMissDialog();
                showToast(msg);
                pull.onPullDownRefreshComplete();
            }
        });

    }

    /**
     * 计算总收益
     * 收益率 = (最新价 - 成本价) / 成本价
     */
    private void countAllIncome() {
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

    public void getAssetsInfo() {
        try {
            SimtradeCenter.getInstance().getAssetsInfo(new HscloudUtils.onGetListListener() {
                @Override
                public void onSuccess(String response) {
//                    Log.d("KeepActivity", response);
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
     * 获取当日收益率
     */
    public void getUserIncomeInfo() {
        try {
            SimtradeCenter.getInstance().getUserIncomeInfo(new HscloudUtils.onGetListListener() {
                @Override
                public void onSuccess(String response) {
                    // Log.d("KeepActivitys", "获取当日收益率："+response);
                    PersonalReturnRate deserialize = JsonUtils.deserialize(response, PersonalReturnRate.class);
                    if (deserialize.getData() != null && deserialize.getData().size() > 0) {
                        if (Double.valueOf(deserialize.getData().get(0).getProfit_rate()) < 0) {
                            dayReturnRate.setTextColor(getResources().getColor(R.color.Olive));
                        }
                        NumberFormat fmt = NumberFormat.getPercentInstance();
                        fmt.setMaximumFractionDigits(2);
                        String format = fmt.format(Double.valueOf(deserialize.getData().get(0).getProfit_rate()));
                        dayReturnRate.setText(format);
                    }

                }

                @Override
                public void onFailure(String msg, Exception e) {
                    SimtradeCenter.getInstance().init(UserInfoSingleton.getUserInfo().getHsAccount(), UserInfoSingleton.getUserInfo().getHsPwd());
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
            SimtradeCenter.getInstance().getUserMoneyInfo(
                    new HscloudUtils.onGetListListener() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d("KeepActivityss", "获取总资产和可用自资产：" + response);
                            UserMoneyInfo deserialize = JsonUtils.deserialize(response, UserMoneyInfo.class);
                            //防止有些异常的情况(如获取令牌失败)下取不到数据
                            if (deserialize.getData() != null) {
                                allMoney.setText(String.valueOf(deserialize.getData().get(0).getAsset_balance()));
                                enableMoney.setText(String.valueOf(deserialize.getData().get(0).getEnable_balance()));
                                if (((Double.valueOf(deserialize.getData().get(0).getAsset_balance()) - 1000000) / 1000000) < 0) {
                                    allReturnRate.setTextColor(getResources().getColor(R.color.Olive));
                                }
                                NumberFormat fmt = NumberFormat.getPercentInstance();
                                fmt.setMaximumFractionDigits(2);
                                String format = fmt.format((Double.valueOf(deserialize.getData().get(0).getAsset_balance()) - 1000000) / 1000000);
                                allReturnRate.setText(format);
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
