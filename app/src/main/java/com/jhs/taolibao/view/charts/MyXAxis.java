package com.jhs.taolibao.view.charts;

import android.util.SparseArray;

import com.jhs.taolibao.view.charting.components.XAxis;

public class MyXAxis extends XAxis {
    private SparseArray<String> labels;
    public SparseArray<String> getXLabels() {
        return labels;
    }
    public void setXLabels(SparseArray<String> labels) {
        this.labels = labels;
    }
}
