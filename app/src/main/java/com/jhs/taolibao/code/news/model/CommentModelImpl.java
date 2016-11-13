package com.jhs.taolibao.code.news.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.Comment;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2016/6/7.
 *
 * @TODO
 */
public class CommentModelImpl implements CommentModel {


    @Override
    public void loadComment(int newsId, int page, final OnCommentListener listener) {
        String url = WebBiz.SERVICE_COMMENTLIST + "?infoid=" + newsId + "&page=" + page + "&pagesize=15";
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onfailture("网络不给力", e);
            }

            @Override
            public void onResponse(String response) {
                String str = response;
                List<Comment> list = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getInt("code") == WebBiz.SUCCESS) {
                        JSONObject object = json.getJSONObject("data");
                        JSONArray array = object.getJSONArray("DirectoryResults");
                        for (int i = 0; i < array.length(); i++) {
                            Comment comment = new Comment();
                            JSONObject object1 = (JSONObject) array.get(i);
                            comment.SetJSONObject(object1);
                            list.add(comment);
                        }
                        int totalcount=object.getInt("TotalCount");
                        listener.onSuccess(list,totalcount);

                    } else {
                        //没有评论
                        listener.onSuccess(list,0);
                    }
                } catch (Exception e) {
                    listener.onfailture("服务器数据解析错误", e);
                }
            }

        };
        OkHttpUtil.getAsyn(url, callback);


    }

    @Override
    public void insertComment(int userId, int newsId, String comment, final OnInsertCommentListener listener) {
        String url = WebBiz.SERVICE_INSERT + "?infoid=" + newsId + "&userid=" + userId + "&Comment=" + comment;
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onfailture("网络不给力", e);
            }

            @Override
            public void onResponse(String response) {
                String str=response;
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getInt("code") == WebBiz.SUCCESS) {
                        listener.onSuccess();
                    } else {
                        listener.onfailture("服务器错误", null);
                    }
                } catch (Exception e) {
                    listener.onfailture("服务器数据解析错误", e);
                }

            }

        };
        OkHttpUtil.getAsyn(url, callback);
    }

    public interface OnCommentListener {
        void onSuccess(List<Comment> commentList,int totalcount);

        void onfailture(String msg, Exception e);
    }

    public interface OnInsertCommentListener {
        void onSuccess();

        void onfailture(String msg, Exception e);
    }
}
