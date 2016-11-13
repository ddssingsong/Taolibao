package com.jhs.taolibao.code.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.challenge.activity.MyArenaActivity;
import com.jhs.taolibao.code.guess.GuessActivity;
import com.jhs.taolibao.code.my.prestenter.MyPresenterimpl;
import com.jhs.taolibao.code.my.view.MyView;
import com.jhs.taolibao.code.my.widget.BaobiActivity;
import com.jhs.taolibao.code.my.widget.CheckInFragment;
import com.jhs.taolibao.code.my.widget.ExchangeActivity;
import com.jhs.taolibao.code.my.widget.PhotoActivity;
import com.jhs.taolibao.code.simtrade.activity.KeepActivity;
import com.jhs.taolibao.code.user.LoginAndRegitActivity;
import com.jhs.taolibao.code.user.UserInfoActivity;
import com.jhs.taolibao.entity.UserInfo;
import com.jhs.taolibao.utils.DialogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by dds on 2016/6/13.
 *
 * @TODO 我的账户
 */
public class MyFragment extends BaseFragment implements View.OnClickListener, MyView {

    private ImageView iv_my_edit;//编辑
    private com.jhs.taolibao.view.CircleImageView iv_my_icon;//头像
    private TextView tv_my_name;//昵称
    private TextView tv_my_sign;//签名
    private LinearLayout layout_my_checkin;//签到
    private LinearLayout layout_my_challenge;//我的挑战
    private LinearLayout layout_my_guess;//猜涨跌
    private RelativeLayout layout_my_coin;//我的宝币
    private TextView tv_my_coin;//我的宝币
    private RelativeLayout layout_my_coin_change;//宝币兑换
    private RelativeLayout layout_my_simulate;//模拟交易
    private RelativeLayout layout_my_fenji;//分级套利
    private RelativeLayout layout_my_jingu;//每日金股
    private RelativeLayout layout_my_setting;//设置

    private Button btn_user_login;
    private Button btn_user_register;
    private boolean flag;//登陆标志

    private String url;


    private MyPresenterimpl myPresenter;
    private boolean ispay;
    private int total;//用户剩余宝币

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPresenter = new MyPresenterimpl(this);
    }

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        iv_my_edit = (ImageView) rootView.findViewById(R.id.iv_my_edit);
        iv_my_icon = (com.jhs.taolibao.view.CircleImageView) rootView.findViewById(R.id.iv_my_icon);
        tv_my_name = (TextView) rootView.findViewById(R.id.tv_my_name);
        tv_my_sign = (TextView) rootView.findViewById(R.id.tv_my_sign);
        layout_my_checkin = (LinearLayout) rootView.findViewById(R.id.layout_my_checkin);
        layout_my_challenge = (LinearLayout) rootView.findViewById(R.id.layout_my_challenge);
        layout_my_guess = (LinearLayout) rootView.findViewById(R.id.layout_my_guess);
        layout_my_coin = (RelativeLayout) rootView.findViewById(R.id.layout_my_coin);
        tv_my_coin = (TextView) rootView.findViewById(R.id.tv_my_coin);
        layout_my_coin_change = (RelativeLayout) rootView.findViewById(R.id.layout_my_coin_change);
        layout_my_simulate = (RelativeLayout) rootView.findViewById(R.id.layout_my_simulate);
        layout_my_fenji = (RelativeLayout) rootView.findViewById(R.id.layout_my_fenji);
        layout_my_setting = (RelativeLayout) rootView.findViewById(R.id.layout_my_setting);
        btn_user_login = (Button) rootView.findViewById(R.id.btn_user_login);
        btn_user_register = (Button) rootView.findViewById(R.id.btn_user_register);
        layout_my_jingu = (RelativeLayout) rootView.findViewById(R.id.layout_my_jingu);
        btn_user_register.setOnClickListener(this);
        btn_user_login.setOnClickListener(this);

        iv_my_edit.setOnClickListener(this);
        layout_my_checkin.setOnClickListener(this);
        layout_my_coin.setOnClickListener(this);
        layout_my_setting.setOnClickListener(this);
        layout_my_coin_change.setOnClickListener(this);
        layout_my_guess.setOnClickListener(this);
        tv_my_name.setOnClickListener(this);
        iv_my_icon.setOnClickListener(this);
        layout_my_simulate.setOnClickListener(this);
        layout_my_challenge.setOnClickListener(this);
        layout_my_jingu.setOnClickListener(this);
        layout_my_jingu.setClickable(false);

    }

    @Override
    public void onInitloadData() {
        //检查是否登陆
        if (UserInfoSingleton.getUserInfo() != null) {
            flag = true;
            UserInfo userInfo = UserInfoSingleton.getUserInfo();
            tv_my_name.setText((userInfo.getAlias() == null || userInfo.getAlias().equals("")) ? "未设置" : userInfo.getAlias());
            tv_my_sign.setVisibility(View.VISIBLE);
            tv_my_sign.setText((userInfo.getSign() == null || userInfo.getSign().equals("")) ? "这个人什么都没说" : userInfo.getSign());
            iv_my_edit.setVisibility(View.VISIBLE);
            btn_user_register.setVisibility(View.GONE);
            btn_user_login.setVisibility(View.GONE);
            if (userInfo.getIcon() == null || userInfo.getIcon().equals("")) {
                iv_my_icon.setImageResource(R.drawable.img_default_avatar);
            } else {
                url = WebBiz.UPLOAD_PREFIX + userInfo.getIcon();
                ImageLoader.getInstance().displayImage(url, iv_my_icon, BaseActivity.options1);
            }
            myPresenter.GetuserInfo(UserInfoSingleton.getUserId());

        } else {
            //未登录
            flag = false;
            tv_my_name.setText("游客");
            btn_user_register.setVisibility(View.VISIBLE);
            btn_user_login.setVisibility(View.VISIBLE);
            tv_my_sign.setVisibility(View.GONE);
            iv_my_edit.setVisibility(View.GONE);
            tv_my_coin.setText("0");
            iv_my_icon.setImageResource(R.drawable.img_default_avatar);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //检查是否登陆
        onInitloadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_icon:
                //显示头像大图
                if (flag) {
                    startActivity(new Intent(getActivity(), PhotoActivity.class).putExtra("url", url));

                } else {
                    //跳到登陆界面
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }

                break;
            case R.id.iv_my_edit:
                //修改用户信息
                if (flag) {
                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                } else {
                    //跳到登陆界面
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }

                break;
            case R.id.tv_my_name:
                //修改用户信息
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;

            case R.id.layout_my_checkin:
                //签到
                if (flag) {
                    CheckInFragment checkInFragment = new CheckInFragment();
                    checkInFragment.show(getActivity().getSupportFragmentManager(), "checkin");
                } else {
                    //跳到登陆界面
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }

                break;

            case R.id.layout_my_challenge:
                //我的挑战
                if (flag) {
                    startActivity(new Intent(getActivity(), MyArenaActivity.class));

                } else {
                    //跳到登陆界面
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }
                break;

            case R.id.layout_my_guess:
                //跳到猜涨跌界面
                if (flag) {
                    startActivity(new Intent(getActivity(), GuessActivity.class));

                } else {
                    //跳到登陆界面
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }


                break;
            case R.id.layout_my_coin:
                //我的宝币
                if (flag) {
                    startActivity(new Intent(getActivity(), BaobiActivity.class));
                } else {
                    //跳到登陆界面
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }

                break;
            case R.id.layout_my_coin_change:
                //宝币兑换
                if (flag) {
                    startActivity(new Intent(getActivity(), ExchangeActivity.class));
                } else {
                    //跳到登陆界面
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }
                break;

            case R.id.layout_my_simulate:
                //模拟交易
                if (flag) {
                    startActivity(new Intent(getActivity(), KeepActivity.class));
                } else {
                    //跳到登陆界面
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }
                break;
            case R.id.layout_my_fenji:
                //分级套利

                break;


            case R.id.layout_my_jingu:
                //每日金股
                if (flag) {
                    if (ispay) {
                        startActivity(new Intent(getActivity(), GoldActivity.class));
                    } else {
                        //弹出提示框要扣除宝币
                        DialogUtil.alertDialogTips(getActivity(), "温馨提示", "每天都推金股，一个月才3000宝币，太值了，马上扣~", new DialogUtil.ICommonCallBack() {
                            @Override
                            public void excute(Object object) {
                                //确定
                                if (total >= 3000) {
                                    myPresenter.PayRemStockDate(UserInfoSingleton.getUserId());
                                } else {
                                    DialogUtil.alertDialogTipsNoDraw(getActivity(), "温馨提示", "您的宝币不足，请及时充值!,每月只需3000宝币");
                                }

                            }
                        }, null);

                    }

                } else {
                    //跳到登陆界面
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }

                break;
            case R.id.layout_my_setting:
                //设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.btn_user_login:
                //跳到登陆界面
                startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                break;
            case R.id.btn_user_register:
                //跳到注册界面
                startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "register"));
                break;

        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            //状态栏字体设置为白色
            callbackToActivity(1, null);

        } else {
            //状态栏字体设置为黑色
            callbackToActivity(2, null);
        }

    }

    @Override
    public void getUserBalanceSuccess(UserInfo userInfo) {
        tv_my_coin.setText(Integer.toString(userInfo.getUserTotalPoint()));
        this.ispay = userInfo.isPayuser();
        this.total = userInfo.getUserTotalPoint();
        layout_my_jingu.setClickable(true);
    }

    @Override
    public void payGoldSuccess() {
        startActivity(new Intent(getActivity(), GoldActivity.class));
    }
}
