package com.jhs.taolibao.code.guess;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.code.guess.presenter.GuessPresenterImpl;
import com.jhs.taolibao.code.guess.view.GuessView;
import com.jhs.taolibao.code.guess.widget.RuleFragment;
import com.jhs.taolibao.code.my.widget.BaobiActivity;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.entity.Interval;
import com.jhs.taolibao.entity.Street;
import com.jhs.taolibao.entity.TodayGuess;
import com.jhs.taolibao.utils.CalendarUtil;
import com.jhs.taolibao.utils.DateUtil;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.utils.SharedPreferencesUtils;
import com.jhs.taolibao.utils.StatusBarUtil;
import com.jhs.taolibao.utils.ToastUtil;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GuessActivity extends BaseActivity implements GuessView, View.OnClickListener {

    private RelativeLayout left_layout;//退出
    private RelativeLayout right_layout;//规则
    private TextView tv_date;//日期
    private TextView tv_time;//倒计时
    private ImageView iv_guess_history;//猜涨跌历史

    private TextView tv_guess_shang;//上证
    private TextView tv_guess_min1;
    private TextView tv_guess_max1;
    private EditText et_guess1;

    private TextView tv_guess_shen;//深证
    private TextView tv_guess_min2;
    private TextView tv_guess_max2;
    private EditText et_guess2;

    private TextView tv_guess_chuang;//创业板
    private TextView tv_guess_min3;
    private TextView tv_guess_max3;
    private EditText et_guess3;

    private TextView tv_guess_zhong;//中小板
    private TextView tv_guess_min4;
    private TextView tv_guess_max4;
    private EditText et_guess4;

    private TextView tv_guess_role; //底部规则
    private TextView tv_guess_price; //所需宝币数

    private Button btn_guess; //添加或者修改
    private Button btn_go_buy;//跳到充值界面
    private RelativeLayout layout_can_guess;
    private RelativeLayout layout_cannot_guess;


    private GuessPresenterImpl guesspresenter;

    private String gamejson;
    private boolean flag;//是否已经猜的标志
    private TodayGuess todayGuess1;//如果已经猜了才有用
    private TodayGuess todayGuess2;
    private TodayGuess todayGuess3;
    private TodayGuess todayGuess4;

    private String dapan1;
    private String dapan2;
    private String dapan3;
    private String dapan4;

    private MyCount myCount;//倒计时
    private MyCount1 myCount1;//倒计时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.activity_guess);
        guesspresenter = new GuessPresenterImpl(this);
        initView();
        initData();
    }


    private void initView() {
        left_layout = (RelativeLayout) findViewById(R.id.left_layout);
        right_layout = (RelativeLayout) findViewById(R.id.right_layout);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_guess_history = (ImageView) findViewById(R.id.iv_guess_history);
        tv_guess_shang = (TextView) findViewById(R.id.tv_guess_shang);
        tv_guess_min1 = (TextView) findViewById(R.id.tv_guess_min1);
        tv_guess_max1 = (TextView) findViewById(R.id.tv_guess_max1);
        et_guess1 = (EditText) findViewById(R.id.et_guess1);
        tv_guess_shen = (TextView) findViewById(R.id.tv_guess_shen);
        tv_guess_min2 = (TextView) findViewById(R.id.tv_guess_min2);
        tv_guess_max2 = (TextView) findViewById(R.id.tv_guess_max2);
        et_guess2 = (EditText) findViewById(R.id.et_guess2);
        tv_guess_chuang = (TextView) findViewById(R.id.tv_guess_chuang);
        tv_guess_min3 = (TextView) findViewById(R.id.tv_guess_min3);
        tv_guess_max3 = (TextView) findViewById(R.id.tv_guess_max3);
        et_guess3 = (EditText) findViewById(R.id.et_guess3);
        tv_guess_zhong = (TextView) findViewById(R.id.tv_guess_zhong);
        tv_guess_min4 = (TextView) findViewById(R.id.tv_guess_min4);
        tv_guess_max4 = (TextView) findViewById(R.id.tv_guess_max4);
        et_guess4 = (EditText) findViewById(R.id.et_guess4);
        tv_guess_role = (TextView) findViewById(R.id.tv_guess_role);
        tv_guess_price = (TextView) findViewById(R.id.tv_guess_price);
        btn_guess = (Button) findViewById(R.id.btn_guess);
        btn_go_buy = (Button) findViewById(R.id.btn_go_buy);
        layout_can_guess = (RelativeLayout) findViewById(R.id.layout_can_guess);
        layout_cannot_guess = (RelativeLayout) findViewById(R.id.layout_cannot_guess);

        left_layout.setOnClickListener(this);
        right_layout.setOnClickListener(this);
        iv_guess_history.setOnClickListener(this);
        btn_guess.setOnClickListener(this);
        btn_go_buy.setOnClickListener(this);
    }

    private void initData() {
        guesspresenter.getGameRuleList(GuessActivity.this);//获取竞猜区间
        guesspresenter.getSystemTime();//获取系统时间
        guesspresenter.getHistoryStreet();//获取大盘指数


    }


    //获取大盘指数成功
    @Override
    public void getHistoryStreetSuccess(Street street) {
        tv_guess_shang.setText(String.format("%.2f", street.getStreetValue()).toUpperCase(Locale.getDefault()));
//        tv_guess_shen.setText(String.format("%.2f", street.getStreetSZValue()));
//        tv_guess_chuang.setText(String.format("%.2f", street.getStreetCYValue()));
//        tv_guess_zhong.setText(String.format("%.2f", street.getStreetZXValue()));

        et_guess1.setHint(String.format("%.0f", street.getStreetValue()));
//        et_guess2.setHint(String.format("%.0f", street.getStreetSZValue()));
//        et_guess3.setHint(String.format("%.0f", street.getStreetCYValue()));
//        et_guess4.setHint(String.format("%.0f", street.getStreetZXValue()));

        dapan1 = String.format("%.0f", street.getStreetValue());
//        dapan2 = String.format("%.0f", street.getStreetSZValue());
//        dapan3 = String.format("%.0f", street.getStreetCYValue());
//        dapan4 = String.format("%.0f", street.getStreetZXValue());


    }

    //设置奖励区间
    @Override
    public void getRuleListSuccess(Interval interval1, Interval interval2, Interval interval3, Interval interval4, String effDate) {

        TCAgent.onEvent(GuessActivity.this, "猜涨跌数据", "第一步");
        tv_guess_min1.setText(Integer.toString(interval1.getMinPoint()));
        tv_guess_max1.setText(Integer.toString(interval1.getMaxPoint()));
//        tv_guess_min2.setText(Integer.toString(interval2.getMinPoint()));
//        tv_guess_max2.setText(Integer.toString(interval2.getMaxPoint()));
//        tv_guess_min3.setText(Integer.toString(interval3.getMinPoint()));
//        tv_guess_max3.setText(Integer.toString(interval3.getMaxPoint()));
//        tv_guess_min4.setText(Integer.toString(interval4.getMinPoint()));
//        tv_guess_max4.setText(Integer.toString(interval4.getMaxPoint()));
        TCAgent.onEvent(GuessActivity.this, "猜涨跌数据", "第2步");


        long dffdate = Long.parseLong(effDate);
        long curtime = System.currentTimeMillis();
        long cha = curtime - dffdate;
        if (cha >= 0) {
            tv_date.setText("下一交易日竞猜即将开始");
            myCount1 = new MyCount1(Long.valueOf(23400000) - cha, 1000);
            myCount1.start();

        } else {
            tv_date.setText("下注竞猜" + DateUtil.timeStamp2Date1(effDate, "MM月dd日") + "猜涨跌");
            myCount = new MyCount(Math.abs(cha), 1000);
            myCount.start();
        }
        TCAgent.onEvent(GuessActivity.this, "猜涨跌数据", "第3步");
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append("1、夺股奇兵预测" + DateUtil.timeStamp2Date1(effDate, "MM月dd日") + "上证指数区间为" + interval1.getMinPoint() + "-" + interval1.getMaxPoint() + ";");
        stringBuilder.append("\n2、如果夺股奇兵预测准确，并且您能猜中指数点位，即可获得");
        SpannableString spanString = new SpannableString(((interval1.getMaxPoint() - interval1.getMinPoint()) * 500) + "");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(spanString);
        stringBuilder.append("宝币;");
        stringBuilder.append("\n3、如果夺股奇兵预测错误，并且您的竞猜点位在预测区间外，即可获得1000宝币；");
        tv_guess_role.setText(stringBuilder);
        TCAgent.onEvent(GuessActivity.this, "猜涨跌数据", "第4步");
    }


    //还没结束的倒计时
    class MyCount extends CountDownTimer {

        /**
         * @param millisInFuture
         * @param countDownInterval
         */
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //00:00:00
            String time = DateUtils.toTimeBySecond(millisUntilFinished / 1000);
            tv_time.setText("距离本次竞猜结束还有" + time);
        }

        @Override
        public void onFinish() {
            myCount.cancel();
            guesspresenter.getGameRuleList(GuessActivity.this);//获取竞猜区间
        }
    }

    //还没开始的倒计时
    class MyCount1 extends CountDownTimer {

        /**
         * @param millisInFuture
         * @param countDownInterval
         */
        public MyCount1(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //00:00:00
            String time = DateUtils.toTimeBySecond(millisUntilFinished / 1000);
            tv_time.setText("距离竞猜开始还有" + time);
        }

        @Override
        public void onFinish() {
            myCount1.cancel();
            guesspresenter.getGameRuleList(GuessActivity.this);//获取竞猜区间
        }
    }

    @Override
    public void getsystemtimeSuccess(String time) {
        Date date = new Date(time);
        String datestr = CalendarUtil.convertDateToString(date);
        SharedPreferencesUtils.putString("join", datestr, this);
        if (!DateUtil.isTrueTime2(date)) {
            ToastUtil.showToast(this, "未到猜涨跌时间");
            et_guess1.setFocusable(false);

        } else {
            et_guess1.setFocusable(true);
            if (UserInfoSingleton.getUserInfo() != null) {
                //获取用户是否可猜
                guesspresenter.GetUserGameInfo(UserInfoSingleton.getUserId());
            }
        }

    }

    //今天已经猜了
    @Override
    public void getTodayGuessSuccess(TodayGuess todayGuess1, TodayGuess todayGuess2, TodayGuess todayGuess3, TodayGuess todayGuess4) {
        et_guess1.setText(Integer.toString(todayGuess1.getGamePoint()));
//        et_guess2.setText(Integer.toString(todayGuess2.getGamePoint()));
//        et_guess3.setText(Integer.toString(todayGuess3.getGamePoint()));
//        et_guess4.setText(Integer.toString(todayGuess4.getGamePoint()));
        layout_can_guess.setVisibility(View.VISIBLE);
        layout_cannot_guess.setVisibility(View.GONE);
        this.todayGuess1 = todayGuess1;
//        this.todayGuess2 = todayGuess2;
//        this.todayGuess3 = todayGuess3;
//        this.todayGuess4 = todayGuess4;
        btn_guess.setEnabled(true);
        btn_guess.setText("修改");
        flag = true;
    }

    //今天还没有猜
    @Override
    public void todayNoGuess() {
        flag = false;
        btn_guess.setText("我来猜");
        if (UserInfoSingleton.getUserInfo() != null) {
            guesspresenter.getBaobi(UserInfoSingleton.getUserId());
        }

    }

    //宝币够用
    @Override
    public void BaobiIsEnough() {
        layout_can_guess.setVisibility(View.VISIBLE);
        layout_cannot_guess.setVisibility(View.GONE);
        btn_guess.setEnabled(true);
    }

    //宝币不够用
    @Override
    public void BaobiIsNotEnugh() {
        layout_can_guess.setVisibility(View.GONE);
        layout_cannot_guess.setVisibility(View.VISIBLE);

    }

    //添加或者更新成功
    @Override
    public void updateOrAddSuccess() {
        DialogUtil.alertDialogTipsNoDraw(this, "提交成功", "您已提交猜涨跌数据，请静候开奖结果");
        guesspresenter.GetUserGameInfo(UserInfoSingleton.getUserId());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.right_layout:
                //弹出游戏规则
                RuleFragment ruleFragment = new RuleFragment();
                ruleFragment.setTitle("猜涨跌游戏规则", R.drawable.ic_guess_rule);
                ruleFragment.setType(2);
                ruleFragment.show(GuessActivity.this.getSupportFragmentManager(), "rule");
                break;
            case R.id.iv_guess_history:
                //跳到猜涨跌历史
                startActivity(new Intent(this, GuessHistoryActivity.class));
                break;
            case R.id.btn_guess:
                //添加和修改

                handleUpdateOrAdd();
                break;
            case R.id.btn_go_buy:
                //跳到充值界面
                startActivity(new Intent(this, BaobiActivity.class));
                break;
        }
    }


    //处理添加和修改
    private void handleUpdateOrAdd() {
        if (flag) {
            //修改数据
            DialogUtil.alertDialogTips(this, "温馨提示", "您将修改已经提交的数据，此操作将不扣除宝币，您确定吗？", new DialogUtil.ICommonCallBack() {
                @Override
                public void excute(Object object) {
                    int shangint = 0;//上证
//                    int shenint = 0;//深证
//                    int chuangint = 0;//创业板
//                    int zhongint = 0;//中小板
                    shangint = Integer.parseInt((et_guess1.getText() != null && !et_guess1.getText().toString().trim().equals("")) ? et_guess1.getText().toString() : "0");
//                    shenint = Integer.parseInt((et_guess2.getText() != null && !et_guess2.getText().toString().trim().equals("")) ? et_guess2.getText().toString() : "0");
//                    chuangint = Integer.parseInt((et_guess3.getText() != null && !et_guess3.getText().toString().trim().equals("")) ? et_guess3.getText().toString() : "0");
//                    zhongint = Integer.parseInt((et_guess4.getText() != null && !et_guess4.getText().toString().trim().equals("")) ? et_guess4.getText().toString() : "0");
                    todayGuess1.setGamePoint(shangint);
//                    todayGuess2.setGamePoint(shenint);
//                    todayGuess3.setGamePoint(chuangint);
//                    todayGuess4.setGamePoint(zhongint);
                    todayGuess1.setCreateDate(CalendarUtil.getCurrentDate("yyyy-MM-dd"));
//                    todayGuess2.setCreateDate(CalendarUtil.getCurrentDate("yyyy-MM-dd"));
//                    todayGuess3.setCreateDate(CalendarUtil.getCurrentDate("yyyy-MM-dd"));
//                    todayGuess4.setCreateDate(CalendarUtil.getCurrentDate("yyyy-MM-dd"));
                    List<TodayGuess> jsonlist = new ArrayList<TodayGuess>();
                    jsonlist.add(todayGuess1);
//                    jsonlist.add(todayGuess2);
//                    jsonlist.add(todayGuess3);
//                    jsonlist.add(todayGuess4);
                    gamejson = JsonUtils.serialize(jsonlist);
                    guesspresenter.GameInfoAddOrUpdate(gamejson);
                }
            }, null);

        } else {
            DialogUtil.alertDialogTips(this, "温馨提示", "您将花费500宝币进行猜涨跌，您确定吗？", new DialogUtil.ICommonCallBack() {
                @Override
                public void excute(Object object) {
                    int shangint = 0;//上证
//                    int shenint = 0;//深证
//                    int chuangint = 0;//创业板
//                    int zhongint = 0;//中小板
                    shangint = Integer.parseInt((et_guess1.getText() != null && !et_guess1.getText().toString().trim().equals("")) ? et_guess1.getText().toString() : dapan1);
//                    shenint = Integer.parseInt((et_guess2.getText() != null && !et_guess2.getText().toString().trim().equals("")) ? et_guess2.getText().toString() : dapan2);
//                    chuangint = Integer.parseInt((et_guess3.getText() != null && !et_guess3.getText().toString().trim().equals("")) ? et_guess3.getText().toString() : dapan3);
//                    zhongint = Integer.parseInt((et_guess4.getText() != null && !et_guess4.getText().toString().trim().equals("")) ? et_guess4.getText().toString() : dapan4);
                    //Integer[] indexs = {shangint, shenint, chuangint, zhongint};
                    Integer[] indexs = {shangint};
                    List<TodayGuess> jsonlist = new ArrayList<TodayGuess>();
                    for (int i = 0; i < 1; i++) {
                        TodayGuess info = new TodayGuess();
                        info.setID(0);
                        info.setUserID(UserInfoSingleton.getUserInfo().getId());
                        info.setGamePoint(indexs[i]);
                        info.setType(i + 1);
                        jsonlist.add(info);
                    }
                    gamejson = JsonUtils.serialize(jsonlist);
                    guesspresenter.GameInfoAddOrUpdate(gamejson);
                }
            }, null);
        }


    }
}
