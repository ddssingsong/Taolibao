package com.jhs.taolibao.code.my.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.VersionInfo;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

/**
 * Created by dds on 2016/7/18.
 *
 * @TODO
 */
public class SoftUpdateModelImpl implements SoftUpdateModel {


    @Override
    public void getVersionInfo(final onGetVersionCodeListener listener) {
        String url = WebBiz.VERSION;
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                VersionInfo versionInfo;
                try {
                    versionInfo = JsonUtils.deserialize(response, VersionInfo.class);
                    //设置下载的apk名称
                    if (versionInfo.getApkname() != null && !versionInfo.getApkname().equals("")) {
                        WebBiz.APKNAME = versionInfo.getApkname();
                    }
                    listener.onSuccess(versionInfo);

                } catch (Exception e) {
                    listener.onFailure("网络数据解析错误", null);
                }

            }
        };
        OkHttpUtil.getAsyn(url, callback);
    }


    public interface onGetVersionCodeListener {

        void onSuccess(VersionInfo versionInfo);

        void onFailure(String msg, Exception e);
    }


    public interface DownLoadListener {

        void onSuccess(String path);

        void onFailure(String msg, Exception e);
    }

}
