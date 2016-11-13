package com.jhs.taolibao.utils;

/**
 * Created by dds on 2016/7/20.
 *
 * @TODO
 */
public class MathUtil {

    public static String get(double temp) {
        if (temp > 100000000) {
            return String.format("%.2f", temp / 100000000) + "亿";
        } else {
            return String.format("%.2f", temp / 10000) + "万";
        }

    }

}
