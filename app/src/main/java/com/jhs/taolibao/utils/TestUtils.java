package com.jhs.taolibao.utils;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by dds on 2016/8/23.
 *
 * @TODO
 */
public class TestUtils {


    String str = "[" +
            "  145959350," +
            "  100," +
            "  \"兴发集团\"," +
            "  \"13.15,8830350,0,13.14,12300,0,13.12,2400,0,13.11,100,0,13.10,5200,0,\"," +
            "  \"0.00,0,0,0.00,0,0,0.00,0,0,0.00,0,0,0.00,0,0,\"," +
            "  12.14," +
            "  11.95," +
            "  13.15," +
            "  12.01," +
            "  13.15," +
            "  319918409," +
            "  24968419," +
            "  1.2," +
            "  10.04," +
            "  12927412," +
            "  12041007," +
            "  100.84," +
            "  5.2" +
            "]";


    public static void getResult(String str) {
        try {
            JSONArray array = new JSONArray(str);
            int str1 = array.optInt(0);
            int str2 = array.optInt(1);
            String str3 = array.optString(2);
            String str4 = array.optString(3);
            String[] dd = str4.split(",");
            for (int i = 0; i < dd.length; i++) {
                double dd1 = Double.parseDouble(dd[0]);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
