package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dds on 2016/6/7.
 *
 * @TODO
 */
public class News extends BaseEntity {
    private int id;
    private String title;
    private String infoContent;//新闻内容
    private int ccount;//评论条数
    private int displaytype;//展示样式 1表示文字，2是图文混排，3是多图
    private String TitleIMG1;
    private String TitleIMG2;
    private String TitleIMG3;
    private String dec;//新闻简要描述
    private String publictime;//发布日期
    private String TypeName;
    private int Bull;


    public void SetJSONObject(JSONObject json) throws JSONException {
        if (!json.isNull("ID")) {
            try {
                this.setId(json.getInt("ID"));
            } catch (Exception e) {
            }
        }
        if (!json.isNull("Title")) {
            this.setTitle(json.getString("Title"));
        }

        if (!json.isNull("InfoContent")) {
            this.setInfoContent(json.getString("InfoContent"));
        }
        if (!json.isNull("TotalComments")) {
            try {
                this.setCcount(json.getInt("TotalComments"));
            } catch (Exception e) {
            }
        }
        if (!json.isNull("ViewType")) {
            try {
                this.setDisplaytype(json.getInt("ViewType"));
            } catch (Exception e) {
            }
        }
        if (!json.isNull("TitleIMG1")) {
            this.setTitleIMG1(json.getString("TitleIMG1"));
        }
        if (!json.isNull("TitleIMG2")) {
            this.setTitleIMG2(json.getString("TitleIMG2"));
        }
        if (!json.isNull("TitleIMG3")) {
            this.setTitleIMG3(json.getString("TitleIMG3"));
        }
        if (!json.isNull("Dec")) {
            this.setDec(json.getString("Dec"));
        }
        if (!json.isNull("CreateDate")) {
            this.setPublictime(json.getString("CreateDate"));
        }
        if (!json.isNull("TypeName")) {
            this.setTypeName(json.getString("TypeName"));
        }

        if (!json.isNull("Bull")) {
            try {
                this.setBull(json.getInt("Bull"));
            } catch (Exception e) {
            }
        }


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getPublictime() {
        return publictime;
    }

    public void setPublictime(String publictime) {
        this.publictime = publictime;
    }

    public int getDisplaytype() {
        return displaytype;
    }

    public void setDisplaytype(int displaytype) {
        this.displaytype = displaytype;
    }


    public int getCcount() {
        return ccount;
    }

    public void setCcount(int ccount) {
        this.ccount = ccount;
    }


    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    public String getTitleIMG1() {
        return TitleIMG1;
    }

    public void setTitleIMG1(String titleIMG1) {
        TitleIMG1 = titleIMG1;
    }

    public String getTitleIMG2() {
        return TitleIMG2;
    }

    public void setTitleIMG2(String titleIMG2) {
        TitleIMG2 = titleIMG2;
    }

    public String getTitleIMG3() {
        return TitleIMG3;
    }

    public void setTitleIMG3(String titleIMG3) {
        TitleIMG3 = titleIMG3;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public int getBull() {
        return Bull;
    }

    public void setBull(int bull) {
        Bull = bull;
    }
}
