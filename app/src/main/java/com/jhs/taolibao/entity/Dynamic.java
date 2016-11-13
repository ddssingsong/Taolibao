package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dds on 2016/6/14.
 *
 * @TODO
 */
public class Dynamic extends BaseEntity {
    private int picId;
    private String picurl;//图片链接
    private String content;//图片描述
    private int action;//跳转事件
    private String ActionUrl;//点击图片链接
    private String TimeStr;



    public void SetJSONObject(JSONObject json) throws JSONException {
        if (!json.isNull("ID")) {
            try {
                this.setPicId(json.getInt("ID"));
            } catch (Exception e) {
            }
        }
        if (!json.isNull("Picurl")) {
            this.setPicurl(json.getString("Picurl"));
        }
        if (!json.isNull("Action")) {
            try {
                this.setAction(json.getInt("Action"));
            } catch (Exception e) {
            }
        }
        if (!json.isNull("Notes")) {
            this.setContent(json.getString("Notes"));
        }
        if (!json.isNull("ActionUrl")) {
            this.setActionUrl(json.getString("ActionUrl"));
        }
        if (!json.isNull("TimeStr")) {
            this.setTimeStr(json.getString("TimeStr"));
        }

    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }


    public String getActionUrl() {
        return ActionUrl;
    }

    public void setActionUrl(String actionUrl) {
        ActionUrl = actionUrl;
    }

    public String getTimeStr() {
        return TimeStr;
    }

    public void setTimeStr(String timeStr) {
        TimeStr = timeStr;
    }
}
