package com.jhs.taolibao.adapter;

import android.content.Context;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.recyclerview.CommonAdapter;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.entity.Stock;

/**
 * Created by dds on 2016/7/5.
 *
 * @TODO
 */
public class StockRiseAdapter extends CommonAdapter<Stock> {
    public StockRiseAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Stock stock) {
        String stockname = stock.getStockName().replace("&nbsp;", " ");
        holder.setText(R.id.tv_stock_name, stockname);
        String stockcode=stock.getStockCode();
        holder.setText(R.id.tv_stock_code, stockcode);
        holder.setText(R.id.tv_stock_dealprice, stock.getDealPrice());


        double rate=Double.parseDouble(stock.getChangeRate());
        if(rate>=0){
            holder.setTextColorRes(R.id.tv_stock_ratio, R.color.Red);
            holder.setText(R.id.tv_stock_ratio, "+"+String.format("%.2f%%",rate));
        }else{
            holder.setTextColorRes(R.id.tv_stock_ratio, R.color.Olive);
            holder.setText(R.id.tv_stock_ratio, String.format("%.2f%%",rate));
        }

    }
}
