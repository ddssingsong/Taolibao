package com.jhs.taolibao.adapter;

import android.content.Context;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.recyclerview.CommonAdapter;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.entity.MyStock;

/**
 * Created by dds on 2016/7/12.
 *
 * @TODO
 */
public class MyStockAdapter extends CommonAdapter<MyStock> {
    public MyStockAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, MyStock myStock) {
        holder.setText(R.id.tv_stock_name, myStock.getStockName());
        String code = myStock.getStockCode();
        if (code.contains("sz") || code.contains("sh")) {
            code = code.substring(2);
        }
        holder.setText(R.id.tv_stock_code, code);
        holder.setText(R.id.tv_stock_dealprice, myStock.getPrice());

        if (myStock.getRatio().equals("")) {
            holder.setText(R.id.tv_stock_ratio, "已停牌");
            holder.setTextColorRes(R.id.tv_stock_ratio, R.color.Gunpowder);
        } else {
            double ratio = Double.parseDouble(myStock.getRatio());
            if (ratio >= 0) {
                holder.setText(R.id.tv_stock_ratio, "+"+String.format("%.2f%%", ratio));
                holder.setTextColorRes(R.id.tv_stock_ratio, R.color.Red);
            } else {
                holder.setText(R.id.tv_stock_ratio, String.format("%.2f%%", ratio));
                holder.setTextColorRes(R.id.tv_stock_ratio, R.color.Olive);
            }

        }
    }
}
