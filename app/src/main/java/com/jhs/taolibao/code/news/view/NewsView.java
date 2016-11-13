package com.jhs.taolibao.code.news.view;

import com.jhs.taolibao.entity.News;

import java.util.List;

/**
 * Created by dds on 2016/6/14.
 *
 * @TODO
 */
public interface NewsView {
    void addNews(List<News> newsList);

    void showProgress();

    void hideProgress();

    void showErrorMsg();
}
