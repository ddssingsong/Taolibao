package com.jhs.taolibao.code.market.widget;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhs.taolibao.view.charting.charts.BarChart;
import com.jhs.taolibao.view.charting.charts.Chart;
import com.jhs.taolibao.view.charting.charts.CombinedChart;
import com.jhs.taolibao.view.charting.components.Legend;
import com.jhs.taolibao.view.charting.components.XAxis;
import com.jhs.taolibao.view.charting.components.YAxis;
import com.jhs.taolibao.view.charting.data.BarData;
import com.jhs.taolibao.view.charting.data.BarDataSet;
import com.jhs.taolibao.view.charting.data.BarEntry;
import com.jhs.taolibao.view.charting.data.CandleData;
import com.jhs.taolibao.view.charting.data.CandleDataSet;
import com.jhs.taolibao.view.charting.data.CandleEntry;
import com.jhs.taolibao.view.charting.data.CombinedData;
import com.jhs.taolibao.view.charting.data.Entry;
import com.jhs.taolibao.view.charting.data.LineData;
import com.jhs.taolibao.view.charting.data.LineDataSet;
import com.jhs.taolibao.view.charting.highlight.Highlight;
import com.jhs.taolibao.view.charting.interfaces.datasets.ILineDataSet;
import com.jhs.taolibao.view.charting.listener.BarLineChartTouchListener;
import com.jhs.taolibao.view.charting.listener.OnChartValueSelectedListener;
import com.jhs.taolibao.view.charting.utils.Utils;
import com.jhs.taolibao.view.charting.utils.ViewPortHandler;
import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.market.prestener.TuPresenterImpl;
import com.jhs.taolibao.code.market.view.KlineView;
import com.jhs.taolibao.code.simtrade.entity.KLineBean;
import com.jhs.taolibao.code.simtrade.utils.ChartUtils;
import com.jhs.taolibao.code.simtrade.utils.VolFormatter;
import com.jhs.taolibao.utils.CalendarUtil;
import com.jhs.taolibao.view.charts.CoupleChartGestureListener;
import com.jhs.taolibao.view.charts.DataParse;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dds on 2016/7/7.
 *
 * @TODO 日K图
 */
public class KlineFragment extends BaseFragment implements KlineView {

    private TuPresenterImpl tuPresenter;
    private CombinedChart combinedchart;
    private BarChart barChart;
    private DataParse mData;
    private ArrayList<KLineBean> kLineDatas;
    private String stockId;
    XAxis xAxisBar, xAxisK;
    YAxis axisLeftBar, axisLeftK;
    YAxis axisRightBar, axisRightK;
    BarDataSet barDataSet;
    private BarLineChartTouchListener mChartTouchListener;
    private CoupleChartGestureListener coupleChartGestureListener;
    float sum = 0;


    private boolean flag;//是否是指数
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            barChart.setAutoScaleMinMaxEnabled(true);
            combinedchart.setAutoScaleMinMaxEnabled(true);
            combinedchart.notifyDataSetChanged();
            barChart.notifyDataSetChanged();
            combinedchart.invalidate();
            barChart.invalidate();
        }
    };


    public void setStockid(String stockid) {
        if (flag) {
            this.stockId = stockid;
        } else {
//            if (stockid.contains("sh") || stockid.contains("sz")) {
//                stockid = stockid.substring(2);
//            }
            this.stockId = stockid;
        }

    }

    //如果是从指数进去的就把五档数据隐藏
    public void IsIndex() {
        flag = true;
    }

    @Override
    protected boolean onBackPressed() {
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tuPresenter = new TuPresenterImpl(this);
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kline, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        combinedchart = (CombinedChart) rootView.findViewById(R.id.combinedchart);
        barChart = (BarChart) rootView.findViewById(R.id.barchart);
        initChart();
    }

    private void initChart() {
        barChart.setDrawBorders(true);
        barChart.setBorderWidth(1);
        barChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        barChart.setDescription("成交量");
        barChart.setDragEnabled(true);
        barChart.setScaleYEnabled(false);

        Legend barChartLegend = barChart.getLegend();
        barChartLegend.setEnabled(false);

        //BarYAxisFormatter  barYAxisFormatter=new BarYAxisFormatter();
        //bar x y轴
        xAxisBar = barChart.getXAxis();
        xAxisBar.setDrawLabels(true);
        xAxisBar.setDrawGridLines(false);
        xAxisBar.setDrawAxisLine(false);
        xAxisBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisBar.setGridColor(getResources().getColor(R.color.minute_grayLine));

        axisLeftBar = barChart.getAxisLeft();
        axisLeftBar.setAxisMinValue(0);
        axisLeftBar.setDrawGridLines(false);
        axisLeftBar.setDrawAxisLine(false);
        axisLeftBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftBar.setDrawLabels(true);
        axisLeftBar.setSpaceTop(0);
        axisLeftBar.setShowOnlyMinMax(true);
        axisRightBar = barChart.getAxisRight();
        axisRightBar.setDrawLabels(false);
        axisRightBar.setDrawGridLines(false);
        axisRightBar.setDrawAxisLine(false);
        /****************************************************************/
        combinedchart.setDrawBorders(true);
        combinedchart.setBorderWidth(1);
        combinedchart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        combinedchart.setDescription("K线图");
        combinedchart.setDragEnabled(true);
        combinedchart.setScaleYEnabled(false);

        Legend combinedchartLegend = combinedchart.getLegend();
        combinedchartLegend.setEnabled(false);
        //bar x y轴
        xAxisK = combinedchart.getXAxis();
        xAxisK.setDrawLabels(true);
        xAxisK.setDrawGridLines(false);
        xAxisK.setDrawAxisLine(false);
        xAxisK.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        xAxisK.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisK.setGridColor(getResources().getColor(R.color.minute_grayLine));

        axisLeftK = combinedchart.getAxisLeft();
        axisLeftK.setDrawGridLines(true);
        axisLeftK.setDrawAxisLine(false);
        axisLeftK.setDrawLabels(true);
        axisLeftK.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftK.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftK.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisRightK = combinedchart.getAxisRight();
        axisRightK.setDrawLabels(false);
        axisRightK.setDrawGridLines(true);
        axisRightK.setDrawAxisLine(false);
        axisRightK.setGridColor(getResources().getColor(R.color.minute_grayLine));
        combinedchart.setDragDecelerationEnabled(true);
        barChart.setDragDecelerationEnabled(true);
        combinedchart.setDragDecelerationFrictionCoef(0.2f);
        barChart.setDragDecelerationFrictionCoef(0.2f);


        // 将K线控的滑动事件传递给交易量控件
        combinedchart.setOnChartGestureListener(new com.jhs.taolibao.code.simtrade.chart.CoupleChartGestureListener(combinedchart, new Chart[]{barChart}));
        // 将交易量控件的滑动事件传递给K线控件
        barChart.setOnChartGestureListener(new com.jhs.taolibao.code.simtrade.chart.CoupleChartGestureListener(barChart, new Chart[]{combinedchart}));
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.e("%%%%", h.getXIndex() + "");
                combinedchart.highlightValues(new Highlight[]{h});
            }

            @Override
            public void onNothingSelected() {
                combinedchart.highlightValue(null);
            }
        });
        combinedchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                barChart.highlightValues(new Highlight[]{h});
            }

            @Override
            public void onNothingSelected() {
                barChart.highlightValue(null);
            }
        });


    }

    @Override
    public void onInitloadData() {
        tuPresenter.getKlineDate("day", stockId, CalendarUtil.convertDateToString4(CalendarUtil.getDateBefore(new Date(), 365)), CalendarUtil.getCurrentDate("yyyyMMdd"));
    }

    @Override
    public void getKlineSuccess(DataParse mData) {
        this.mData = mData;
        setData(mData);

    }

    private void setData(DataParse mData) {
        kLineDatas = mData.getKLineDatas();
        // axisLeftBar.setAxisMaxValue(mData.getVolmax());
        String unit = ChartUtils.getVolUnit(mData.getVolmax());
        int u = 1;
        if ("万手".equals(unit)) {
            u = 4;
        } else if ("亿手".equals(unit)) {
            u = 8;
        }
        axisLeftBar.setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
        // axisRightBar.setAxisMaxValue(mData.getVolmax());
        // Log.e("@@@", mData.getVolmax() + "da");

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<CandleEntry> candleEntries = new ArrayList<>();
        ArrayList<Entry> line5Entries = new ArrayList<>();
        ArrayList<Entry> line10Entries = new ArrayList<>();
        ArrayList<Entry> line30Entries = new ArrayList<>();
        for (int i = 0, j = 0; i < mData.getKLineDatas().size(); i++, j++) {
            xVals.add(mData.getKLineDatas().get(i).date + "");
            barEntries.add(new BarEntry(mData.getKLineDatas().get(i).vol, i));
            candleEntries.add(new CandleEntry(i, mData.getKLineDatas().get(i).high, mData.getKLineDatas().get(i).low, mData.getKLineDatas().get(i).open, mData.getKLineDatas().get(i).close));
            if (i >= 4) {
                sum = 0;
                line5Entries.add(new Entry(getSum(i - 4, i) / 5, i));
            }
            if (i >= 9) {
                sum = 0;
                line10Entries.add(new Entry(getSum(i - 9, i) / 10, i));
            }
            if (i >= 29) {
                sum = 0;
                line30Entries.add(new Entry(getSum(i - 29, i) / 30, i));
            }

        }
        barDataSet = new BarDataSet(barEntries, "成交量");
        barDataSet.setBarSpacePercent(50); //bar空隙
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setHighLightColor(Color.WHITE);
        barDataSet.setDrawValues(false);

        //设置柱状线的颜色
        barDataSet.setColor(getResources().getColor(R.color.Red));
//        List<Integer> list = new ArrayList<>();
//        list.add(getResources().getColor(R.color.Red));//跌
//        list.add(getResources().getColor(R.color.Olive));
//        barDataSet.setColors(list);


        BarData barData = new BarData(xVals, barDataSet);
        barChart.setData(barData);
        final ViewPortHandler viewPortHandlerBar = barChart.getViewPortHandler();
        viewPortHandlerBar.setMaximumScaleX(culcMaxscale(xVals.size()));
        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
        final float xscale = 3;
        touchmatrix.postScale(xscale, 1f);


        CandleDataSet candleDataSet = new CandleDataSet(candleEntries, "KLine");
        candleDataSet.setDrawHorizontalHighlightIndicator(false);
        candleDataSet.setHighlightEnabled(true);
        candleDataSet.setHighLightColor(Color.WHITE);
        candleDataSet.setValueTextSize(10f);
        candleDataSet.setDrawValues(false);
        //设置k线图的颜色
        candleDataSet.setColor(getResources().getColor(R.color.Red));
//        List<Integer> list1 = new ArrayList<>();
//        list1.add(getResources().getColor(R.color.Red));//跌
//        list1.add(getResources().getColor(R.color.Olive));
//        candleDataSet.setColors(list1);


        candleDataSet.setShadowWidth(1f);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        CandleData candleData = new CandleData(xVals, candleDataSet);


        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setMaLine(5, xVals, line5Entries));
        sets.add(setMaLine(10, xVals, line10Entries));
        sets.add(setMaLine(30, xVals, line30Entries));


        CombinedData combinedData = new CombinedData(xVals);
        LineData lineData = new LineData(xVals, sets);
        combinedData.setData(candleData);
        combinedData.setData(lineData);
        combinedchart.setData(combinedData);
        combinedchart.moveViewToX(mData.getKLineDatas().size() - 1);
        final ViewPortHandler viewPortHandlerCombin = combinedchart.getViewPortHandler();
        viewPortHandlerCombin.setMaximumScaleX(culcMaxscale(xVals.size()));
        Matrix matrixCombin = viewPortHandlerCombin.getMatrixTouch();
        final float xscaleCombin = 3;
        matrixCombin.postScale(xscaleCombin, 1f);

        combinedchart.moveViewToX(mData.getKLineDatas().size() - 1);
        barChart.moveViewToX(mData.getKLineDatas().size() - 1);
        setOffset();

/****************************************************************************************
 此处解决方法来源于CombinedChartDemo，k线图y轴显示问题，图表滑动后才能对齐的bug，希望有人给出解决方法
 (注：此bug现已修复，感谢和chenguang79一起研究)
 ****************************************************************************************/

        combinedchart.invalidate();
        barChart.invalidate();
        handler.sendEmptyMessageDelayed(0, 300);

    }

    private float getSum(Integer a, Integer b) {

        for (int i = a; i <= b; i++) {
            sum += mData.getKLineDatas().get(i).close;
        }
        return sum;
    }

    private float culcMaxscale(float count) {
        float max = 1;
        max = count / 127 * 5;
        return max;
    }

    @NonNull
    private LineDataSet setMaLine(int ma, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        if (ma == 5) {
            lineDataSetMa.setHighlightEnabled(true);
            lineDataSetMa.setDrawHorizontalHighlightIndicator(false);
            lineDataSetMa.setHighLightColor(Color.WHITE);
        } else {/*此处必须得写*/
            lineDataSetMa.setHighlightEnabled(false);
        }
        lineDataSetMa.setDrawValues(false);
        if (ma == 5) {
            lineDataSetMa.setColor(getResources().getColor(R.color.Olive));
        } else if (ma == 10) {
            lineDataSetMa.setColor(Color.GRAY);
        } else {
            lineDataSetMa.setColor(getResources().getColor(R.color.Yellow));
        }
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        return lineDataSetMa;
    }

    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = combinedchart.getViewPortHandler().offsetLeft();
        float barLeft = barChart.getViewPortHandler().offsetLeft();
        float lineRight = combinedchart.getViewPortHandler().offsetRight();
        float barRight = barChart.getViewPortHandler().offsetRight();
        float barBottom = barChart.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
           /* offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            barChart.setExtraLeftOffset(offsetLeft);*/
            transLeft = lineLeft;
        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            combinedchart.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }
  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
          /*  offsetRight = Utils.convertPixelsToDp(lineRight);
            barChart.setExtraRightOffset(offsetRight);*/
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            combinedchart.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        barChart.setViewPortOffsets(transLeft, 15, transRight, barBottom);
    }

    /****************************K线逻辑 end****************************************/
}
