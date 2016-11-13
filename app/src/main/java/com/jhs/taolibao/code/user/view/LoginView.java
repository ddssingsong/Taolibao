package com.jhs.taolibao.code.user.view;

/**
 * Created by dds on 2016/6/3.
 *
 * @TODO
 */
public interface LoginView {

    void showProgress();

    void hideProgress();

    void loginSuccess();
    void inputUsernameError();
    void inputPwdNull();
}
