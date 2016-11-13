package com.jhs.taolibao.code.my.view;

import com.jhs.taolibao.entity.UserInfo;

/**
 * Created by dds on 2016/7/27.
 *
 * @TODO
 */
public interface MyView {

    void getUserBalanceSuccess(UserInfo userInfo);
    void payGoldSuccess();

}
