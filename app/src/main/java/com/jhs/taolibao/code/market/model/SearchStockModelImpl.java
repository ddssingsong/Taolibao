package com.jhs.taolibao.code.market.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.SearchStock;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public class SearchStockModelImpl implements SearchStockModel {


    @Override
    public void GetBinefStock(String keyword, final onGetBinefStockListener listener) {

        String url = WebBiz.GetBinefStock + "?keyWord=" + keyword;
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {

                List<SearchStock> indexList = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray array = json.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = (JSONObject) array.get(i);
                            SearchStock index = new SearchStock();
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

    public interface onGetBinefStockListener {
        void onSuccess(List<SearchStock> stockList);

        void onFailure(String msg, Exception e);
    }
}
