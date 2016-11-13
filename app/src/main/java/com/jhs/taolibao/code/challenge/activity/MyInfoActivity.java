package com.jhs.taolibao.code.challenge.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.challenge.ChallengeCenter;
import com.jhs.taolibao.code.challenge.constant.ChallengeConstant;
import com.jhs.taolibao.code.challenge.model.ArenaUserInfo;
import com.jhs.taolibao.code.challenge.model.UserInfo;
import com.jhs.taolibao.code.challenge.utils.ChallengeTask;
import com.jhs.taolibao.code.simtrade.base.BaseActivity2;
import com.jhs.taolibao.code.simtrade.utils.ImageLoaderUtil;

/**
 * 用户个人信息详情界面
 * Created by KANGXIANGTAO、xujingbo on 2016/7/13.
 */
public class MyInfoActivity extends BaseActivity2 {
    private static final String TAG = "MyInfoActivity";
    /**
     * 头像
     */
    private ImageView ivAvatar;
    /**
     * 用户名字
     */
    private TextView tvName;
    /**
     * 用户签名
     */
    private TextView tvSignature;
    /**
     * 累计收益率
     */
    private TextView tvAccumulativeRate;

    /**
     * 累计收益区间
     */
    private TextView tvAccumulativeRange;
    /**
     * 年化收益率
     */
    private TextView tvAnnualYield;
    /**
     * 守擂次数
     */
    private TextView tvGuardCount;
    /**
     * 胜率
     */
    private TextView tvOdds;
    private ArenaUserInfo userInfo;
    @Override
    public int getLayout() {
        return R.layout.activity_myinfo;
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        int userId = getIntent().getIntExtra("userid", 0);
        Log.d(TAG, "OnActCreate: userId = "+ userId);

        getUserInfo(userId);
        initView();
        //refreshUserInfo();
        //refreshDatas();
    }

    /**
     * 初始化view
     */
    private void initView(){

        ivAvatar = (ImageView) findViewById(R.id.person_info_iv_avatar);
        tvName = (TextView)findViewById(R.id.person_info_tv_name);
        tvSignature = (TextView)findViewById(R.id.person_info_tv_signature);

        tvAccumulativeRate = (TextView)findViewById(R.id.challege_info_tv_accumul_rate);
        tvGuardCount = (TextView)findViewById(R.id.challege_info_tv_total_count);
        tvAccumulativeRange = (TextView)findViewById(R.id.challege_info_tv_accumul_range);
        tvAnnualYield = (TextView)findViewById(R.id.challege_info_tv_annual_yield);

        tvOdds = (TextView)findViewById(R.id.challege_info_tv_odds);

        //返回按钮
        setViewClick(R.id.person_info_ll_back);
    }

    /**
     * 刷新用户信息
     */
    private void refreshUserInfo(){
        if (null == userInfo){
            return;
        }
        Log.d(TAG, "refreshUserInfo: "+ ChallengeConstant.URL+userInfo.getIconImg());
        ImageLoaderUtil.getInstance().displayFromWeb(
                ChallengeConstant.URL+userInfo.getIconImg(),ivAvatar,ImageLoaderUtil.roundOptions);
        tvName.setText(userInfo.getAlias());
        tvSignature.setText(userInfo.getSignature());
    }

    /**
     * 刷新擂台信息
     */
    private void refreshDatas(){
        if (null == userInfo){
            return;
        }
        tvAccumulativeRate.setText(userInfo.getCumulativeReturns());
        tvAccumulativeRange.setText(userInfo.getYeildRegion());
        tvAnnualYield.setText(userInfo.getYearsYield());
        tvGuardCount.setText(String.valueOf(userInfo.getGuard()));
        tvOdds.setText(String.valueOf(userInfo.getWin()) + " : " +String.valueOf(userInfo.getLose()));
    }

    private void getUserInfo(int userId){
        showProgressDialog();
        try {
            ChallengeCenter.getInstance().getUserInfo(userId,new ChallengeTask.OnChallengeListener() {
                @Override
                public void onSuccess(Object response) {
                    disMissDialog();
                    UserInfo info = (UserInfo)response;

                    userInfo = info.getData();
                    refreshUserInfo();
                    refreshDatas();

                }

                @Override
                public void onFailure(String msg, Exception e) {
                    disMissDialog();
                    showToast("获取信息失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void OnViewClick(View v) {
        switch (v.getId()){
            case R.id.person_info_ll_back:
                finish();
                break;
        }
    }
}
