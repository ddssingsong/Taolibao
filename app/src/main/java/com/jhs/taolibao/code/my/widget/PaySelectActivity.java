package com.jhs.taolibao.code.my.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.code.my.prestenter.PaySelectPresenterImpl;
import com.jhs.taolibao.code.my.view.PaySelectView;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.utils.StatusBarUtil;
import com.jhs.taolibao.utils.ToastUtil;
import com.jhs.taolibao.view.TitleBar;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 支付方式选择
 */
public class PaySelectActivity extends BaseActivity implements PaySelectView {
    private com.jhs.taolibao.view.TitleBar titlebar;//标题栏
    private RadioGroup radioGroup;
    private RadioButton radio_yinlian;
    private RadioButton radio_huifu;
    private Button submit;
    private int balance;
    private int orderId;
    private String msg = "";
    private PaySelectPresenterImpl paySelectPresenter;

    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.activity_pay_select);
        paySelectPresenter = new PaySelectPresenterImpl(this);
        if (null != getIntent()) {
            balance = getIntent().getIntExtra("balance", 0);
        }
        initView();
        initData();
    }

    private void initView() {
        titlebar = (TitleBar) findViewById(R.id.titlebar);
        radioGroup = (RadioGroup) findViewById(R.id.select_pay);
        radio_yinlian = (RadioButton) findViewById(R.id.radio_yinlian);
        radio_huifu = (RadioButton) findViewById(R.id.radio_huifu);
        submit = (Button) findViewById(R.id.btn_pay);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //银联支付
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == radio_yinlian.getId()) {
                    type = 0;
                }
                if (checkedId == radio_huifu.getId()) {
                    type = 1;
                }
            }
        });
    }

    private void initData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    paySelectPresenter.GetOrderWaterTn(UserInfoSingleton.getUserId(), balance * 100);
                } else if (type == 1) {

                    ToastUtil.showToast(PaySelectActivity.this, "开发中，敬请期待");
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 此处的verify，商户需送去商户后台做验签
                    boolean ret = verify(dataOrg, sign, WebBiz.mMode);
                    if (ret) {
                        msg = "支付成功！";
                        paySelectPresenter.notifyAddBaobi(Integer.toString(orderId));
                    } else {
                        // 验证不通过后的处理
                        // 建议通过商户后台查询支付结果
                        msg = "支付失败！";
                    }
                } catch (JSONException e) {
                }
            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                msg = "支付成功，但未收到签名信息！";
                paySelectPresenter.notifyAddBaobi(Integer.toString(orderId));
            }
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

    }

    private boolean verify(String msg, String sign64, String mode) {
        return true;
    }

    @Override
    public void getTnSuccess(String tn, int orderid) {
        this.orderId = orderid;
        DialogUtil.closeProgressDialog();
        UPPayAssistEx.startPay(PaySelectActivity.this, null, null, tn, WebBiz.mMode);
    }

    @Override
    public void notifyAddBaobisuccess() {
        //  DialogUtil.alertDialogTipsNoDraw(this, "支付结果通知", msg);
        finish();
    }
}
