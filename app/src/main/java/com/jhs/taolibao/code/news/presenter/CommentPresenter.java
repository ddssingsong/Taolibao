package com.jhs.taolibao.code.news.presenter;

/**
 * Created by dds on 2016/6/24.
 *
 * @TODO
 */
public interface CommentPresenter {
    void loadComment(int newsId, int page,boolean isfirst);
    void insertComment(int userId, int newsId, String comment);

}
