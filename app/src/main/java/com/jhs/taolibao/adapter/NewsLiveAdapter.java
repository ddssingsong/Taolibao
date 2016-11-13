package com.jhs.taolibao.adapter;

import android.content.Context;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.base.recyclerview.CommonAdapter;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.entity.News;
import com.jhs.taolibao.utils.DateUtil;

/**
 * Created by dds on 2016/6/24.
 *
 * @TODO
 */
public class NewsLiveAdapter extends CommonAdapter<News> {
    public NewsLiveAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }


    @Override
    public void convert(ViewHolder holder, News news) {
        holder.setText(R.id.tv_news_dec, news.getTitle());
        holder.setText(R.id.tv_news_time, DateUtil.timeStamp2Date(news.getPublictime(),"yyyy-MM-dd HH"));
        holder.setText(R.id.tv_news_ccount, news.getCcount() + "评论");
        if (news.getTitleIMG1() != null) {
            holder.setImageDrawable(R.id.iv_news_live_icon, WebBiz.UPLOAD_PREFIX + news.getTitleIMG1());
        }

    }
}
