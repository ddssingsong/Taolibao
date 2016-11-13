package com.jhs.taolibao.code.news.presenter;

import com.jhs.taolibao.code.news.model.DynamicModelImpl;
import com.jhs.taolibao.code.news.model.NewsModelImpl;
import com.jhs.taolibao.code.news.view.NewsFirstView;
import com.jhs.taolibao.code.news.view.NewsLiveView;
import com.jhs.taolibao.code.news.view.NewsView;
import com.jhs.taolibao.code.user.model.UserModelImpl;
import com.jhs.taolibao.entity.Dynamic;
import com.jhs.taolibao.entity.News;
import com.jhs.taolibao.entity.UserInfo;

import java.util.List;

/**
 * Created by dds on 2016/6/12.
 *
 * @TODO 新闻链接类
 */
public class NewsPresenterImpl implements NewsPresenter {
    private NewsModelImpl newsModel;
    private DynamicModelImpl dynamicModel;
    private NewsFirstView firstNews;
    private NewsView newsView;
    private NewsLiveView newsLiveView;
    private UserModelImpl userModel;


    public NewsPresenterImpl(NewsFirstView firstNews) {
        this.firstNews = firstNews;
        newsModel = new NewsModelImpl();
        dynamicModel = new DynamicModelImpl();
        userModel = new UserModelImpl();
    }

    public NewsPresenterImpl(NewsView newsView) {
        this.newsView = newsView;
        newsModel = new NewsModelImpl();
    }

    public NewsPresenterImpl(NewsLiveView newsLiveView) {
        this.newsLiveView = newsLiveView;
        newsModel = new NewsModelImpl();
    }


    @Override
    public void loadFirstNews(int type, int page, String time, boolean isfirst) {
        if (isfirst) {
            firstNews.showProgress();
        }
        String url = null;
        newsModel.loadNews(url, page, type, time, new NewsModelImpl.OnLoadNewsListListener() {
            @Override
            public void onSuccess(List<News> list) {
                firstNews.hideProgress();
                firstNews.addNews(list);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                firstNews.hideProgress();
                firstNews.showErrorMsg();


            }
        });

    }

    @Override
    public void loadLiveNews(int type, int page, String time, boolean isfirst) {
        if (isfirst) {
            newsLiveView.showProgress();
        }
        String url = null;
        newsModel.loadNews(url, page, type, time, new NewsModelImpl.OnLoadNewsListListener() {
            @Override
            public void onSuccess(List<News> list) {
                newsLiveView.hideProgress();
                newsLiveView.addNews(list);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                // ToastUtil.showToast(BaseApplication.getApplication(), msg);
                newsLiveView.hideProgress();
                newsLiveView.showErrorMsg();
            }
        });
    }

    @Override
    public void getuserInfo(String userid) {
        userModel.GetuserInfo(userid, new UserModelImpl.onGetUserInfoListener() {
            @Override
            public void onSuccess(UserInfo userinfo) {
                firstNews.getuserInfoSuccess(userinfo);


            }

            @Override
            public void onFailure(String msg, Exception e) {
                //  ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void loadNews(int type, int page, String time, boolean isfirst) {
        if (isfirst) {
            newsView.showProgress();
        }
        String url = null;
        newsModel.loadNews(url, page, type, time, new NewsModelImpl.OnLoadNewsListListener() {
            @Override
            public void onSuccess(List<News> list) {
                newsView.hideProgress();
                newsView.addNews(list);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                newsView.hideProgress();
                newsView.showErrorMsg();
            }
        });
    }

    @Override
    public void loadDynamic() {
        dynamicModel.loadDynamic(new DynamicModelImpl.OnDynamicListener() {
            @Override
            public void onSuccess(List<Dynamic> dynamics) {
                firstNews.addDynamic(dynamics);
            }

            @Override
            public void onfailture(String msg, Exception e) {
                //ToastUtil.showToast(BaseApplication.getApplication(), msg);

            }
        });
    }


}
