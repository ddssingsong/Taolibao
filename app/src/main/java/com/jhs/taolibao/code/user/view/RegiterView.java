package com.jhs.taolibao.code.user.view;

/**
 * Created by dds on 2016/6/3.
 *
 * @TODO
 */
public interface RegiterView {

    void sendMsgToServer();//

    void clearTimer();

    void getCodeSuccess();

    void showProcess();

    void hideProcess();

    void registerSuccess();

    void inputUsernameError();

    void inputUsernameHavingRegister();
    void errorCode();

    void inputPwdNull();

    void inputCodeNull();

}
