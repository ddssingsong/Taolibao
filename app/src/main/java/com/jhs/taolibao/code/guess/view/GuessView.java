package com.jhs.taolibao.code.guess.view;

import com.jhs.taolibao.entity.Interval;
import com.jhs.taolibao.entity.Street;
import com.jhs.taolibao.entity.TodayGuess;

/**
 * Created by dds on 2016/7/14.
 *
 * @TODO
 */
public interface GuessView {

    void getHistoryStreetSuccess(Street street);

    void getRuleListSuccess(Interval interval1, Interval interval2, Interval interval3, Interval interval4,String effDate);

    void getTodayGuessSuccess(TodayGuess todayGuess1, TodayGuess todayGuess2, TodayGuess todayGuess3, TodayGuess todayGuess4);

    void todayNoGuess();
    void updateOrAddSuccess();

    void BaobiIsEnough();
    void BaobiIsNotEnugh();

    void getsystemtimeSuccess(String time);
}
