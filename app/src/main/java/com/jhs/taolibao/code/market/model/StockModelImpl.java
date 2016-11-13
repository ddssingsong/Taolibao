package com.jhs.taolibao.code.market.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.MyStock;
import com.jhs.taolibao.entity.Stock;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dds on 2016/7/5.
 *
 * @TODO
 */
public class StockModelImpl implements StockModel {

    @Override
    public void GetRankingList(final onGetRankingListListener listener) {
        String url = WebBiz.GetRankingList+"?dataCount=8";
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络,70001", e);
            }

            @Override
            public void onResponse(String response) {

                List<Stock> riseList = new ArrayList<>();
                List<Stock> fallList = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONObject jsonObject = json.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("increaseList");
                        JSONArray array1 = jsonObject.getJSONArray("dropList");
                        if (array != null && array1 != null) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = (JSONObject) array.get(i);
                                Stock index = new Stock();
                                index.SetJSONObject(object);
                                riseList.add(index);
                            }

                            for (int i = 0; i < array1.length(); i++) {
                                JSONObject object = (JSONObject) array1.get(i);
                                Stock index = new Stock();
                                index.SetJSONObject(object);
                                fallList.add(index);

                            }
                            listener.onSuccess(riseList, fallList);
                        }else{
                            listener.onFailure("", null);
                        }


                    } else {
                        listener.onFailure("服务器不稳定，请稍后重试", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", e);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback);
    }

    @Override
    public void GetStockByIndustry(int page, int size, String order, String sortby, String inustrycode, final onGetStockByIndustryListener listener) {
        String url = WebBiz.GetStockByIndustry;
        Map<String, String> map = new HashMap<String, String>();
        map.put("size", Integer.toString(size));
        map.put("order", order);
        map.put("industryCode", inustrycode);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络 error 70002", e);
            }

            @Override
            public void onResponse(String response) {
                List<Stock> stockList = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray array = json.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = (JSONObject) array.get(i);
                            Stock index = new Stock();
                            index.SetJSONObject(object);
                            stockList.add(index);
                        }
                        listener.onSuccess(stockList);

                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);

    }

    @Override
    public void GetRankListByType(int type, final onGetRankListByTypeListener listener) {
        String url = WebBiz.GetRankListByType;
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", Integer.toString(type));

        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络 error 70003", e);
            }

            @Override
            public void onResponse(String response) {
                List<Stock> stockList = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray array = json.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = (JSONObject) array.get(i);
                            Stock index = new Stock();
                            index.SetJSONObject(object);
                            stockList.add(index);
                        }
                        listener.onSuccess(stockList);

                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);

    }

    //获取自选股列表
    @Override
    public void GetOptionalByUser(String userid, final onGetOptionalByUserListener listener) {
        String url = WebBiz.GetOptionalByUser;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userID", userid);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络 error 70004", e);
            }

            @Override
            public void onResponse(String response) {
                List<MyStock> stockList = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray array = json.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = (JSONObject) array.get(i);
                            MyStock index = new MyStock();
                            index.SetJSONObject(object);
                            stockList.add(index);
                        }
                        listener.onSuccess(stockList);

                    } else {
                        listener.onFailure("null", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }


    public interface onGetRankingListListener {
        void onSuccess(List<Stock> riseList, List<Stock> fallList);

        void onFailure(String msg, Exception e);
    }

    public interface onGetStockByIndustryListener {
        void onSuccess(List<Stock> stockList);

        void onFailure(String msg, Exception e);
    }

    public interface onGetRankListByTypeListener {
        void onSuccess(List<Stock> stockList);

        void onFailure(String msg, Exception e);
    }

    public interface onGetOptionalByUserListener {
        void onSuccess(List<MyStock> stockList);

        void onFailure(String msg, Exception e);
    }
}
