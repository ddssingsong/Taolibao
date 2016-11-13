package com.jhs.taolibao.code.user;

import android.os.Bundle;
import android.view.View;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.user.widget.ModifyNickFragment;
import com.jhs.taolibao.code.user.widget.ModifySignFragment;
import com.jhs.taolibao.code.user.widget.UserInfoFragment;
import com.jhs.taolibao.utils.StatusBarUtil;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener, BaseFragment.BaseFragmentCallback {
    private UserInfoFragment userInfoFragment;
    private ModifyNickFragment modifyNickFragment;
    private ModifySignFragment modifySignFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.activity_user_info);
        initView();
        initData();
        userInfoFragment = new UserInfoFragment();
        modifyNickFragment = new ModifyNickFragment();
        modifySignFragment = new ModifySignFragment();
        replaceFragment(R.id.content_userinfo, userInfoFragment);

    }


    private void initView() {

    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onCallback(String tag, int eventID, Object data) {
        if (tag.equals(userInfoFragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                finish();

            }
            if (eventID == 2) {
                //修改昵称
                replaceFragment(R.id.content_userinfo, modifyNickFragment);
            }
            if (eventID == 3) {
                //修改签名
                replaceFragment(R.id.content_userinfo, modifySignFragment);

            }
        }

        if (tag.equals(modifyNickFragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                replaceFragment(R.id.content_userinfo, userInfoFragment);
            }
            if (eventID == 2) {
                //修改成功
                replaceFragment(R.id.content_userinfo, userInfoFragment);
                userInfoFragment.getUserInfo();
            }
            if (eventID == 3) {

            }
        }
        if (tag.equals(modifySignFragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                replaceFragment(R.id.content_userinfo, userInfoFragment);
            }
            if (eventID == 2) {
                replaceFragment(R.id.content_userinfo, userInfoFragment);
                userInfoFragment.getUserInfo();
            }
            if (eventID == 3) {

            }
        }
    }
}
