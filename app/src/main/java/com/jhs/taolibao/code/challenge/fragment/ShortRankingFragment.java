package com.jhs.taolibao.code.challenge.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.challenge.ChallengeCenter;
import com.jhs.taolibao.code.challenge.adapter.ShortRankingFragmentAdapter;
import com.jhs.taolibao.code.challenge.model.ArenaRankingList;
import com.jhs.taolibao.code.challenge.model.ArenaUserInfo;
import com.jhs.taolibao.code.challenge.model.RankList;
import com.jhs.taolibao.code.challenge.utils.ChallengeTask;
import com.jhs.taolibao.code.simtrade.base.BaseFragment2;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshBase;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jiao on 2016/7/15 10:59
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:短线排行榜 fragment
 */
public class ShortRankingFragment extends BaseFragment2 {
    public static final String TAG = ShortRankingFragment.class.getSimpleName();
    private PullToRefreshListView lvRank;
    private ListView mLv;
    private ShortRankingFragmentAdapter adapter;
    private List<ArenaUserInfo> arenaRankDatas = new ArrayList<>();

    private int rankType;//1:综合排名 2：累计收益排名

    public static ShortRankingFragment newInstance(int type) {
        ShortRankingFragment shortRankingFragment = new ShortRankingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, type);
        shortRankingFragment.setArguments(bundle);
        return shortRankingFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        rankType = bundle.getInt(TAG, 1);

        return inflater.inflate(R.layout.fragment_shortranking, container, false);
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) throws IOException {
        initView();
        refreshDatas();
        setListView();
    }

    /**
     * 配置listview
     */
    private void setListView() {
        adapter = new ShortRankingFragmentAdapter();
        //adapter.setOnItemClickListener(this);
        mLv.setAdapter(adapter);
        mLv.setEmptyView(getView().findViewById(R.id.ll_data_null));

        lvRank.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshDatas();
                String s = DateUtils.getTimestampString(new Date(System.currentTimeMillis()));
                lvRank.setLastUpdatedLabel(s);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        lvRank = (PullToRefreshListView) getView().findViewById(R.id.short_lv);
        mLv = lvRank.getRefreshableView();
    }

    @Override
    public void OnViewClick(View v) {

    }


    private void refreshDatas() {
        Log.d(TAG, "refreshDatas: ");
//        showProgressDialog();
        try {
            ChallengeCenter.getInstance().getRankList(rankType,new ChallengeTask.OnChallengeListener() {
                @Override
                public void onSuccess(Object response) {
                    RankList rankLilst = (RankList) response;

                    ArenaRankingList aranaRankingList = rankLilst.getData();
                    if (null != aranaRankingList) {
                        arenaRankDatas = aranaRankingList.getArList();
                        adapter.setDatas(arenaRankDatas);
                    }

//                    disMissDialog();
                    lvRank.onPullDownRefreshComplete();
                }

                @Override
                public void onFailure(String msg, Exception e) {
//                    disMissDialog();
                    lvRank.onPullDownRefreshComplete();
                    showToast("网络请求失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
