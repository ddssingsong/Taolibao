package com.jhs.taolibao.code.news.presenter;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.news.model.CommentModelImpl;
import com.jhs.taolibao.code.news.view.NewsDetailView;
import com.jhs.taolibao.entity.Comment;
import com.jhs.taolibao.utils.ToastUtil;

import java.util.List;

/**
 * Created by dds on 2016/6/24.
 *
 * @TODO
 */
public class CommentPresenterImpl implements CommentPresenter {
    private NewsDetailView newsDetailView;
    private CommentModelImpl commentModel;

    public CommentPresenterImpl(NewsDetailView newsDetailView) {
        this.newsDetailView = newsDetailView;
        commentModel = new CommentModelImpl();
    }


    @Override
    public void loadComment(int newsId, int page,boolean isfirst) {
        if (isfirst) {
            newsDetailView.showProgress();
        }
        commentModel.loadComment(newsId, page, new CommentModelImpl.OnCommentListener() {
            @Override
            public void onSuccess(List<Comment> commentlist,int totalcount) {
                newsDetailView.hideProgress();
                newsDetailView.addComment(commentlist,totalcount);

            }

            @Override
            public void onfailture(String msg, Exception e) {
                newsDetailView.hideProgress();
                newsDetailView.showErrorMsg();
            }
        });

    }

    @Override
    public void insertComment(int userId, int newsId, String comment) {
        if (comment == null || comment.equals("")) {
            return;
        }
        commentModel.insertComment(userId, newsId, comment, new CommentModelImpl.OnInsertCommentListener() {
            @Override
            public void onSuccess() {
                newsDetailView.commentSuccess();
            }

            @Override
            public void onfailture(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);

            }
        });

    }
}
