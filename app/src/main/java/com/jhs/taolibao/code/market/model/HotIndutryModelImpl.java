package com.jhs.taolibao.code.market.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.HotIndustry;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2016/7/4.
 *
 * @TODO
 */
public class HotIndutryModelImpl implements HotIndutryModel {


    @Override
    public void getHotIndutry(int page, int pagesize, final onGetHotIndustryListener listener) {

        String url = WebBiz.GetHotIndustry + "?page=" + page + "&size=" + pagesize;

        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
               // listener.onFailure("网络状况不佳，请检查网络 error 70005", e);
            }

            @Override
            public void onResponse(String response) {

                List<HotIndustry> indexList = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray array = json.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = (JSONObject) array.get(i);
                            HotIndustry index = new HotIndustry();
                            index.SetJSONObject(object);
                            indexList.add(index);
                        }
                        listener.onSuccess(indexList);

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

    public interface onGetHotIndustryListener {
        void onSuccess(List<HotIndustry> hotIndustries);

        void onFailure(String msg, Exception e);
    }
}
