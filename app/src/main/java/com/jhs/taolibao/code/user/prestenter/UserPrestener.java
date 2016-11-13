package com.jhs.taolibao.code.user.prestenter;

/**
 * Created by dds on 2016/6/3.
 *
 * @TODO
 */
public interface UserPrestener {
    void Login(String username, String password);

    void Register(String username, String password, String code);

    void getCode(boolean isRegist, String mobile);
    void getCode1(boolean isRegist, String mobile);

    void ModifyPwd(String oldpwd, String newpwd,String newpwd2);

    void FindPwd(String username, String newpwd, String code);

    void GetuserInfo();

    void uploadImage(String photo);

    void updateUserInfo(String userJson);
}
