package com.jhs.taolibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;


public class ALeftAdapter extends BaseAdapter {

    private Context context;
    private List<String> list = new ArrayList<>();

    public ALeftAdapter(Context context) {
        super();
        this.context = context;
    }

    public void AddDataToAdapter(List<String> list) {
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
        ViewHold hold;
        if (convertView == null) {
            hold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_left_item, null);
            hold.textView = (TextView) convertView.findViewById(R.id.left_container_textview0);
            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }
        AutoUtils.autoSize(convertView);
        hold.textView.setText(list.get(position));
        return convertView;
    }

    static class ViewHold {
        TextView textView;
    }

}
