package com.jhs.taolibao.code.my.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.Jingu;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dds on 2016/7/27.
 *
 * @TODO
 */
public class GoldModelImpl implements GoldModel {


    @Override
    public void GetrecommendStock(String Time, final GetrecommendStockListener listener) {
        String url = WebBiz.GetrecommendStock;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("Time", Time);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {

                try {

                    Jingu jingu = JsonUtils.deserialize(response, Jingu.class);
                    if (jingu.getCode() == 0) {
                        listener.onSuccess(jingu);
                    } else {
                        listener.onFailure("今日还未发布金股", null);
                    }

                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    @Override
    public void PayRemStockDate(String userID, final PayRemStockDateListener listener) {
        String url = WebBiz.PayRemStockDate;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("UserID", userID);
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
                    if (code == 0) {
                        listener.onSuccess();
                    } else {
                        listener.onFailure("服务器出现异常", null);
                    }

                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    public interface GetrecommendStockListener {

        void onSuccess(Jingu jingu);

        void onFailure(String msg, Exception e);
    }

    public interface PayRemStockDateListener {

        void onSuccess();

        void onFailure(String msg, Exception e);
    }
}
