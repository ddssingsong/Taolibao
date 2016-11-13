package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dds on 2016/7/1.
 *
 * @TODO
 */
public class Index implements Serializable {
    private String IndexName;//指数名称
    private String IndexCode;//指数代码
    private String IndexValue;//最新指数
    private String RatioValue;//涨跌额
    private String RatioRatio;//涨跌幅

    public void SetJSONObject(JSONObject json) throws JSONException {

        if (!json.isNull("IndexName")) {
            this.setIndexName(json.getString("IndexName"));
        }

        if (!json.isNull("IndexCode")) {
            this.setIndexCode(json.getString("IndexCode"));
        }
        if (!json.isNull("IndexValue")) {
            this.setIndexValue(json.getString("IndexValue"));
        }
        if (!json.isNull("RatioValue")) {
            this.setRatioValue(json.getString("RatioValue"));
        }
        if (!json.isNull("RatioRatio")) {
            this.setRatioRatio(json.getString("RatioRatio"));
        }

    }

    public String getIndexValue() {
        return IndexValue;
    }

    public void setIndexValue(String indexValue) {
        IndexValue = indexValue;
    }

    public String getIndexName() {
        return IndexName;
    }

    public void setIndexName(String indexName) {
        IndexName = indexName;
    }

    public String getIndexCode() {
        return IndexCode;
    }

    public void setIndexCode(String indexCode) {
        IndexCode = indexCode;
    }

    public String getRatioValue() {
        return RatioValue;
    }

    public void setRatioValue(String ratioValue) {
        RatioValue = ratioValue;
    }

    public String getRatioRatio() {
        return RatioRatio;
    }

    public void setRatioRatio(String ratioRatio) {
        RatioRatio = ratioRatio;
    }


}
