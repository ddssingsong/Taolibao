package com.jhs.taolibao.code.news.presenter;

/**
 * Created by dds on 2016/6/12.
 *
 * @TODO
 */
public interface NewsPresenter {
    void loadFirstNews(int type, int page, String time,boolean isfirst);

    void loadNews(int type, int page, String time,boolean isfirst);

    void loadDynamic();

    void loadLiveNews(int type, int page, String time,boolean isfirst);


    void getuserInfo(String userid);
}
