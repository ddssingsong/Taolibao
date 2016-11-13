package com.jhs.taolibao.code.simtrade.utils;

import android.content.SharedPreferences;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.app.UserInfoSingleton;

/**
 * Created by KANGXIANGTAO on 2016/7/11.
 */
public class SharePreference {

    public static String PRIVATE_ACCTOKEN = "private_acctoken";


    public static String PUBLIC_ACCTOKEN = "public_acctoken";
    public static String PUBLIC_REFRESH_ACCTOKEN = "public_refrash_acctoken";
    public static String PUBLIC_EXPIRES_IN = "public_expires_in";
    public static String PUBLIC_GET_TOKEN_TIEM = "public_get_token_tiem";

    public static String HS_ACCOUNT = "hs_account"+ UserInfoSingleton.getUserInfo().getHsAccount();
    public static String HS_PASSWORD = "ha_password"+ UserInfoSingleton.getUserInfo().getHsPwd();

    public static void savePrivateTokenData(String token){
        SharedPreferences.Editor spUserInfoEditor = BaseApplication.spUserInfoEditor;
        spUserInfoEditor.putString(SharePreference.PRIVATE_ACCTOKEN,token).commit();

    }
    public static void savePublicTokenData(String token,String refreshToken,Long invalidTime,Long getTokenTime){
        SharedPreferences.Editor spUserInfoEditor = BaseApplication.spUserInfoEditor;
        spUserInfoEditor.putString(SharePreference.PUBLIC_ACCTOKEN,token).commit();
        spUserInfoEditor.putString(SharePreference.PUBLIC_REFRESH_ACCTOKEN,refreshToken).commit();
        spUserInfoEditor.putLong(SharePreference.PUBLIC_EXPIRES_IN, invalidTime).commit();
        spUserInfoEditor.putLong(SharePreference.PUBLIC_GET_TOKEN_TIEM, getTokenTime).commit();
    }

    /**
     * 存储恒生账号密码
     * @param account
     * @param pwd
     */
    public static void saveHsAccountPwd(String account,String pwd){
        SharedPreferences.Editor spUserInfoEditor = BaseApplication.spUserInfoEditor;
        spUserInfoEditor.putString(SharePreference.HS_ACCOUNT,account).commit();
        spUserInfoEditor.putString(SharePreference.HS_PASSWORD,pwd).commit();
    }
}
