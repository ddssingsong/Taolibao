package com.jhs.taolibao.code.simtrade.utils;

import android.util.Log;

import com.jhs.taolibao.code.simtrade.constant.Constant;
import com.jhs.taolibao.code.simtrade.entity.AccessToken;
import com.jhs.taolibao.code.simtrade.entity.KeepBean;
import com.jhs.taolibao.code.simtrade.entity.Token;
import com.jhs.taolibao.code.simtrade.entity.UserInfo;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 访问恒生后台工具类
 * Created by xujingbo on 2016/7/11.
 */
public class HscloudUtils {
    private static final String TAG = "HscloudUtils";

    /**
     * 编码格式.
     */
    public static final String CHARSET = "UTF-8";
    /**
     * HTTP HEADER字段 Authorization应填充字符串Bearer
     */
    public static final String BEARER = "Bearer ";

    /**
     * HTTP HEADER字段 Authorization应填充字符串BASIC
     */
    public static final String BASIC = "Basic ";
    /**
     * str 是对"App Key:App Secret"进行 Base64 编码后的字符串（区分大小写，包含冒号，但不包含双引号,采用
     * UTF-8 编码）。 例如: Authorization: Basic eHh4LUtleS14eHg6eHh4LXNlY3JldC14eHg=
     * 其中App Key和App Secret可在开放平台上创建应用后获取。
     */
    public static String Base64() throws UnsupportedEncodingException {
        String str = Constant.APP_KEY + ":" + Constant.APP_SECRET;
        byte[] encodeBase64 = Base64.encodeBase64(str
                .getBytes(CHARSET));

        return BASIC + new String(encodeBase64);
    }

    /**
     * 获取私有令牌
     */
    public static void getPrivateTokenFromHs(Map<String, String> tokenMap,final onGetListListener listener) throws Exception{

        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d(TAG, "onError: " + request.toString(), e);
            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);
                    if (json.isNull("error")){
                        listener.onSuccess(response);
//                        accessToken = accessToken.refreshFromJson(json);
//                        savaPrivateToken(accessToken);
                    }else{
                        Log.d(TAG, "PrivateToken onResponse: " + json.has("error"));
                    }

                } catch (Exception e) {

                    Log.d(TAG, "PrivateToken onResponse:网络数据解析错误 ", e);
                }
            }
        };

        OkHttpUtil.postHsAsyn(Constant.URL + "/oauth2/oauth2/oauthacct_trade_bind", callback, tokenMap, Base64());
    }

    /**
     * 将获取到的令牌存储到后台
     * @param url
     * @param tokenMap
     * @param listener
     */
    public static void savePrivateToken(String url,Map<String, String> tokenMap,final onGetListListener listener){
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>(){

            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(), e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getInt("code") == 0){
                        listener.onSuccess(response);
                    } else {
                        listener.onFailure("存储失败", null);
                    }

                } catch (Exception e) {

                    Log.d(TAG, "onResponse:网络数据解析错误 ", e);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, tokenMap);
    }

    /**
     * 从自己后台获取令牌
     * @param url
     * @param tokenMap
     * @param listener
     */
    public static void getPrivateToken(String url,Map<String, String> tokenMap,final OnTokenGetListener listener){
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>(){

            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(),e);
            }

            @Override
            public void onResponse(String response) {
                Token token = JsonUtils.deserialize(response, Token.class);
                if (token.getCode() == 0){
                    try {
                        listener.onSuccess(token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else {
                    listener.onFailure("token获取失败",null);
                }
            }
        };
        OkHttpUtil.postAsyn(url, callback,tokenMap);
    }
    /**
     * 获取公有令牌
     * 访问行情、咨询
     */
    public static void getPubicAccessToken(Map<String, String> tokenMap)throws Exception{

        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                AccessToken accessToken = new AccessToken();
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.isNull("error")){
                        accessToken = accessToken.refreshFromJson(json);
                        savaPublicToken(accessToken);
                    } else {
                        Log.d(TAG, "onResponse:  "+ json.has("error"));
                    }


                } catch (Exception e) {

                    Log.d(TAG, "onResponse:网络数据解析错误 ", e);
                }
            }
        };
        OkHttpUtil.postHsAsyn(Constant.OPEN_URL + "/oauth2/oauth2/token", callback, tokenMap, Base64());
    }
    /**
     * 获取信息列表
     * @param listener
     * @param params
     * @param accessToken 令牌
     * @throws Exception
     */
    public static void getInfoList(String url,Map<String, String> params,String accessToken,final onGetListListener listener) throws Exception{
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>(){

            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(),e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                   // Log.i("msg", json.toString());
                    listener.onSuccess(json.toString());

                } catch (JSONException e) {
                    listener.onFailure("网络数据解析错误", e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        OkHttpUtil.postHsAsyn(url, callback,params,BEARER + accessToken);
    }

    /**
     * 获取用户信息（恒生账号）
     * @param url
     * @param params
     * @param listener
     */
    public static void getUserHsAccount(String url,Map<String, String> params, final OnGetHsAccount listener){
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>(){

            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.toString(),e);
            }

            @Override
            public void onResponse(String response) {
                UserInfo userInfo = JsonUtils.deserialize(response, UserInfo.class);
                if (userInfo.getCode() == 0){
                    try {
                        listener.onSuccess(userInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else {
                    listener.onFailure("token获取失败",null);
                }
            }
        };
        OkHttpUtil.postAsyn(url, callback,params);
    }
    public interface onGetListListener{
        void onSuccess(String response) throws Exception;

        void onFailure(String msg, Exception e);
    }

    public interface onGetBeanListener{
        void onSuccess(KeepBean response);

        void onFailure(String msg, Exception e);
    }
    //token存储回调
    public interface OnTokenGetListener {
        void onSuccess(Token response) throws Exception;

        void onFailure(String msg, Exception e);
    }
    public interface OnGetHsAccount{
        void onSuccess(UserInfo response) throws Exception;

        void onFailure(String msg, Exception e);

    }
    /**
     * 存储公有token
     * @param accessToken
     */
    private static void savaPublicToken(AccessToken accessToken){
        Log.d(TAG, "savaPublicToken: ");
        String token = accessToken.getAccessToken();
        String refreshToken = accessToken.getRefreshToken();
        long expiresIn = Long.parseLong(accessToken.getExpiresIn());
        long curTime = DateUtils.getCurrentTime().getTime();
        SharePreference.savePublicTokenData(token, refreshToken, expiresIn, curTime);
    }


}
