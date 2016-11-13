package com.jhs.taolibao.code.simtrade;

import android.util.Log;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.code.simtrade.constant.Constant;
import com.jhs.taolibao.code.simtrade.entity.AccessToken;
import com.jhs.taolibao.code.simtrade.entity.KeepBean;
import com.jhs.taolibao.code.simtrade.entity.Token;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.code.simtrade.utils.HscloudUtils;
import com.jhs.taolibao.code.simtrade.utils.SharePreference;
import com.jhs.taolibao.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 模拟交易管理类
 * Created by xujingbo on 2016/7/12.
 */
public class SimtradeCenter {
    private static final String TAG = "SimtradeCenter";

    //恒生账户
    private static String accountContent = "70007011";//"70007012","70007011","70960295"

    private static String accountPwd = "111111";

    /**
     * singleton instance
     */
    private static SimtradeCenter singletonInstance;

    private String token;
    private String refreshToken;

    /**
     * 获取单体实例
     *
     * @return
     */
    public static SimtradeCenter getInstance() {
        if (null == singletonInstance) {
            synchronized (SimtradeCenter.class) {
                if (null == singletonInstance) {
                    singletonInstance = new SimtradeCenter();
                }
            }
        }
        return singletonInstance;
    }

    /**
     * 禁止单独创建模拟交易管理类
     */
    private SimtradeCenter() {

    }

    /**
     * 先从后台获取privatetoken,如果后台没有，重新从恒生获取
     * 在启动页调用（先判断用户是否登录，登录成功则调用此方法，获取该用户恒生访问的令牌）
     *
     * @param account  恒生的账号
     * @param password 恒生的密码
     */
    public void init(final String account, final String password) {
        saveHsAccountPwd(account, password);
        final String userId = getUserId();
        if (null == userId || userId.equals("")) {
            return;
        }
        //final String userId = "11";
        getPrivateToken(userId, new HscloudUtils.OnTokenGetListener() {
            @Override
            public void onSuccess(Token response) {
                String token = response.getToken();
                if (null != token && !token.equals("")) {
                    //把令牌存储到本地
                    savePrivateTokenToSp(token);
                } else {//后台没有token，重新从恒生获取
                    try {
                        getPrivateAccessToken(account, password, new HscloudUtils.onGetListListener() {
                            @Override
                            public void onSuccess(String response) {
                                AccessToken accessToken = new AccessToken();
                                try {
                                    //令牌获取成功
                                    JSONObject json = new JSONObject(response);
                                    accessToken = accessToken.refreshFromJson(json);
                                    String token = accessToken.getAccessToken();
                                    //把令牌存储到本地
                                    savePrivateTokenToSp(token);
                                    //存储令牌到后台
                                    savePrivateToken(userId, token, new HscloudUtils.onGetListListener() {
                                        @Override
                                        public void onSuccess(String response) {

                                        }

                                        @Override
                                        public void onFailure(String msg, Exception e) {

                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(String msg, Exception e) {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                try {
                    getPrivateAccessToken(account, password, new HscloudUtils.onGetListListener() {
                        @Override
                        public void onSuccess(String response) {
                            AccessToken accessToken = new AccessToken();
                            try {
                                //令牌获取成功
                                JSONObject json = new JSONObject(response);
                                accessToken = accessToken.refreshFromJson(json);
                                String token = accessToken.getAccessToken();
                                //把令牌存储到本地
                                savePrivateTokenToSp(token);
                                //存储令牌到后台
                                savePrivateToken(userId, token, new HscloudUtils.onGetListListener() {
                                    @Override
                                    public void onSuccess(String response) {

                                    }

                                    @Override
                                    public void onFailure(String msg, Exception e) {

                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(String msg, Exception e) {

                        }
                    });
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        //获取公有令牌
//        if (isPublicTokenInvalid()) {
//            Log.d(TAG, "OnActCreate: publicToken未获取或publicToken无效，重新获取令牌");
//            //token未获取或token无效，重新获取令牌
        getPublicAccessToken();
//        }
    }

    /**
     * 从恒生获取私有token
     *
     * @param account
     * @param password
     */
    public void getPrivateAccessToken(String account, String password,
                                      HscloudUtils.onGetListListener listener) throws Exception {

        Map<String, String> tokenMap = new HashMap<String, String>();

        tokenMap.put("targetcomp_id", Constant.TARGETCOMP_ID);
        tokenMap.put("sendercomp_id", Constant.SENDERCOMP_ID);
        tokenMap.put("targetbusinsys_no", Constant.TARGETBUSINSYS_NO);
        tokenMap.put("op_station", Constant.REDIRECT_URI);
        tokenMap.put("input_content", Constant.INPUT_CONTENT);
        tokenMap.put("account_content", account);
        tokenMap.put("password", password);
        HscloudUtils.getPrivateTokenFromHs(tokenMap, listener);
    }

    /**
     * 将获取到的令牌存储到后台
     */
    public void savePrivateToken(String userId, String token, HscloudUtils.onGetListListener listener) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userId));
        map.put("token", token);
        HscloudUtils.savePrivateToken(Constant.TOKEN_SAVE, map, listener);
    }

    /**
     * 从自己后台获取令牌
     */
    public void getPrivateToken(String userId, HscloudUtils.OnTokenGetListener listener) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        HscloudUtils.getPrivateToken(Constant.TOKEN_GET, map, listener);
    }

    /**
     * 获取公有token
     */
    public void getPublicAccessToken() {
        try {
            Map<String, String> tokenMap = new HashMap<String, String>();
            //String token = SimtradeCenter.getInstance().getPublicToken();
            /*if (null != token) {
                Log.d(TAG, "getToken: token" + token);
                String refreshToken = SimtradeCenter.getInstance().getPublicRefreshToken();
                Log.d(TAG, "getToken: refreshToken " + refreshToken);
                tokenMap.put("grant_type", "refresh_token");
                tokenMap.put("refresh_token", refreshToken);
            } else {*/
            tokenMap.put("grant_type", Constant.GRANT_TYPE);
            //以用户Id作为公有令牌的openid
            tokenMap.put("open_id", UserInfoSingleton.getUserId());


            //}
            HscloudUtils.getPubicAccessToken(tokenMap);
        } catch (Exception e) {

        }
    }

    /**
     * 获取行情报价
     *
     * @param code     股票代码
     * @param listener
     * @throws Exception
     */

    public void getReal(String code,
                        final HscloudUtils.onGetListListener listener) throws Exception {

        Map<String, String> params = new HashMap<>();

        params.put("en_prod_code", code);
        params.put("fields", "high_px,low_px,bid_grp,offer_grp,up_px,down_px,prod_name");

        HscloudUtils.getInfoList(Constant.OPEN_URL + Constant.REAL, params, getPublicToken(), listener);

    }

    /**
     * 获取"持仓信息"
     *
     * @throws Exception
     */

    public void getKeepInfo(String userId, String token, final String code, final HscloudUtils.onGetBeanListener listener) throws Exception {
        if (null == token && userId == getUserId()) {
            //如果是用户自己，token从本地存储的token获取
            token = getPrivateTokenFromSp();
        }
        String code1;
        if (null == code) {
            code1 = "";
        } else {
            code1 = code;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("targetcomp_id", Constant.TARGETCOMP_ID);
        params.put("sendercomp_id", Constant.SENDERCOMP_ID);
        params.put("exchange_type", "");
        params.put("stock_account", "");
        params.put("stock_code", code1);
        params.put("position_str", "");
        params.put("request_num", "");
        HscloudUtils.getInfoList(Constant.URL + Constant.KEEP, params, token, new HscloudUtils
                .onGetListListener() {
            @Override
            public void onSuccess(String response) {
               // Log.d("KeepActivitys", "获取持仓信息："+response);
                KeepBean deserialize = JsonUtils.deserialize(response, KeepBean.class);
                if (null != deserialize.getData()) {
                    for (KeepBean.KeepItem item : deserialize.getData()) {
                        if (item.getExchange_type().equals("1")) {
                            item.setStock_code1(item.getStock_code() + ".SS");
                        } else if (item.getExchange_type().equals("2")) {
                            item.setStock_code1(item.getStock_code() + ".SZ");
                        }
                    }
                }
                listener.onSuccess(deserialize);

            }

            @Override
            public void onFailure(String msg, Exception e) {
                listener.onFailure(msg, e);
            }
        });
    }


    /**
     * 获取公有令牌
     *
     * @return
     */
    public String getPublicToken() {
        this.token = BaseApplication.spUserInfo.getString(SharePreference.PUBLIC_ACCTOKEN, null);
        Log.d(TAG, "getPublicToken: token " + token);
        return token;
    }

    public String getPublicRefreshToken() {
        this.refreshToken = BaseApplication.spUserInfo.getString(SharePreference.PUBLIC_REFRESH_ACCTOKEN, null);
        return refreshToken;
    }


    /**
     * 判断公有token是否失效
     */
    public boolean isPublicTokenInvalid() {
        Log.d(TAG, "isPublicTokenInvalid: ");
        if (null != getPublicToken()) {
            long curTime = DateUtils.getCurrentTime().getTime();
            long oriTime = BaseApplication.spUserInfo.getLong(SharePreference.PUBLIC_GET_TOKEN_TIEM, 0);
            long expiresIn = BaseApplication.spUserInfo.getLong(SharePreference.PUBLIC_EXPIRES_IN, 0);

            if ((oriTime + expiresIn * 1000) > curTime) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * 获取证券成交查询
     *
     * @param listener
     */
    public void getBusiness(final HscloudUtils.onGetListListener listener) throws Exception {
        String url = Constant.URL + Constant.BUSINESS_QRY;
        Map<String, String> params = new HashMap<>();
        params.put("targetcomp_id", Constant.TARGETCOMP_ID);
        params.put("sendercomp_id", Constant.SENDERCOMP_ID);
        params.put("exchange_type", "");
        params.put("stock_account", "");
        params.put("stock_code", "");
        params.put("position_str", "");
        params.put("request_num", "");

        HscloudUtils.getInfoList(url, params, getPrivateTokenFromSp(), listener);

    }

    /**
     * 获取证券委托查询
     *
     * @param listener
     */
    public void getEntrustqry(final HscloudUtils.onGetListListener listener) throws Exception {

        String url = Constant.URL + Constant.ENTRUST_QRY;
        HashMap<String, String> params = new HashMap<>();
        params.put("targetcomp_id", Constant.TARGETCOMP_ID);
        params.put("sendercomp_id", Constant.SENDERCOMP_ID);
        params.put("exchange_type", "");
        params.put("stock_account", "");
        params.put("stock_code", "");
        params.put("action_in", "");
        params.put("locate_entrust_no", "");
        params.put("position_str", "");
        params.put("request_num", "");

        HscloudUtils.getInfoList(url, params, getPrivateTokenFromSp(), listener);


    }

    /**
     * 获取历史证券委托查询
     *
     * @param listener
     */
    public void getEntrustHistoryqry(final String start_date, final String end_date, final HscloudUtils.onGetListListener listener) throws Exception {

        String url = Constant.URL + Constant.ENTRUSTHIS_QRY;
        Map<String, String> params = new HashMap<>();
        params.put("targetcomp_id", Constant.TARGETCOMP_ID);
        params.put("sendercomp_id", Constant.SENDERCOMP_ID);
        params.put("start_date", start_date);
        params.put("end_date", end_date);
        params.put("exchange_type", "");
        params.put("stock_account", "");
        params.put("stock_code", "");
        params.put("position_str", "");
        params.put("request_num", "");


        HscloudUtils.getInfoList(url, params, getPrivateTokenFromSp(), listener);


    }

    /**
     * 获取历史证券成交查询
     *
     * @param listener
     */
    public void getBusinessHistoryqry(final String start_date, final String end_date, final HscloudUtils.onGetListListener listener) throws Exception {

        String url = Constant.URL + Constant.BUSINESSHIS_QRY;
        Map<String, String> params = new HashMap<>();
        params.put("targetcomp_id", Constant.TARGETCOMP_ID);
        params.put("sendercomp_id", Constant.SENDERCOMP_ID);
        params.put("start_date", start_date);
        params.put("end_date", end_date);
        params.put("position_str", "");
        params.put("request_num", "");


        HscloudUtils.getInfoList(url, params, getPrivateTokenFromSp(), listener);

    }


    /**
     * 获取按键精灵
     *
     * @param listener
     * @throws IOException
     */
    public void getWizard(String code, HscloudUtils.onGetListListener listener) throws Exception {
        String url = Constant.OPEN_URL + Constant.WIZARD;
        Map<String, String> params = new HashMap<>();

        params.put("prod_code", code);
        // 下边两个参数非必需。
        params.put("en_finance_mic", "SZ,SS");
//        params.put("data_count", "8");
        HscloudUtils.getInfoList(url, params, getPublicToken(), listener);

    }


    /**
     * 获取大约可买数量
     *
     * @param code
     * @param price
     * @param listener
     */
    public void getAlmostBuyCount(final String code, final String price, final String exchangeType,
                                  final HscloudUtils.onGetListListener listener) throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("targetcomp_id", Constant.TARGETCOMP_ID);//目标机构编码
        params.put("sendercomp_id", Constant.SENDERCOMP_ID);//发送机构编码
        params.put("exchange_type", exchangeType);
        params.put("stock_account", "");
        params.put("stock_code", code);
        params.put("entrust_price", price);
        params.put("entrust_prop", "0");

        HscloudUtils.getInfoList(Constant.URL + Constant.ALMOST_BUY_COUNT, params, getPrivateTokenFromSp(), listener);


    }

    /**
     * 普通委托
     *
     * @param code     证券代码
     * @param price    委托价格
     * @param bs       买卖方向
     * @param count    委托数量
     * @param listener
     * @throws Exception
     */
    public void entrustEnter(final String code, final String price, final String count, final String bs,
                             final HscloudUtils.onGetListListener listener) throws Exception {

        String exchangeType = "1";
        if (code.contains(".SS")) {
            exchangeType = "1";
        } else if (code.contains(".SZ")) {
            exchangeType = "2";
        }
        Map<String, String> params = new HashMap<>();
        params.put("targetcomp_id", Constant.TARGETCOMP_ID);//目标机构编码
        params.put("sendercomp_id", Constant.SENDERCOMP_ID);//发送机构编码
        params.put("exchange_type", exchangeType);//证券类别
        params.put("stock_account", "");//证券账号
        params.put("stock_code", code);
        params.put("entrust_price", price);
        params.put("entrust_amount", count);
        params.put("entrust_bs", bs);
        params.put("entrust_prop", "0");//委托属性

        HscloudUtils.getInfoList(Constant.URL + Constant.ENTRUST_ENTER, params, getPrivateTokenFromSp(), listener);
    }

    /**
     * 撤单
     *
     * @param entrustno 委托编号
     * @param listener
     * @throws Exception
     */
    public void withdrawFlag(final String entrustno, final HscloudUtils.onGetListListener listener) throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("targetcomp_id", Constant.TARGETCOMP_ID);//目标机构编码
        params.put("sendercomp_id", Constant.SENDERCOMP_ID);//发送机构编码
        params.put("exchange_type", "");
        params.put("entrust_no", entrustno);
        params.put("stock_account", "");
        params.put("entrust_date", "");

        HscloudUtils.getInfoList(Constant.URL + Constant.WITHDRAW_ENTER, params, getPrivateTokenFromSp(), listener);


    }

    /**
     * 收益率排名查询
     *
     * @param listener
     * @throws Exception
     */
    public void getAssetsInfo(final HscloudUtils.onGetListListener listener) throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("targetcomp_id", Constant.TARGETCOMP_ID);//目标机构编码
        params.put("sendercomp_id", Constant.SENDERCOMP_ID2);//发送机构编码
        params.put("apply_no", "N000001");
        params.put("profit_period", "1");
        params.put("request_num", "");

        HscloudUtils.getInfoList(Constant.URL + Constant.ASSETS, params, getPrivateTokenFromSp(), listener);


    }

    /**
     * 用户个人收益率排名查询
     *
     * @param listener
     * @throws Exception
     */
    public void getUserIncomeInfo(final HscloudUtils.onGetListListener listener) throws Exception {
        String account = getHsAccount();
        Map<String, String> params = new HashMap<>();
        params.put("targetcomp_id", Constant.TARGETCOMP_ID);//目标机构编码
        params.put("sendercomp_id", Constant.SENDERCOMP_ID2);//发送机构编码
        params.put("profit_period", "1");//1代表当日收益率;2 周;3 历史合计;0  全部
        params.put("fund_account", account);//恒生的账号

        HscloudUtils.getInfoList(Constant.URL + Constant.USERINCOME, params, getPrivateTokenFromSp(), listener);


    }

    /**
     * 客户资金精确查询
     *
     * @param listener
     * @throws Exception
     */
    public void getUserMoneyInfo(final HscloudUtils.onGetListListener listener) throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("targetcomp_id", Constant.TARGETCOMP_ID);//目标机构编码
        params.put("sendercomp_id", Constant.SENDERCOMP_ID2);//发送机构编码
        params.put("money_type", "0");//0指人民币

        HscloudUtils.getInfoList(Constant.URL + Constant.USERMONEYINFO, params, getPrivateTokenFromSp(), listener);

    }

    /**
     * 获取恒生账号
     *
     * @param userId
     * @param listener
     */
    public void getUserHsAccount(String userId, HscloudUtils.OnGetHsAccount listener) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        HscloudUtils.getUserHsAccount(Constant.GET_HS_ACCOUNT, params, listener);
    }

    /**
     * 获取用户自己的Id
     *
     * @return
     */
    public String getUserId() {
        String userId = UserInfoSingleton.getUserId();
        return userId;
    }

    /**
     * 存储私有令牌到本地
     *
     * @param token
     */
    public void savePrivateTokenToSp(String token) {
        SharePreference.savePrivateTokenData(token);
    }

    /**
     * 从本地获取私有令牌
     *
     * @return
     */
    public String getPrivateTokenFromSp() {
        this.token = BaseApplication.spUserInfo.getString(SharePreference.PRIVATE_ACCTOKEN, null);
        Log.d(TAG, "getPrivateTokenFromSp: token = " + token);
        return token;
    }

    /**
     * 存储恒生账号密码
     *
     * @param account
     * @param pwd
     */
    public void saveHsAccountPwd(String account, String pwd) {
        SharePreference.saveHsAccountPwd(account, pwd);
    }

    /**
     * 获取恒生账号
     *
     * @return
     */
    public String getHsAccount() {
        String account = BaseApplication.spUserInfo.getString(SharePreference.HS_ACCOUNT, null);
        return account;
    }

    /**
     * 获取恒生密码
     *
     * @return
     */
    public String getHsPwd() {
        String pwd = BaseApplication.spUserInfo.getString(SharePreference.HS_PASSWORD, null);
        return pwd;
    }
}
