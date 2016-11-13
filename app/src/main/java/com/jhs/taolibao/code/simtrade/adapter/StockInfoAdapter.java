package com.jhs.taolibao.code.simtrade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.entity.Stock;

import java.util.List;

/**
 * 股票行情adapter
 *
 * Created by xujingbo on 2016/7/8.
 */
public class StockInfoAdapter extends BaseAdapter{
    private static final String TAG = "StockInfoAdapter";
    List<Stock> datas;
    private Context context;
    public StockInfoAdapter(Context context,List<Stock> datas){
        this.context = context;
        this.datas = datas;
    }

    public void setDatas(List<Stock> datas){
        this.datas = datas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_stock_info,null);
            holder.tvName = (TextView)convertView.findViewById(R.id.tv_stock_name);
            holder.tvCode = (TextView)convertView.findViewById(R.id.tv_stock_code);
            holder.tvProfit = (TextView)convertView.findViewById(R.id.tv_stock_profit_loss);
            holder.tvProfitPercent = (TextView)convertView.findViewById(R.id.tv_stock_profit_loss_persent);
            holder.tvOriPrice = (TextView)convertView.findViewById(R.id.tv_stock_cost);
            holder.tvCurPrice = (TextView)convertView.findViewById(R.id.tv_stock_current_price);
            holder.tvKeepCount = (TextView)convertView.findViewById(R.id.tv_stock_keep_count);
            holder.tvAvailablePercent = (TextView)convertView.findViewById(R.id.tv_stock_available_persent);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        Stock stock = datas.get(position);
        if (null != stock){
            holder.tvName.setText(stock.getName());
            holder.tvCode.setText(stock.getCode());
            holder.tvOriPrice.setText(String.valueOf(stock.getOriPrice()));
            holder.tvCurPrice.setText(String.valueOf(stock.getCurPrice()));
            holder.tvKeepCount.setText(String.valueOf(stock.getCount()));
            holder.tvAvailablePercent.setText("15.3%");
            holder.tvProfit.setText("345");
            holder.tvProfitPercent.setText("4.07%");
        }
        return convertView;
    }

    class ViewHolder{
        TextView tvName,tvCode;
        TextView tvProfit,tvProfitPercent;
        TextView tvOriPrice,tvCurPrice;
        TextView tvKeepCount,tvAvailablePercent;

    }
}
