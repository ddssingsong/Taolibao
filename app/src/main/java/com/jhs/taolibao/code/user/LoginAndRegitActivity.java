package com.jhs.taolibao.code.user;

import android.os.Bundle;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.user.widget.FindPwdFragment;
import com.jhs.taolibao.code.user.widget.LoginFragment;
import com.jhs.taolibao.code.user.widget.RegisterFragment;
import com.jhs.taolibao.utils.AppUtil;
import com.jhs.taolibao.utils.StatusBarUtil;

public class LoginAndRegitActivity extends BaseActivity implements BaseFragment.BaseFragmentCallback {
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private FindPwdFragment findPwdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.activity_login_and_regit);
        initData();


    }

    private void initData() {
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        findPwdFragment = new FindPwdFragment();

        String type = getIntent().getStringExtra("type");
        if (type.equals("login")) {
            replaceFragment(R.id.login_content, loginFragment);
        }
        if (type.equals("register")) {
            replaceFragment(R.id.login_content, registerFragment);
        }


    }

    @Override
    public void onCallback(String tag, int eventID, Object data) {
        if (tag.equals(loginFragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                finish();
            }
            if (eventID == 2) {
                //忘记密码
                replaceFragment(R.id.login_content, findPwdFragment);
            }
            if (eventID == 3) {
                //登陆成功
                AppUtil.closeSoftInput(this);
                finish();


            }
        }

        if (tag.equals(findPwdFragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                replaceFragment(R.id.login_content, loginFragment);
            }
            if (eventID == 2) {
                replaceFragment(R.id.login_content, loginFragment);
            }
        }
        if (tag.equals(registerFragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                finish();
            }
            if (eventID == 2) {
                replaceFragment(R.id.login_content, loginFragment);
            }
        }
    }
}
