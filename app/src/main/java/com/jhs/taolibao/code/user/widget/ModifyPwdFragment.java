package com.jhs.taolibao.code.user.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.user.prestenter.UserPrestenerImpl;
import com.jhs.taolibao.code.user.view.ModifyPwdView;
import com.jhs.taolibao.utils.ToastUtil;
import com.jhs.taolibao.view.TitleBar;

/**
 * Created by dds on 2016/6/8.
 *
 * @TODO
 */
public class ModifyPwdFragment extends BaseFragment implements View.OnClickListener, ModifyPwdView {
    private TitleBar titleBar;
    private Button btn_modifypwd;
    private EditText et_oldpwd;
    private EditText et_pwd;
    private EditText et_pwd2;

    private UserPrestenerImpl userPrestener;

    @Override
    protected boolean onBackPressed() {
        callbackToActivity(1, null);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPrestener = new UserPrestenerImpl(this);

    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modifypwd, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        btn_modifypwd = (Button) rootView.findViewById(R.id.btn_modifypwd);
        et_oldpwd = (EditText) rootView.findViewById(R.id.et_oldpwd);
        et_pwd = (EditText) rootView.findViewById(R.id.et_pwd);
        et_pwd2 = (EditText) rootView.findViewById(R.id.et_pwd2);

        titleBar = (TitleBar) rootView.findViewById(R.id.titlebar);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回到设置界面
                callbackToActivity(1, null);
            }
        });
        btn_modifypwd.setOnClickListener(this);
    }

    @Override
    public void onInitloadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_modifypwd:
                //提交修改密码
                handleModifyPwd();
                break;
        }
    }


    //修改密码
    private void handleModifyPwd() {
        String oldpwd = et_oldpwd.getText().toString().trim();
        String newpwd1 = et_pwd.getText().toString().trim();
        String newpwd2 = et_pwd2.getText().toString().trim();
        userPrestener.ModifyPwd(oldpwd, newpwd1, newpwd2);
    }

    @Override
    public void modifyPwdSuccess() {
        et_oldpwd.setText("");
        et_pwd.setText("");
        et_pwd2.setText("");
        callbackToActivity(2,null);


    }

    @Override
    public void showProcess() {

    }

    @Override
    public void hideProcess() {

    }

    @Override
    public void inputPwdError() {
        ToastUtil.showToast(BaseApplication.getApplication(), "请输入正确的密码");
        et_pwd.setText("");
        et_pwd2.setText("");
    }
}
