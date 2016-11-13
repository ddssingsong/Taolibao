package com.jhs.taolibao.code.simtrade.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.entity.KeepBean;

import java.text.NumberFormat;
import java.util.List;

/**
 * @author jiao on 2016/7/8 15:40
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:
 */
public class KeepAdapter2 extends BaseAdapter {
    private List<KeepBean.KeepItem> mList;

    public void setData(List<KeepBean.KeepItem> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public KeepBean.KeepItem getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stock_info,
                    null, false);

            holder.name1 = (TextView) convertView.findViewById(R.id.tv_stock_name);
            holder.name2 = (TextView) convertView.findViewById(R.id.tv_stock_code);
            holder.profit1 = (TextView) convertView.findViewById(R.id.tv_stock_profit_loss);
            holder.profit2 = (TextView) convertView.findViewById(R.id.tv_stock_profit_loss_persent);
            holder.keep1 = (TextView) convertView.findViewById(R.id.tv_stock_keep_count);
            holder.keep2 = (TextView) convertView.findViewById(R.id.tv_stock_available_persent);
            holder.cost1 = (TextView) convertView.findViewById(R.id.tv_stock_cost);
            holder.cost2 = (TextView) convertView.findViewById(R.id.tv_stock_current_price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        KeepBean.KeepItem keepItem = mList.get(position);
        holder.name1.setText(keepItem.getStock_name());
        holder.name2.setText(keepItem.getStock_code1());
        if (keepItem.getIncome_balance() != null && !keepItem.getIncome_balance().equals("")) {
            if (Double.valueOf(keepItem.getIncome_balance()) < 0) {
                holder.profit1.setTextColor(parent.getContext().getResources().getColor(R.color.Olive));
            } else {
                holder.profit1.setTextColor(parent.getContext().getResources().getColor(R.color.Red));
            }
        }
//        if (keepItem.getDay_income_balance() != null && !keepItem.getDay_income_balance().equals("")) {
//            //转成百分比
//            Log.d("keep" , keepItem.getDay_income_balance());
//            NumberFormat fmt = NumberFormat.getPercentInstance();
//            fmt.setMaximumFractionDigits(2);
//            String format = fmt.format((Double.valueOf(keepItem.getDay_income_balance())));
//            String str = format + "%";
//            holder.profit2.setText(str);
//            //设置颜色---<0是绿色,>=0是红色
//            if (Double.valueOf(keepItem.getDay_income_balance()) < 0) {
//                holder.profit2.setTextColor(Color.GREEN);
//            } else {
//                holder.profit2.setTextColor(Color.RED);
//            }
//        }
        double v = (keepItem.getLast_price() - keepItem.getCost_price()) / keepItem.getCost_price();
        if (v < 0){
            holder.profit2.setTextColor(parent.getContext().getResources().getColor(R.color.Olive));
        }else{
            holder.profit2.setTextColor(parent.getContext().getResources().getColor(R.color.Red));
        }
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(2);
        String format = fmt.format(v);
        holder.profit2.setText(format);

        holder.profit1.setText(keepItem.getIncome_balance());
//        holder.profit2.setText(keepItem.getDay_income_balance());
        holder.keep1.setText(keepItem.getCurrent_amount());
        holder.keep2.setText(keepItem.getEnable_amount());
        holder.cost1.setText(String.valueOf(keepItem.getCost_price()));
        holder.cost2.setText(String.valueOf(keepItem.getLast_price()));

        return convertView;
    }

    class ViewHolder {
        //一共两层数据,1为第一层,2为第二层
        TextView name1, name2, profit1, profit2, keep1, keep2, cost1, cost2;
    }
}
