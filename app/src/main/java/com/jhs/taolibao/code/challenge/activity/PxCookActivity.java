package com.jhs.taolibao.code.challenge.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.challenge.ChallengeCenter;
import com.jhs.taolibao.code.challenge.constant.ChallengeConstant;
import com.jhs.taolibao.code.challenge.model.ArenaUserInfo;
import com.jhs.taolibao.code.challenge.model.UserInfo;
import com.jhs.taolibao.code.challenge.utils.ChallengeTask;
import com.jhs.taolibao.code.simtrade.adapter.KeepAdapter2;
import com.jhs.taolibao.code.simtrade.base.BaseActivity2;
import com.jhs.taolibao.code.simtrade.entity.KeepBean;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.code.simtrade.utils.HscloudUtils;
import com.jhs.taolibao.code.simtrade.utils.ImageLoaderUtil;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshBase;
import com.jhs.taolibao.base.pullrefresh.ui.PullToRefreshListView;

import java.util.Date;
import java.util.List;

/**
 * 用户个人持仓界面
 * Created by KANGXIANGTAO、xujingbo on 2016/7/13.
 */
public class PxCookActivity extends BaseActivity2 {
    private static final String TAG = "PxCookActivity";
    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private LinearLayout llBack;
    private ImageView ivAvatar;
    private TextView tvName,tvAccumulativeRate;
    //
    private List<KeepBean> datasList;
    private ArenaUserInfo userInfo;
    private KeepAdapter2 keepAdapter;
    @Override
    public int getLayout() {
        return R.layout.activity_pxcook;
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        int userId = getIntent().getIntExtra("userid",0);

        getUserInfo(userId);
        initView();

    }

    /**
     * 初始化view
     */
    private void initView() {
        findViewById(R.id.person_info_ll_profit).setVisibility(View.VISIBLE);
        findViewById(R.id.person_info_tv_signature).setVisibility(View.GONE);

        ivAvatar = (ImageView) findViewById(R.id.person_info_iv_avatar);
        tvAccumulativeRate = (TextView) findViewById(R.id.person_info_tv_profit);
        tvName = (TextView) findViewById(R.id.person_info_tv_name);

        //持仓列表
        keepAdapter = new KeepAdapter2();
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.ptrl);
        listView = pullToRefreshListView.getRefreshableView();
        listView.setCacheColorHint(0);
        listView.setAdapter(keepAdapter);
        listView.setEmptyView(findViewById(R.id.ll_data_null));
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String s = DateUtils.getTimestampString(new Date(System.currentTimeMillis()));
                pullToRefreshListView.setLastUpdatedLabel(s);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        setViewClick(R.id.person_info_ll_back);
    }

    /**
     * 刷新用户信息
     */
    private void refreshUserInfo(){
        Log.d(TAG, "refreshUserInfo: ");
        if (null == userInfo){
            return;
        }
        ImageLoaderUtil.getInstance().displayFromWeb(
                ChallengeConstant.URL + userInfo.getIconImg(), ivAvatar, ImageLoaderUtil.roundOptions);
        tvName.setText(userInfo.getAlias());
        tvAccumulativeRate.setText(userInfo.getCumulativeReturns());

    }


    /**
     *获取用户信息
     * @param userId
     */
    private void getUserInfo(int userId){
        Log.d(TAG, "getUserInfo: ");
        try {
            ChallengeCenter.getInstance().getUserInfo(userId, new ChallengeTask.OnChallengeListener() {
                @Override
                public void onSuccess(Object response) {
                    Log.e(TAG, "onSuccess: response :");
                    UserInfo info = (UserInfo) response;
                    userInfo = info.getData();
                    getKeepInfo();
                    refreshUserInfo();

                }

                @Override
                public void onFailure(String msg, Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取持仓信息
     */
    private void getKeepInfo(){
        Log.d(TAG, "getKeepInfo: ");
        //showProgressDialog();
        int userId = userInfo.getUserID();
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
    @Override
    public void OnViewClick(View v) {
        switch (v.getId()){
            case R.id.person_info_ll_back:
                finish();
                break;
            default:
                break;
        }

    }


}
