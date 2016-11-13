package com.jhs.taolibao.code.market.widget;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.base.recyclerview.CommonAdapter;
import com.jhs.taolibao.base.recyclerview.OnItemClickListener;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.code.market.prestener.HotIndustryPresenerImpl;
import com.jhs.taolibao.code.market.view.HotIndutryView;
import com.jhs.taolibao.entity.HotIndustry;
import com.jhs.taolibao.view.TitleBar;

import java.util.List;

/**
 * Created by dds on 2016/7/5.
 *
 * @TODO 更多行业涨幅
 */
public class IndustryListFragment extends BaseFragment implements HotIndutryView {
    private TitleBar titleBar;
    private TextView tv_industry_up_down;
    private XRecyclerView recycle_industry;
    private CommonAdapter adapter;

    private HotIndustryPresenerImpl hotIndustryPresener;

    private int page = 1;
    private int pagesize = 15;

    @Override
    protected boolean onBackPressed() {
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotIndustryPresener = new HotIndustryPresenerImpl(this);
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_industry_list, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        titleBar = (TitleBar) rootView.findViewById(R.id.titlebar);
        tv_industry_up_down = (TextView) rootView.findViewById(R.id.tv_industry_up_down);
        recycle_industry = (XRecyclerView) rootView.findViewById(R.id.recycle_industry);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackToActivity(1, null);
            }
        });
        //tv_industry_up_down.setOnClickListener(this);
    }

    @Override
    public void onInitloadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_industry.setLayoutManager(linearLayoutManager);
        adapter = new CommonAdapter<HotIndustry>(getActivity(), R.layout.item_industry_alllist) {


            @Override
            public void convert(ViewHolder holder, HotIndustry hotIndustry) {
                holder.setText(R.id.tv_industry_name, hotIndustry.getIndustryName());
                String topstock = hotIndustry.getStockName().replace("&nbsp;", "");
                holder.setText(R.id.tv_industry_top_stock, topstock);

                double ratio = Double.parseDouble(hotIndustry.getIndustryRatio());
                if (ratio < 0) {
                    holder.setTextColorRes(R.id.tv_industry_up_down, R.color.Olive);
                    holder.setText(R.id.tv_industry_up_down, String.format("%.2f%%", ratio));
                } else {
                    holder.setTextColorRes(R.id.tv_industry_up_down, R.color.Red);
                    holder.setText(R.id.tv_industry_up_down, "+" + String.format("%.2f%%", ratio));
                }


            }
        };
        recycle_industry.setAdapter(adapter);
        recycle_industry.setLoadingListener(listener);
        hotIndustryPresener.getHotIndustry(page, pagesize);
    }


    XRecyclerView.LoadingListener listener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            page = 1;
            hotIndustryPresener.getHotIndustry(page, pagesize);
        }

        @Override
        public void onLoadMore() {
            page++;
            hotIndustryPresener.getHotIndustry(page, pagesize);
        }
    };

    @Override
    public void GetHotIndustrySuccess(List<HotIndustry> hotIndustries) {
        if (page == 1) {
            adapter.addDataToAdapater(hotIndustries, true);
            recycle_industry.refreshComplete();
        } else {
            adapter.addDataToAdapater(hotIndustries, false);
            recycle_industry.loadMoreComplete();
        }

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                HotIndustry index = (HotIndustry) o;
                String industry = index.getIndustryName();
                if (!industry.equals("")) {

                    callbackToActivity(2,industry);
                }

            }
        });
    }
}
