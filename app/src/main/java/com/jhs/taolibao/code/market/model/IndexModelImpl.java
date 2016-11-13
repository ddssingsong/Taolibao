package com.jhs.taolibao.code.market.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.Index;
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
public class IndexModelImpl implements IndexModel {


    //获取顶部指数
    @Override
    public void GetIndexView(final onGetIndexViewListener listener) {
        String url = WebBiz.GetIndexView;
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络 error_70006", e);
            }

            @Override
            public void onResponse(String response) {

                List<Index> indexList = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONArray array = json.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = (JSONObject) array.get(i);
                            Index index = new Index();
                            index.SetJSONObject(object);
                            indexList.add(index);
                        }
                        listener.onSuccess(indexList);

                    } else {
                        listener.onFailure("服务器不稳定，请稍后重试 error_70006", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", e);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback);

    }

    public interface onGetIndexViewListener {
        void onSuccess(List<Index> indexlist);

        void onFailure(String msg, Exception e);
    }

}
