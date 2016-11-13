package com.jhs.taolibao.code.my;

import android.os.Bundle;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.my.widget.SettingFragment;
import com.jhs.taolibao.code.user.widget.ModifyPwdFragment;
import com.jhs.taolibao.utils.StatusBarUtil;

public class SettingActivity extends BaseActivity implements BaseFragment.BaseFragmentCallback {

    private SettingFragment settingFragment;
    private ModifyPwdFragment modifyPwdFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
        modifyPwdFragment=new ModifyPwdFragment();
        settingFragment = new SettingFragment();
        replaceFragment(R.id.content_setting, settingFragment);
    }


    private void initView() {

    }

    private void initData() {
    }

    @Override
    public void onCallback(String tag, int eventID, Object data) {
        if (tag.equals(settingFragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                finish();
            }
            if (eventID == 2) {
                //退出登录
                UserInfoSingleton.setUserInfo(null);
                UserInfoSingleton.setUserId(null);
                finish();
            }
            if (eventID == 3) {
                replaceFragment(R.id.content_setting, modifyPwdFragment);
            }
        }

        if (tag.equals(modifyPwdFragment.getClass().getSimpleName())) {
            if (eventID == 1) {
                replaceFragment(R.id.content_setting, settingFragment);
            }
            if (eventID == 2) {
                replaceFragment(R.id.content_setting, settingFragment);
            }

        }
    }
}
