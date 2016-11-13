package com.jhs.taolibao.code.news.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jhs.taolibao.R;
import com.jhs.taolibao.adapter.NewsLiveAdapter;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.base.recyclerview.DividerItemDecoration;
import com.jhs.taolibao.base.recyclerview.OnItemClickListener1;
import com.jhs.taolibao.code.news.presenter.NewsPresenterImpl;
import com.jhs.taolibao.code.news.view.NewsLiveView;
import com.jhs.taolibao.entity.News;
import com.jhs.taolibao.event.LiveComment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by dds on 2016/6/24.
 *
 * @TODO 新闻直播
 */
public class NewsLiveFragment extends BaseFragment implements NewsLiveView, OnItemClickListener1 {
    private XRecyclerView recyclerView;
    private NewsLiveAdapter adapter;
    private RelativeLayout layout_faild;//错误界面
    private TextView tv_faild_msg;

    private NewsPresenterImpl newsPresenter;
    private int type = 4;//直播
    private int page = 1;

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        newsPresenter = new NewsPresenterImpl(this);
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_live, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = (XRecyclerView) rootView.findViewById(R.id.recycle_view);
        layout_faild = (RelativeLayout) rootView.findViewById(R.id.layout_faild);
        tv_faild_msg = (TextView) rootView.findViewById(R.id.tv_faild_msg);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        adapter = new NewsLiveAdapter(getActivity(), R.layout.item_news_live);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallTrianglePath);
        recyclerView.setAdapter(adapter);
        //网络故障，请点击重试
        layout_faild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsPresenter.loadLiveNews(type, page, null, true);
            }
        });
    }

    @Override
    public void onInitloadData() {
        newsPresenter.loadLiveNews(type, page, null, true);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        newsPresenter.loadLiveNews(type, page, null, false);
                    }
                }, 500);

            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        newsPresenter.loadLiveNews(type, page, null, false);
                    }
                }, 500);
            }
        });
    }

    //获取新闻成功
    @Override
    public void addNews(List<News> newsList) {
        if (page == 1) {
            adapter.addDataToAdapater(newsList, true);
            recyclerView.refreshComplete();
        } else {
            adapter.addDataToAdapater(newsList);
            recyclerView.loadMoreComplete();
        }
        adapter.setOnItemClickListener1(this);
    }

    @Override
    public void showProgress() {
        layout_faild.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        layout_faild.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMsg() {
        layout_faild.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        tv_faild_msg.setText("暂无数据");
        recyclerView.refreshComplete();
    }


    //详情界面评论，这里实时更新评论条数
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dddEnvent(LiveComment event) {
        newsPresenter.loadLiveNews(type, page, null, false);
    }

    //item点击进入新闻详情
    @Override
    public void onItemClick1(ViewGroup parent, View view, Object object, int position) {
        News news = (News) object;
        Intent intent = new Intent(getActivity(), NewsLiveActivity.class);
        intent.putExtra("news", news);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
