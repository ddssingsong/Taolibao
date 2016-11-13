package com.jhs.taolibao.code.simtrade.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.entity.Stock;

import java.util.ArrayList;
import java.util.List;

/**
 * 股票代码查询的按键精灵adapter
 * Created by xujingbo on 2016/7/13.
 */
public class StockWizardAdapter extends BaseAdapter implements Filterable {
    private static final String TAG = "StockWizardAdapter";
    private Context context;
    private List<Stock> datas;
    private ArrayFilter filter;
    private String curEtCode;
    private ArrayList<Stock> unfilteredData;
    public StockWizardAdapter(Context context,List<Stock> datas,String code) {
        this.context = context;
        this.datas = datas;
        this.curEtCode = code;
    }

    public void setDatas(List<Stock> datas){
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Stock getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_stock_autocomplete,null);
            holder.tvCode = (TextView)convertView.findViewById(R.id.auto_tv_code);
            holder.tvName = (TextView)convertView.findViewById(R.id.auto_tv_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Stock stock = datas.get(position);
        if (null != stock){
            holder.tvCode.setText(stock.getCodeWithoutMic());
            holder.tvName.setText(stock.getName());
            if (curEtCode.equals(stock.getCodeWithoutMic())){
                holder.tvCode.setTextColor(context.getResources().getColor(R.color.Black));
                holder.tvName.setTextColor(context.getResources().getColor(R.color.Black));
            }else {
                holder.tvCode.setTextColor(context.getResources().getColor(R.color.Aluminum));
                holder.tvName.setTextColor(context.getResources().getColor(R.color.Aluminum));
            }
        }


        return convertView;
    }

    class ViewHolder{
        TextView tvCode,tvName;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ArrayFilter();
        }
        return filter;
    }

    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (unfilteredData == null) {
                unfilteredData = new ArrayList<Stock>(datas);
            }
            //如果没有输入，显示所有
            if (prefix == null || prefix.length() == 0) {
                ArrayList<Stock> list = unfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();
                Log.d(TAG, "performFiltering: prefixString " +prefixString);
                ArrayList<Stock> unfilteredValues = unfilteredData;
                int count = unfilteredValues.size();

                ArrayList<Stock> newValues = new ArrayList<Stock>(count);

                for (int i = 0; i < count; i++) {
                    Stock pc = unfilteredValues.get(i);
                    if (pc != null) {
                        Log.d(TAG, "performFiltering: code "+ pc.getCode());
                        if(pc.getCode()!=null && pc.getCode().startsWith(prefixString)){

                            newValues.add(pc);
                        }else if(pc.getName()!=null && pc.getName().startsWith(prefixString)){

                            newValues.add(pc);
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //noinspection unchecked
            datas = (List<Stock>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}
