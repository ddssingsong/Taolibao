package com.jhs.taolibao.code.my.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.code.my.prestenter.ExchangePresenterImpl;
import com.jhs.taolibao.code.my.view.ExchangeView;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.utils.StatusBarUtil;
import com.jhs.taolibao.utils.ToastUtil;

public class ExchangeActivity extends BaseActivity implements ExchangeView, View.OnClickListener {

    private com.jhs.taolibao.view.TitleBar titlebar;//标题
    private TextView tv_baobi_baobi; //宝币数量
    private TextView tv_exchange_type;//兑换类型
    private TextView tv_exchange_price;//兑换价格
    private TextView tv_exchange_baobi;//兑换所需宝币
    private int totalpoint = 0;

    private RelativeLayout layout_change_jingdong;

    private ExchangePresenterImpl exchangePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exchangePresenter = new ExchangePresenterImpl(this);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.activity_exchange);
        initView();
        initData();
    }


    private void initView() {
        layout_change_jingdong = (RelativeLayout) findViewById(R.id.layout_change_jingdong);
        titlebar = (com.jhs.taolibao.view.TitleBar) findViewById(R.id.titlebar);
        tv_baobi_baobi = (TextView) findViewById(R.id.tv_baobi_baobi);
        tv_exchange_type = (TextView) findViewById(R.id.tv_exchange_type);
        tv_exchange_price = (TextView) findViewById(R.id.tv_exchange_price);
        tv_exchange_baobi = (TextView) findViewById(R.id.tv_exchange_baobi);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout_change_jingdong.setOnClickListener(this);
    }

    private void initData() {
        exchangePresenter.GetuserInfo(UserInfoSingleton.getUserId());
    }

    @Override
    public void getUserBalanceSuccess(int totalpoint) {
        this.totalpoint = totalpoint;
        tv_baobi_baobi.setText(Integer.toString(totalpoint));
    }

    //兑换成功
    @Override
    public void changeSuccess() {
        DialogUtil.alertDialogTipsNoDraw(this, "恭喜", "恭喜您兑换价值500元的京东卡成功，请等待发放京东卡");
        exchangePresenter.GetuserInfo(UserInfoSingleton.getUserId());
    }

    @Override
    public void changeFaided() {
        ToastUtil.showToast(this, "兑换失败error-1");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_change_jingdong:
                //兑换京东卡
                if (totalpoint >= 50000) {
                    String content = "您将花费50000宝币兑换价值500元的京东卡，您确定吗？";
                    DialogUtil.alertDialogTips(this, "请您确认", content, new DialogUtil.ICommonCallBack() {
                        @Override
                        public void excute(Object object) {
                            exchangePresenter.ExchangeJingdong(UserInfoSingleton.getUserId(), 50000);
                        }
                    }, null);

                } else {
                    ToastUtil.showToast(this, "宝币不足");
                }


                break;
        }
    }
}
