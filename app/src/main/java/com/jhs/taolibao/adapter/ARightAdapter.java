package com.jhs.taolibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.entity.Fund;

import java.util.ArrayList;
import java.util.List;

public class ARightAdapter extends BaseAdapter {
    private Context context;
    List<Fund> list=new ArrayList<>();


    public ARightAdapter(Context context, List<Fund> models) {
        super();
        this.context = context;
        this.list = models;
    }

    public ARightAdapter(Context context) {
        super();
        this.context = context;
    }

    public void addDataToAdapter(List<Fund> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_right_item_a, parent, false);
            viewHold.textView0 = (TextView) convertView.findViewById(R.id.right_item_textview0);
            viewHold.textView1 = (TextView) convertView.findViewById(R.id.right_item_textview1);
            viewHold.textView2 = (TextView) convertView.findViewById(R.id.right_item_textview2);
            viewHold.textView3 = (TextView) convertView.findViewById(R.id.right_item_textview3);
            viewHold.textView4 = (TextView) convertView.findViewById(R.id.right_item_textview4);
            viewHold.textView5 = (TextView) convertView.findViewById(R.id.right_item_textview5);
            viewHold.textView6 = (TextView) convertView.findViewById(R.id.right_item_textview6);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }

        Fund fund = (Fund) getItem(position);
        viewHold.textView0.setText(fund.getFundACode());
        viewHold.textView1.setText(String.format("%.4f", fund.getFundARealPrice()));

        viewHold.textView2.setText(String.format("%.2f%%", fund.getFundAConvertPrice() * 100));
        viewHold.textView3.setText(fund.getStandardConvertDate());
        viewHold.textView4.setText(String.format("%.2f", fund.getFundARate()));
        viewHold.textView5.setText(String.format("%.2f%%", fund.getFundNeedRatio() * 100.0));
        viewHold.textView6.setText(String.format("%.2f%%", fund.getFundARatio()));


        if (fund.getFundARatio() < 0) {
            viewHold.textView1.setTextColor(context.getResources().getColor(R.color.Olive));
        } else {
            viewHold.textView1.setTextColor(context.getResources().getColor(R.color.Red));
        }


        return convertView;
    }

    static class ViewHold {

        TextView textView0, textView1, textView2, textView3, textView4, textView5, textView6;

    }

}
