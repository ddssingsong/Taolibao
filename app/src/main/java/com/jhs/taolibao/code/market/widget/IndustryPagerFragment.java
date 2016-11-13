package com.jhs.taolibao.code.market.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhs.taolibao.R;
import com.jhs.taolibao.adapter.HotIndustryAdapter;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.base.recyclerview.DividerItemDecoration;
import com.jhs.taolibao.base.recyclerview.OnItemClickListener;
import com.jhs.taolibao.code.market.StockListActivity;
import com.jhs.taolibao.code.market.prestener.HotIndustryPresenerImpl;
import com.jhs.taolibao.code.market.view.HotIndutryView;
import com.jhs.taolibao.entity.HotIndustry;

import java.util.List;

/**
 * Created by dds on 2016/7/4.
 *
 * @TODO
 */
public class IndustryPagerFragment extends BaseFragment implements HotIndutryView, OnItemClickListener {
    private RecyclerView recycle_hot_industry;
    private HotIndustryAdapter adapter;


    private HotIndustryPresenerImpl hotIndustryPresener;

    private int page = 1;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            hotIndustryPresener.getHotIndustry(page, 6);
            handler.postDelayed(this, 6000);//6秒刷新一次
        }

    };

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    public static IndustryPagerFragment newInstance(int page) {
        Bundle args = new Bundle();
        IndustryPagerFragment fragment = new IndustryPagerFragment();
        args.putInt("page", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotIndustryPresener = new HotIndustryPresenerImpl(this);
        page = getArguments().getInt("page");

    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_industry_pager_item, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        recycle_hot_industry = (RecyclerView) rootView.findViewById(R.id.recycle_hot_industry);


    }

    @Override
    public void onInitloadData() {
        adapter = new HotIndustryAdapter(getActivity(), R.layout.item_hot_industry);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 3);
        recycle_hot_industry.setLayoutManager(linearLayoutManager);
        recycle_hot_industry.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        recycle_hot_industry.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycle_hot_industry.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        handler.postDelayed(runnable, 6000); //开始刷新


    }


    @Override
    public void ResetData() {
        super.ResetData();
        hotIndustryPresener.getHotIndustry(page, 6);
    }

    @Override
    public void GetHotIndustrySuccess(List<HotIndustry> hotIndustries) {
        adapter.addDataToAdapater(hotIndustries,true);

    }

    @Override
    public void onItemClick(ViewGroup parent, View view, Object object, int position) {
        HotIndustry hotIndustry = (HotIndustry) object;
        String industry = hotIndustry.getIndustryName();
        String industrycode = hotIndustry.getIndustryCode();
        Intent intent = new Intent(getActivity(), StockListActivity.class);
        intent.putExtra("type", "industry2");
        intent.putExtra("industry", industry);
        intent.putExtra("industrycode", industrycode);
        startActivity(intent);

    }
}
