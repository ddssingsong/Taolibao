package com.jhs.taolibao.code.market.model;

import android.os.Looper;
import android.util.Log;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.app.WebData;
import com.jhs.taolibao.entity.Fund;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dds on 2016/7/9.
 */
public class FundModelImpl implements FundModel {


    //分级A
    @Override
    public void getFundList(String StockID, String scn, String doa, String op, final onGetFundAListListener listener) {
        String url = WebData.Fuzaiurl;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("StockID", StockID);
        map.put("scn", scn);
        map.put("doa", doa);
        map.put("op", op);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {

                if(Looper.myLooper() == Looper.getMainLooper()){
                    Log.i("Thread","在主线线程中");
                }else{
                    Log.i("Thread","在子线线程中");
                }
                List<Fund> list = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("Code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray itemArray = json.getJSONArray("Data");
                        for (int i = 0; i < itemArray.length(); i++) {
                            JSONObject jsonObject = itemArray.getJSONObject(i);
                            Fund fund = new Fund();
                            fund.setJsonObject(jsonObject);
                            list.add(fund);
                        }
                        listener.onSuccess(list);

                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);

    }


    //套利宝
    @Override
    public void getTaoliList(String StockID, String op, final onGetFundAListListener listener) {
        String url = WebData.Fuzaiurl;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("StockID", StockID);
        map.put("op", op);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                List<Fund> list = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("Code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray itemArray = json.getJSONArray("Data");
                        for (int i = 0; i < itemArray.length(); i++) {
                            JSONObject jsonObject = itemArray.getJSONObject(i);
                            Fund fund = new Fund();
                            fund.setJsonObject(jsonObject);
                            list.add(fund);
                        }
                        listener.onSuccess(list);

                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    @Override
    public void getTaoliDetail(String op, String stockid, final onGetTaoliDetailListener listener) {
        String url = WebData.Fuzaiurl;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("op", op);
        map.put("StockID", stockid);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                Fund fund = new Fund();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("Code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray itemArray = json.getJSONArray("Data");
                        JSONObject jsonObject = itemArray.getJSONObject(0);
                        fund.setJsonObject(jsonObject);
                        listener.onSuccess(fund);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }


    public interface onGetFundAListListener {
        void onSuccess(List<Fund> list);

        void onFailure(String msg, Exception e);
    }

    public interface onGetTaoliDetailListener {
        void onSuccess(Fund fund);

        void onFailure(String msg, Exception e);
    }


}
