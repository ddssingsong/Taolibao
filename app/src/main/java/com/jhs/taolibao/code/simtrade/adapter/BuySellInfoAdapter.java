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
 * 买入和卖出界面 买 卖top5信息adapter
 * Created by xujingbo on 2016/7/8.
 */
public class BuySellInfoAdapter extends BaseAdapter{
    private Context context;
    private List<Stock.Grp> datas;
    private Stock.Type curType ;
    public BuySellInfoAdapter(Context context, List<Stock.Grp> datas, Stock.Type type){
        this.context = context;
        this.datas = datas;
        this.curType = type;
    }

    public void setDatas(List<Stock.Grp> datas){
        this.datas = datas;
    }
    @Override
    public int getCount() {
        return 5;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_buy_sell_top_info,null);
            holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_buy_sell_order);
            holder.tvPrice = (TextView)convertView.findViewById(R.id.tv_buy_sell_price);
            holder.tvCount = (TextView)convertView.findViewById(R.id.tv_buy_sell_total_count);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }


        if (curType == Stock.Type.SELL){
            holder.tvTitle.setText("卖"+String.valueOf(getCount() - position));
            holder.tvPrice.setTextColor(context.getResources().getColor(R.color.Olive));
        }else {
            holder.tvTitle.setText("买"+String.valueOf(position + 1));
            holder.tvPrice.setTextColor(context.getResources().getColor(R.color.Red));
        }
        if (datas.size() > position) {
            Stock.Grp grp = datas.get(position);
            if (null != grp) {
                if (null != grp.getPrice()) {
                    holder.tvPrice.setText(String.valueOf(grp.getPrice()));

                }
                if (null != grp.getCount()) {
                    holder.tvCount.setText(String.valueOf(grp.getCount()));
                }
            }
        }else {
            holder.tvPrice.setText("--");
            holder.tvCount.setText("--");
        }

        return convertView;
    }

    class ViewHolder{
        TextView tvTitle,tvPrice,tvCount;
    }
}
