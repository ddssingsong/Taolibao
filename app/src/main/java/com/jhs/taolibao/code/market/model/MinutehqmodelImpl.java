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
 * Created by dds on 2016/7/8.
 *
 * @TODO
 */
public class MinutehqmodelImpl implements Minutehqmodel {


    //获取分时数据并进行转化
    @Override
    public void GetMinutehqData(String StockID, final onGetMinutehqDataListener listener) {
        String url = WebBiz.GetMinutehqData;
        Map<String, String> map = new HashMap<String, String>();
        map.put("StockID", StockID);
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
                        //解析数据
                        JSONArray array = json.getJSONArray("data");
                        mData.parseMinutes1(array);


                        listener.onSuccess(mData);

                    } else {
                        listener.onFailure("服务器不稳定，请稍后重试,time-1", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", e);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    public interface onGetMinutehqDataListener {
        void onSuccess(DataParse mData);

        void onFailure(String msg, Exception e);
    }
}
