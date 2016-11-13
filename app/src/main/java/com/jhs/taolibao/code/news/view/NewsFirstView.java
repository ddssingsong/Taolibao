package com.jhs.taolibao.code.news.view;

import com.jhs.taolibao.entity.Dynamic;
import com.jhs.taolibao.entity.News;
import com.jhs.taolibao.entity.UserInfo;

import java.util.List;

/**
 * Created by dds on 2016/6/12.
 *
 * @TODO
 */
public interface NewsFirstView {

    void addNews(List<News> newsList);

    void showProgress();

    void hideProgress();

    void showErrorMsg();

    void addDynamic(List<Dynamic> dynamicList);
    void getuserInfoSuccess(UserInfo userInfo);

}
