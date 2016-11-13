package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public class SearchStock implements Serializable {

    private String StockCode;
    private String Code;
    private String Spell;
    private String Name;

    public void SetJSONObject(JSONObject json) throws JSONException {

        if (!json.isNull("StockCode")) {
            this.setStockCode(json.getString("StockCode"));
        }

        if (!json.isNull("Code")) {
            this.setCode(json.getString("Code"));
        }
        if (!json.isNull("Spell")) {
            this.setSpell(json.getString("Spell"));
        }
        if (!json.isNull("Name")) {
            this.setName(json.getString("Name"));
        }

    }


    public String getStockCode() {
        return StockCode;
    }

    public void setStockCode(String stockCode) {
        StockCode = stockCode;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getSpell() {
        return Spell;
    }

    public void setSpell(String spell) {
        Spell = spell;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
