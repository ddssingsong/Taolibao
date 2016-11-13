package com.jhs.taolibao.code.challenge.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.MainActivity;
import com.jhs.taolibao.code.challenge.ChallengeCenter;
import com.jhs.taolibao.code.challenge.activity.ShortRankingActivity;
import com.jhs.taolibao.code.challenge.adapter.ChallengeAdapter;
import com.jhs.taolibao.code.challenge.adapter.ChallengeCheckAdapter;
import com.jhs.taolibao.code.challenge.fragment.ChallengeChildFragment;
import com.jhs.taolibao.code.challenge.model.Arena;
import com.jhs.taolibao.code.challenge.model.ArenaAds;
import com.jhs.taolibao.code.challenge.utils.ChallengeTask;
import com.jhs.taolibao.code.challenge.view.ZoomOutPageTransformer;
import com.jhs.taolibao.code.guess.widget.RuleFragment;
import com.jhs.taolibao.code.simtrade.utils.AnimationUtils;
import com.jhs.taolibao.view.WaitDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2016/6/13.
 *
 * @TODO 挑战
 */
public class ChallengeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "ChallengeFragment";
    //数据为空时,默认加载的页数
    private static final int SIZE = 6;
    private RelativeLayout challengeBg;
    private ImageView challengeIv;
    private LinearLayout ll;
    private TextView title;
    private TextView challenge_role;

    //底部广告
    private LinearLayout adLl;
    private TextView adTv;
    //底部广告背景
    private RelativeLayout adbg;
    //广告信息
    private List<String> mList;
    //横向布局
    private RelativeLayout horizontalRe;
    private ViewPager vpArana;
    private ChallengeAdapter adapter;

    //对话框
    private WaitDialog progressDialog = null;

    //方格布局
    private RelativeLayout checkRe;
    private ListView lvArana;
    //挑战列表信息
    private List<Arena.Challenger> arenaDatas = new ArrayList<>();
    //viewPager 子view
    List<ChallengeChildFragment> vpChildren = new ArrayList<ChallengeChildFragment>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int position = msg.what;
            if (null == getActivity()) {
                return;
            }
            AnimationUtils.showAd(getActivity(), adLl, adTv, mList.get(position));
        }
    };

    private RelativeLayout layout_chalange_top;

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        StatusBarUtil.StatusBarDarkMode(getActivity());
//        StatusBarUtil.setStatusBarColor(getActivity(), R.color.Velvet);
        View view = inflater.inflate(R.layout.fragment_challenge, container, false);
        return view;

    }

    @Override
    protected void initView(View rootView) {
        layout_chalange_top = (RelativeLayout) rootView.findViewById(R.id.layout_chalange_top);
        challengeBg = (RelativeLayout) rootView.findViewById(R.id.challenge_bg);
        TextView ranking = (TextView) rootView.findViewById(R.id.ranking);
        ll = (LinearLayout) rootView.findViewById(R.id.challenge_ll);
        challengeIv = (ImageView) rootView.findViewById(R.id.challenge_iv);
        title = (TextView) rootView.findViewById(R.id.challenge_title);
        adLl = (LinearLayout) rootView.findViewById(R.id.challenge_ad_ll);
        adTv = (TextView) rootView.findViewById(R.id.challenge_ad_tv);
        adbg = (RelativeLayout) rootView.findViewById(R.id.challenge_ad_bg);
        challenge_role = (TextView) rootView.findViewById(R.id.challenge_role);

        //adbg.getBackground().setAlpha((int) (255 * 0.12));
        getAdContent();//获取轮播广告
        challengeIv.setSelected(true);
        ranking.setOnClickListener(this);
        ll.setOnClickListener(this);
        challenge_role.setClickable(true);
        challenge_role.setOnClickListener(this);

        //横向布局
        initHorizontalRe(rootView);
        //方格布局
        initCheckRe(rootView);
        getArena();
    }


    /**
     * 横向布局的相关逻辑
     */
    private void initHorizontalRe(View rootView) {
        horizontalRe = (RelativeLayout) rootView.findViewById(R.id.challenge_ho);
        vpArana = (ViewPager) rootView.findViewById(R.id.view_pager);

        initViewPager();
    }

    /**
     * 方格布局的相关逻辑
     */
    private void initCheckRe(View rootView) {
        checkRe = (RelativeLayout) rootView.findViewById(R.id.challenge_check);
        lvArana = (ListView) rootView.findViewById(R.id.challenge_lv);
        initListView();
    }

    private void initListView() {

        ChallengeCheckAdapter adapter = new ChallengeCheckAdapter(getContext());
        lvArana.setAdapter(adapter);
        lvArana.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                // Log.i("msg",firstVisibleItem+"====="+visibleItemCount+"======="+totalItemCount);

                int top = getScrollY();
                if (top < 0) {
                    layout_chalange_top.setBackgroundColor(getResources().getColor(R.color.black_apha));
                } else {
                    layout_chalange_top.setBackgroundColor(getResources().getColor(R.color.transparent));
                }

            }
        });

    }


    public int getScrollY() {
        View c = lvArana.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int top_bu = layout_chalange_top.getBottom();
        int top = c.getTop();
        return top + top_bu;
    }

    @Override
    public void onInitloadData() {

    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        for (int i = 0; i < 1; i++) {
            ChallengeChildFragment childFragment = new ChallengeChildFragment();
            vpChildren.add(childFragment);
//            arenaDatas.size()arenaDatas.get(i)
        }
        adapter = new ChallengeAdapter(getChildFragmentManager(), vpChildren);
        vpArana.setAdapter(adapter);
        vpArana.setPageTransformer(true, new ZoomOutPageTransformer());
//        //设置加载页数,在设置适配器之后设置
        vpArana.setOffscreenPageLimit(vpChildren == null ? SIZE : vpChildren.size());
    }

    /**
     * 刷新横向排列信息
     */
    private void refershViewPager() {
        if (null == arenaDatas) {
            return;
        }
        vpChildren.clear();

        for (int i = 0; i < arenaDatas.size(); i++) {
            //((ChallengeAdapter) vpArana.getAdapter()).getItem(i).setData(arenaDatas.get(i));
            vpChildren.add(ChallengeChildFragment.newInstance(arenaDatas.get(i)));
        }
        vpArana.removeAllViews();
        vpArana.setAdapter(adapter);
        adapter.setDatas(vpChildren);
        vpArana.setOffscreenPageLimit(vpChildren.size());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.challenge_ll:
                if (horizontalRe.getVisibility() == View.VISIBLE) {
                    title.setVisibility(View.VISIBLE);
                    horizontalRe.setVisibility(View.GONE);
                    checkRe.setVisibility(View.VISIBLE);
                    challengeBg.setBackgroundResource(R.drawable.tiaozhan2);
                    challengeIv.setSelected(true);
                } else {
                    title.setVisibility(View.GONE);
                    horizontalRe.setVisibility(View.VISIBLE);
                    checkRe.setVisibility(View.GONE);
                    challengeBg.setBackgroundResource(R.drawable.tiaozhan);
                    challengeIv.setSelected(false);
                }
                break;
            case R.id.ranking:
                getActivity().startActivity(new Intent(getActivity(), ShortRankingActivity.class));
                break;

            case R.id.challenge_role:
                //竞猜规则
                RuleFragment ruleFragment = new RuleFragment();
                ruleFragment.setTitle("每日挑战赛规则", R.drawable.ic_guess_rule);
                ruleFragment.setType(1);
                ruleFragment.show(getChildFragmentManager(), "challenge");


                break;
        }
    }

    /**
     * 设置广告信息
     */
    private void setAd(final List<String> list) {
        if (null == list) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    for (int i = 0; i < list.size(); i++) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(i);
                    }
                }
            }
        }.start();
    }

    /**
     * 获取擂台信息
     */
    public void getArena() {
        // showProgressDialog("拼命加载中...");
        try {
            ChallengeCenter.getInstance().getArena(new ChallengeTask.OnChallengeListener() {
                @Override
                public void onSuccess(Object response) {
                    Arena arena = (Arena) response;
                    arenaDatas = arena.getData();

                    //刷新信息
                    ((ChallengeCheckAdapter) lvArana.getAdapter()).setData(arenaDatas);
                    refershViewPager();
                    // disMissDialog();
                    ((MainActivity) getActivity()).onSuccess(true);
                }

                @Override
                public void onFailure(String msg, Exception e) {

                    // ((MainActivity) getActivity()).onSuccess(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示对话框
     *
     * @param msg
     */
    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new WaitDialog(BaseApplication.getApplication());
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消对话框显示
     */
    public void disMissDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取乱播广告内容
     */
    private void getAdContent() {
        ChallengeCenter.getInstance().getAds(new ChallengeTask.OnChallengeListener() {
            @Override
            public void onSuccess(Object response) {
                ArenaAds arenaAds = (ArenaAds) response;
                mList = arenaAds.getSlogan();
                setAd(mList);
            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });
    }
}
