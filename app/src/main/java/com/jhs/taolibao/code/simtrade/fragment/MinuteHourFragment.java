package com.jhs.taolibao.code.simtrade.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jhs.taolibao.view.charting.components.Legend;
import com.jhs.taolibao.view.charting.components.LimitLine;
import com.jhs.taolibao.view.charting.components.XAxis;
import com.jhs.taolibao.view.charting.components.YAxis;
import com.jhs.taolibao.view.charting.data.BarData;
import com.jhs.taolibao.view.charting.data.BarDataSet;
import com.jhs.taolibao.view.charting.data.BarEntry;
import com.jhs.taolibao.view.charting.data.Entry;
import com.jhs.taolibao.view.charting.data.LineData;
import com.jhs.taolibao.view.charting.data.LineDataSet;
import com.jhs.taolibao.view.charting.formatter.YAxisValueFormatter;
import com.jhs.taolibao.view.charting.highlight.Highlight;
import com.jhs.taolibao.view.charting.interfaces.datasets.ILineDataSet;
import com.jhs.taolibao.view.charting.listener.OnChartValueSelectedListener;
import com.jhs.taolibao.view.charting.utils.Utils;
import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.adapter.KeepAdapter;
import com.jhs.taolibao.code.simtrade.base.BaseFragment2;
import com.jhs.taolibao.code.simtrade.chart.MyBarChart;
import com.jhs.taolibao.code.simtrade.chart.MyLeftMarkerView;
import com.jhs.taolibao.code.simtrade.chart.MyLineChart;
import com.jhs.taolibao.code.simtrade.chart.MyRightMarkerView;
import com.jhs.taolibao.code.simtrade.chart.MyXAxis;
import com.jhs.taolibao.code.simtrade.chart.MyYAxis;
import com.jhs.taolibao.code.simtrade.constant.ConstantTest;
import com.jhs.taolibao.view.charts.DataParse;
import com.jhs.taolibao.code.simtrade.entity.MinutesBean;
import com.jhs.taolibao.code.simtrade.utils.ChartUtils;
import com.jhs.taolibao.code.simtrade.utils.VolFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiao on 2016/7/8 12:30
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:"分时"fragment
 */
public class MinuteHourFragment extends BaseFragment2 {
    //分时曲线
    private MyLineChart lineChart;
    private MyBarChart barChart;

    private ListView mLv;

    //分时折线部分
    //private Subscription subscriptionMinute;
    private LineDataSet d1, d2;
    MyXAxis xAxisLine;
    MyYAxis axisRightLine;
    MyYAxis axisLeftLine;
    BarDataSet barDataSet;

    MyXAxis xAxisBar;
    MyYAxis axisLeftBar;
    MyYAxis axisRightBar;
    SparseArray<String> stringSparseArray;
    private DataParse mData;
    private Integer sum = 0;
    List<Integer> listA, listB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_minute_hour, null);
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        initView();
        setChart();
        setListener();
        setListView();
    }

    /**
     * 配置listview
     */
    private void setListView() {
        KeepAdapter adapter = new KeepAdapter();
        mLv.setAdapter(adapter);
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                barChart.highlightValues(new Highlight[]{h});
                lineChart.setHighlightValue(h);
            }

            @Override
            public void onNothingSelected() {
            }
        });
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                //  barChart.highlightValues(new Highlight[]{h});
                lineChart.setHighlightValue(new Highlight(h.getXIndex(), 0));//此函数已经返回highlightBValues的变量，并且刷新，故上面方法可以注释
                // barChart.setHighlightValue(h);
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        mLv = (ListView) getView().findViewById(R.id.listview);
        lineChart = (MyLineChart) getView().findViewById(R.id.line_chart);
        barChart = (MyBarChart) getView().findViewById(R.id.bar_chart);
    }


    /**
     * 配置"分时"曲线
     */
    private void setChart() {
        initChart();
        stringSparseArray = setXLabels();

        /*网络数据*/
        //getMinutesData();
        /*离线测试数据*/
        getOffLineData();
    }

    @Override
    public void OnViewClick(View v) {

    }

    /********************************* "分时"逻辑 start*************************************/

    private Integer fund(Integer a, Integer b) {

        for (int i = a; i <= b; i++) {
            sum += listA.get(i);
        }
        return sum;
    }

    private void initChart() {
        lineChart.setScaleEnabled(false);
        lineChart.setDrawBorders(true);
        lineChart.setBorderWidth(1);
        lineChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        lineChart.setDescription("");
        Legend lineChartLegend = lineChart.getLegend();
        lineChartLegend.setEnabled(false);

        barChart.setScaleEnabled(false);
        barChart.setDrawBorders(true);
        barChart.setBorderWidth(1);
        barChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        barChart.setDescription("");


        Legend barChartLegend = barChart.getLegend();
        barChartLegend.setEnabled(false);
        //x轴
        xAxisLine = lineChart.getXAxis();
        xAxisLine.setDrawLabels(true);
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
        // xAxisLine.setLabelsToSkip(59);


        //左边y
        axisLeftLine = lineChart.getAxisLeft();
        /*折线图y轴左没有basevalue，调用系统的*/
        axisLeftLine.setLabelCount(5, true);
        axisLeftLine.setDrawLabels(true);
        axisLeftLine.setDrawGridLines(false);
        /*轴不显示 避免和border冲突*/
        axisLeftLine.setDrawAxisLine(false);


        //右边y
        axisRightLine = lineChart.getAxisRight();
        axisRightLine.setLabelCount(2, true);
        axisRightLine.setDrawLabels(true);
        axisRightLine.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00%");
                return mFormat.format(value);
            }
        });

        axisRightLine.setStartAtZero(false);
        axisRightLine.setDrawGridLines(false);
        axisRightLine.setDrawAxisLine(false);
        //背景线
        xAxisLine.setGridColor(getResources().getColor(R.color.minute_grayLine));
        xAxisLine.setAxisLineColor(getResources().getColor(R.color.minute_grayLine));
        xAxisLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftLine.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisRightLine.setAxisLineColor(getResources().getColor(R.color.minute_grayLine));
        axisRightLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));

        //bar x y轴
        xAxisBar = barChart.getXAxis();
        xAxisBar.setDrawLabels(false);
        xAxisBar.setDrawGridLines(true);
        xAxisBar.setDrawAxisLine(false);
        // xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisBar.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftBar = barChart.getAxisLeft();
        axisLeftBar.setAxisMinValue(0);
        axisLeftBar.setDrawGridLines(false);
        axisLeftBar.setDrawAxisLine(false);
        axisLeftBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));


        axisRightBar = barChart.getAxisRight();
        axisRightBar.setDrawLabels(false);
        axisRightBar.setDrawGridLines(false);
        axisRightBar.setDrawAxisLine(false);
        //y轴样式
        this.axisLeftLine.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00");
                return mFormat.format(value);
            }
        });

    }


    private void setData(DataParse mData) {
        setMarkerView(mData);
        setShowLabels(stringSparseArray);
        Log.e("###", mData.getDatas().size() + "ee");
        if (mData.getDatas().size() == 0) {
            lineChart.setNoDataText("暂无数据");
            return;
        }
        //设置y左右两轴最大最小值
        axisLeftLine.setAxisMinValue(mData.getMin());
        axisLeftLine.setAxisMaxValue(mData.getMax());
        axisRightLine.setAxisMinValue(mData.getPercentMin());
        axisRightLine.setAxisMaxValue(mData.getPercentMax());


        axisLeftBar.setAxisMaxValue(mData.getVolmax());
        /*单位*/
        String unit = ChartUtils.getVolUnit(mData.getVolmax());
        int u = 1;
        if ("万手".equals(unit)) {
            u = 4;
        } else if ("亿手".equals(unit)) {
            u = 8;
        }
        /*次方*/
        axisLeftBar.setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
        axisLeftBar.setShowMaxAndUnit(unit);
        axisLeftBar.setDrawLabels(true);
        //axisLeftBar.setAxisMinValue(0);//即使最小是不是0，也无碍
        axisLeftBar.setShowOnlyMinMax(true);
        axisRightBar.setAxisMaxValue(mData.getVolmax());
        //   axisRightBar.setAxisMinValue(mData.getVolmin);//即使最小是不是0，也无碍
        //axisRightBar.setShowOnlyMinMax(true);

        //基准线
        LimitLine ll = new LimitLine(0);
        ll.setLineWidth(1f);
        ll.setLineColor(getResources().getColor(R.color.minute_jizhun));
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setLineWidth(1);
        axisRightLine.addLimitLine(ll);
        axisRightLine.setBaseValue(0);

        ArrayList<Entry> lineCJEntries = new ArrayList<>();
        ArrayList<Entry> lineJJEntries = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        Log.e("##", Integer.toString(xVals.size()));
        for (int i = 0, j = 0; i < mData.getDatas().size(); i++, j++) {
           /* //避免数据重复，skip也能正常显示
            if (mData.getDatas().get(i).time.equals("13:30")) {
                continue;
            }*/
            MinutesBean t = mData.getDatas().get(j);

            if (t == null) {
                lineCJEntries.add(new Entry(Float.NaN, i));
                lineJJEntries.add(new Entry(Float.NaN, i));
                barEntries.add(new BarEntry(Float.NaN, i));
                continue;
            }
            if (!TextUtils.isEmpty(stringSparseArray.get(i)) &&
                    stringSparseArray.get(i).contains("/")) {
                i++;
            }
            lineCJEntries.add(new Entry(mData.getDatas().get(i).cjprice, i));
            lineJJEntries.add(new Entry(mData.getDatas().get(i).avprice, i));
            barEntries.add(new BarEntry(mData.getDatas().get(i).cjnum, i));
            // dateList.add(mData.getDatas().get(i).time);
        }
        d1 = new LineDataSet(lineCJEntries, "成交价");
        d2 = new LineDataSet(lineJJEntries, "均价");
        d1.setDrawValues(false);
        d2.setDrawValues(false);
        barDataSet = new BarDataSet(barEntries, "成交量");

        d1.setCircleRadius(0);
        d2.setCircleRadius(0);
        d1.setColor(getResources().getColor(R.color.minute_blue));
        d2.setColor(getResources().getColor(R.color.minute_yellow));
        d1.setHighLightColor(Color.WHITE);
        d2.setHighlightEnabled(false);
        d1.setDrawFilled(true);


        barDataSet.setBarSpacePercent(50); //bar空隙
        barDataSet.setHighLightColor(Color.WHITE);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setDrawValues(false);
        barDataSet.setHighlightEnabled(true);
        barDataSet.setColor(Color.RED);
        List<Integer> list = new ArrayList<>();
        list.add(Color.RED);
        list.add(Color.GREEN);
        barDataSet.setColors(list);
        //谁为基准
        d1.setAxisDependency(YAxis.AxisDependency.LEFT);
        // d2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        sets.add(d2);
        /*注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断*/
        LineData cd = new LineData(getMinutesCount(), sets);
        lineChart.setData(cd);
        BarData barData = new BarData(getMinutesCount(), barDataSet);
        barChart.setData(barData);

        setOffset();
        lineChart.invalidate();//刷新图
        barChart.invalidate();

    }

//    private void getMinutesData() {
//        String code = "sz002081";
//        subscriptionMinute = clientApi.getMinutes(code)
//                .compose(SchedulersCompat.<ResponseBody>applyIoSchedulers())
//                .subscribe(new Subscriber<ResponseBody>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        showToast("更新失败" + e.toString());
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody minutes) {
//
//                        mData = new DataParse();
//                        JSONObject object = null;
//                        try {
//                            object = new JSONObject(minutes.string());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        mData.parseMinutes(object);
//                        setData(mData);
//
//                    }
//                });
//
//        mCompositeSubscription.add(subscriptionMinute);
//    }

    private void getOffLineData() {
           /*方便测试，加入假数据*/
        mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.MINUTESURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.parseMinutes(object);
        setData(mData);
    }

    private SparseArray<String> setXLabels() {
        SparseArray<String> xLabels = new SparseArray<>();
        xLabels.put(0, "09:30");
        xLabels.put(60, "10:30");
        xLabels.put(121, "11:30/13:00");
        xLabels.put(182, "14:00");
        xLabels.put(241, "15:00");
        return xLabels;
    }


    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = lineChart.getViewPortHandler().offsetLeft();
        float barLeft = barChart.getViewPortHandler().offsetLeft();
        float lineRight = lineChart.getViewPortHandler().offsetRight();
        float barRight = barChart.getViewPortHandler().offsetRight();
        float barBottom = barChart.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
            //offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            // barChart.setExtraLeftOffset(offsetLeft);
            transLeft = lineLeft;

        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            lineChart.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }

  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
            //offsetRight = Utils.convertPixelsToDp(lineRight);
            //barChart.setExtraRightOffset(offsetRight);
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            lineChart.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        barChart.setViewPortOffsets(transLeft, 5, transRight, barBottom);
    }

    public void setShowLabels(SparseArray<String> labels) {
        xAxisLine.setXLabels(labels);
        xAxisBar.setXLabels(labels);
    }

    public String[] getMinutesCount() {
        return new String[242];
    }

    private void setMarkerView(DataParse mData) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(getActivity(), R.layout.mymarkerview);
        MyRightMarkerView rightMarkerView = new MyRightMarkerView(getActivity(), R.layout.mymarkerview);
        lineChart.setMarker(leftMarkerView, rightMarkerView, mData);
    }

    /********************************"分时"逻辑 end*************************************/
}
