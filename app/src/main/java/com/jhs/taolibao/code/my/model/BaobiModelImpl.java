package com.jhs.taolibao.code.my.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.Order;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dds on 2016/7/15.
 *
 * @TODO
 */
public class BaobiModelImpl implements BaobiModel {

    @Override
    public void Pay(String userID, int amount, int payType, int bankID, String notes, final onPayListener listener) {
        String url = WebBiz.Pay;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("userID", userID);
        map.put("amount", Integer.toString(amount));
        map.put("payType", Integer.toString(payType));//1是苹果，2 安卓
        map.put("bankID", Integer.toString(bankID)); //不同的支付渠道 0 银联 1 汇付
        map.put("notes", notes);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                Order order = null;
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONObject object = json.getJSONObject("data");
                        order = JsonUtils.deserialize(object, Order.class);
                        listener.onSuccess(order);
                    } else {
                        listener.onFailure("生成订单错误，1", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);

    }

    @Override
    public void getOrderWaterTn(String orderID, String txnTime, int amount, final onGetOrderWaterTnListener listener) {
        String url = WebBiz.GetUnionpayTN;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("orderID", orderID);
        map.put("txnTime", txnTime);
        map.put("amount", Integer.toString(amount));
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
                        String tn = json.getString("UnionpayTN");
                        if (!tn.equals("")) {
                            listener.onSuccess(tn);
                        }else{
                            listener.onFailure("获取流水单号错误，-1", null);
                        }
                    } else {
                        listener.onFailure("生成订单错误，2", null);
                    }


                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    @Override
    public void notifyAddBaobi(String id, final onotifyAddBaobiListener listener) {
        String url = WebBiz.UpPayState;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("PayID", id);
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
                    } else {
                        listener.onFailure("未通知到后台，，3", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    //兑换京东卡
    @Override
    public void exchangeJingdong(String userid, int pointType, int amount, int month, final onExchangeJingdongListener listener) {
        String url = WebBiz.PointChange;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("userId", userid);
        map.put("pointType", Integer.toString(pointType));
        map.put("amount", Integer.toString(amount));
        map.put("months", Integer.toString(month));
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    listener.onSuccess();
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }


    public interface onGetOrderWaterTnListener {

        void onSuccess(String tn);

        void onFailure(String msg, Exception e);
    }

    public interface onPayListener {

        void onSuccess(Order order);

        void onFailure(String msg, Exception e);
    }
    public interface onotifyAddBaobiListener {

        void onSuccess();

        void onFailure(String msg, Exception e);
    }


    public interface onExchangeJingdongListener {

        void onSuccess();

        void onFailure(String msg, Exception e);
    }
}
