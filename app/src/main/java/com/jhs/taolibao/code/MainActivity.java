package com.jhs.taolibao.code;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.jhs.taolibao.R;
import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.challenge.widget.ChallengeFragment;
import com.jhs.taolibao.code.market.HangQingFragment;
import com.jhs.taolibao.code.market.MyStockFragment;
import com.jhs.taolibao.code.my.MyFragment;
import com.jhs.taolibao.code.my.prestenter.MainPresenterImpl;
import com.jhs.taolibao.code.my.view.MainView;
import com.jhs.taolibao.code.news.widget.NewsFragment;
import com.jhs.taolibao.entity.VersionInfo;
import com.jhs.taolibao.service.UpdateService;
import com.jhs.taolibao.utils.CalendarUtil;
import com.jhs.taolibao.utils.SharedPreferencesUtils;
import com.jhs.taolibao.utils.StatusBarUtil;

/**
 * 主界面菜单,自动更新
 *
 * @author dds
 */
public class MainActivity extends BaseActivity implements BaseFragment.BaseFragmentCallback, MainView {
    private NewsFragment newsFragment;
    private HangQingFragment hangqingFragment;
    private MyStockFragment taotiFragment;
    private ChallengeFragment challengeFragment;
    private MyFragment myFragment;

    private RelativeLayout layout_main;

    private ImageView image_toutiao;
    private TextView tv_toutiao;
    private ImageView image_hangqing;
    private TextView tv_hangqing;
    private ImageView image_tiaozhan;
    private TextView tv_tiaozhan;
    private ImageView image_user;
    private TextView tv_user;
    private ImageView plus_btn;
    private FrameLayout main_menu;

    private MainPresenterImpl mainPresenter;

    private int type = 0;


    BadgeView badgeView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏字体颜色
        type = StatusBarUtil.StatusBarLightMode(MainActivity.this);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenterImpl(this);

        initView();
        initVar();
        //初始化显示头条
        showFragment(R.id.main_content, newsFragment);
        image_toutiao.setImageResource(R.drawable.ic_tab_news_sel);
        tv_toutiao.setTextColor(getResources().getColor(R.color.Red));
        //获版本信息，是否进行更新
        mainPresenter.getVersionCode();


    }


    @Override
    protected void onResume() {
        super.onResume();

        String now = CalendarUtil.getCurrentDate("yyyy-MM-dd");
        String datestr = SharedPreferencesUtils.getString("join", "2016-8-1", this);
        if (!datestr.equals(now)) {
            badgeView.setVisibility(View.VISIBLE);
        } else {
            badgeView.setVisibility(View.INVISIBLE);
        }
    }

    private void initView() {
        image_toutiao = (ImageView) findViewById(R.id.image_toutiao);
        tv_toutiao = (TextView) findViewById(R.id.tv_toutiao);
        image_hangqing = (ImageView) findViewById(R.id.image_hangqing);
        tv_hangqing = (TextView) findViewById(R.id.tv_hangqing);
        image_tiaozhan = (ImageView) findViewById(R.id.image_tiaozhan);
        tv_tiaozhan = (TextView) findViewById(R.id.tv_tiaozhan);
        image_user = (ImageView) findViewById(R.id.image_user);
        tv_user = (TextView) findViewById(R.id.tv_user);
        plus_btn = (ImageView) findViewById(R.id.plus_btn);
        main_menu = (FrameLayout) findViewById(R.id.main_menu);
        layout_main = (RelativeLayout) findViewById(R.id.layout_main);


        badgeView = new BadgeView(this);
        badgeView.setTargetView(image_user);
        badgeView.setBackgroundResource(R.drawable.badge);
        badgeView.setTextColor(getResources().getColor(R.color.Red));
        badgeView.setTextSize(2);
        badgeView.setBadgeMargin(0, 5, 18, 0);
        badgeView.setHideOnNull(true);
        badgeView.setBadgeCount(1);

    }

    private void initVar() {
        newsFragment = new NewsFragment();
        hangqingFragment = new HangQingFragment();
        taotiFragment = new MyStockFragment();
        challengeFragment = new ChallengeFragment();
        myFragment = new MyFragment();
    }


    public void onClick(View view) {
        restartBotton();
        switch (view.getId()) {
            case R.id.layout_toutiao:
                //头条
                showFragment(R.id.main_content, newsFragment);
                image_toutiao.setImageResource(R.drawable.ic_tab_news_sel);
                tv_toutiao.setTextColor(getResources().getColor(R.color.Red));
                if (type == 0) {
                    StatusBarUtil.StatusBarLightMode(MainActivity.this);
                }

                break;
            case R.id.layout_hangqing:
                //行情
                showFragment(R.id.main_content, hangqingFragment);
                image_hangqing.setImageResource(R.drawable.ic_tab_market_sel);
                tv_hangqing.setTextColor(getResources().getColor(R.color.Red));
                if (type == 0) {
                    StatusBarUtil.StatusBarLightMode(MainActivity.this);
                }
                break;
            case R.id.plus_btn:
                //中间我的自选
                showFragment(R.id.main_content, taotiFragment);
                plus_btn.setImageResource(R.drawable.ic_tab_mine_sel);
                if (type == 0) {
                    StatusBarUtil.StatusBarLightMode(MainActivity.this);
                }
                break;
            case R.id.layout_tiaozhan:
                //挑战
                showFragment(R.id.main_content, challengeFragment);
                image_tiaozhan.setImageResource(R.drawable.ic_tab_challenge_sel);
                tv_tiaozhan.setTextColor(getResources().getColor(R.color.Red));

                StatusBarUtil.StatusBarDarkMode(MainActivity.this);
                StatusBarUtil.setStatusBarColor(MainActivity.this, R.color.black_aa);
                type = 0;
                //如果挑战界面的数据请求失败,则重新请求
                if (!isSuccess) {
                    challengeFragment.getArena();
                }
                break;
            case R.id.layout_user:
                //我的
                showFragment(R.id.main_content, myFragment);
                image_user.setImageResource(R.drawable.ic_tab_account_sel);
                tv_user.setTextColor(getResources().getColor(R.color.Red));

                StatusBarUtil.StatusBarDarkMode(MainActivity.this);
                StatusBarUtil.setStatusBarColor(MainActivity.this, R.color.Velvet);
                type = 0;
                break;
        }
    }


    private void restartBotton() {
        image_toutiao.setImageResource(R.drawable.ic_tab_news);
        image_hangqing.setImageResource(R.drawable.ic_tab_market);
        image_tiaozhan.setImageResource(R.drawable.ic_tab_challenge);
        image_user.setImageResource(R.drawable.ic_tab_account);
        plus_btn.setImageResource(R.drawable.ic_tab_mine);
        tv_toutiao.setTextColor(getResources().getColor(R.color.Gunpowder));
        tv_hangqing.setTextColor(getResources().getColor(R.color.Gunpowder));
        tv_tiaozhan.setTextColor(getResources().getColor(R.color.Gunpowder));
        tv_user.setTextColor(getResources().getColor(R.color.Gunpowder));
    }

    @Override
    public void onCallback(String tag, int eventID, Object data) {
        if (tag.equals(myFragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                //状态栏字体设置为白色
                //  StatusBarUtil.setStatusBarColor(this, R.color.Velvet);
//                StatusBarUtil.StatusBarDarkMode(MainActivity.this);

            }
            if (eventID == 2) {
                //  StatusBarUtil.StatusBarLightMode(MainActivity.this);
            }


        }


    }

    //检查更新
    @Override
    public void needUpdate(VersionInfo versionInfo) {
        String str = "当前版本：" + versionInfo.getAppname() + " Code:" + BaseApplication.getVersion() + " ,发现新版本：" + versionInfo.getAppname() +
                " Code:" + versionInfo.getVerName() + " ,是否更新？";
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("软件更新").setMessage(str)
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //启动服务
                                startService(new Intent(MainActivity.this, UpdateService.class));

                            }
                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序
                                dialog.dismiss();
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }


    //fragment数据请求是否成功
    private Boolean isSuccess = true;

    /**
     * 数据请求是否成功
     *
     * @param bool
     */
    public void onSuccess(Boolean bool) {
        this.isSuccess = bool;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
