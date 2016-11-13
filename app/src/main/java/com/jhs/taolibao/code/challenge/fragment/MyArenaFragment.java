package com.jhs.taolibao.code.challenge.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.challenge.ChallengeCenter;
import com.jhs.taolibao.code.challenge.activity.MyArenaActivity;
import com.jhs.taolibao.code.challenge.adapter.MyArenaAdapter;
import com.jhs.taolibao.code.challenge.model.MyArena;
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
 * 我的擂台列表
 * Created by xujingbo on 2016/7/22.
 */
public class MyArenaFragment extends BaseFragment2 {
    private static final String TAG = "MyArenaFragment";

    public enum ArenaType {
        CHALLENGING,//挑战中
        WATCHING,//观战中
        COMPLETED,//已完成
    }

    private static final String KEY = "ArenaType";
    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private LinearLayout ll;
    private MyArenaAdapter adapter;
    private int arenaType;//2比赛中  4观战 3完成比赛
    private List<MyArena.ArenaDetailView> dataList = new ArrayList<>();

    public static MyArenaFragment newInstance(int type) {
        MyArenaFragment fragment = new MyArenaFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, type);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_challenging, null);
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) throws IOException {
        arenaType = getArguments().getInt(KEY);

        initView();
        getArenaInfo();
    }

    private void initView() {
        ll = (LinearLayout) getView().findViewById(R.id.ll);
        pullToRefreshListView = (PullToRefreshListView) getView().findViewById(R.id.ptrl);
        listView = pullToRefreshListView.getRefreshableView();
        listView.setCacheColorHint(0);
        listView.setDividerHeight(0);

        adapter = new MyArenaAdapter(getContext(), arenaType, dataList);
        listView.setAdapter(adapter);

        listView.setEmptyView(getView().findViewById(R.id.ll_data_null));
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String s = DateUtils.getTimestampString(new Date(System.currentTimeMillis()));
                pullToRefreshListView.setLastUpdatedLabel(s);
                getArenaInfo();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(getContext(), ChallengeDetailsActivity.class));
//            }
//        });

        setViewClick(R.id.btn_challenge);

    }

    @Override
    public void OnViewClick(View v) {
        switch (v.getId()) {
            //跳转到挑战主界面
            case R.id.btn_challenge:
                getActivity().setResult(MyArenaActivity.RESULT_OK);
                getActivity().finish();
                break;
        }
    }

    /**
     * 获取我的擂台列表
     */
    private void getArenaInfo() {
        try {
            ChallengeCenter.getInstance().getMyArena(arenaType, new ChallengeTask.OnChallengeListener() {
                @Override
                public void onSuccess(Object response) {
                    MyArena data = (MyArena) response;
                    dataList = data.getData();
                    adapter.setData(dataList);
                    adapter.notifyDataSetChanged();
                    pullToRefreshListView.onPullDownRefreshComplete();
                }

                @Override
                public void onFailure(String msg, Exception e) {
                    pullToRefreshListView.onPullDownRefreshComplete();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
