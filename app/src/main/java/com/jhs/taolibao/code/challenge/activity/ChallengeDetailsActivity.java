package com.jhs.taolibao.code.challenge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.code.challenge.ChallengeCenter;
import com.jhs.taolibao.code.challenge.constant.ChallengeConstant;
import com.jhs.taolibao.code.challenge.model.ArenaUserInfo;
import com.jhs.taolibao.code.challenge.model.ChallengeInfo;
import com.jhs.taolibao.code.challenge.utils.ChallengeTask;
import com.jhs.taolibao.code.simtrade.activity.KeepActivity;
import com.jhs.taolibao.code.simtrade.adapter.KeepAdapter2;
import com.jhs.taolibao.code.simtrade.base.BaseActivity2;
import com.jhs.taolibao.code.simtrade.entity.KeepBean;
import com.jhs.taolibao.code.simtrade.utils.HscloudUtils;
import com.jhs.taolibao.code.simtrade.utils.ImageLoaderUtil;

import java.util.List;

/**
 * 观战：擂主 vs 挑战者们 ，默认显示擂主持仓,若自身为擂主，则显示挑战者持仓
 * 挑战：擂主 vs 用户自己，默认显示擂主持仓
 *
 * @author jiao 、xujingbo on 2016/7/14 17:21
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:挑战详情
 */
public class ChallengeDetailsActivity extends BaseActivity2 {
    private static final String TAG = "CheDetaisActi";
    //擂主头像
    private ImageView ivChampionAvatar;
    //挑战者头像
    private ImageView ivChallengerAvatar;
    //擂主
    private TextView tvChampionName;
    //挑战者
    private TextView tvChallengerName;

    //擂主本期收益率
    private TextView tvNowIncomePercent;
    //擂主累计收益率
    private TextView tvAllIncomePercent;
    //擂主守擂次数
    private TextView tvGuardTimes;
    //擂主胜率
    private TextView tvSuccessPercent;

    //挑战者本期收益率
    private TextView tvNowIncomePercent1;
    //挑战者累计收益率
    private TextView tvAllIncomePercent1;
    //挑战者守擂次数
    private TextView tvGuardTimes1;
    //挑战者胜率
    private TextView tvSuccessPercent1;
    //当前挑战人数
    private TextView tvCurrentChallengersCount;
    //当前显示持仓的所有者
    private TextView tvKeepUser;
    //当前显示的挑战者
    private int currentChallenger = 0;
    private int totalChallengersCount = 0;
    //擂主信息
    private ArenaUserInfo championUserinfo;
    //挑战者信息
    private List<ArenaUserInfo> challengers;
    //挑战者持仓信息
    private ListView lvKeep;
    private KeepAdapter2 keepAdapter;

    private int arenaId;
    private ChallengeConstant.ArenaType type;
    /**
     * 用户在此擂台的角色
     */
    private ChallengeConstant.ArenaRole role;

    @Override
    public int getLayout() {
        return R.layout.activity_challenge_details;
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {

        arenaId = getIntent().getIntExtra("arenaId", 0);
        type = ChallengeConstant.ArenaType.values()[getIntent().getIntExtra("type", 2)];
        role = ChallengeConstant.ArenaRole.values()[getIntent().getIntExtra("role", 3)];

        initView();
        setListener();
        getData();

    }

    private void initView() {
        tvCurrentChallengersCount = (TextView) findViewById(R.id.challenge_tv_countdown);

        if (role == ChallengeConstant.ArenaRole.CHAMPION) {
            //角色为擂主，可查看所有挑战者信息
            setViewClick(R.id.challengeinfo_iv_left);
            setViewClick(R.id.challengeinfo_iv_right);
        } else {

            findViewById(R.id.challengeinfo_iv_left).setVisibility(View.INVISIBLE);
            findViewById(R.id.challengeinfo_iv_right).setVisibility(View.INVISIBLE);
        }
        //擂主
        ivChampionAvatar = (ImageView) findViewById(R.id.iv_master);
        tvChampionName = (TextView) findViewById(R.id.challengeinfo_tv_masterName);
        tvNowIncomePercent = (TextView) findViewById(R.id.challengeinfo_tv_incomePercent);
        tvAllIncomePercent = (TextView) findViewById(R.id.challengeinfo_tv_allIncomePercent);
        tvGuardTimes = (TextView) findViewById(R.id.challengeinfo_tv_guardTimes);
        tvSuccessPercent = (TextView) findViewById(R.id.challengeinfo_tv_successPercent);
        //挑战者
        tvNowIncomePercent1 = (TextView) findViewById(R.id.challengeinfo_tv_incomePercent1);
        tvAllIncomePercent1 = (TextView) findViewById(R.id.challengeinfo_tv_allIncomePercent1);
        tvGuardTimes1 = (TextView) findViewById(R.id.challengeinfo_tv_guardTimes1);
        tvSuccessPercent1 = (TextView) findViewById(R.id.challengeinfo_tv_successPercent1);
        ivChallengerAvatar = (ImageView) findViewById(R.id.iv_challenger);
        tvChallengerName = (TextView) findViewById(R.id.challengeinfo_tv_challengerName);

        tvKeepUser = (TextView) findViewById(R.id.challenge_tv_keepUser);
        lvKeep = (ListView) findViewById(R.id.challenge_lv_keep);

        lvKeep.setEmptyView(findViewById(R.id.ll_data_null));
        //持仓列表
        keepAdapter = new KeepAdapter2();
        lvKeep.setAdapter(keepAdapter);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        setViewClick(R.id.challenge_back);
        setViewClick(R.id.challenge_btn_chackKeep);
    }

    @Override
    public void OnViewClick(View v) {
        switch (v.getId()) {
            case R.id.challenge_back:
                finish();
                break;
            //下一个挑战者
            case R.id.challengeinfo_iv_right:

                if (currentChallenger < totalChallengersCount - 1) {
                    currentChallenger++;
                    refreshChallengersInfo();
                    refreshKeep();

                }
                break;
            //上一个挑战者
            case R.id.challengeinfo_iv_left:

                if (currentChallenger > 0) {
                    currentChallenger--;
                    refreshChallengersInfo();
                    refreshKeep();

                }
                break;
            case R.id.challenge_btn_chackKeep:
                startActivity(new Intent(this, KeepActivity.class));
                break;
        }
    }

    /**
     * 刷新擂主信息
     */
    private void refreshChampionInfo() {
        if (null == championUserinfo) {
            return;
        }

        ImageLoaderUtil.getInstance().displayFromWeb(
                ChallengeConstant.URL + championUserinfo.getIconImg(),
                ivChampionAvatar, ImageLoaderUtil.roundOptions);
        tvChampionName.setText(championUserinfo.getAlias());

        tvNowIncomePercent.setText(championUserinfo.getCurrentYield());
        tvAllIncomePercent.setText(championUserinfo.getCumulativeReturns());
        tvGuardTimes.setText(String.valueOf(championUserinfo.getGuard()));
        tvSuccessPercent.setText(
                String.valueOf(championUserinfo.getWin()) + ":" +
                        String.valueOf(championUserinfo.getLose() + championUserinfo.getWin()));
        ivChampionAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(ChallengeDetailsActivity.this,
                                MyInfoActivity.class).putExtra("userid", championUserinfo.getUserID()));
            }
        });
    }

    /**
     * 刷新挑战者信息
     */
    private void refreshChallengersInfo() {


        //非挑战者
        if (null == challengers) {
            return;
        }
        for (int i = 0; i < challengers.size(); i++) {
            if (String.valueOf(challengers.get(i).getUserID()).equals(UserInfoSingleton.getUserId())) {
                currentChallenger = i;
            }
        }
        final ArenaUserInfo userInfo = challengers.get(currentChallenger);
        ImageLoaderUtil.getInstance().displayFromWeb(
                ChallengeConstant.URL + userInfo.getIconImg(),
                ivChallengerAvatar, ImageLoaderUtil.roundOptions);
        tvChallengerName.setText(userInfo.getAlias());
        tvNowIncomePercent1.setText(userInfo.getCurrentYield());
        tvAllIncomePercent1.setText(userInfo.getCumulativeReturns());
        tvGuardTimes1.setText(String.valueOf(userInfo.getGuard()));
        tvSuccessPercent1.setText(
                String.valueOf(userInfo.getWin()) + ":" +
                        String.valueOf(userInfo.getLose() + userInfo.getWin()));
        //头像点击事件
        ivChallengerAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(ChallengeDetailsActivity.this,
                                MyInfoActivity.class).putExtra("userid", userInfo.getUserID()));
            }
        });
    }

    private void refreshNotes(String notes) {
        if (null == notes) {
            return;
        }
        tvCurrentChallengersCount.setText(notes);
    }

    /**
     * 获取数据 擂主信息、挑战者信息
     */
    public void getData() {
        showProgressDialog();
        try {
            ChallengeCenter.getInstance().getChallengeInfo(arenaId, new ChallengeTask.OnChallengeListener() {
                @Override
                public void onSuccess(Object response) {
                    disMissDialog();
                    ChallengeInfo dataInfo = (ChallengeInfo) response;
                    championUserinfo = dataInfo.getData().getChampion();
                    challengers = dataInfo.getData().getChallenger();
                    if (null == challengers) {
                        totalChallengersCount = 0;
                    } else {
                        totalChallengersCount = challengers.size();
                    }

                    refreshChampionInfo();
                    refreshChallengersInfo();
                    refreshNotes(dataInfo.getData().getNotes());
                    refreshKeep();
                }

                @Override
                public void onFailure(String msg, Exception e) {
                    disMissDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 刷新持仓信息,先获取要查看持仓信息用户的token
     */
    private void refreshKeep() {
        int userId = 0;
        if (role == ChallengeConstant.ArenaRole.CHAMPION) {
            //擂主,显示挑战者持仓信息
            tvKeepUser.setText(tvChallengerName.getText().toString() + "持仓");
            if (null == challengers) {
                return;
            }
            userId = challengers.get(currentChallenger).getUserID();
        } else {
            //显示擂主持仓信息
            tvKeepUser.setText("擂主持仓");
            if (null == championUserinfo) {
                return;
            }
            userId = championUserinfo.getUserID();
        }
        //showProgressDialog();
        //获取持仓信息
        ChallengeCenter.getInstance().getKeepInfo(String.valueOf(userId), new HscloudUtils.onGetBeanListener() {
                    @Override
                    public void onSuccess(KeepBean response) {
                        //disMissDialog();
                        if (null != response) {
                            keepAdapter.setData(response.getData());
                        }
                    }

                    @Override
                    public void onFailure(String msg, Exception e) {
                        //disMissDialog();
                    }
                }
        );


    }
}
