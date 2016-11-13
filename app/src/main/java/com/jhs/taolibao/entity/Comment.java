package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dds on 2016/6/7.
 *
 * @TODO
 */
public class Comment extends BaseEntity {
    private int id;
    private int userId;
    private int newsId;
    private String Comment;
    private String TimeStr;
    private String CreateDate;
    private String Mobile;
    private String UserIcon;
    private String Alias;

    public void SetJSONObject(JSONObject json) throws JSONException {
        if (!json.isNull("ID")) {
            try {
                this.setId(json.getInt("ID"));
            } catch (Exception e) {
            }
        }
        if (!json.isNull("UserID")) {
            try {
                this.setUserId(json.getInt("UserID"));
            } catch (Exception e) {
            }
        }
        if (!json.isNull("InfoID")) {
            try {
                this.setNewsId(json.getInt("InfoID"));
            } catch (Exception e) {
            }
        }
        if (!json.isNull("Comment")) {
            this.setComment(json.getString("Comment"));
        }

        if (!json.isNull("TimeStr")) {
            this.setTimeStr(json.getString("TimeStr"));
        }
        if (!json.isNull("CreateDate")) {
            this.setCreateDate(json.getString("CreateDate"));
        }
        if (!json.isNull("Mobile")) {
            this.setMobile(json.getString("Mobile"));
        }
        if (!json.isNull("UserIcon")) {
            this.setUserIcon(json.getString("UserIcon"));
        }
        if (!json.isNull("Alias")) {
            this.setAlias(json.getString("Alias"));
        }


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getTimeStr() {
        return TimeStr;
    }

    public void setTimeStr(String timeStr) {
        TimeStr = timeStr;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getUserIcon() {
        return UserIcon;
    }

    public void setUserIcon(String userIcon) {
        UserIcon = userIcon;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }
}
