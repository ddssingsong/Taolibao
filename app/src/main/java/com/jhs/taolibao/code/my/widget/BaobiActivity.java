package com.jhs.taolibao.code.my.widget;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.code.my.prestenter.BaobiPresenterImpl;
import com.jhs.taolibao.code.my.view.BaobiView;
import com.jhs.taolibao.utils.StatusBarUtil;
import com.jhs.taolibao.utils.ToastUtil;

public class BaobiActivity extends BaseActivity implements View.OnClickListener, BaobiView {
    private com.jhs.taolibao.view.TitleBar titlebar;//标题栏
    private TextView tv_baobi_baobi;//显示宝币数量
    private RelativeLayout layout_baobi_30;
    private RelativeLayout layout_baobi_50;
    private RelativeLayout layout_baobi_108;

    private RelativeLayout layout_baobi_so;
    private EditText et_baobi_edit;//自由冲输入
    private TextView tv_baobi_so;//自由冲显示

    private BaobiPresenterImpl baobiPresenter;
    private int balance = 10;
    private int totalpoit = 0;
    private int orderId;


    private String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.activity_baobi);
        baobiPresenter = new BaobiPresenterImpl(this);
        initView();
    }


    private void initView() {
        tv_baobi_baobi = (TextView) findViewById(R.id.tv_baobi_baobi);
        layout_baobi_30 = (RelativeLayout) findViewById(R.id.layout_baobi_30);
        layout_baobi_50 = (RelativeLayout) findViewById(R.id.layout_baobi_50);
        layout_baobi_108 = (RelativeLayout) findViewById(R.id.layout_baobi_108);
        layout_baobi_so = (RelativeLayout) findViewById(R.id.layout_baobi_so);
        et_baobi_edit = (EditText) findViewById(R.id.et_baobi_edit);
        tv_baobi_so = (TextView) findViewById(R.id.tv_baobi_so);
        titlebar = (com.jhs.taolibao.view.TitleBar) findViewById(R.id.titlebar);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout_baobi_30.setOnClickListener(this);
        layout_baobi_50.setOnClickListener(this);
        layout_baobi_108.setOnClickListener(this);
        layout_baobi_so.setOnClickListener(this);
        et_baobi_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_baobi_edit.getText() != null && !et_baobi_edit.getText().toString().equals("")) {
                    String baobistr = et_baobi_edit.getText().toString();
                    int baobi = Integer.parseInt(baobistr);
                    double qian = baobi / 100.0;
                    tv_baobi_so.setText("￥" + qian);
                } else {
                    tv_baobi_so.setText("￥" + "0.00");
                }


            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        baobiPresenter.GetuserInfo(UserInfoSingleton.getUserId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_baobi_30:
                //冲30
                balance = 30;
                break;
            case R.id.layout_baobi_50:
                balance = 50;
                //冲50
                break;
            case R.id.layout_baobi_108:
                balance = 108;
                //冲108
                break;
            case R.id.layout_baobi_so:
                if (et_baobi_edit.getText() != null && !et_baobi_edit.getText().toString().equals("")) {
                    balance = (Integer.parseInt(et_baobi_edit.getText().toString().trim())) / 100;
                } else {
                    ToastUtil.showToast(this, "请输入正确的金额");
                }
                //自由冲
                break;
        }
        startActivityForResult(new Intent(this, PaySelectActivity.class).putExtra("balance", balance), 1);


    }

    ///获取用户宝币数成功
    @Override
    public void getUserBalanceSuccess(int totalpoint) {
        this.totalpoit = totalpoint;
        tv_baobi_baobi.setText(totalpoint + "");
    }


}
