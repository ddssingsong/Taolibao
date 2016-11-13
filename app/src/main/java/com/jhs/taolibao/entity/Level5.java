package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dds on 2016/7/6.
 *
 * @TODO
 */
public class Level5 implements Serializable {
    private String Quantity;
    private String Price;

    public void SetJSONObject(JSONObject json) throws JSONException {
        if (!json.isNull("Quantity")) {
            this.setQuantity(json.getString("Quantity"));
        }

        if (!json.isNull("Price")) {
            this.setPrice(json.getString("Price"));
        }

    }
    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
