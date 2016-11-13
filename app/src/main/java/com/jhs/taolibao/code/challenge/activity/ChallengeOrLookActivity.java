package com.jhs.taolibao.code.challenge.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.challenge.ChallengeCenter;
import com.jhs.taolibao.code.challenge.constant.ChallengeConstant;
import com.jhs.taolibao.code.challenge.model.ArenaRule;
import com.jhs.taolibao.code.challenge.model.ArenaUserInfo;
import com.jhs.taolibao.code.challenge.model.ChallengeInfo;
import com.jhs.taolibao.code.challenge.utils.ChallengeTask;
import com.jhs.taolibao.code.challenge.view.CostAlertDialog;
import com.jhs.taolibao.code.challenge.view.CostChallengeDialog;
import com.jhs.taolibao.code.simtrade.base.BaseActivity2;
import com.jhs.taolibao.code.simtrade.entity.UserInfo;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.code.simtrade.utils.ImageLoaderUtil;

import java.util.List;

/**
 * @author xujingbo on 2016/7/14 10:53
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:挑战或观战
 */
public class ChallengeOrLookActivity extends BaseActivity2 {
    private static final String TAG = "ChallengeOrLookActivity";

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

    //当前挑战人数或开战剩余时间
    private TextView tvCountDown;

    private ViewPager vpChallengeersInfo;

    //擂主信息
    private ArenaUserInfo championUserinfo;
    //挑战者信息
    private List<ArenaUserInfo> challengers;

    private int currentChallenger = 0;
    private int arenaId;
    private int state;//状态 1 等待开始 2比赛中  3 比赛已结
    private long cutDownTime;
    private MyCount myCount;//倒计时
    @Override
    public int getLayout() {
        return R.layout.activity_challengeorlook;
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        arenaId = getIntent().getIntExtra("arenaId",0);
        state = getIntent().getIntExtra("state",1);
       // Log.e(TAG, "OnActCreate: arenaId "+ arenaId );
       // Log.e(TAG, "OnActCreate: state "+ state );
        if (state == 1){
            cutDownTime = getIntent().getLongExtra("cutDownTime",0);
        }
        initView();
        setListener();
        getData();
    }

    /**
     * 初始化view
     */
    private void initView() {
        tvCountDown = (TextView)findViewById(R.id.challenge_tv_countdown);

        //擂主
        ivChampionAvatar = (ImageView)findViewById(R.id.iv_master);
        tvChampionName = (TextView)findViewById(R.id.challengeinfo_tv_masterName);
        tvNowIncomePercent = (TextView)findViewById(R.id.challengeinfo_tv_incomePercent);
        tvAllIncomePercent = (TextView)findViewById(R.id.challengeinfo_tv_allIncomePercent);
        tvGuardTimes = (TextView)findViewById(R.id.challengeinfo_tv_guardTimes);
        tvSuccessPercent = (TextView)findViewById(R.id.challengeinfo_tv_successPercent);
        //挑战者
        tvNowIncomePercent1 = (TextView)findViewById(R.id.challengeinfo_tv_incomePercent1);
        tvAllIncomePercent1 = (TextView)findViewById(R.id.challengeinfo_tv_allIncomePercent1);
        tvGuardTimes1 = (TextView)findViewById(R.id.challengeinfo_tv_guardTimes1);
        tvSuccessPercent1 = (TextView)findViewById(R.id.challengeinfo_tv_successPercent1);
        ivChallengerAvatar = (ImageView)findViewById(R.id.iv_challenger);
        tvChallengerName = (TextView)findViewById(R.id.challengeinfo_tv_challengerName);

        findViewById(R.id.challengeinfo_iv_left).setVisibility(View.INVISIBLE);
        findViewById(R.id.challengeinfo_iv_right).setVisibility(View.INVISIBLE);

        if (state == 1){
            //未开始显示倒计时
            myCount = new MyCount(cutDownTime,1000);
            myCount.start();

            findViewById(R.id.look_challenge).setVisibility(View.VISIBLE);
            findViewById(R.id.look_rule).setVisibility(View.VISIBLE);
            findViewById(R.id.challenge_btn).setVisibility(View.VISIBLE);
            findViewById(R.id.challenge_rule).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.look_challenge).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_Watching_rules).setVisibility(View.VISIBLE);
            findViewById(R.id.challenge_btn).setVisibility(View.GONE);
            findViewById(R.id.ll_Challenge_rules).setVisibility(View.GONE);
        }
    }
    /**
     * 设置监听
     */
    private void setListener() {
        setViewClick(R.id.look_challenge);
        setViewClick(R.id.look_rule);
        setViewClick(R.id.challenge_btn);
        setViewClick(R.id.challenge_rule);
        setViewClick(R.id.challenge_back);
        setViewClick(R.id.iv_master);
        setViewClick(R.id.iv_challenger);
        setViewClick(R.id.ll_Watching_rules);
        setViewClick(R.id.ll_Challenge_rules);

    }
    @Override
    public void OnViewClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //点击"观战"
            case R.id.look_challenge:
                showArenaCostDialog(ChallengeConstant.ArenaType.TYPE_WHATCH);

                break;

            //点击"挑战"
            case R.id.challenge_btn:
                showArenaCostDialog(ChallengeConstant.ArenaType.TYPE_CHALLENGE);

                break;

            case R.id.challenge_back:
                finish();
                break;

            //点击擂主头像
            case R.id.iv_master:
                if (null == championUserinfo){
                    return;
                }
                intent = new Intent(this, MyInfoActivity.class);
                intent.putExtra("userid",championUserinfo.getUserID());
                        startActivity(intent);
                break;

            //点击挑战者头像
            case R.id.iv_challenger:
                if (null == challengers){
                    return;
                }
                intent = new Intent(this, MyInfoActivity.class);
                intent.putExtra("userid",challengers.get(0).getUserID());
                startActivity(intent);
                break;


            //点击观战规则
            case R.id.ll_Watching_rules:
                getArenaRule(ChallengeConstant.ArenaType.TYPE_WHATCH);
                break;

            //点击挑战者头像
            case R.id.ll_Challenge_rules:
                getArenaRule(ChallengeConstant.ArenaType.TYPE_CHALLENGE);
                break;
        }
    }

    /**
     * 刷新擂主信息
     */
    private void refreshChampionInfo(){
        if (null == championUserinfo){
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
                String.valueOf(championUserinfo.getWin()) + ":" + String.valueOf(championUserinfo.getLose()+championUserinfo.getWin()));
        ivChampionAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(ChallengeOrLookActivity.this,
                                MyInfoActivity.class).putExtra("userid", championUserinfo.getUserID()));
            }
        });
    }

    /**
     * 刷新挑战者信息
     */
    private void refreshChallengersInfo(){
        if (null == challengers){
            return;
        }
        final ArenaUserInfo userInfo = challengers.get(0);
        ImageLoaderUtil.getInstance().displayFromWeb(
                ChallengeConstant.URL + userInfo.getIconImg(),
                ivChallengerAvatar, ImageLoaderUtil.roundOptions);
        tvChallengerName.setText(userInfo.getAlias());
        tvNowIncomePercent1.setText(userInfo.getCurrentYield());
        tvAllIncomePercent1.setText(userInfo.getCumulativeReturns());
        tvGuardTimes1.setText(String.valueOf(userInfo.getGuard()));
        tvSuccessPercent1.setText(
                String.valueOf(userInfo.getWin()) + ":" + String.valueOf(userInfo.getLose()+userInfo.getWin()));

        ivChallengerAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(ChallengeOrLookActivity.this,
                                MyInfoActivity.class).putExtra("userid", userInfo.getUserID()));
            }
        });


    }

    private void refreshNotes(String notes){

        if (null == notes){
            return;
        }
        if (0 == cutDownTime){
            tvCountDown.setText(notes);
        }
    }
    /**
     * 获取数据 擂主信息、挑战者信息
     */
    public void getData() {
        showProgressDialog();
        try {
            ChallengeCenter.getInstance().getChallengeInfo(arenaId,new ChallengeTask.OnChallengeListener() {
                @Override
                public void onSuccess(Object response) {
                    disMissDialog();
                    ChallengeInfo dataInfo = (ChallengeInfo)response;

                    championUserinfo = dataInfo.getData().getChampion();
                    challengers = dataInfo.getData().getChallenger();
                    refreshChampionInfo();
                    refreshChallengersInfo();
                    refreshNotes(dataInfo.getData().getNotes());

                }

                @Override
                public void onFailure(String msg, Exception e) {
                    showToast(msg);
                    disMissDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 规则dialig
     *  @param type 1：挑战规则		2：观战规则
     *  @param rule 规则内容
     */
    private void dialogOfRule(ChallengeConstant.ArenaType type,String rule) {
        LayoutInflater inflater = getLayoutInflater();
        View view = null;
        switch (type){
            case TYPE_WHATCH:
                view = inflater.inflate(R.layout.layout_watching_rules, null);
                break;
            case TYPE_CHALLENGE:
                view = inflater.inflate(R.layout.layout_challenging_rules, null);
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (null == view){
            return;
        }
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        TextView tvRuleContent = (TextView) view.findViewById(R.id.rule_tv_content);
        tvRuleContent.setText(rule);
        tvRuleContent.setLineSpacing(0, 1.3F);

        TextView tv = (TextView) view.findViewById(R.id.tv_btn);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 观战、挑战扣币确认框
     * @param type
     */
    private void showArenaCostDialog(final ChallengeConstant.ArenaType type){
        //获取宝币总数
        ChallengeCenter.getInstance().getTotalPoint(new ChallengeTask.OnChallengeListener() {
            @Override
            public void onSuccess(Object response) {
                UserInfo userInfo = (UserInfo) response;
                final int totalPoint = userInfo.getUser().getUserTotalPoint();
                String msg = "";
                switch (type){
                    case TYPE_CHALLENGE:
                        msg = "您选择了挑战，将扣除宝币";
                        final CostChallengeDialog dialog =  new CostChallengeDialog(ChallengeOrLookActivity.this).builder();
                        dialog.setTotalCoin(totalPoint)
                                .setMsg(msg)
                                .setPositiveButton(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int cost = dialog.getCost();
                                        challengeOrWatch(type.ordinal(), cost);

                                    }
                                }).show();
                        break;
                    case TYPE_WHATCH:
                        final int costPoint = ChallengeConstant.COST_POINT;
                        msg = "您选择了观战，将扣除" + costPoint + "宝币";
                        new CostAlertDialog(ChallengeOrLookActivity.this).builder()
                                .setTotalCoin(String.valueOf(totalPoint))
                                .setMsg(msg)
                                .setPositiveButton(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (totalPoint < costPoint) {
                                            showToast("提示：您的宝币数量不够");
                                        } else {
                                            challengeOrWatch(type.ordinal(), costPoint);

                                        }
                                    }
                                }).show();
                        break;
                }

            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });

    }

    /**
     * 进入观战活挑战界面
     * @param type 1:挑战 2:观战
     */
    private void startNewActivity(int type){
        Intent intent = new Intent(this, ChallengeDetailsActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
        finish();
    }

    /**
     * 选择观战或挑战
     * @param type 1:挑战 2:观战
     */
    private void challengeOrWatch(final int type,int count){
        ChallengeCenter.getInstance().getInserArenaUser(arenaId, type,count, new ChallengeTask.OnChallengeListener() {
            @Override
            public void onSuccess(Object response) {
                startNewActivity(type);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!msg.equals("")) {
                    showToast(msg);
                }
            }
        });
    }

    /**
     * 获取挑战或观战规则
     * @param type 1：挑战规则		2：观战规则
     */
    private void getArenaRule(final ChallengeConstant.ArenaType type){

        try {
            ChallengeCenter.getInstance().getArenaRule(type.ordinal(), new ChallengeTask.OnChallengeListener() {
                @Override
                public void onSuccess(Object response) {
                    ArenaRule arenaRule = (ArenaRule)response;
                    String rule = arenaRule.getRule();
                    dialogOfRule(type, rule);
                }

                @Override
                public void onFailure(String msg, Exception e) {
                    showToast("获取失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class MyCount extends CountDownTimer{

        /**
         * @param millisInFuture
         * @param countDownInterval
         */
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //00:00:00
            String time = DateUtils.toTimeBySecond(millisUntilFinished/1000);
            tvCountDown.setText("距离开战还有"+ time);
        }

        @Override
        public void onFinish() {
            myCount.cancel();
            getData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != myCount){
            myCount.cancel();
        }

    }
}
