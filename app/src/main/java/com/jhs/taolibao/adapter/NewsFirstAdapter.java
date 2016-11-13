package com.jhs.taolibao.adapter;

import android.content.Context;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.recyclerview.CommonAdapter;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.entity.News;
import com.jhs.taolibao.utils.DateUtil;
import com.jhs.taolibao.utils.HtmlUtil;

import java.util.Date;

/**
 * Created by dds on 2016/6/14.
 *
 * @TODO
 */
public class NewsFirstAdapter extends CommonAdapter<News> {

    public NewsFirstAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, News news) {
        Date date = DateUtil.timeStamp2Date(news.getPublictime());
        String time = DateUtil.getTimeString(date);
        holder.setText(R.id.tv_news_time, time);

        // String str=news.getInfoContent();
        String info = HtmlUtil.getTextFromHtml1(news.getInfoContent());
        holder.setText(R.id.tv_news_content, info);
        if (news.getBull() == 1) {
            holder.setImageResource(R.id.iv_dapan_rate, R.drawable.ic_news_rise);
        } else if (news.getBull() == 2) {
            holder.setImageResource(R.id.iv_dapan_rate, R.drawable.ic_news_fall);
        } else {
            holder.setImageResource(R.id.iv_dapan_rate, R.drawable.ic_news_emery);
        }

    }
}