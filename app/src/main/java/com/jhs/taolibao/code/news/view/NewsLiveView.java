package com.jhs.taolibao.code.news.view;

import com.jhs.taolibao.entity.News;

import java.util.List;

/**
 * Created by dds on 2016/6/24.
 *
 * @TODO
 */
public interface NewsLiveView {
    void addNews(List<News> newsList);

    void showProgress();

    void hideProgress();

    void showErrorMsg();
}
