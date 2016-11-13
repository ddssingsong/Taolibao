package com.jhs.taolibao.code.challenge.utils;

import android.util.Log;

import com.jhs.taolibao.code.challenge.model.Arena;
import com.jhs.taolibao.code.challenge.model.ArenaAds;
import com.jhs.taolibao.code.challenge.model.ArenaRule;
import com.jhs.taolibao.code.challenge.model.ChallengeInfo;
import com.jhs.taolibao.code.challenge.model.MyArena;
import com.jhs.taolibao.code.challenge.model.OfficialArenaRight;
import com.jhs.taolibao.code.challenge.model.RankList;
import com.jhs.taolibao.code.challenge.model.UserInfo;
import com.jhs.taolibao.code.challenge.model.UserOpenAccountInfo;
import com.jhs.taolibao.code.challenge.model.UserRole;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author jiao on 2016/7/18 11:40
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:"擂台"访问网络工具类
 */
public class ChallengeTask {
    private static final String TAG = ChallengeTask.class.getSimpleName();

    /**
     * "擂台信息"
     *
     * @param url
     * @param map
     * @param listener
     * @throws Exception
     */
    public static void getChallengeInfoTask(String url, Map<String, String> map, final OnChallengeListener listener)
            throws Exception {
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {
                ChallengeInfo deserialize = JsonUtils.deserialize(response, ChallengeInfo.class);

                if (deserialize.getCode() == 0) {
                    listener.onSuccess(deserialize);
                } else {
                    listener.onFailure(deserialize.getErrinfo(), null);
                }
            }

        }, map);
    }

    /**
     * 用户擂台角色
     *
     * @param url
     * @param map
     * @param listener
     * @throws Exception
     */
    public static void getUserRole(String url, Map<String, String> map, final OnChallengeListener listener)
            throws Exception {
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {
                UserRole userRole = JsonUtils.deserialize(response, UserRole.class);
                if (userRole.getCode() == 0) {
                    listener.onSuccess(userRole);
                } else {
                    listener.onFailure(userRole.getErrinfo(), null);
                }

            }
        }, map);

    }

    /**
     * 短线排行榜
     *
     * @param url
     * @param map
     * @param listener
     * @throws Exception
     */
    public static void getRankList(String url, Map<String, String> map, final OnChallengeListener listener)
            throws Exception {
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {
                RankList deserialize = JsonUtils.deserialize(response, RankList.class);
                if (deserialize.getCode() == 0) {
                    listener.onSuccess(deserialize);
                } else {
                    listener.onFailure(deserialize.getErrinfo(), null);
                }
            }
        }, map);
    }

    /**
     * 我的擂台
     *
     * @param url
     * @param map
     * @param listener
     * @throws Exception
     */
    public static void getMyArena(String url, Map<String, String> map, final OnChallengeListener listener)
            throws Exception {
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {
                MyArena deserialize = JsonUtils.deserialize(response, MyArena.class);
                if (deserialize.getCode() == 0) {
                    listener.onSuccess(deserialize);
                } else {
                    listener.onFailure(deserialize.getErrinfo(), null);
                }
            }
        }, map);
    }

    /**
     * 用户开户信息
     *
     * @param url
     * @param map
     * @param listener
     * @throws Exception
     */
    public static void getOpenAccountInfo(String url, Map<String, String> map, final OnChallengeListener listener)
            throws Exception {
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {

                UserOpenAccountInfo deserialize = JsonUtils.deserialize(response, UserOpenAccountInfo.class);
                if (deserialize.getCode() == 0) {
                    listener.onSuccess(deserialize);
                } else {
                    listener.onFailure(deserialize.getErrinfo(), null);
                }
            }
        }, map);

    }

    /**
     * 用户信息
     *
     * @param url
     * @param map
     * @param listener
     * @throws Exception
     */
    public static void getUserInfo(String url, Map<String, String> map, final OnChallengeListener listener)
            throws Exception {
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {
                try {

                    UserInfo deserialize = JsonUtils.deserialize(response, UserInfo.class);
                    if (deserialize.getCode() == 0) {
                        listener.onSuccess(deserialize);
                    } else {
                        listener.onFailure(deserialize.getErrinfo(), null);
                    }

                } catch (Exception e) {
                }

            }
        }, map);
    }

    /**
     * 获取首页的擂台
     *
     * @param url
     * @param map
     * @param listener
     * @throws Exception
     */
    public static void getArena(String url, Map<String, String> map, final OnChallengeListener listener)
            throws Exception {
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    Arena deserialize = JsonUtils.deserialize(response, Arena.class);
                    if (deserialize.getCode() == 0) {
                        listener.onSuccess(deserialize);
                    } else {
                        listener.onFailure(deserialize.getErrinfo(), null);
                    }

                } catch (Exception e) {

                }
            }
        }, map);
    }

    public static void getInserArenaUser(String url, Map<String, String> map, final OnChallengeListener listener) {
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("code") && (jsonObject.getInt("code") == 0)) {
                        listener.onSuccess(response);
                    } else {
                        listener.onFailure("扣除失败", null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, map);
    }

    /**
     * 观战 挑战规则
     *
     * @param url
     * @param listener
     */
    public static void getArenaRule(String url, Map<String, String> map, final OnChallengeListener listener) throws Exception {
        Log.e(TAG, "getArenaRule getArenaRule: " + url);
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    ArenaRule deserialize = JsonUtils.deserialize(response, ArenaRule.class);
                    if (deserialize.getCode() == 0) {
                        listener.onSuccess(deserialize);
                    } else {
                        listener.onFailure(deserialize.getErrinfo(), null);
                    }

                } catch (Exception e) {
                }

            }
        }, map);
    }

    public static void getAds(String url, Map<String, String> map, final OnChallengeListener listener) {
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {

                try {
                    ArenaAds deserialize = JsonUtils.deserialize(response, ArenaAds.class);
                    if (deserialize.getCode() == 0) {
                        listener.onSuccess(deserialize);
                    } else {
                        listener.onFailure(deserialize.getErrinfo(), null);
                    }

                } catch (Exception e) {
                }

            }
        }, map);
    }

    /**
     * 获取用户总宝币数
     *
     * @param url
     * @param map
     * @param listener
     */
    public static void getTotalPoint(String url, Map<String, String> map, final OnChallengeListener listener) {
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {

                try {
                    com.jhs.taolibao.code.simtrade.entity.UserInfo deserialize =
                            JsonUtils.deserialize(response, com.jhs.taolibao.code.simtrade.entity.UserInfo.class);
                    if (deserialize.getCode() == 0) {
                        listener.onSuccess(deserialize);
                    } else {
                        listener.onFailure("获取失败", null);
                    }
                } catch (Exception e) {
                }

            }
        }, map);
    }

    /**
     * 是否有观看官方擂台的权限\购买观看时长
     *
     * @param url
     * @param map
     * @param listener
     */
    public static void officialArena(String url, Map<String, String> map, final OnChallengeListener listener) {
        OkHttpUtil.postAsyn(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {

                OfficialArenaRight deserialize =
                        JsonUtils.deserialize(response, OfficialArenaRight.class);

                listener.onSuccess(deserialize);

            }
        }, map);
    }

    /**
     * "擂台"模块数据回调接口
     */
    public interface OnChallengeListener<T> {
        void onSuccess(T response);

        void onFailure(String msg, Exception e);
    }
}