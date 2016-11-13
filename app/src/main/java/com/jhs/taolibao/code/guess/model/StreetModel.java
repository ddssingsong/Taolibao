package com.jhs.taolibao.code.guess.model;

import android.content.Context;

/**
 * Created by dds on 2016/7/14.
 *
 * @TODO
 */
public interface StreetModel {
    void GetHistoryStreet(StreetModelImpl.onGetHistoryStreetListener listener);

    void GetGameRuleList(Context context , StreetModelImpl.onGetGetGameRuleListListener listener);

    void GetTodayGuessList(String userid, StreetModelImpl.onGetTodayGuessListListener listener);

    void GameInfoAddOrUpdate(String gamejson,StreetModelImpl.onGameInfoAddOrUpdateListListener listener);

    void getSystemTime(StreetModelImpl.onGetSystemTimeListener listener);

    void getGuesshistory(String userid,int page,StreetModelImpl.onGetGetGuesshistoryListener listener);
}
