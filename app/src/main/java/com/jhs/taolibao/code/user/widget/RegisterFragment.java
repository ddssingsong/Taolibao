package com.jhs.taolibao.code.user.widget;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.user.prestenter.UserPrestenerImpl;
import com.jhs.taolibao.code.user.view.RegiterView;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.view.TitleBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dds on 2016/6/7.
 *
 * @TODO
 */
public class RegisterFragment extends BaseFragment implements RegiterView, View.OnClickListener {
    private TitleBar titleBar;
    private EditText et_username;
    private EditText et_pwd;
    private EditText et_code;
    private Button btn_code;
    private Button btn_register;
    private UserPrestenerImpl userPrestener;

    private TextView tv_phone_error;
    private TextView tv_pwd_error;
    private TextView tv_code_error;

    private TextView tv_label_mobile;
    private TextView tv_label_code;
    private TextView tv_label_pwd;

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPrestener = new UserPrestenerImpl(this);

    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        titleBar = (TitleBar) rootView.findViewById(R.id.titlebar);
        et_username = (EditText) rootView.findViewById(R.id.et_username);
        et_pwd = (EditText) rootView.findViewById(R.id.et_pwd);
        et_code = (EditText) rootView.findViewById(R.id.et_code);
        btn_code = (Button) rootView.findViewById(R.id.tx_code_time);
        btn_register = (Button) rootView.findViewById(R.id.btn_register);
        tv_phone_error = (TextView) rootView.findViewById(R.id.tv_phone_error);
        tv_pwd_error = (TextView) rootView.findViewById(R.id.tv_pwd_error);
        tv_code_error = (TextView) rootView.findViewById(R.id.tv_code_error);
        tv_label_mobile = (TextView) rootView.findViewById(R.id.tv_label_mobile);
        tv_label_code = (TextView) rootView.findViewById(R.id.tv_label_code);
        tv_label_pwd = (TextView) rootView.findViewById(R.id.tv_label_pwd);
        btn_code.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回主界面
                callbackToActivity(1, null);
            }
        });
    }

    @Override
    public void onInitloadData() {

    }

    @Override
    public void onClick(View v) {
        tv_phone_error.setVisibility(View.INVISIBLE);
        tv_code_error.setVisibility(View.INVISIBLE);
        tv_pwd_error.setVisibility(View.INVISIBLE);
        tv_label_mobile.setTextColor(ContextCompat.getColor(getActivity(), R.color.Black));
        tv_label_code.setTextColor(ContextCompat.getColor(getActivity(), R.color.Black));
        tv_label_pwd.setTextColor(ContextCompat.getColor(getActivity(), R.color.Black));
        String mobile = "";
        switch (v.getId()) {
            case R.id.tx_code_time:
                //获取验证码
                mobile = et_username.getText().toString().trim();
                userPrestener.getCode(true, mobile);
                break;
            case R.id.btn_register:
                //注册
                mobile = et_username.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                String code = et_code.getText().toString().trim();
                userPrestener.Register(mobile, pwd, code);
                break;
        }
    }

    @Override
    public void showProcess() {
        DialogUtil.showProgress(getActivity(),"正在加载中..");
    }

    @Override
    public void hideProcess() {
        DialogUtil.closeProgressDialog();
    }
    @Override
    public void getCodeSuccess() {
        et_code.setHint("请查收验证短信");
    }
    @Override
    public void registerSuccess() {
       //注册成功
        callbackToActivity(2,null);
    }

    @Override
    public void inputUsernameError() {
        tv_phone_error.setVisibility(View.VISIBLE);
        tv_phone_error.setText("请输入正确的手机号！");
        tv_label_mobile.setTextColor(ContextCompat.getColor(getActivity(), R.color.Red));
    }

    @Override
    public void inputUsernameHavingRegister() {
        tv_phone_error.setVisibility(View.VISIBLE);
        tv_phone_error.setText("不可重复注册！");
        tv_label_mobile.setTextColor(ContextCompat.getColor(getActivity(), R.color.Red));
    }

    @Override
    public void errorCode() {
        tv_code_error.setVisibility(View.VISIBLE);
        tv_code_error.setText("验证码错误");
        tv_label_code.setTextColor(ContextCompat.getColor(getActivity(), R.color.Red));
    }

    @Override
    public void inputPwdNull() {
        tv_pwd_error.setVisibility(View.VISIBLE);
        tv_pwd_error.setText("请输入密码！");
        tv_label_pwd.setTextColor(ContextCompat.getColor(getActivity(), R.color.Red));
    }

    @Override
    public void inputCodeNull() {
        tv_code_error.setVisibility(View.VISIBLE);
        tv_code_error.setText("请输入验证码！");
        tv_label_code.setTextColor(ContextCompat.getColor(getActivity(), R.color.Red));
    }


    private int recLen = 61;
    Timer timer;
    boolean flag;

    //开启倒计时
    @Override
    public void sendMsgToServer() {
        timer = new Timer();
        timer.schedule(new DateTask(), 1000, 1000);
        btn_code.setClickable(false);
    }

    class DateTask extends TimerTask {
        @Override
        public void run() {

            getActivity().runOnUiThread(new Runnable() { // UI thread
                @SuppressLint("StringFormatInvalid")
                @Override
                public void run() {
                    flag = true;
                    recLen--;
                    btn_code.setText(getString(R.string.register_testgetcode_left, recLen));
                    btn_code.setTextColor(getResources().getColor(R.color.Gunpowder));
                    btn_code.setEnabled(false);
                    if (recLen < 0) {
                        btn_code.setClickable(true);
                        btn_code.setEnabled(true);
                        btn_code.setTextColor(getResources().getColor(R.color.Red));
                        timer.cancel();
                        btn_code.setText(getString(R.string.register_testgetcode_again, 60));
                        recLen = 61;
                    }
                }
            });
        }
    }


    //取消计时器
    @Override
    public void clearTimer() {
        timer.cancel();
        btn_code.setClickable(true);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (flag) {
            timer.cancel();
        }

    }
}
