package com.jhs.taolibao.adapter;

import android.content.Context;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.base.recyclerview.MultiItemCommonAdapter;
import com.jhs.taolibao.base.recyclerview.MultiItemTypeSupport;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.entity.News;
import com.jhs.taolibao.utils.DateUtil;

import java.util.Date;

/**
 * Created by dds on 2016/6/12.
 *
 * @TODO newsAdapter
 */
public class NewsAdapter extends MultiItemCommonAdapter<News> {

    public NewsAdapter(Context context) {
        super(context, new MultiItemTypeSupport<News>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == 0) {
                    return R.layout.item_news_iamge_text;
                }
                if (itemType == 1) {
                    return R.layout.item_news_multi_image;
                }
                return R.layout.item_news_text;
            }

            @Override
            public int getItemViewType(int position, News news) {
                if (news.getDisplaytype() == 3) {
                    return 0;
                }
                if (news.getDisplaytype() == 2) {
                    return 1;
                }
                return 2;
            }
        });
    }

    @Override
    public void convert(ViewHolder holder, News news) {

        switch (holder.getLayoutId()) {

            case R.layout.item_news_iamge_text:
                //图文单张图片
                holder.setText(R.id.tv_news_title, news.getTitle());
                Date date=DateUtil.timeStamp2Date(news.getPublictime());
                holder.setText(R.id.tv_news_time, DateUtil.getTimeString(date));
                holder.setText(R.id.tv_news_ccount, news.getCcount() + "评论");
                if (news.getTitleIMG1() != null || !news.getTitleIMG1().equals("")) {
                    holder.setImageDrawable(R.id.iv_news_icon, WebBiz.UPLOAD_PREFIX + news.getTitleIMG1());
                }
                break;
            case R.layout.item_news_multi_image:
                //图文都多张图片
                holder.setText(R.id.tv_news_dec, news.getTitle());
                Date date1=DateUtil.timeStamp2Date(news.getPublictime());
                holder.setText(R.id.tv_news_time, DateUtil.getTimeString(date1));
                holder.setText(R.id.tv_news_ccount, news.getCcount() + "评论");
                if (news.getTitleIMG1() != null || !news.getTitleIMG1().equals("")) {
                    holder.setImageDrawable(R.id.iv_news_image1, WebBiz.UPLOAD_PREFIX + news.getTitleIMG1());
                    holder.setImageDrawable(R.id.iv_news_image2, WebBiz.UPLOAD_PREFIX + news.getTitleIMG2());
                    holder.setImageDrawable(R.id.iv_news_image3, WebBiz.UPLOAD_PREFIX + news.getTitleIMG3());
                }
                break;
            case R.layout.item_news_text:
                //纯文字
                holder.setText(R.id.tv_news_dec, news.getTitle());
                Date date2=DateUtil.timeStamp2Date(news.getPublictime());
                holder.setText(R.id.tv_news_time, DateUtil.getTimeString(date2));
                holder.setText(R.id.tv_news_ccount, news.getCcount() + "评论");
                break;


        }
    }
}
