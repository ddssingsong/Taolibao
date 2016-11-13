package com.jhs.taolibao.code.challenge;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.code.challenge.constant.ChallengeConstant;
import com.jhs.taolibao.code.challenge.model.Arena;
import com.jhs.taolibao.code.challenge.model.ChallengeInfo;
import com.jhs.taolibao.code.challenge.model.MyArena;
import com.jhs.taolibao.code.challenge.model.RankList;
import com.jhs.taolibao.code.challenge.model.UserInfo;
import com.jhs.taolibao.code.challenge.utils.ChallengeTask;
import com.jhs.taolibao.code.simtrade.SimtradeCenter;
import com.jhs.taolibao.code.simtrade.entity.AccessToken;
import com.jhs.taolibao.code.simtrade.entity.Token;
import com.jhs.taolibao.code.simtrade.utils.HscloudUtils;
import com.jhs.taolibao.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiao on 2016/7/18 11:23
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:"擂台"中心管理类
 */
public class ChallengeCenter {

    private static final String TAG = ChallengeCenter.class.getSimpleName();
    private static ChallengeCenter challengeCenter = null;
    //赛事信息
    private List<Arena.Challenger> arenaDatas = new ArrayList<>();

    /**
     * 获取单例实例
     *
     * @return
     */
    public static ChallengeCenter getInstance() {
        if (null == challengeCenter) {
            synchronized (ChallengeCenter.class) {
                if (null == challengeCenter) {
                    return new ChallengeCenter();
                }
            }
        }
        return challengeCenter;
    }

    /**
     * 私有化构造
     */
    private ChallengeCenter() {

    }

    /**
     * 获取擂台信息
     * 回调接口泛型请传{@link ChallengeInfo}
     *
     * @param arenaId
     * @param listener
     * @throws Exception
     */
    public void getChallengeInfo(int arenaId, ChallengeTask.OnChallengeListener listener) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("ArenaID", String.valueOf(arenaId));
        ChallengeTask.getChallengeInfoTask(ChallengeConstant.ChallengeUrl + ChallengeConstant.ARENAINFO, map, listener);
    }

    /**
     * 用户擂台角色
     * 回调接口泛型请传{@link UserInfo}
     *
     * @param listener
     * @throws Exception
     */
    public void getUserRole(int arenaId, ChallengeTask.OnChallengeListener listener) throws Exception {
        String userId = UserInfoSingleton.getUserId();
        Map<String, String> map = new HashMap<>();
        map.put("ArenaID", String.valueOf(arenaId));
        map.put("UserID", userId);
        ChallengeTask.getUserRole(ChallengeConstant.ChallengeUrl + ChallengeConstant.USERROLE, map, listener);
    }

    /**
     * 短线排行榜
     * 回调接口泛型请传{@link RankList}
     *
     * @param listener
     * @throws Exception
     */
    public void getRankList(int type, ChallengeTask.OnChallengeListener listener) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("Type", String.valueOf(type));
        ChallengeTask.getRankList(ChallengeConstant.ChallengeUrl + ChallengeConstant.RANKLIST, map, listener);
    }

    /**
     * 我的擂台
     * 回调接口泛型请传{@link MyArena}
     *
     * @param listener
     * @throws Exception
     */
    public void getMyArena(int state, ChallengeTask.OnChallengeListener listener) throws Exception {
        String userId = UserInfoSingleton.getUserId();
        Map<String, String> map = new HashMap<>();
        map.put("UserID", userId);
        map.put("State", String.valueOf(state));
        ChallengeTask.getMyArena(ChallengeConstant.ChallengeUrl + ChallengeConstant.MYARENA, map, listener);
    }

    /**
     * 用户开户信息
     * 回调接口泛型请传{@link com.jhs.taolibao.code.challenge.model.UserOpenAccountInfo}
     *
     * @param listener
     * @throws Exception
     */
    public void getOpenAccountInfo(ChallengeTask.OnChallengeListener listener) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("UserID", "1");
        map.put("hsAccount", "70960295");
        map.put("hsPwd", "111111");
        ChallengeTask.getOpenAccountInfo(ChallengeConstant.ChallengeUrl + ChallengeConstant.USEROPENACCOUNT,
                map, listener);
    }

    /**
     * 获取首页的擂台
     * 回调接口泛型请传{@link Arena}
     *
     * @param listener
     * @throws Exception
     */
    public void getArena(ChallengeTask.OnChallengeListener listener) throws Exception {
        ChallengeTask.getArena(ChallengeConstant.ChallengeUrl + ChallengeConstant.ARENA, null, listener);
    }

    /**
     * 用户信息
     * 回调接口泛型请传{@link UserInfo}
     *
     * @param userId   用户id
     * @param listener
     * @throws Exception
     */
    public void getUserInfo(int userId, ChallengeTask.OnChallengeListener listener) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("UserID", String.valueOf(userId));
        ChallengeTask.getUserInfo(ChallengeConstant.ChallengeUrl + ChallengeConstant.USERINFO, map, listener);
    }


    /**
     * 观战 挑战的选择
     *
     * @param arenaId  擂台ID
     * @param type     类型   1 挑战   2观战
     * @param count    虚拟货币扣除数量
     * @param listener
     */
    public void getInserArenaUser(int arenaId, int type, int count, ChallengeTask.OnChallengeListener listener) {
        String userId = UserInfoSingleton.getUserId();

        Map<String, String> map = new HashMap<>();
        map.put("arenaID", String.valueOf(arenaId));
        map.put("userID", userId);
        map.put("Type", String.valueOf(type));
        map.put("Point", String.valueOf(count));
        ChallengeTask.getInserArenaUser(ChallengeConstant.ChallengeUrl + ChallengeConstant.INSERTARENAUSER, map, listener);
    }

    /**
     * 观战 挑战规则
     *
     * @param type     规则类型
     * @param listener
     */
    public void getArenaRule(int type, ChallengeTask.OnChallengeListener listener) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("type", String.valueOf(type));
        ChallengeTask.getArenaRule(ChallengeConstant.ChallengeUrl + ChallengeConstant.RULE, map, listener);
    }

    /**
     * 获取轮播广告
     *
     * @param listener
     */
    public void getAds(ChallengeTask.OnChallengeListener listener) {
        ChallengeTask.getAds(ChallengeConstant.ChallengeUrl + ChallengeConstant.ADS, null, listener);
    }

    /**
     * 获取用户总宝币数
     *
     * @param listener
     */
    public void getTotalPoint(ChallengeTask.OnChallengeListener listener) {
        String userId = UserInfoSingleton.getUserId();

        if (userId.equals("")) {
            ToastUtil.showToast(BaseApplication.getApplication(), "登录失效,请重新登录");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        ChallengeTask.getTotalPoint(ChallengeConstant.GET_USER_INFO, params, listener);
    }

    /**
     * 获取持仓信息
     *
     * @param userId
     * @param listener
     */
    public void getKeepInfo(final String userId, final HscloudUtils.onGetBeanListener listener) {
        //获取私钥
        SimtradeCenter.getInstance().getPrivateToken(String.valueOf(userId), new HscloudUtils.OnTokenGetListener() {
            @Override
            public void onSuccess(Token response) throws Exception {
                String token = response.getToken();
                if (null != token && !token.isEmpty()) {
                    SimtradeCenter.getInstance().getKeepInfo(userId, token,
                            null, listener);
                } else {
                }

            }

            @Override
            public void onFailure(String msg, Exception e) {
                //重新申请令牌
                SimtradeCenter.getInstance().getUserHsAccount(userId, new HscloudUtils.OnGetHsAccount() {
                    @Override
                    public void onSuccess(com.jhs.taolibao.code.simtrade.entity.UserInfo response) throws Exception {
                        String account = response.getUser().getHsAccount();
                        String pwd = response.getUser().getHsPwd();
                        SimtradeCenter.getInstance().getPrivateAccessToken(account, pwd, new HscloudUtils.onGetListListener() {
                            @Override
                            public void onSuccess(String response) {
                                AccessToken accessToken = new AccessToken();
                                try {
                                    //令牌获取成功
                                    JSONObject json = new JSONObject(response);
                                    accessToken = accessToken.refreshFromJson(json);
                                    final String token = accessToken.getAccessToken();
                                    //存储令牌
                                    SimtradeCenter.getInstance().savePrivateToken(userId, token, new HscloudUtils.onGetListListener() {
                                        @Override
                                        public void onSuccess(String response) throws Exception {
                                            SimtradeCenter.getInstance().getKeepInfo(userId, token,
                                                    null, listener);
                                        }

                                        @Override
                                        public void onFailure(String msg, Exception e) {

                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(String msg, Exception e) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(String msg, Exception e) {

                    }
                });
            }
        });
    }

    /**
     * 是否有观看官方擂台的权限
     *
     * @param listener
     */
    public void checkOfficialArena(ChallengeTask.OnChallengeListener listener) {
        String userId = UserInfoSingleton.getUserId();
        if (userId.equals("")) {
            ToastUtil.showToast(BaseApplication.getApplication(), "登录失效,请重新登录");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        ChallengeTask.officialArena(ChallengeConstant.ChallengeUrl + ChallengeConstant.CHECK_OFFICIAL_ARENA, params, listener);
    }

    /**
     * 购买官方擂台观看时长
     *
     * @param listener
     */
    public void setOfficialArena(ChallengeTask.OnChallengeListener listener) {
        String userId = UserInfoSingleton.getUserId();

        if (userId.equals("")) {
            ToastUtil.showToast(BaseApplication.getApplication(), "登录失效,请重新登录");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        ChallengeTask.officialArena(ChallengeConstant.ChallengeUrl + ChallengeConstant.SET_OFFICIAL_ARENA, params, listener);
    }

}
