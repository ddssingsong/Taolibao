package com.jhs.taolibao.code.market.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.adapter.TaoliAdapter;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.market.prestener.FundPresenterImpl;
import com.jhs.taolibao.code.market.view.FundTaoliView;
import com.jhs.taolibao.entity.Fund;

import java.util.List;

/**
 * Created by dds on 2016/7/11.
 *
 * @TODO
 */
public class FundTaoliFragment extends BaseFragment implements FundTaoliView, AdapterView.OnItemClickListener {
    private ListView list_taoli;
    private TaoliAdapter adapter;
    private FundPresenterImpl fundPresenter;
    String stockId = "";

    private RelativeLayout layout_faild;//错误界面
    private TextView tv_faild_msg;
    @Override
    protected boolean onBackPressed() {
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fundPresenter = new FundPresenterImpl(this);
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fund_taoli, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        list_taoli = (ListView) rootView.findViewById(R.id.list_taoli);
        layout_faild = (RelativeLayout) rootView.findViewById(R.id.layout_faild);
        tv_faild_msg = (TextView) rootView.findViewById(R.id.tv_faild_msg);
        list_taoli.setOnItemClickListener(this);
    }

    @Override
    public void onInitloadData() {
        fundPresenter.getFundTaoli(stockId, true);
    }

    @Override
    public void getFundListSuccess(List<Fund> list) {
        adapter = new TaoliAdapter(getActivity(), list);
        list_taoli.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        layout_faild.setVisibility(View.VISIBLE);
        list_taoli.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        layout_faild.setVisibility(View.GONE);
        list_taoli.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fund fund = (Fund) parent.getAdapter().getItem(position);
        FundDetailFragment detailFragment = new FundDetailFragment();
        detailFragment.show(getChildFragmentManager(), "TaolibaoDetail");
        detailFragment.setFund(fund);
    }

    public void searchStock(String keyword) {
        fundPresenter.getFundTaoli(keyword, true);

    }
}
