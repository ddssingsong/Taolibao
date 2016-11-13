package com.jhs.taolibao.code.simtrade.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhs.taolibao.R;

/**
 * @author jiao on 2016/7/8 11:17
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:"持仓"activity的适配器
 */
public class KeepAdapter extends BaseAdapter{
    @Override
    public int getCount() {
        return 10;
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
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_keep , null);
        TextView t1 = (TextView) convertView.findViewById(R.id.keep_adapter_tv1);
        TextView t2 = (TextView) convertView.findViewById(R.id.keep_adapter_tv2);
        TextView t3 = (TextView) convertView.findViewById(R.id.keep_adapter_tv3);


        return convertView;
    }
}
