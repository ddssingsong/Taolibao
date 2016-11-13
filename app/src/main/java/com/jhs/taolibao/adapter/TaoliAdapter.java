package com.jhs.taolibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.entity.Fund;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

public class TaoliAdapter extends BaseAdapter {
    private Context context;
    List<Fund> list;


    public TaoliAdapter(Context context, List<Fund> models) {
        super();
        this.context = context;
        this.list = models;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_taoli, parent, false);
            viewHold.textView0 = (TextView) convertView.findViewById(R.id.txt_taolibaolist_item_fundAName);
            viewHold.textView1 = (TextView) convertView.findViewById(R.id.txt_taolibaolist_item_fundACode);
            viewHold.textView2 = (TextView) convertView.findViewById(R.id.txt_taolibaolist_item_fundARealprice);
            viewHold.textView3 = (TextView) convertView.findViewById(R.id.txt_taolibaolist_item_fundBName);
            viewHold.textView4 = (TextView) convertView.findViewById(R.id.txt_taolibaolist_item_fundBCode);
            viewHold.textView5 = (TextView) convertView.findViewById(R.id.txt_taolibaolist_item_fundBRealprice);
            viewHold.textView6 = (TextView) convertView.findViewById(R.id.txt_taolibaolist_item_fundMName);
            viewHold.textView7 = (TextView) convertView.findViewById(R.id.txt_taolibaolist_item_fundMCode);
            viewHold.textView8 = (TextView) convertView.findViewById(R.id.txt_taolibaolist_item_fundMRealprice);
            viewHold.textView9 = (TextView) convertView.findViewById(R.id.txt_taolibaolist_item_fundConvertPrice);

            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        AutoUtils.autoSize(convertView);
        Fund fund = (Fund) getItem(position);
        viewHold.textView0.setText(fund.getFundAName());
        viewHold.textView1.setText(fund.getFundACode());
        viewHold.textView2.setText(String.format("%.4f", fund.getFundARealPrice()));
        viewHold.textView3.setText(fund.getFundBName());
        viewHold.textView4.setText(fund.getFundBCode());
        viewHold.textView5.setText(String.format("%.4f", fund.getFundBRealPrice()));
        viewHold.textView6.setText(fund.getFundName());
        viewHold.textView7.setText(fund.getFundCode());
        viewHold.textView8.setText(String.format("%.4f", fund.getMatchedValue()));
        viewHold.textView9.setText(String.format("%.2f%%", fund.getFundConvertPrice() * 100));

        if (fund.getFundARatio() >= 0) {
            viewHold.textView2.setTextColor(context.getResources().getColor(R.color.Red));
        } else {
            viewHold.textView2.setTextColor(context.getResources().getColor(R.color.Olive));
        }
        if (fund.getFundBRatio() >= 0) {
            viewHold.textView5.setTextColor(context.getResources().getColor(R.color.Red));
        } else {
            viewHold.textView5.setTextColor(context.getResources().getColor(R.color.Olive));
        }

        return convertView;
    }

    static class ViewHold {

        TextView textView0, textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9;

    }

}
