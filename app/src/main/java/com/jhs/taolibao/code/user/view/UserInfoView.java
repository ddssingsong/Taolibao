package com.jhs.taolibao.code.user.view;

import com.jhs.taolibao.entity.UserInfo;

/**
 * Created by dds on 2016/6/29.
 *
 * @TODO
 */
public interface UserInfoView {

    void uploadImageSuccess();

    void getUserInforSucess(UserInfo userInfo);

    void modifyUserInfoSucess();

    void showProcess();

    void hideProcess();

}
