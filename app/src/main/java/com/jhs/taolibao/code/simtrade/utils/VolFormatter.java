package com.jhs.taolibao.code.simtrade.utils;



import com.jhs.taolibao.view.charting.components.YAxis;
import com.jhs.taolibao.view.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

public class VolFormatter implements YAxisValueFormatter {

    private final int unit;
    private DecimalFormat mFormat;
    private String u;
    public VolFormatter(int unit) {
        if (unit == 1) {
            mFormat = new DecimalFormat("#0");
        } else {
            mFormat = new DecimalFormat("#0.00");
        }
        this.unit = unit;
        this.u=ChartUtils.getVolUnit(unit);
    }


    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        value = value / unit;
        if(value==0){
            return u;
        }
        return mFormat.format(value);
    }
}
