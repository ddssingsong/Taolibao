package com.jhs.taolibao.code.guess.model;

import android.content.Context;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.GuessHistory;
import com.jhs.taolibao.entity.Interval;
import com.jhs.taolibao.entity.Street;
import com.jhs.taolibao.entity.TodayGuess;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;
import com.tendcloud.tenddata.TCAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dds on 2016/7/14.
 *
 * @TODO
 */
public class StreetModelImpl implements StreetModel {


    @Override
    public void GetHistoryStreet(final onGetHistoryStreetListener listener) {
        String url = WebBiz.GetIndexView;
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int code = object.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray array = object.getJSONArray("data");
                        JSONObject object0 = (JSONObject) array.get(0);
                        Double shang = Double.parseDouble(object0.getString("IndexValue"));
                        JSONObject object1 = (JSONObject) array.get(1);
                        Double shen = Double.parseDouble(object1.getString("IndexValue"));
                        JSONObject object2 = (JSONObject) array.get(2);
                        Double chuang = Double.parseDouble(object2.getString("IndexValue"));
                        JSONObject object3 = (JSONObject) array.get(3);
                        Double zhong = Double.parseDouble(object3.getString("IndexValue"));
                        Street street = new Street();
                        street.setStreetValue(shang);
                        street.setStreetSZValue(shen);
                        street.setStreetCYValue(chuang);
                        street.setStreetZXValue(zhong);
                        listener.onSuccess(street);
                    } else {
                        listener.onFailure("服务器错误，请稍后重试", null);
                    }


                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback);
    }

    //获取奖励区间
    @Override
    public void GetGameRuleList(final Context context ,final onGetGetGameRuleListListener listener) {
        String url = WebBiz.GetGameRuleList;
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {

                String str=response;
                TCAgent.onEvent(context, "猜涨跌数据", str);
                List<Interval> list = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response);
                    int code = object.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray array = object.getJSONArray("Stree");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = (JSONObject) array.get(i);
                            Interval interval = JsonUtils.deserialize(jsonObject, Interval.class);
                            list.add(interval);
                        }
                        TCAgent.onEvent(context, "猜涨跌数据", "开始了没有");
                        listener.onSuccess(list);
                    } else if (code == 1) {
                        listener.onFailure("今日未出奖励区间", null);
                    } else {
                        listener.onFailure("服务器错误", null);
                    }


                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback);
    }

    //获取该用户已经猜了
    @Override
    public void GetTodayGuessList(String userid, final onGetTodayGuessListListener listener) {
        String url = WebBiz.GetUserGameInfo;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("userId", userid);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                List<TodayGuess> list = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response);
                    int code = object.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONObject jsonObject = object.getJSONObject("data");
                        int TotalCount = jsonObject.getInt("TotalCount");
                        JSONArray array = jsonObject.getJSONArray("DirectoryResults");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject1 = (JSONObject) array.get(i);
                            TodayGuess todayGuess = JsonUtils.deserialize(jsonObject1, TodayGuess.class);
                            list.add(todayGuess);
                        }
                        listener.onSuccess(list);
                    } else if (code == 1) {
                        listener.todayNoGuess();
                    } else {
                        listener.onFailure("服务器错误", null);
                    }


                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    //添加或者更新猜的数据
    @Override
    public void GameInfoAddOrUpdate(String gamejson, final onGameInfoAddOrUpdateListListener listener) {
        String url = WebBiz.GameInfoAddOrUpdate;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("GameJsonInfo", gamejson);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int code = object.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        listener.onSuccess();
                    } else {
                        listener.onFailure("服务器错误,未提交成功", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    //获取系统时间
    @Override
    public void getSystemTime(final onGetSystemTimeListener listener) {
        String url = WebBiz.GetNowDate;
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int code = object.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        String time = object.getString("TimeStr");
                        listener.onSuccess(time);
                    } else {
                        listener.onFailure("服务器时间正在调整", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback);

    }

    @Override
    public void getGuesshistory(String userid, int page, final onGetGetGuesshistoryListener listener) {
        String url = WebBiz.GetInfoByUser;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("userID", userid);
        map.put("page", Integer.toString(page));
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                List<GuessHistory.DirectoryResultsEntity> DirectoryResults = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response);
                    int code = object.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONObject jsonObject = object.getJSONObject("data");
                        GuessHistory guesshistory = JsonUtils.deserialize(jsonObject, GuessHistory.class);
                        DirectoryResults = guesshistory.getDirectoryResults();
                        listener.onSuccess(DirectoryResults);
                    } else {
                        listener.onFailure("数据请求失败，code="+code, null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);


    }


    public interface onGetGetGuesshistoryListener {
        void onSuccess(List<GuessHistory.DirectoryResultsEntity> DirectoryResults);

        void onFailure(String msg, Exception e);
    }

    public interface onGetSystemTimeListener {
        void onSuccess(String time);

        void onFailure(String msg, Exception e);
    }

    public interface onGetHistoryStreetListener {
        void onSuccess(Street street);

        void onFailure(String msg, Exception e);
    }

    public interface onGetGetGameRuleListListener {
        void onSuccess(List<Interval> list);

        void onFailure(String msg, Exception e);
    }

    public interface onGetTodayGuessListListener {
        void onSuccess(List<TodayGuess> list);

        void todayNoGuess();

        void onFailure(String msg, Exception e);
    }

    public interface onGameInfoAddOrUpdateListListener {
        void onSuccess();


        void onFailure(String msg, Exception e);
    }
}
