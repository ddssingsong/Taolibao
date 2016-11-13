package com.jhs.taolibao.code.news.view;

import com.jhs.taolibao.entity.Comment;

import java.util.List;

/**
 * Created by dds on 2016/6/24.
 *
 * @TODO
 */
public interface NewsDetailView {

    void addComment(List<Comment> commentlist,int totalcount);
    void showProgress();

    void hideProgress();

    void showErrorMsg();

    void commentSuccess();

}
