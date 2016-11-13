package com.jhs.taolibao.code.simtrade.utils;

public class ChartUtils {
    /**
     * Prevent class instantiation.
     */
    private ChartUtils() {
    }

    public static String getVolUnit(float num) {

        int e = (int) Math.floor(Math.log10(num));

        if (e >= 8) {
            return "亿手";
        } else if (e >= 4) {
            return "万手";
        } else {
            return "手";
        }


    }
}
