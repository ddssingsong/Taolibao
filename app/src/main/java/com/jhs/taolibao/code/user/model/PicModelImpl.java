package com.jhs.taolibao.code.user.model;

import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

/**
 * Created by dds on 2016/6/29.
 *
 * @TODO
 */
public class PicModelImpl implements PicModel {


    @Override
    public void uploadimage(String photo, final onPicListener listener) {
        String url = WebBiz.SERVICE_PHOTOUPLOAD;
        String userid = UserInfoSingleton.getUserId();
        String phoo=photo;
        OkHttpUtil.Param param = new OkHttpUtil.Param("userID", userid);
        OkHttpUtil.Param param1 = new OkHttpUtil.Param("imgStearm", photo);
        OkHttpUtil.Param[] params = {param, param1};

        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                String str = response;
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == WebBiz.SUCCESS) {
                        listener.onSuccess();
                    } else if (code == 1) {
                        listener.onFailure("上传图片失败", null);
                    } else {
                        listener.onFailure("服务器不稳定，请稍后重试", null);
                    }
                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", e);
                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, params);

    }

    public interface onPicListener {
        void onSuccess();

        void onFailure(String msg, Exception e);
    }

}
