package com.jhs.taolibao.code.news.model;

/**
 * Created by dds on 2016/6/7.
 *
 * @TODO
 */
public interface CommentModel {
    void loadComment(int newsId, int page, CommentModelImpl.OnCommentListener listener);
    void insertComment(int userId, int newsId, String comment, CommentModelImpl.OnInsertCommentListener listener);


}
