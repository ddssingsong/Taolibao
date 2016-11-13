package com.jhs.taolibao.code.news.model;

/**
 * Created by dds on 2016/6/7.
 *
 * @TODO
 */
public interface NewsModel {
    /**
     * 获取新闻列表数据
     *
     * @param url      新闻url
     * @param page     请求页
     * @param listener 回调接口
     */
    void loadNews(String url, int page, int infoType, String time, NewsModelImpl.OnLoadNewsListListener listener);
}
