package com.jhs.taolibao.adapter;

import android.content.Context;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.recyclerview.CommonAdapter;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.entity.HotIndustry;

/**
 * Created by dds on 2016/7/4.
 *
 * @TODO
 */
public class HotIndustryAdapter extends CommonAdapter<HotIndustry> {

    public HotIndustryAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, HotIndustry hotIndustry) {
        holder.setText(R.id.tv_industry_name, hotIndustry.getIndustryName());

        double ratio = Double.parseDouble(hotIndustry.getIndustryRatio());
        if (ratio < 0) {
            holder.setTextColorRes(R.id.tv_industry_ratio, R.color.Olive);
            holder.setText(R.id.tv_industry_ratio, String.format("%.2f%%", ratio));
        } else {
            holder.setTextColorRes(R.id.tv_industry_ratio, R.color.Red);
            holder.setText(R.id.tv_industry_ratio, "+"+String.format("%.2f%%", ratio));
        }
        String stockname=hotIndustry.getStockName().replace("&nbsp;","");
        holder.setText(R.id.tv_stock_name, stockname);
        holder.setText(R.id.tv_stock_price, hotIndustry.getStockPrice());

        double stockratio = Double.parseDouble(hotIndustry.getStockRatio());
        if(stockratio<0){
            holder.setText(R.id.tv_stock_ratio, String.format("%.2f%%", stockratio));
        }else{
            holder.setText(R.id.tv_stock_ratio, "+"+String.format("%.2f%%", stockratio));
        }




    }
}
