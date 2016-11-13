package com.jhs.taolibao.adapter;

import android.content.Context;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.recyclerview.CommonAdapter;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.entity.Index;

/**
 * Created by dds on 2016/7/4.
 *
 * @TODO
 */
public class IndexAdapter extends CommonAdapter<Index> {
    public IndexAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Index index) {
        holder.setText(R.id.tv_index1_name, index.getIndexName());
        holder.setText(R.id.tv_index1_value, index.getIndexValue());

        double ratio = Double.parseDouble(index.getRatioRatio());

        if (ratio < 0) {
            holder.setText(R.id.tv_index1_ratio_value, index.getRatioValue());
            holder.setText(R.id.tv_index1_ratio_ratio, String.format("%.2f%%", ratio));
            holder.setTextColorRes(R.id.tv_index1_ratio_ratio, R.color.Olive);
            holder.setTextColorRes(R.id.tv_index1_value, R.color.Olive);
            holder.setTextColorRes(R.id.tv_index1_ratio_value, R.color.Olive);

        } else {
            holder.setText(R.id.tv_index1_ratio_value, "+" + index.getRatioValue());
            holder.setText(R.id.tv_index1_ratio_ratio, "+" + String.format("%.2f%%", ratio));
            holder.setTextColorRes(R.id.tv_index1_ratio_ratio, R.color.Red);
            holder.setTextColorRes(R.id.tv_index1_value, R.color.Red);
            holder.setTextColorRes(R.id.tv_index1_ratio_value, R.color.Red);


        }


    }
}
