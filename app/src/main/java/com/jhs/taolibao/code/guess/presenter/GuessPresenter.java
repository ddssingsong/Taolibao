package com.jhs.taolibao.code.guess.presenter;

import android.content.Context;

/**
 * Created by dds on 2016/7/14.
 *
 * @TODO
 */
public interface GuessPresenter {


    void getHistoryStreet();
    void getGameRuleList(Context context);
    void GetUserGameInfo(String userid);
    void GameInfoAddOrUpdate(String GameJsonInfo);

    void getBaobi(String userid);
    void getSystemTime();




}
