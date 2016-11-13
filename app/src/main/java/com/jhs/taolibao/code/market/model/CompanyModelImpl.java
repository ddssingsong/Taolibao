package com.jhs.taolibao.code.market.model;

import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.entity.CompanyResponse;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dds on 2016/7/13.
 *
 * @TODO
 */
public class CompanyModelImpl implements CompanyModel {


    @Override
    public void GetTockCompany(String StockCode, final onGetTockCompanyListener listener) {
        String url = WebBiz.GetTockCompany;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("StockCode", StockCode);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure("网络状况不佳，请检查网络", e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    CompanyResponse companyResponse = JsonUtils.deserialize(response, CompanyResponse.class);
                    if (companyResponse.getCode() == 0) {
                        listener.onSuccess(companyResponse);
                    } else {
                        listener.onFailure("无法连接到服务器", null);
                    }

                } catch (Exception e) {

                }

            }
        };
        OkHttpUtil.postAsyn(url, callback, map);
    }

    public interface onGetTockCompanyListener {
        void onSuccess(CompanyResponse response);

        void onFailure(String msg, Exception e);
    }
}
