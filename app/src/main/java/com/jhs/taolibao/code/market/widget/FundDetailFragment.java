package com.jhs.taolibao.code.market.widget;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.market.prestener.FundPresenterImpl;
import com.jhs.taolibao.code.market.view.FundDetailView;
import com.jhs.taolibao.entity.Fund;


public class FundDetailFragment extends DialogFragment implements OnClickListener, FundDetailView {
    private Fund fund;
    private TextView txt_Arealprice;
    private TextView txt_Brealprice;
    private TextView txt_realprice;
    private TextView txt_matchedValue;
    private TextView txt_fundConvertPrice;
    private TextView txt_ratio;
    private TextView txt_buyprice;
    private TextView txt_saleprice;
    private TextView txt_turnover;
    private Button btn_refresh;
    private ImageView txt_taoli_close;
    View view = null;

    private FundPresenterImpl fundPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (view == null) {
            view = inflater.inflate(R.layout.layout_taolibao_item_detail, container, false);
            initView(view);
            fundPresenter = new FundPresenterImpl(this);
        }

        initListener();
        initData(fund);
        return view;
    }

    private void initListener() {
        btn_refresh.setOnClickListener(this);
        txt_taoli_close.setOnClickListener(this);
    }

    private void initData(Fund fund) {
        String code=fund.getFundACode();
        fundPresenter.getFundDetail(code);
    }


    private void initView(View view) {
        txt_Arealprice = (TextView) view.findViewById(R.id.txt_Arealprice);
        txt_Brealprice = (TextView) view.findViewById(R.id.txt_Brealprice);
        txt_realprice = (TextView) view.findViewById(R.id.txt_realprice);
        txt_matchedValue = (TextView) view.findViewById(R.id.txt_matchedValue);
        txt_fundConvertPrice = (TextView) view.findViewById(R.id.txt_fundConvertPrice);
        txt_ratio = (TextView) view.findViewById(R.id.txt_ratio);
        txt_buyprice = (TextView) view.findViewById(R.id.txt_buyprice);
        txt_saleprice = (TextView) view.findViewById(R.id.txt_saleprice);
        btn_refresh = (Button) view.findViewById(R.id.btn_refresh);
        txt_taoli_close = (ImageView) view.findViewById(R.id.txt_taoli_close);
        txt_turnover = (TextView) view.findViewById(R.id.txt_turnover);


    }


    private void showData(Fund fund) {
        txt_Arealprice.setText(fund.getFundARealPrice() + "");
        txt_Brealprice.setText(fund.getFundBRealPrice() + "");
        txt_realprice.setText(String.format("%.4f", fund.getFundNetValue()));
        txt_matchedValue.setText(String.format("%.4f", fund.getMatchedValue()));
        txt_fundConvertPrice.setText(String.format("%.2f%%", fund.getFundConvertPrice() * 100));
        txt_ratio.setText(String.format("%.0f", fund.getMultiple() * 10) + ":"
                + String.format("%.0f", (1 - fund.getMultiple()) * 10));
        txt_buyprice.setText(fund.getBuyInfo() == null ? "" : fund.getBuyInfo());
        txt_saleprice.setText(fund.getSaleInfo() == null ? "" : fund.getSaleInfo());
        txt_turnover.setText(String.format("%.2f万", Double.parseDouble(fund.getRealtradeNum()) / 10000));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_refresh:
                initData(fund);
                break;
            case R.id.txt_taoli_close:
                this.dismiss();
                break;
        }
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    @Override
    public void getFundDetailSuccess(Fund fund) {
        showData(fund);
    }
}
