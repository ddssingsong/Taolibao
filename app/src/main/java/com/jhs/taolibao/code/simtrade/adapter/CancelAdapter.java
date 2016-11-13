package com.jhs.taolibao.code.simtrade.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.entity.Stock;

import java.util.List;

/**
 * Created by KANGXIANGTAO on 2016/7/11.
 */
public class CancelAdapter extends BaseAdapter {

    private Context ctx;
    private List<Stock> dataList;
    public CancelAdapter(Context context, List<Stock> dataList){
        this.ctx = context;
        this.dataList = dataList;
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_stock_info,null);
            holder.tvName = (TextView)convertView.findViewById(R.id.tv_stock_name);
            holder.tvCode = (TextView)convertView.findViewById(R.id.tv_stock_code);
            holder.tvPrice = (TextView)convertView.findViewById(R.id.tv_stock_profit_loss);
            holder.tvDeleNum = (TextView)convertView.findViewById(R.id.tv_stock_profit_loss_persent);
            holder.tvStyle = (TextView)convertView.findViewById(R.id.tv_stock_cost);
            holder.tvDealNum = (TextView)convertView.findViewById(R.id.tv_stock_current_price);
            holder.tvStatus = (TextView)convertView.findViewById(R.id.tv_stock_keep_count);
            holder.tvTime = (TextView)convertView.findViewById(R.id.tv_stock_available_persent);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvStatus.setTextColor(ctx.getResources().getColor(R.color.Olive));
        holder.tvTime.setTextColor(Color.BLACK);
        holder.tvDeleNum.setTextColor(Color.BLACK);
        holder.tvStyle.setTextColor(Color.BLACK);
        holder.tvPrice.setTextColor(Color.BLACK);
        // 设置字体颜色
        holder.tvDealNum.setTextColor(Color.GRAY);
        Stock stock = dataList.get(position);
        if (null != stock){
            holder.tvName.setText(stock.getName());
            holder.tvCode.setText(stock.getCode());
            holder.tvPrice.setText(stock.getEntrustPrice());
            holder.tvDeleNum.setText(stock.getEntrustAmount());
            holder.tvStatus.setText(stock.getEntrustStatus());
            holder.tvTime.setText(stock.getEntrustTime());
            holder.tvStyle.setText(stock.getEntrustBs());
            holder.tvDealNum.setText(stock.getBusinessAmount());
        }
        return convertView;
    }


    class ViewHolder{
        TextView tvName,tvCode,tvPrice,tvDeleNum,tvStatus,tvTime,tvStyle,tvDealNum;
    }
}
