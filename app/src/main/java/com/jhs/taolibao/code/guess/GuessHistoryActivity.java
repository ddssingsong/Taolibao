package com.jhs.taolibao.code.guess;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.jhs.taolibao.R;
import com.jhs.taolibao.adapter.GuessHistoryAdapter;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.code.guess.presenter.GuessHistoryPresenterImpl;
import com.jhs.taolibao.code.guess.view.GuessHistoryView;
import com.jhs.taolibao.entity.GuessHistory;
import com.jhs.taolibao.utils.StatusBarUtil;
import com.jhs.taolibao.view.TitleBar;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GuessHistoryActivity extends BaseActivity implements GuessHistoryView {
    private TitleBar titlebar;
    private ExpandableListView pull_guess_history;
    private GuessHistoryAdapter adapter;
    private int page = 1;
    private GuessHistoryPresenterImpl guessHistoryPresenter;
    private SwipeRefreshLayout swiperefresh;
    private LinearLayout ll_data_null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.activity_guess_history);
        guessHistoryPresenter = new GuessHistoryPresenterImpl(this);
        initView();
        initData();
    }


    private void initView() {
        titlebar = (TitleBar) findViewById(R.id.titlebar);
        pull_guess_history = (ExpandableListView) findViewById(R.id.pull_guess_history);
        pull_guess_history.setGroupIndicator(null);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_data_null = (LinearLayout) findViewById(R.id.ll_data_null);
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swiperefresh.setOnRefreshListener(listener);
    }

    SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            guessHistoryPresenter.getGuessHistoryList(UserInfoSingleton.getUserId(), page);
        }
    };

    private void initData() {
        guessHistoryPresenter.getGuessHistoryList(UserInfoSingleton.getUserId(), page);
    }

    //获取到
    @Override
    public void getHistoryList(List<GuessHistory.DirectoryResultsEntity> list) {
        HashMap<Integer, List<GuessHistory.DirectoryResultsEntity.GameListEntity>> childlist = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            childlist.put(i, list.get(i).getGameList());
        }
        adapter = new GuessHistoryAdapter(this, list, childlist);
        pull_guess_history.setAdapter(adapter);
        swiperefresh.setRefreshing(false);
        ll_data_null.setVisibility(View.INVISIBLE);
        pull_guess_history.setVisibility(View.VISIBLE);
    }

    @Override
    public void getHistoryListFaild() {
        ll_data_null.setVisibility(View.VISIBLE);
        pull_guess_history.setVisibility(View.INVISIBLE);
    }


    class SortByType implements Comparator {
        public int compare(Object o1, Object o2) {
            GuessHistory.DirectoryResultsEntity.GameListEntity s1 = (GuessHistory.DirectoryResultsEntity.GameListEntity) o1;
            GuessHistory.DirectoryResultsEntity.GameListEntity s2 = (GuessHistory.DirectoryResultsEntity.GameListEntity) o2;
            if (s1.getType() > s2.getType())
                return 1;
            return 0;
        }
    }
}
