package com.jhs.taolibao.code.news.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.Dynamic;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2016/6/14.
 *
 * @TODO
 */
public class DynamicModelImpl implements DynamicModel {

    @Override
    public void loadDynamic(final OnDynamicListener listener) {
        String url = WebBiz.SERVICE_DYNAMICPIC;
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onfailture("网络不给力", e);
            }

            @Override
            public void onResponse(String response) {
                List<Dynamic> list = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getInt("code") == WebBiz.SUCCESS) {
                        JSONObject object = json.getJSONObject("data");
                        JSONArray array = object.getJSONArray("DirectoryItems");
                        for (int i = 0; i < array.length(); i++) {
                            Dynamic dynamic = new Dynamic();
                            JSONObject object1 = (JSONObject) array.get(i);
                            dynamic.SetJSONObject(object1);
                            list.add(dynamic);
                        }
                        listener.onSuccess(list);
                    }

                } catch (Exception e) {
                    listener.onfailture("解析错误", e);
                }

            }

        };
        OkHttpUtil.getAsyn(url, callback);

    }

    public interface OnDynamicListener {
        void onSuccess(List<Dynamic> dynamics);

        void onfailture(String msg, Exception e);
    }
}
