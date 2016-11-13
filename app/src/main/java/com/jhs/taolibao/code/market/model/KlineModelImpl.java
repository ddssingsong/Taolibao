package com.jhs.taolibao.code.market.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.view.charts.DataParse;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dds on 2016/7/19.
 *
 * @TODO
 */
public class KlineModelImpl implements KlineModel {
    @Override
    public void getKlineData(String Type, String Stockid, String Start, String end, final ongetKlineDataListener listener) {
        String url = WebBiz.GetKline;
        Map<String, String> map = new HashMap<String, String>();
       // map.put("Type", Type);
        map.put("StockID", Stockid);
//        map.put("Start", Start);
//        map.put("End", end);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                DataParse mData = new DataParse();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray array = json.getJSONArray("data");
                        mData.parseKLine1(array);
                        listener.onSuccess(mData);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", e);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    public interface ongetKlineDataListener {
        void onSuccess(DataParse mData);

        void onFailure(String msg, Exception e);
    }
}
