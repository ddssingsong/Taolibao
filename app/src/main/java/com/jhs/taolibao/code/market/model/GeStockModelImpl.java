package com.jhs.taolibao.code.market.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.GeStock;
import com.jhs.taolibao.entity.Level5;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public class GeStockModelImpl implements GeStockModel {


    @Override
    public void GetStcokInfo(String stockId, final onGetStcokInfoListener listener) {
        String url = WebBiz.GetStcokInfo;
        Map<String, String> map = new HashMap<String, String>();
        map.put("StockID", stockId);

        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                GeStock geStock = new GeStock();
                List<Level5> buylist = new ArrayList<>();
                List<Level5> salelist = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONObject object = json.getJSONObject("data");
                        geStock.SetJSONObject(object);

                        JSONArray array1 = object.getJSONArray("buyList");
                        for (int i = 0; i < array1.length(); i++) {
                            JSONObject jsonObject = (JSONObject) array1.get(i);
                            Level5 level5 = new Level5();
                            level5.SetJSONObject(jsonObject);
                            buylist.add(level5);
                        }

                        JSONArray array2 = object.getJSONArray("saleList");
                        for (int i = 0; i < array2.length(); i++) {
                            JSONObject jsonObject = (JSONObject) array2.get(i);
                            Level5 level5 = new Level5();
                            level5.SetJSONObject(jsonObject);
                            salelist.add(level5);
                        }
                        listener.onSuccess(geStock, buylist, salelist);

                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    @Override
    public void InsetOptional(String userid, String stockId, final onInsetOptionalListener listener) {

        String url = WebBiz.InsetOptional;
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserID", userid);
        map.put("stockCode", stockId);

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
                        int optionId=json.getInt("OptionalID");
                        listener.onSuccess(optionId);

                    }else if(code==2){
                        listener.onFailure("该股票已在您的自选列表", null);
                    }else{
                        listener.onFailure("null", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    @Override
    public void DelOptional(String OptionalId, final int position, final onDelOptionalListener listener) {
        String url = WebBiz.DelOptional;
        Map<String, String> map = new HashMap<String, String>();
        map.put("optinalID", OptionalId);

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
                        listener.onSuccess(position);

                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);

    }

    @Override
    public void isSelect(String userid, String stockId, final onisSelectListener listener) {
        String url = WebBiz.CheckOptional;
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserID", userid);
        map.put("stockCode", stockId);

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
                        String OptionalID = json.getString("OptionalID");
                        listener.onSuccess(true, OptionalID);

                    } else {
                        listener.onSuccess(false, "");
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);

    }

    public interface onGetStcokInfoListener {
        void onSuccess(GeStock geStock, List<Level5> buylist, List<Level5> salelist);

        void onFailure(String msg, Exception e);
    }

    public interface onInsetOptionalListener {
        void onSuccess(int optionId);

        void onFailure(String msg, Exception e);
    }

    public interface onDelOptionalListener {
        void onSuccess(int position);

        void onFailure(String msg, Exception e);
    }

    public interface onisSelectListener {
        void onSuccess(boolean isSelect, String optionid);

        void onFailure(String msg, Exception e);
    }
}
