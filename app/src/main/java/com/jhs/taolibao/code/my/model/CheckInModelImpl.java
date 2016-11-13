package com.jhs.taolibao.code.my.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.CheckInInfo;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dds on 2016/7/25.
 *
 * @TODO
 */
public class CheckInModelImpl implements CheckInModel {


    //签到
    @Override
    public void CheckIn(String userId, final onCheckInListener listener) {
        String url = WebBiz.LuckDraw;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("userID", userId);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {

                try {
                    CheckInInfo checkInInfo = JsonUtils.deserialize(response, CheckInInfo.class);
                    listener.onSuccess(checkInInfo);


                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    public interface onCheckInListener {
        void onSuccess(CheckInInfo checkInInfo);

        void onFailure(String msg, Exception e);
    }
}
