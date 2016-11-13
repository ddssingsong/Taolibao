package com.jhs.taolibao.code.user.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.user.prestenter.UserPrestenerImpl;
import com.jhs.taolibao.code.user.view.UserInfoView;
import com.jhs.taolibao.entity.UserInfo;
import com.jhs.taolibao.utils.AppUtil;
import com.jhs.taolibao.utils.DateUtil;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.utils.ToastUtil;

/**
 * Created by dds on 2016/6/30.
 *
 * @TODO
 */
public class ModifyNickFragment extends BaseFragment implements UserInfoView {
    private com.jhs.taolibao.view.TitleBar titlebar;//标题栏
    private EditText tv_modify_nick;
    private ImageView iv_clear;
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
        View view = inflater.inflate(R.layout.fragment_modify_nick, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        tv_modify_nick = (EditText) rootView.findViewById(R.id.tv_modify_nick);
        iv_clear = (ImageView) rootView.findViewById(R.id.iv_clear);
        titlebar = (com.jhs.taolibao.view.TitleBar) rootView.findViewById(R.id.titlebar);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_modify_nick.setText("");
            }
        });
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackToActivity(1, null);
            }
        });
        titlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias = "";
                if (tv_modify_nick.getText() != null&&!tv_modify_nick.getText().toString().equals("")) {
                    alias = tv_modify_nick.getText().toString().trim();
                    UserInfo userInfo = UserInfoSingleton.getUserInfo();
                    userInfo.setAlias(alias);
                    if(userInfo.getBirthday()!=null&&!userInfo.getBirthday().equals("")){
                        userInfo.setBirthday(DateUtil.timeStamp2Date(userInfo.getBirthday(),"yyyy-MM-dd"));
                    }

                    if (userInfo != null) {
                        String userJson = JsonUtils.serialize(userInfo);
                        userPrestener.updateUserInfo(userJson);
                    }
                }else{
                    ToastUtil.showToast(getActivity(),"请输入昵称");
                }


            }
        });

    }

    @Override
    public void onInitloadData() {

    }

    @Override
    public void uploadImageSuccess() {

    }

    @Override
    public void getUserInforSucess(UserInfo userInfo) {

    }

    @Override
    public void modifyUserInfoSucess() {
        ToastUtil.showToast(getActivity(), "修改成功");
        tv_modify_nick.setText("");
        AppUtil.closeSoftInput(getActivity());
        callbackToActivity(2, null);

    }

    @Override
    public void showProcess() {

    }

    @Override
    public void hideProcess() {

    }
}
