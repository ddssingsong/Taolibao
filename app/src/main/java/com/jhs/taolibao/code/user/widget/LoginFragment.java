package com.jhs.taolibao.code.user.widget;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.user.prestenter.UserPrestenerImpl;
import com.jhs.taolibao.code.user.view.LoginView;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.view.TitleBar;

/**
 * Created by dds on 2016/6/3.
 *
 * @TODO
 */
public class LoginFragment extends BaseFragment implements LoginView, View.OnClickListener {
    private TitleBar titleBar;
    private EditText etUsername;
    private EditText etPwd;
    private Button btnLogin;
    private TextView tv_login_forgetpwd;

    private TextView tv_phone_error;
    private TextView tv_pwd_error;
    private UserPrestenerImpl userPrestener;

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        titleBar = (TitleBar) rootView.findViewById(R.id.titlebar);
        etUsername = (EditText) rootView.findViewById(R.id.et_username);
        etPwd = (EditText) rootView.findViewById(R.id.et_pwd);
        btnLogin = (Button) rootView.findViewById(R.id.btn_login);
        tv_login_forgetpwd = (TextView) rootView.findViewById(R.id.tv_login_forgetpwd);
        tv_phone_error = (TextView) rootView.findViewById(R.id.tv_phone_error);
        tv_pwd_error = (TextView) rootView.findViewById(R.id.tv_pwd_error);

        tv_login_forgetpwd.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
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
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPwd.getText().length() >= 6) {
                    String username = etUsername.getText().toString();
                    if (isMobileNO(username) && username.length() == 11) {
                        btnLogin.setEnabled(true);
                    } else {
                        btnLogin.setEnabled(false);
                    }
                } else {
                    btnLogin.setEnabled(false);
                }

            }
        });

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = etUsername.getText().toString();
                if (isMobileNO(username) && username.length() == 11) {
                    if (etPwd.getText().length() >= 6) {
                        btnLogin.setEnabled(true);
                    } else {
                        btnLogin.setEnabled(false);
                    }
                } else {
                    btnLogin.setEnabled(false);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_forgetpwd:
                //忘记密码
                callbackToActivity(2, null);
                break;
            case R.id.btn_login:
                //登陆
                String name = etUsername.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                userPrestener.Login(name, pwd);
                break;
        }
    }


    @Override
    public void showProgress() {
        DialogUtil.showProgress(getActivity(), "正在登陆中");
    }

    @Override
    public void hideProgress() {
        DialogUtil.closeProgressDialog();
    }

    @Override
    public void loginSuccess() {
        callbackToActivity(3, null);
    }

    @Override
    public void inputUsernameError() {
        tv_phone_error.setVisibility(View.VISIBLE);
        tv_phone_error.setText("您输入的手机号有误");

    }

    @Override
    public void inputPwdNull() {
        tv_pwd_error.setVisibility(View.VISIBLE);
        tv_pwd_error.setText("您输入的密码有误");
    }

    public boolean isMobileNO(String mobiles) {
        if (mobiles.startsWith("13")) {
            return true;
        }
        if (mobiles.startsWith("14")) {
            return true;
        }
        if (mobiles.startsWith("15")) {
            return true;
        }
        if (mobiles.startsWith("17")) {
            return true;
        }
        if (mobiles.startsWith("18")) {
            return true;
        }
        return false;
    }
}
