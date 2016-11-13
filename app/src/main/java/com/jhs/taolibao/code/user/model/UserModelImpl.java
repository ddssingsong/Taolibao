package com.jhs.taolibao.code.user.model;

import android.util.Log;

import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.UserInfo;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dds on 2016/6/1.
 *
 * @TODO
 */
public class UserModelImpl implements UserModel {
    //登陆
    @Override
    public void Login(String username, String password, final onLoginListener listener) {
        String url = WebBiz.SERVICE_LOGIN;
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", username);
        map.put("password", password);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络 error -1", e);
            }

            @Override
            public void onResponse(String response) {
                UserInfo userInfo = new UserInfo();
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getInt("code") == WebBiz.SUCCESS) {
                        userInfo.SetJSONObject(json.getJSONObject("user"));
                        listener.onSuccess(userInfo);
                    } else {
                        listener.onFailure("用户名或密码错误 error -2", null);
                    }

                } catch (Exception e) {
                    listener.onFailure("服务器数据解析错误 error -3", e);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);


    }

    //注册
    @Override
    public void Register(String username, String password, String code, final onRegisterListener listener) {
        String url = WebBiz.SERVICE_REGISTER;
        //String str = WebBiz.SERVICE_REGISTER + "?mobile=" + username + "&password=" + password + "&mobileCode=" + code + "IconName=";
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", username);
        map.put("password", password);
        map.put("mobileCode", code);
        map.put("IconName", "");
        map.put("alias", "u_" + username.substring(8));

        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                UserInfo userInfo = new UserInfo();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        listener.onSuccess();
                    } else if (code == 1) {
                        listener.onHaveRegister("该手机已经注册过了");
                    } else if (code == 2) {
                        listener.onCodeError();
                    } else {
                        listener.onFailure("服务器错误code=?", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);


    }

    //获取验证码
    @Override
    public void getCode(boolean isRegist, String mobile, final onGetCodeListener listener) {
        String url = WebBiz.SERVICE_MOBILECODE;
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        if (isRegist) {
            map.put("checkDuplicate", "1");
        }
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        listener.onSuccess();
                    } else if (code == 1) {
                        listener.onHaveRegister("该手机号已经注册过了，不能重复注册！");
                    } else {
                        listener.onFailure("服务器错误！", null);
                    }
                } catch (JSONException e) {
                    listener.onFailure("服务器数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }


    //修改密码
    @Override
    public void ModifyPwd(String oldpwd, String newpwd, final onModifyListener listener) {
        String url = WebBiz.SERVICE_CHANGEPWD;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserInfoSingleton.getUserId());
        map.put("oldpwd", oldpwd);
        map.put("newpwd", newpwd);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        listener.onSuccess();
                    } else if (code == 1) {
                        listener.onFailure("旧密码不正确！", null);
                    } else {
                        listener.onFailure("服务器不稳定，请稍后重试", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", e);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);


    }

    //找回密码
    @Override
    public void FindPwd(String username, String newpwd, String code, final onFindPwdListener listener) {
        String url = WebBiz.SERVICE_FINDPWD;
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", username);
        map.put("password", newpwd);
        map.put("mobileCode", code);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                UserInfo userInfo = new UserInfo();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        listener.onSuccess();
                    }
                    if (code == 1) {
                        listener.onFailure("手机号不存在！", null);
                    }
                    if (code == 2) {
                        listener.onCodeError();
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);

    }

    //获取用户信息
    @Override
    public void GetuserInfo(String userid, final onGetUserInfoListener listener) {
        String url = WebBiz.SERVICE_USERINFO;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userid);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                UserInfo userInfo = new UserInfo();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        boolean payuser = json.getBoolean("payUser");
                        userInfo.SetJSONObject(json.getJSONObject("user"));
                        userInfo.setPayuser(payuser);
                        listener.onSuccess(userInfo);
                    } else {
                        listener.onFailure("error -1 getUserinfoFailed", null);
                    }

                } catch (Exception e) {
                    listener.onFailure("服务器数据解析错误", e);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);

    }

    //修改用户信息
    @Override
    public void updateUserInfo(String userJson, final onUpdateUserListener listener) {

        String url = WebBiz.SERVICE_UPDATEUSER;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userJson", userJson);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    Log.i("msg", json.toString());
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        listener.onSuccess();
                    } else {
                        listener.onFailure("服务器错误！", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", e);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);

    }


    public interface onLoginListener {
        void onSuccess(UserInfo userinfo);

        void onFailure(String msg, Exception e);
    }

    public interface onRegisterListener {
        void onSuccess();

        void onHaveRegister(String msg);

        void onCodeError();

        void onFailure(String msg, Exception e);
    }

    public interface onGetCodeListener {
        void onSuccess();

        void onHaveRegister(String msg);

        void onFailure(String msg, Exception e);
    }

    public interface onModifyListener {
        void onSuccess();

        void onFailure(String msg, Exception e);
    }

    public interface onFindPwdListener {
        void onSuccess();

        void onCodeError();

        void onFailure(String msg, Exception e);
    }

    public interface onGetUserInfoListener {
        void onSuccess(UserInfo userinfo);

        void onFailure(String msg, Exception e);
    }


    public interface onUpdateUserListener {
        void onSuccess();

        void onFailure(String msg, Exception e);
    }

}
