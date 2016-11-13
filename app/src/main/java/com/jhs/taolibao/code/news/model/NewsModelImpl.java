package com.jhs.taolibao.code.news.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.News;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dds on 2016/6/7.
 *
 * @TODO
 */
public class NewsModelImpl implements NewsModel {

    @Override
    public void loadNews(String newsurl, int page, int infoType, String time, final OnLoadNewsListListener listener) {
        String url = WebBiz.SERVICE_NEWSLIST;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("page", Integer.toString(page));
        map.put("infoType", Integer.toString(infoType));
        map.put("pagesize", Integer.toString(15));
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络不给力", e);
            }

            @Override
            public void onResponse(String response) {
                String str = response;
                List<News> newslist = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        JSONObject object = json.getJSONObject("data");
                        JSONArray array = object.getJSONArray("DirectoryResults");
                        for (int i = 0; i < array.length(); i++) {
                            News news = new News();
                            JSONObject object1 = (JSONObject) array.get(i);
                            news.SetJSONObject(object1);
                            newslist.add(news);
                        }
                        listener.onSuccess(newslist);
                    } else if (code == 1) {
                        listener.onFailure("暂无数据", null);
                    } else {
                        listener.onFailure("服务器异常", null);
                    }

                } catch (Exception e) {
                    listener.onFailure("解析错误 news-1", e);
                }
            }
        };
        OkHttpUtil.postAsyn(url, callback, map);


    }

    public interface OnLoadNewsListListener {
        void onSuccess(List<News> list);

        void onFailure(String msg, Exception e);
    }
}
