package com.jhs.taolibao.code.user.view;

/**
 * Created by dds on 2016/6/3.
 *
 * @TODO
 */
public interface FindPwdView {
    void sendMsgToServer();//

    void getCodeSuccess();

    void showProcess();

    void hideProcess();

    void FindPwdSuccess();

    void inputUsernameError();

    void errorCode();

    void inputPwdNull();

    void inputCodeNull();

}
