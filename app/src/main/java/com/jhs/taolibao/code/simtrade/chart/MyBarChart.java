package com.jhs.taolibao.code.simtrade.chart;

import android.content.Context;
import android.util.AttributeSet;

import com.jhs.taolibao.view.charting.charts.BarChart;
import com.jhs.taolibao.view.charting.components.YAxis;
import com.jhs.taolibao.view.charting.highlight.Highlight;

public class MyBarChart extends BarChart {
    public MyBarChart(Context context) {
        super(context);
    }

    public MyBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        /*此处不能重新示例*/
        mXAxis = new MyXAxis();

        mAxisLeft = new MyYAxis(YAxis.AxisDependency.LEFT);
        mAxisRendererLeft = new MyYAxisRenderer(mViewPortHandler, (MyYAxis) mAxisLeft, mLeftAxisTransformer);
        mXAxisRenderer = new MyXAxisRenderer(mViewPortHandler, (MyXAxis) mXAxis, mLeftAxisTransformer,this);
        mAxisRight = new MyYAxis(YAxis.AxisDependency.RIGHT);
        mAxisRendererRight = new MyYAxisRenderer(mViewPortHandler, (MyYAxis) mAxisRight, mRightAxisTransformer);

    }
    @Override
    protected void calcModulus() {

        mXAxis.mAxisLabelModulus = 1;
    }
    /*返回转型后的左右轴*/
    @Override
    public MyYAxis getAxisLeft() {
        return (MyYAxis) super.getAxisLeft();
    }

    @Override
    public MyXAxis getXAxis() {
        return (MyXAxis)super.getXAxis();
    }


    @Override
    public MyYAxis getAxisRight() {
        return (MyYAxis) super.getAxisRight();
    }

    public void setHighlightValue(Highlight h){
        mIndicesToHighlight = new Highlight[]{
                h};
    }
}
