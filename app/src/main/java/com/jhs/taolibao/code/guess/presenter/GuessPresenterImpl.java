package com.jhs.taolibao.code.guess.presenter;

import android.content.Context;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.code.guess.model.StreetModelImpl;
import com.jhs.taolibao.code.guess.view.GuessView;
import com.jhs.taolibao.code.user.model.UserModelImpl;
import com.jhs.taolibao.entity.Interval;
import com.jhs.taolibao.entity.Street;
import com.jhs.taolibao.entity.TodayGuess;
import com.jhs.taolibao.entity.UserInfo;
import com.jhs.taolibao.utils.ToastUtil;
import com.tendcloud.tenddata.TCAgent;

import java.util.Comparator;
import java.util.List;

/**
 * Created by dds on 2016/7/14.
 *
 * @TODO
 */
public class GuessPresenterImpl implements GuessPresenter {
    private GuessView guessView;
    private StreetModelImpl streetModel;
    private UserModelImpl userModel;


    public GuessPresenterImpl(GuessView guessView) {
        this.guessView = guessView;
        streetModel = new StreetModelImpl();
        userModel = new UserModelImpl();
    }

    //获取大盘指数
    @Override
    public void getHistoryStreet() {
        streetModel.GetHistoryStreet(new StreetModelImpl.onGetHistoryStreetListener() {
            @Override
            public void onSuccess(Street street) {
                guessView.getHistoryStreetSuccess(street);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                // ToastUtil.showToast(BaseApplication.getApplication(), msg);

            }
        });
    }

    //获取奖励区间
    @Override
    public void getGameRuleList(final Context context) {
        streetModel.GetGameRuleList(context, new StreetModelImpl.onGetGetGameRuleListListener() {
            @Override
            public void onSuccess(List<Interval> list) {
                // Collections.sort(list, new TypeComparator());
                TCAgent.onEvent(context, "猜涨跌数据", "开始了没有1");
                Interval interval1 = list.get(0);
//                    Interval interval2 = list.get(1);
//                    Interval interval3 = list.get(2);
//                    Interval interva14 = list.get(3);
                //String effDate = DateUtil.timeStamp2Date(interval1.getEffDate(), "MM-dd");
                // String str = interval1.getEffDate();
                TCAgent.onEvent(context, "猜涨跌数据", interval1.getEffDate());
                String effDate = interval1.getEffDate().substring(6, interval1.getEffDate().length() - 2);
                TCAgent.onEvent(context, "猜涨跌数据", "开始了没有2");
                guessView.getRuleListSuccess(interval1, null, null, null, effDate);


            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    class TypeComparator implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            Interval p1 = (Interval) object1; // 强制转换
            Interval p2 = (Interval) object2;
            return new Double(p1.getType()).compareTo(new Double(p2.getType()));
        }
    }

    //获取是否已猜
    @Override
    public void GetUserGameInfo(String userid) {
        streetModel.GetTodayGuessList(userid, new StreetModelImpl.onGetTodayGuessListListener() {
            @Override
            public void onSuccess(List<TodayGuess> list) {
                TodayGuess todayGuess1 = list.get(0);
//                TodayGuess todayGuess2 = list.get(2);
//                TodayGuess todayGuess3 = list.get(1);
//                TodayGuess todayGuess4 = list.get(0);
                guessView.getTodayGuessSuccess(todayGuess1, null, null, null);
            }

            @Override
            public void todayNoGuess() {
                guessView.todayNoGuess();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    //添加或者更新数据
    @Override
    public void GameInfoAddOrUpdate(String GameJsonInfo) {
        streetModel.GameInfoAddOrUpdate(GameJsonInfo, new StreetModelImpl.onGameInfoAddOrUpdateListListener() {
            @Override
            public void onSuccess() {
                guessView.updateOrAddSuccess();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void getBaobi(String userid) {
        userModel.GetuserInfo(userid, new UserModelImpl.onGetUserInfoListener() {
            @Override
            public void onSuccess(UserInfo userinfo) {
                int baoobi = userinfo.getUserTotalPoint();
                if (baoobi >= 500) {
                    guessView.BaobiIsEnough();
                } else {
                    guessView.BaobiIsNotEnugh();
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void getSystemTime() {
        streetModel.getSystemTime(new StreetModelImpl.onGetSystemTimeListener() {
            @Override
            public void onSuccess(String time) {
                guessView.getsystemtimeSuccess(time);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
