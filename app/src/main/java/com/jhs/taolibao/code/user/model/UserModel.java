package com.jhs.taolibao.code.user.model;

/**
 * Created by dds on 2016/6/1.
 *
 * @TODO
 */
public interface UserModel {

    void Login(String username, String password, UserModelImpl.onLoginListener listener);

    void Register(String username, String password, String code, UserModelImpl.onRegisterListener listener);

    void getCode(boolean isRegist, String mobile, UserModelImpl.onGetCodeListener listener);

    void ModifyPwd(String oldpwd, String newpwd, UserModelImpl.onModifyListener listener);

    void FindPwd(String username, String newpwd, String code, UserModelImpl.onFindPwdListener listener);

    void GetuserInfo(String userid,UserModelImpl.onGetUserInfoListener listener);

    void updateUserInfo(String userJson, UserModelImpl.onUpdateUserListener listener);
}
